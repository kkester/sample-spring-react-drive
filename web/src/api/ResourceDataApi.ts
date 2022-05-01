import { ApiErrorSet, Schema, SchemaProperty, SchemaSet } from "./DriveApi";

export const isObject = (val: any) => {
    return val && val.constructor.name === "Object";
}

export const isArray = (val: any) => {
    return val && val.constructor.name === "Array";
}

export const isReadOnly = (attribute: ResourceAttribute): boolean => {
    return (attribute.schemaProperty.readOnly ? true : false) || 
        (attribute.propSchema.readOnly ? true : false) || 
        (attribute.schema.readOnly ? true : false);
}

export const isReadOnlyView = (attribute: ResourceAttribute): boolean => {
    return (attribute.schema.readOnly ? true : false) || 
        (attribute.propSchema.readOnly ? true : false);
}

export const emptySchema = {
    title: "empty",
    type: "object"
}

export type ResourceAttribute = {
    name: string;
    schema: Schema;
    propSchema: Schema;
    schemaProperty: SchemaProperty;
    value: string | number | readonly string[] | undefined | object[];
    hasError: boolean;
    required: boolean;
}

export const resolveSchema = (
    schema: Schema,
    schemaProp: SchemaProperty): Schema => {
    if (schemaProp.items && schemaProp.items.$ref && schema.definitions) {
        const definitions: SchemaSet = schema.definitions;
        const schemaName: string = schemaProp.items.$ref.replace('#/definitions/', '');
        return definitions[schemaName];
    } else if (schemaProp.$ref && schema.definitions) {
        const definitions: SchemaSet = schema.definitions;
        const schemaName: string = schemaProp.$ref.replace('#/definitions/', '');
        return definitions[schemaName];
    }
    return emptySchema;
}

export const mapResourceAttribute = (
    key: string,
    schema: Schema,
    propSchema: Schema,
    schemaProperty: SchemaProperty,
    data: { [name: string]: any; },
    attributeErrors: ApiErrorSet): ResourceAttribute => {

    const required: string[] = schema.required ? schema.required : [];

    return {
        name: key,
        schema: schema,
        propSchema: propSchema,
        schemaProperty: schemaProperty,
        value: data[key],
        hasError: attributeErrors[key] !== undefined,
        required: required.includes(key),
    }
}