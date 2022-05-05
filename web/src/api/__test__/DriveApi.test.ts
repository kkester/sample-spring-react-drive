
import { deleteResource, DriveResource, getResource, saveResource, updateResource } from "../../api/DriveApi";
import * as api from "../AxiosConfig";

const nock = require('nock');

const data: DriveResource = {
    links: {},
    data: {},
    schema: {
        type: 'object'
    }
};

const nockServer = nock('http://localhost:8080')
    .defaultReplyHeaders({
        'access-control-allow-origin': '*',
        'access-control-allow-credentials': 'true',
        'access-control-request-method': '*',
    });

it("get a resource", async () => {
    nockServer.get('/games').reply(200, data);

    const response: Promise<DriveResource> = getResource('/games');

    await expect(response).resolves.toEqual(data);
});

it("save a resource", async () => {
    nockServer.post('/games').reply(200, data);

    const response: Promise<DriveResource> = saveResource('/games', {});

    await expect(response).resolves.toEqual(data);
});

it("update a resource", async () => {
    nockServer.intercept("/games", "OPTIONS")
        .reply(200, null)
        .put('/games')
        .reply(200, data);

    const response: Promise<DriveResource> = updateResource('/games', {});

    await expect(response).resolves.toEqual(data);
});

it("delete a resource", async () => {
    nockServer.intercept("/games", "OPTIONS")
        .reply(200, null)
        .delete('/games')
        .reply(200, data);

    const response: Promise<DriveResource> = deleteResource('/games');

    await expect(response).resolves.toEqual(data);
});