import { useState } from "react";
import { Schema, SchemaProperty } from "../api/DriveApi";
import { ResourceAttribute } from "../api/ResourceDataApi";

const TextField = (props: {
    id: string | number;
    attribute: ResourceAttribute;
    dataChangeHandler: (name: string, value: any) => void;
}) => {
    const [value, setValue] = useState<any>(props.attribute.value);

    const handleChange = (event: React.FormEvent<HTMLInputElement>) => {
        setValue(event.currentTarget.value);
        props.dataChangeHandler(props.attribute.name, event.currentTarget.value);
    }

    const schema: Schema = props.attribute.schema;
    const schemaProperty: SchemaProperty = props.attribute.schemaProperty;
    const readOnly: boolean = (schemaProperty.readOnly ? true : false) || (schema.readOnly ? true : false);
    const required: boolean = !readOnly && props.attribute.required;
    const id = props.attribute.name + props.id;
    const labelId = id + '-label';
    const inputId = id + '-input';
    return (
        <div id={id} className="Component-text">
            <div id={id} className={props.attribute.hasError ? "Component-text-error" : "Component-text"}>
                <label id={labelId} className="Component-text-label">
                    {schemaProperty.title}{required && ' *'}:
                </label><br />
                <input type="text"
                    id={inputId}
                    value={value}
                    readOnly={readOnly}
                    className={readOnly ? "Component-readonly-text-input" : "Component-text-input"}
                    onChange={handleChange}
                />
            </div>
        </div>
    );
}

export { TextField };