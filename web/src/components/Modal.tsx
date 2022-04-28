import styled from "styled-components";

const ModalBackground = styled.div`
    position: fixed;
    z-index: 1;
    left: 0;
    top: 0;
    width: 100%;
    height: 100%;
    overflow: auto;
    background-color: rgba(0,0,0,0.5);
`;

const ModalBody = styled.div`
    border-radius: 20px;
    border: 1.5px solid #d2d0d0ec;
    background-color: #282c34;
    margin: 10% auto;
    padding: 20px;
    width: 60%;
`;

export const Modal = (props: {
    shouldShow: boolean;
    setShouldShow: (shouldShow: boolean) => void;
    children: any;
}) => {
    return (
        <>
            {props.shouldShow && (
                <ModalBackground>
                    <ModalBody>
                        <div>
                            {props.children}
                        </div>
                        <div className="Component-button">
                            <div className="Component-button-row">
                                <button className={"Component-button-input"} onClick={() => props.setShouldShow(false)}>Close</button>
                            </div>
                        </div>
                    </ModalBody>
                </ModalBackground>
            )}
        </>
    );
}