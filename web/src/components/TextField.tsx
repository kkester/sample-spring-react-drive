import { useState } from "react";
import { SchemaProperty } from "../api/DriveApi";
import { isReadOnly, isReadOnlyView, ResourceAttribute } from "../api/ResourceDataApi";

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
    
    const attribute: ResourceAttribute = props.attribute;
    const schemaProperty: SchemaProperty = attribute.schemaProperty;
    const title: string = schemaProperty.title ? schemaProperty.title : attribute.name;
    const readOnly: boolean = isReadOnly(attribute);
    const readOnlyView: boolean = isReadOnlyView(attribute);

    const required: boolean = !readOnly && props.attribute.required;
    const id = props.attribute.name + props.id;
    const labelId = id + '-label';
    const inputId = id + '-input';
    return (
        <div id={id} className="Component-text">
            <div id={id} className={props.attribute.hasError ? "Component-text-error" : "Component-text"}>
                <label id={labelId} className="Component-text-label">
                    {title}{required && ' *'}:
                </label><br />
                {readOnlyView ? 
                    <label id={labelId} className="Component-text-input-view">
                        {value}
                    </label> :
                    <input type="text"
                        id={inputId}
                        value={value}
                        readOnly={readOnly}
                        className={readOnly ? "Component-readonly-text-input" : "Component-text-input"}
                        onChange={handleChange}
                    />}
            </div>
        </div>
    );
}

export { TextField };