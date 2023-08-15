import { axiosInstance } from './AxiosConfig';

export enum HttpMethod {
    GET = "GET", 
    POST = "POST", 
    PUT = "PUT", 
    DELETE = "DELETE"
}

export type Link = {
    href: string;
    title?: string;
    type?: string;
    $ref?: string;
    method?: HttpMethod;
}

export type SchemaProperty = {
    type: string;
    minLength?: number;
    maxLength?: number;
    title?: string;
    format?: string;
    enum?: string[];
    pattern?: string;
    readOnly?: boolean;
    properties?: SchemaPropertySet;
    items?: Schema;
    $ref?: string;
}

export type SchemaPropertySet = {
    [field: string]: SchemaProperty;
}

export type SchemaSet = {
    [schema: string]: Schema;
}

export type Schema = {
    title?: string;
    type?: string;
    $ref?: string;
    properties?: SchemaPropertySet;
    definitions?: SchemaSet;
    readOnly?: boolean;
    required?: string[];
}

export type DriveResource = {
    id: number;
    links?: {
        [name: string]: Link;
    },
    data?: {
        [name: string]: any;
    },
    schema?: Schema,
}

export type ApiError = {
    description: string;
}

export type ApiErrorSet = {
    [error: string]: ApiError;
}

export type ApiErrors = {
    code: string;
    description: string;
    errors?: ApiErrorSet;
}

const injectId = (resource: DriveResource): DriveResource => {
    return {...resource, id: Date.now()};
}

export const getResource = (uri: string): Promise<DriveResource> => {
    return axiosInstance()
        .get(uri)
        .then((response) => injectId(response.data));
}

export const saveResource = (uri: string, body: any): Promise<DriveResource> => {
    return axiosInstance()
        .post(uri, body)
        .then((response) => injectId(response.data));
}

export const updateResource = (uri: string, body: any): Promise<DriveResource> => {
    return axiosInstance()
        .put(uri, body)
        .then((response) => injectId(response.data));
}

export const deleteResource = (uri: string): Promise<DriveResource> => {
    return axiosInstance()
        .delete(uri)
        .then((response) => injectId(response.data));
}