import axios, { AxiosInstance } from "axios";
import { HttpMethod, companyDriveResource, product1DriveResource, product2DriveResource, product3DriveResource, productsDriveResource } from './SampleDriveResources';

const axiosInstance = (): AxiosInstance =>
    axios.create({
        baseURL: "http://localhost:8080",
    });

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
    id?: string;
    title?: string;
    type: string;
    $ref?: string;
    properties?: SchemaPropertySet;
    definitions?: SchemaSet;
    readOnly?: boolean;
    required?: string[];
}

export type DriveResource = {
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
    errors?: ApiErrorSet;
}

export const getResource = (uri: string): Promise<DriveResource> => {
    return axiosInstance()
        .get(uri)
        .then((response) => response.data);
}

export const saveResource = (uri: string, body: any): Promise<DriveResource> => {
    return axiosInstance()
        .post(uri, body)
        .then((response) => response.data);
}

export const updateResource = (uri: string, body: any): Promise<DriveResource> => {
    return axiosInstance()
        .put(uri, body)
        .then((response) => response.data);
}

export const deleteResource = (uri: string): Promise<DriveResource> => {
    return axiosInstance()
        .delete(uri)
        .then((response) => response.data);
}

export const getSampleDriveResource = (href: string): DriveResource => {
    if (href === "/products") {
        return productsDriveResource;
    } else if (href === "/products/1") {
        return product1DriveResource;
    } else if (href === "/products/2") {
        return product2DriveResource;
    } else if (href === "/products/3") {
        return product3DriveResource;
    }
    return companyDriveResource;
}