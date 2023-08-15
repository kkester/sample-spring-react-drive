import { render, screen } from '@testing-library/react';
import { Link, Schema, SchemaProperty } from '../../api/DriveApi';
import { emptySchema, ResourceAttribute } from '../../api/ResourceFunctions';
import { TableLayout } from '../TableLayout';

const cardData = [
    {
        links: {
            name: {href: '/cards/id', title: 'Dusty'},
            store: {href: '/stores/id', title: 'store'},
        },
        data: {
            name: 'Dusty',
            age: 31,
        }
    }
]

const arrayProperty: SchemaProperty = {
    type: 'array',
    items: {
        $ref: '#/definitions/Card',
    },
    title: 'Cards',
}

const cardSchema: Schema = {
    type: 'object',
    properties: {
        name: {
            type: 'string',
            title: 'Name',
        },
        age: {
            type: 'integer',
            title: 'Age',
        }
    }
}

const schema: Schema = {
    properties: {
        cards: arrayProperty,
    },
    definitions: {
        Card: cardSchema,
    }
}

const arrayAttribute: ResourceAttribute = {
    name: 'cards',
    schema: schema,
    propSchema: emptySchema,
    schemaProperty: arrayProperty,
    value: cardData,
    required: false,
    hasError: false
};

const handleDataChange = jest.fn();

const clickHandler = (link: Link) => { };

it("renders table with title and headings", () => {
    render(<TableLayout id={1} attribute={arrayAttribute} dataChangeHandler={handleDataChange} clickHandler={clickHandler} />);

    expect(screen.getByText('Cards')).toBeInTheDocument();
    expect(screen.getByText('Name')).toBeInTheDocument();
    expect(screen.getByText('Age')).toBeInTheDocument();
    expect(screen.getByText('Dusty')).toBeInTheDocument();
    expect(screen.getByText('31')).toBeInTheDocument();
    expect(screen.getByText('store')).toBeInTheDocument();
});