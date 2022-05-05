import { fireEvent, render, screen } from "@testing-library/react";
import { HttpMethod, ApiErrors, DriveResource } from "../../api/DriveApi";
import { Main } from "../Main";
import * as api from "../../api/DriveApi";
import { act } from "react-dom/test-utils";

const driveResource: DriveResource = {
    links: {
        self: {
            href: '/orders',
            title: 'Orders',
        },
        save: {
            href: '/orders',
            title: 'Save',
            method: HttpMethod.POST,
        },
        update: {
            href: '/orders/id',
            title: 'Update',
            method: HttpMethod.PUT
        },
        delete: {
            href: '/orders/id',
            title: 'Delete',
            method: HttpMethod.DELETE
        }
    },
    data: {
        name: "Chance"
    },
    schema: {
        type: 'object',
        properties: {
            name: {
                type: 'string'
            }
        }
    }
}

const errors = {
    response: {
        data: {
            code: "invalid-request",
            description: "Missing Required Fields",
            errors: {
                name: {
                    "description": "Name is required"
                }
            }
        }
    }
}

const promise = Promise.resolve(driveResource);

it("renders resource response", async () => {
    const getResourceMock = jest.spyOn(api, "getResource").mockResolvedValue(promise);
    const saveResourceMock = jest.spyOn(api, "saveResource").mockResolvedValue(promise);

    render(<Main />);

    await act(async () => { await promise })
    await expect(getResourceMock).toHaveBeenCalledTimes(1);

    expect(screen.getByText("name:")).toBeInTheDocument();
    const nameInputElement = screen.getByDisplayValue('Chance');
    expect(nameInputElement).toBeInTheDocument();
});

describe.only("Main", () => {
    beforeEach(async () => {
        jest.spyOn(api, "getResource").mockResolvedValue(promise);
        render(<Main />);
        await act(async () => { await promise })
    });

    it("saves resource changes", async () => {
        const savePromise = Promise.resolve(driveResource);
        const saveResourceMock = jest.spyOn(api, "saveResource").mockResolvedValue(savePromise);

        const nameInputElement = screen.getByDisplayValue('Chance');
        expect(nameInputElement).toBeInTheDocument();

        fireEvent.change(nameInputElement, { target: { value: 'Clarence' } });
        expect(screen.getByDisplayValue('Clarence')).toBeInTheDocument();

        const saveButtonElement = screen.getByText('Save');
        expect(saveButtonElement).toBeInTheDocument();

        fireEvent.click(saveButtonElement);

        await act(async () => { await savePromise })
        expect(saveResourceMock).toHaveBeenCalledWith('/orders', { name: "Clarence" })
    });

    it("updates resource changes", async () => {
        const updatePromise = Promise.resolve(driveResource);
        const updateResourceMock = jest.spyOn(api, "updateResource").mockResolvedValue(updatePromise);

        const nameInputElement = screen.getByDisplayValue('Chance');
        expect(nameInputElement).toBeInTheDocument();

        fireEvent.change(nameInputElement, { target: { value: 'Clarence' } });
        expect(screen.getByDisplayValue('Clarence')).toBeInTheDocument();

        const saveButtonElement = screen.getByText('Update');
        expect(saveButtonElement).toBeInTheDocument();

        fireEvent.click(saveButtonElement);

        await act(async () => { await updatePromise })
        expect(updateResourceMock).toHaveBeenCalledWith('/orders/id', { name: "Clarence" })
    });

    it("deletes resource", async () => {
        const deletePromise = Promise.resolve(driveResource);
        const deleteResourceMock = jest.spyOn(api, "deleteResource").mockResolvedValue(deletePromise);

        const nameInputElement = screen.getByDisplayValue('Chance');
        expect(nameInputElement).toBeInTheDocument();

        fireEvent.change(nameInputElement, { target: { value: 'Clarence' } });
        expect(screen.getByDisplayValue('Clarence')).toBeInTheDocument();

        const deleteButtonElement = screen.getByText('Delete');
        expect(deleteButtonElement).toBeInTheDocument();

        fireEvent.click(deleteButtonElement);

        await act(async () => { await deletePromise })
        expect(deleteResourceMock).toHaveBeenCalledWith('/orders/id')
    });

    // it("handle errors saving resource changes", async () => {
    //     const errorPromise = Promise.resolve(errors);
    //     const saveResourceMock = jest.spyOn(api, "saveResource").mockRejectedValueOnce(errorPromise);

    //     const nameInputElement = screen.getByDisplayValue('Chance');
    //     expect(nameInputElement).toBeInTheDocument();

    //     fireEvent.change(nameInputElement, { target: { value: 'Clarence' } });
    //     expect(screen.getByDisplayValue('Clarence')).toBeInTheDocument();

    //     const saveButtonElement = screen.getByText('Save');
    //     expect(saveButtonElement).toBeInTheDocument();

    //     fireEvent.click(saveButtonElement);

    //     await act(async () => { await errorPromise })
    // });
});