import { fireEvent, render, screen } from "@testing-library/react";
import { Schema, SchemaProperty } from "../../api/DriveApi";
import { emptySchema, ResourceAttribute } from "../../api/ResourceFunctions";
import { TextField } from "../TextField";

const handleDataChange = (attributeName: string, attributeValue: any) => { }

const schemaProperty: SchemaProperty = {
    type: 'integer',
}

const schemaPropertyWithTitle: SchemaProperty = {
    type: 'integer',
    title: 'Aged',
}

const schemaPropertyReadOnly: SchemaProperty = {
    type: 'integer',
    readOnly: true,
}

const schemaReadOnly: Schema = {...emptySchema, readOnly: true}

const attribute: ResourceAttribute = {
    name: 'age',
    schema: emptySchema,
    propSchema: emptySchema,
    schemaProperty: schemaProperty,
    value: 30,
    required: false,
    hasError: false
};

it("renders label with name and value", () => {
    render(<TextField id={1} attribute={attribute} dataChangeHandler={handleDataChange}/>);

    const titleLabel = screen.getByText('age:');
    const ageValue = screen.getByDisplayValue('30');

    expect(titleLabel).toBeInTheDocument();
    expect(ageValue).toBeInTheDocument();
});

it("renders required label with title and error", () => {
    render(<TextField id={1} attribute={{...attribute, required: true, schemaProperty: schemaPropertyWithTitle, hasError: true}} dataChangeHandler={handleDataChange}/>);

    const titleLabel = screen.getByText('Aged:');
    const requiredLabel = screen.getByText('*');
    const errorElement = screen.getByTestId('age1');

    expect(titleLabel).toBeInTheDocument();
    expect(errorElement.children[3]).toHaveClass('Component-text-error');
    expect(errorElement.children[3].firstChild).toHaveClass('Component-text-input');
});

it("renders label with title and read only value", () => {
    render(<TextField id={1} attribute={{...attribute, schemaProperty: schemaPropertyReadOnly, hasError: true}} dataChangeHandler={handleDataChange}/>);

    const errorElement = screen.getByTestId('age1');

    expect(errorElement.children[2].firstChild).toHaveClass('Component-readonly-text-input');
});

it("renders label with title and read only view", () => {
    render(<TextField id={1} attribute={{...attribute, schema: schemaReadOnly}} dataChangeHandler={handleDataChange}/>);

    const errorElement = screen.getByTestId('age1');

    expect(errorElement.children[2].firstChild).toHaveClass('Component-text-input-view');
});

it("handle input valiue change", () => {
    render(<TextField id={1} attribute={attribute} dataChangeHandler={handleDataChange}/>);
    const input = screen.getByDisplayValue('30');

    fireEvent.change(input, {target: {value: 35}});

    expect(input).toHaveDisplayValue('35');
});
