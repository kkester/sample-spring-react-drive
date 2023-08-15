import './Main.css';
import './MainTextField.css';
import './MainOptionsBox.css';
import './MainDateField.css';
import './MainFieldGroupRow.css';
import './MainTable.css';

import { HttpMethod, ApiErrors, deleteResource, DriveResource, getResource, Link, saveResource, updateResource } from "../api/DriveApi";
import { useEffect, useState } from 'react';
import { FormLayout } from '../components/FormLayout';
import { Modal } from '../components/Modal';

export const Main = () => {
    const [driveResource, setDriveResource] = useState<DriveResource>();
    const [driveResourceUpdated, setDriveResourceUpdated] = useState<DriveResource>();
    const [errors, setErrors] = useState<ApiErrors | undefined>(undefined);
    const [shouldShowModal, setShouldShowModal] = useState<boolean>(false);

    useEffect(() => {
        getResource("/places")
            .then(response => {
                setDriveResource(response);
                setDriveResourceUpdated(response);
            });
    }, [])

    const handleErrors = (newErrors: ApiErrors) => {
        setErrors(newErrors);
        setShouldShowModal(true);
    }

    const applyDriveResource = (response: DriveResource) => {
        setDriveResource(response);
        setDriveResourceUpdated(response);
    }

    const toggleClickHandler = (link: Link) => {
        setErrors(undefined);
        const linkMethod: HttpMethod = link.method ? link.method : HttpMethod.GET;

        if (HttpMethod.DELETE === linkMethod) {
            deleteResource(link.href)
                .then(response => applyDriveResource(response))
                .catch(error => handleErrors(error.response.data));
        } else if (HttpMethod.GET === linkMethod) {
            getResource(link.href)
                .then(response => applyDriveResource(response))
                .catch(error => handleErrors(error.response.data));
        } else if (driveResource && HttpMethod.POST === linkMethod) {
            const resource = driveResourceUpdated ? driveResourceUpdated : driveResource;
            saveResource(link.href, resource.data)
                .then(response => applyDriveResource(response))
                .catch(error => handleErrors(error.response.data));
        } else if (driveResource && HttpMethod.PUT === linkMethod) {
            const resource = driveResourceUpdated ? driveResourceUpdated : driveResource;
            updateResource(link.href, resource.data)
                .then(response => applyDriveResource(response))
                .catch(error => handleErrors(error.response.data));
        }
    }

    const handleDataChange = (attributeName: string, attributeValue: any) => {
        const resource = driveResourceUpdated ? driveResourceUpdated : driveResource;
        const data = { ...resource?.data, [attributeName]: attributeValue };
        setDriveResourceUpdated({ ...resource, id: Date.now(), data: data });
    }

    return (
        <>
            {errors && <Modal shouldShow={shouldShowModal} setShouldShow={setShouldShowModal}>
                <div>
                    <label>
                        {errors.description}
                    </label>
                </div>
            </Modal>}
            {driveResource && <FormLayout key={driveResource.id}
                driveResource={driveResource}
                errors={errors}
                clickHandler={toggleClickHandler}
                dataChangeHandler={handleDataChange} />}
        </>
    );
}
