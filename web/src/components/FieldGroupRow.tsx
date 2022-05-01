import { Link, SchemaProperty } from "../api/DriveApi";
import { ResourceAttribute } from "../api/ResourceDataApi";
import { DateField } from "./DateField";
import { OptionsBox } from "./OptionsBox";
import { TableLayout } from "./TableLayout";
import { TextField } from "./TextField";

export const FieldGroupRow = (props: {
    name?: string;
    title?: string;
    data?: {
        [name: string]: any;
    };
    attributes: ResourceAttribute[];
    dataChangeHandler: (name: string, value: any) => void;
    clickHandler: (link: Link) => void;
}) => {
    const handleDataChange = (attributeName: string, attributeValue: any) => {
        if (props.data && props.name) {
            const updatedData = { ...props.data, [attributeName]: attributeValue };
            props.dataChangeHandler(props.name, updatedData);
        } else {
            props.dataChangeHandler(attributeName, attributeValue);
        }
    }

    const mapAttribute = (index: number, attribute: ResourceAttribute) => {
        if (attribute.schemaProperty === undefined) {
            return <></>;
        }

        const schemaProperty: SchemaProperty = attribute.schemaProperty;
        const key = attribute.name + index;
        if (schemaProperty.format && schemaProperty.format === 'date') {
            return <DateField key={key} id={index} attribute={attribute} dataChangeHandler={handleDataChange} />;
        } else if (schemaProperty.enum && schemaProperty.enum.length > 0) {
            return <OptionsBox key={key} id={index} attribute={attribute} dataChangeHandler={handleDataChange} />;
        } else if (schemaProperty.type === 'array') {
            return <TableLayout key={key}
                id={index}
                attribute={attribute}
                clickHandler={props.clickHandler}
                dataChangeHandler={handleDataChange} />;
        } else if (['string', 'integer', 'number'].includes(schemaProperty.type)) {
            return <TextField key={key} id={index} attribute={attribute} dataChangeHandler={handleDataChange} />;
        }
        return <></>;
    };

    const components: React.ReactNode[] = props.attributes.map((attribute, i) => (
        mapAttribute(i, attribute)
    ));

    if (components.length < 2) {
        return (<>{components}</>);
    } else if (props.title === undefined) {
        return <div className="Compontent-field-row">
            {components}
        </div>
    }
    return (
        <div className="Compontent-field-row-section">
            {props.title && <label className="Compontent-field-section-label">{props.title}</label>}
            <div className="Compontent-field-section-row">
                {components}
            </div>
        </div>
    );
}