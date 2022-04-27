import './Main.css';
import { deleteResource, DriveResource, getResource, Link, saveResource, updateResource } from "../api/DriveApi";
import { useEffect, useState } from 'react';
import { FormLayout } from '../components/FormLayout';
import { HttpMethod } from '../api/SampleDriveResources';

export const Main = () => {
    const [driveResource, setDriveResource] = useState<DriveResource>();

    useEffect(() => {
        getResource("/league")
            .then(response => { setDriveResource(response) });
    }, [])

    const toggleClickHandler = (link: Link) => {
        const linkMethod: HttpMethod = link.method ? link.method : HttpMethod.GET;
        if (HttpMethod.DELETE === linkMethod) {
            deleteResource(link.href).then(response => { setDriveResource(response) });
        } else if (HttpMethod.GET === linkMethod) {
            getResource(link.href).then(response => { setDriveResource(response) });
        } else if (driveResource && HttpMethod.POST === linkMethod) {
            saveResource(link.href, driveResource.data).then(response => { setDriveResource(response) });
        } else if (driveResource && HttpMethod.PUT === linkMethod) {
            updateResource(link.href, driveResource.data).then(response => { setDriveResource(response) });
        }
    }

    const handleDataChange = (attributeName: string, attributeValue: any) => {
        const data = { ...driveResource?.data, [attributeName]: attributeValue };
        setDriveResource({ ...driveResource, data: data });
    }

    return (
        <>
            {driveResource && <FormLayout driveResource={driveResource}
                clickHandler={toggleClickHandler}
                dataChangeHandler={handleDataChange} />}
        </>
    );
}
