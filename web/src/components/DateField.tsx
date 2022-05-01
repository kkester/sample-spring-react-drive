import { useState } from "react";
import { SchemaProperty } from "../api/DriveApi";
import { isReadOnly, isReadOnlyView, ResourceAttribute } from "../api/ResourceDataApi";

const DateField = (props: {
    id: string | number;
    attribute: ResourceAttribute;
    dataChangeHandler: (name: string, value: any) => void;
}) => {
    const [value, setValue] = useState<any>(props.attribute.value);

    const handleChange = (event: React.FormEvent<HTMLInputElement>) => {
        setValue(event.currentTarget.value);
        props.dataChangeHandler(props.attribute.name, event.currentTarget.value);
    }

    const id = props.attribute.name + props.id;
    const schemaProperty: SchemaProperty = props.attribute.schemaProperty;
    const readOnly: boolean = isReadOnly(props.attribute);
    const readOnlyView: boolean = isReadOnlyView(props.attribute);
    const title: string = schemaProperty.title ? schemaProperty.title : props.attribute.name;

    const labelId = id + '-label';
    const inputId = id + '-input';
    return (
        <div key={id} id={id} className="Component-date">
            <div key={id} id={id} className={props.attribute.hasError ? "Component-date-error" : "Component-date"}>
                <label key={labelId} id={labelId} className="Component-date-label">
                    {title}:
                </label><br />
                {readOnlyView ?
                    <label id={labelId} className="Component-text-input-view">
                        {value}
                    </label> :
                    <input type="date"
                        key={inputId}
                        id={inputId}
                        className={readOnly ? "Component-readonly-date-input" : "Component-date-input"}
                        value={value}
                        readOnly={readOnly}
                        onChange={handleChange}
                    />}
            </div>
        </div>
    );
}

export { DateField };