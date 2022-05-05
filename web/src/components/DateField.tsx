import { useState } from "react";
import { SchemaProperty } from "../api/DriveApi";
import { isReadOnly, isReadOnlyView, ResourceAttribute } from "../api/ResourceFunctions";

export const DateField = (props: {
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
    const id = attribute.name + props.id;
    const schemaProperty: SchemaProperty = attribute.schemaProperty;
    const readOnly: boolean = isReadOnly(attribute);
    const readOnlyView: boolean = isReadOnlyView(attribute);
    const title: string = schemaProperty.title ? schemaProperty.title : props.attribute.name;

    const labelId = id + '-label';
    const inputId = id + '-input';
    return (
        <div key={id} data-testid={attribute.name+props.id} className="Component-date">
            <label key={labelId} className="Component-date-label">
                {title}:
            </label>
            {attribute.required && <label className="Component-date-required-label"> *</label>}
            <br />
            <div key={id} className={attribute.hasError ? "Component-date-error" : "Component-date"}>
                {readOnlyView ?
                    <label className="Component-text-input-view">
                        {value}
                    </label> :
                    <input type="date"
                        key={inputId}
                        className={readOnly ? "Component-readonly-date-input" : "Component-date-input"}
                        value={value}
                        readOnly={readOnly}
                        onChange={handleChange}
                    />}
            </div>
        </div>
    );
}