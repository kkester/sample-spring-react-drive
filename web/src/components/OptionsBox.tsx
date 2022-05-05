import { useState } from "react";
import { SchemaProperty } from "../api/DriveApi";
import { ResourceAttribute } from "../api/ResourceFunctions";

export const OptionsBox = (props: {
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
    const title: string = schemaProperty.title ? schemaProperty.title : props.attribute.name;

    return (
        <div data-testid={props.attribute.name+props.id} className="Component-options" >
            <label className="Component-options-label">
                {title}:
            </label>
            {props.attribute.required &&<label className="Component-options-required-label"> *</label>}
            <br />
            <div className={props.attribute.hasError ? "Component-options-error" : "Component-options"} >
                <select className="Component-options-input" onChange={handleChange} value={value}>
                    <option value=""> -- Select --</option>
                    {options.map(option => (
                        <option key={option} value={option}>
                            {option}
                        </option>
                    ))}
                </select>
            </div>
        </div>
    );
}