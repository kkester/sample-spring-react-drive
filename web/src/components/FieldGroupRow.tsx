import { Link, SchemaProperty } from "../api/DriveApi";
import { ResourceAttribute } from "../api/ResourceDataApi";
import { DateField } from "./DateField";
import { OptionsBox } from "./OptionsBox";
import { TableLayout } from "./TableLayout";
import { TextField } from "./TextField";

export const FieldGroupRow = (props: {
    attributes: ResourceAttribute[];
    dataChangeHandler: (name: string, value: any) => void;
    clickHandler: (link: Link) => void;
    name?: string;
    data?: {
        [name: string]: any;
    };
}) => {

    const handleDataChange = (attributeName: string, attributeValue: any) => {
        if (props.data && props.name) {
            const updatedData = {...props.data, [attributeName]: attributeValue}; 
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
            return <DateField key={key} id={index} attribute={attribute} dataChangeHandler={handleDataChange}/>;
        } else if (schemaProperty.enum && schemaProperty.enum.length > 0) {
            return <OptionsBox key={key} id={index} attribute={attribute} dataChangeHandler={handleDataChange}/>;
        } else if (schemaProperty.type === 'array') {
            return <TableLayout key={key} 
                id={index} 
                attribute={attribute}
                clickHandler={props.clickHandler} 
                dataChangeHandler={handleDataChange}/>;
        } else {
            return <TextField key={key} id={index} attribute={attribute} dataChangeHandler={handleDataChange}/>;
        }
    };

    const components: React.ReactNode[] = props.attributes.map((attribute, i) => (
        mapAttribute(i, attribute)
    ));

    if (components.length > 1) {
        return (
            <div className="Compontent-field-row">
                {components}
            </div>  
        );
    } else {
        return (<>{components}</>);
    }
}