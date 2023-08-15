import { useState } from 'react';
import { SchemaProperty } from '../api/DriveApi';
import { isReadOnly, isReadOnlyView, ResourceAttribute } from '../api/ResourceFunctions';

export const TextField = (props: {
  id: string | number;
  attribute: ResourceAttribute;
  dataChangeHandler: (name: string, value: any) => void;
}) => {
  const [value, setValue] = useState<any>(props.attribute.value);

  const handleChange = (event: React.FormEvent<HTMLInputElement>) => {
    setValue(event.currentTarget.value);
    props.dataChangeHandler(props.attribute.name, event.currentTarget.value);
    event.currentTarget.focus();
  };

  const attribute: ResourceAttribute = props.attribute;
  const schemaProperty: SchemaProperty = attribute.schemaProperty;
  const title: string = schemaProperty.title ? schemaProperty.title : attribute.name;
  const readOnly: boolean = isReadOnly(attribute);
  const readOnlyView: boolean = isReadOnlyView(attribute);

  const required: boolean = !readOnly && props.attribute.required;
  return (
    <div data-testid={ attribute.name + props.id } className="Component-text">
      <label className="Component-text-label">
        { title }:
      </label>
      { required && <label className="Component-text-required-label"> *</label> }
      <br/>
      <div className={ props.attribute.hasError ? 'Component-text-error' : 'Component-text' }>
        { readOnlyView ?
          <label className="Component-text-input-view">
            { value }
          </label> :
          <input key={attribute.name + 'input' + props.id}
                 type="text"
                 value={ value }
                 readOnly={ readOnly }
                 className={ readOnly ? 'Component-readonly-text-input' : 'Component-text-input' }
                 onChange={ handleChange }
          /> }
      </div>
    </div>
  );
};