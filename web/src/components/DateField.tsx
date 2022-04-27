import { useState } from "react";
import { ResourceAttribute } from "../api/ResourceDataApi";

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
    const readOnly = props.attribute.schemaProperty.readOnly || props.attribute.schema.readOnly;
    const labelId = id + '-label';
    const inputId = id + '-input';
    return (
        <div key={id} id={id} className="Component-date">
            <label key={labelId} id={labelId} className="Component-date-label">
                {props.attribute.schemaProperty.title}:
            </label><br/>
            <input type="date"
                key={inputId} 
                id={inputId} 
                className={readOnly ? "Component-readonly-date-input" : "Component-date-input"}
                value={value}
                readOnly={readOnly}
                onChange={handleChange}
            />
        </div>
    );
}

export { DateField };