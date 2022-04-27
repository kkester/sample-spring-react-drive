import { useState } from "react";
import { SchemaProperty } from "../api/DriveApi";
import { ResourceAttribute } from "../api/ResourceDataApi";

const OptionsBox = (props: {
    id: string | number;
    attribute: ResourceAttribute;
    dataChangeHandler: (name: string, value: any) => void;
}) => {
    const [value, setValue] = useState<any>(props.attribute.value);

    const handleChange = (event: React.FormEvent<HTMLSelectElement>) => {
        setValue(event.currentTarget.nodeValue);
        props.dataChangeHandler(props.attribute.name, event.currentTarget.value);
    }

    const schemaProperty: SchemaProperty = props.attribute.schemaProperty;
    const options: string[] = schemaProperty.enum ? schemaProperty.enum : [];

    const id = props.attribute.name + props.id;
    const labelId = id + '-label';
    const inputId = id + '-input';
    return (
        <div id={id} className="Component-options">
            <label id={labelId} className="Component-options-label">
                {schemaProperty.title}{props.attribute.required && ' *'}:
            </label><br />
            <select id={inputId} className="Component-options-input" onChange={handleChange} value={value}>
                <option value=""> -- Select --</option>
                {options.map(option => (
                    <option key={option} value={option}>
                        {option}
                    </option>
                ))}
            </select>
        </div>
    );
}

export { OptionsBox };