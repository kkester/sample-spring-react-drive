import { fireEvent, render, screen } from "@testing-library/react";
import { Link, SchemaProperty } from "../../api/DriveApi";
import { emptySchema, ResourceAttribute } from "../../api/ResourceFunctions";
import { FieldGroupRow } from "../FieldGroupRow";

const objectProperty: SchemaProperty = {
    type: 'object',
}

const stringProperty: SchemaProperty = {
    type: 'string',
}

const dateProperty: SchemaProperty = {
    type: 'string',
    format: 'date',
}

const enumProperty: SchemaProperty = {
    type: 'string',
    enum: ['One', 'Two', 'Three'],
}

const arrayProperty: SchemaProperty = {
    type: 'array',
}

const attribute: ResourceAttribute = {
    name: 'place',
    schema: emptySchema,
    propSchema: emptySchema,
    schemaProperty: stringProperty,
    value: 'home',
    required: false,
    hasError: false
};

const arrayAttribute: ResourceAttribute = {
    name: 'places',
    schema: emptySchema,
    propSchema: emptySchema,
    schemaProperty: arrayProperty,
    value: [{places: []}],
    required: false,
    hasError: false
};

const handleDataChange = jest.fn();

const clickHandler = (link: Link) => {};

it("renders empty field group row when attribute not supported", () => {
    render(<FieldGroupRow attributes={ [{...attribute, schemaProperty: objectProperty}] } dataChangeHandler={handleDataChange} clickHandler={clickHandler}/>);

    const rowElement = screen.queryByTestId('field-row');
    const sectionElement = screen.queryByTestId('field-row-section');

    expect(rowElement).not.toBeInTheDocument();
    expect(sectionElement).not.toBeInTheDocument();
});

it("renders text field in field group row", () => {
    render(<FieldGroupRow attributes={ [attribute] } dataChangeHandler={handleDataChange} clickHandler={clickHandler}/>);

    const rowElement = screen.queryByTestId('field-row');
    const sectionElement = screen.queryByTestId('field-row-section');
    const textElement = screen.queryByTestId('place0');

    expect(rowElement).not.toBeInTheDocument();
    expect(sectionElement).not.toBeInTheDocument();
    expect(textElement).toBeInTheDocument();
});

it("renders date field in field group row", () => {
    render(<FieldGroupRow attributes={ [{...attribute, schemaProperty: dateProperty}] } dataChangeHandler={handleDataChange} clickHandler={clickHandler}/>);

    const dateElement = screen.queryByTestId('place0');

    expect(dateElement).toBeInTheDocument();
});

it("renders enum field in field group row", () => {
    render(<FieldGroupRow attributes={ [{...attribute, schemaProperty: enumProperty}] } dataChangeHandler={handleDataChange} clickHandler={clickHandler}/>);

    const enumElement = screen.queryByTestId('place0');

    expect(enumElement).toBeInTheDocument();
});

it("renders array table in field group row", () => {
    render(<FieldGroupRow attributes={ [arrayAttribute] } dataChangeHandler={handleDataChange} clickHandler={clickHandler}/>);

    const arrayElement = screen.queryByTestId('places0');

    expect(arrayElement).toBeInTheDocument();
});

it("renders multiple text fields in field group row", () => {
    render(<FieldGroupRow attributes={ [attribute, attribute] } dataChangeHandler={handleDataChange} clickHandler={clickHandler}/>);

    const rowElement = screen.queryByTestId('field-row');
    const sectionElement = screen.queryByTestId('field-row-section');

    expect(rowElement).toBeInTheDocument();
    expect(sectionElement).not.toBeInTheDocument();
});

it("renders multiple text fields in field group row with a Title", () => {
    render(<FieldGroupRow title='Places' attributes={ [attribute, attribute] } dataChangeHandler={handleDataChange} clickHandler={clickHandler}/>);

    const rowElement = screen.queryByTestId('field-row');
    const sectionElement = screen.queryByTestId('field-row-section');
    const titleElement = screen.getByText('Places');

    expect(rowElement).not.toBeInTheDocument();
    expect(sectionElement).toBeInTheDocument();
    expect(titleElement).toBeInTheDocument();
});

it("handles data when text field changes", () => {
    const data = {
        place: "home"
    }
    render(<FieldGroupRow name='places' 
        data={data} 
        attributes={ [attribute] } 
        dataChangeHandler={handleDataChange} 
        clickHandler={clickHandler}/>);
    const input = screen.getByDisplayValue('home');

    expect(input).toBeInTheDocument();

    fireEvent.change(input, {target: {value: 'away'}});
    expect(handleDataChange).toBeCalledTimes(1);
    expect(handleDataChange).toBeCalledWith('places', {place: 'away'});
});

it("handles text field changes", () => {
    render(<FieldGroupRow attributes={ [attribute] } 
        dataChangeHandler={handleDataChange} 
        clickHandler={clickHandler}/>);
    const input = screen.getByDisplayValue('home');

    expect(input).toBeInTheDocument();

    fireEvent.change(input, {target: {value: 'away'}});
    expect(handleDataChange).toBeCalledTimes(1);
    expect(handleDataChange).toBeCalledWith('place', 'away');
});