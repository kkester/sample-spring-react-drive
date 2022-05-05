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
    const [errors, setErrors] = useState<ApiErrors | undefined>(undefined);
    const [shouldShowModal, setShouldShowModal] = useState<boolean>(false);

    useEffect(() => {
        getResource("/league")
            .then(response => { setDriveResource(response) });
    }, [])

    const handleErrors = (errors: ApiErrors) => {
        setErrors(errors);
        setShouldShowModal(true);
    }

    const toggleClickHandler = (link: Link) => {
        setErrors(undefined);
        const linkMethod: HttpMethod = link.method ? link.method : HttpMethod.GET;
        if (HttpMethod.DELETE === linkMethod) {
            deleteResource(link.href)
                .then(response => setDriveResource(response))
                .catch(error => handleErrors(error.response.data));
        } else if (HttpMethod.GET === linkMethod) {
            getResource(link.href)
                .then(response => setDriveResource(response))
                .catch(error => handleErrors(error.response.data));
        } else if (driveResource && HttpMethod.POST === linkMethod) {
            saveResource(link.href, driveResource.data)
                .then(response => setDriveResource(response))
                .catch(error => handleErrors(error.response.data));
        } else if (driveResource && HttpMethod.PUT === linkMethod) {
            updateResource(link.href, driveResource.data)
                .then(response => setDriveResource(response))
                .catch(error => handleErrors(error.response.data));
        }
    }

    const handleDataChange = (attributeName: string, attributeValue: any) => {
        const data = { ...driveResource?.data, [attributeName]: attributeValue };
        setDriveResource({ ...driveResource, data: data });
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
            {driveResource && <FormLayout key={driveResource.schema?.title} driveResource={driveResource}
                errors={errors}
                clickHandler={toggleClickHandler}
                dataChangeHandler={handleDataChange} />}
        </>
    );
}
