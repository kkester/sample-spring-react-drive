import { Schema, SchemaProperty, SchemaSet } from "./DriveApi";

export const emptySchema = {
    title: "empty",
    type: "object"
}

export type ResourceAttribute = {
    name: string;
    schema: Schema;
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