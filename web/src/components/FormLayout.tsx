
import { ApiErrors, ApiErrorSet, DriveResource, Link, Schema, SchemaProperty, SchemaPropertySet } from "../api/DriveApi";
import { emptySchema, resolveSchema, ResourceAttribute } from "../api/ResourceDataApi";
import { HttpMethod } from "../api/SampleDriveResources";
import { ButtonGroupRow } from "./ButtonGroupRow";
import { FieldGroupRow } from "./FieldGroupRow";

const isObject = (val: any) => {
    return val && val.constructor.name === "Object";
}

const isArray = (val: any) => {
    return val && val.constructor.name === "Array";
}

const mapResourceAttribute = (
    key: string,
    schema: Schema,
    schemaProperty: SchemaProperty,
    data: { [name: string]: any; },
    attributeErrors: ApiErrorSet): ResourceAttribute => {

    const schemaPropertyWithTitle: SchemaProperty = schemaProperty.title ?
        schemaProperty : { ...schemaProperty, title: key };
    const required: string[] = schema.required ? schema.required : [];

    return {
        name: key,
        schema: schema,
        schemaProperty: schemaPropertyWithTitle,
        value: data[key],
        hasError: attributeErrors[key] !== undefined,
        required: required.includes(key),
    }
}

const mapFieldGroupRow = (
    key: string,
    index: number,
    schema: Schema,
    schemaProperty: SchemaProperty,
    data: { [name: string]: any; },
    attributeErrors: ApiErrorSet,
    clickHandler: (link: Link) => void,
    dataChangeHandler: (name: string, value: any) => void): React.ReactNode => {

    const propSchema: Schema = resolveSchema(schema, schemaProperty);
    const schemaProperties: SchemaPropertySet = propSchema.properties ? propSchema.properties : {};

    const attributes: ResourceAttribute[] = Object.keys(schemaProperties)
        .map(key => mapResourceAttribute(key, schema, schemaProperties[key], data, attributeErrors));

    return <FieldGroupRow key={key + 'row-' + index}
        attributes={attributes}
        name={key}
        data={data}
        clickHandler={clickHandler}
        dataChangeHandler={dataChangeHandler} />
}

export const FormLayout = (props: {
    driveResource: DriveResource;
    errors?: ApiErrors;
    clickHandler: (link: Link) => void;
    dataChangeHandler: (name: string, value: any) => void;
}) => {
    const links = props.driveResource.links ? props.driveResource.links : {};
    const navLinks: Link[] = Object.keys(links).map((linkName) => links[linkName])
        .filter(link => link.method === undefined || link.method === HttpMethod.GET);
    const crudLinks: Link[] = Object.keys(links).map((linkName) => links[linkName])
        .filter(link => !(link.method === undefined || link.method === HttpMethod.GET));

    const data = props.driveResource.data ? props.driveResource.data : {};
    const schema: Schema = props.driveResource.schema ? props.driveResource.schema : emptySchema;
    const schemaProperties: SchemaPropertySet = schema.properties ? schema.properties : {};
    const attributeErrors: ApiErrorSet = props.errors && props.errors.errors ? props.errors.errors : {};

    const attributes: ResourceAttribute[] = Object.keys(schemaProperties)
        .filter(key => !isObject(data[key]) && !isArray(data[key]))
        .map(key => mapResourceAttribute(key, schema, schemaProperties[key], data, attributeErrors));

    const rows: React.ReactNode[] = Object.keys(schemaProperties)
        .filter(key => isObject(data[key]))
        .map((key, i) => mapFieldGroupRow(key, i, schema, schemaProperties[key], data[key],
            attributeErrors, props.clickHandler, props.dataChangeHandler));

    const arrayAttributes: ResourceAttribute[] = Object.keys(schemaProperties)
        .filter(key => isArray(data[key]))
        .map(key => mapResourceAttribute(key, schema, schemaProperties[key], data, {}));

    return (
        <div className="Compontent-form-layout">
            <ButtonGroupRow key={schema.id + 'navbar'}
                clickHandler={props.clickHandler}
                navBar={true}
                links={navLinks} />

            {attributes.length > 0 &&
                <FieldGroupRow key={schema.id + 'row-0'}
                    attributes={attributes}
                    clickHandler={props.clickHandler}
                    dataChangeHandler={props.dataChangeHandler} />}

            {rows}

            {arrayAttributes.length > 0 &&
                <FieldGroupRow key={schema.id + 'items-row'}
                    attributes={arrayAttributes}
                    clickHandler={props.clickHandler}
                    dataChangeHandler={props.dataChangeHandler} />}

            <ButtonGroupRow key={schema.id + 'ops'}
                clickHandler={props.clickHandler}
                links={crudLinks} />
        </div>
    );
}
