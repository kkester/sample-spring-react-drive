
import { HttpMethod, ApiErrors, ApiErrorSet, DriveResource, Link, Schema, SchemaPropertySet } from "../api/DriveApi";
import { emptySchema, isArray, isObject, mapResourceAttribute, ResourceAttribute } from "../api/ResourceFunctions";
import { ButtonGroupRow } from "./ButtonGroupRow";
import { mapFieldGroupRow } from "./FieldGroupLayout";
import { FieldGroupRow } from "./FieldGroupRow";

export const FormLayout = (props: {
    driveResource: DriveResource;
    errors?: ApiErrors;
    clickHandler: (link: Link) => void;
    dataChangeHandler: (name: string, value: any) => void;
}) => {
    const key = props.driveResource.id;
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
        .filter(name => !isObject(data[name]) && !isArray(data[name]))
        .map(name => mapResourceAttribute(name, schema, schema, schemaProperties[name], data, attributeErrors));

    const rows: React.ReactNode[] = Object.keys(schemaProperties)
        .filter(name => isObject(data[name]))
        .map((name, i) => mapFieldGroupRow(name, key+'-'+i, schema, schemaProperties[name], data[name],
            attributeErrors, props.clickHandler, props.dataChangeHandler));

    const arrayAttributes: ResourceAttribute[] = Object.keys(schemaProperties)
        .filter(name => isArray(data[name]))
        .map(name => mapResourceAttribute(name, schema, schema, schemaProperties[name], data, {}));

    return (
        <div className="Compontent-form-layout">
            <ButtonGroupRow key={key + 'navbar'}
                clickHandler={props.clickHandler}
                navBar={true}
                links={navLinks} />

            {attributes.length > 0 &&
                <FieldGroupRow key={key + 'row-0'}
                    attributes={attributes}
                    clickHandler={props.clickHandler}
                    dataChangeHandler={props.dataChangeHandler} />}

            {rows}

            {arrayAttributes.length > 0 &&
                <FieldGroupRow key={key + 'items-row'}
                    attributes={arrayAttributes}
                    clickHandler={props.clickHandler}
                    dataChangeHandler={props.dataChangeHandler} />}

            <ButtonGroupRow key={key + 'ops'}
                clickHandler={props.clickHandler}
                links={crudLinks} />
        </div>
    );
}
