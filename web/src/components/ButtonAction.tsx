import { Link } from "../api/DriveApi";

const ButtonAction = (props: {
    id: string | number;
    link: Link;
    navBar?: boolean;
    clickHandler: (link: Link) => void;
}) => {
    const toggleClickHandler = () => {
        props.clickHandler(props.link);
    }

    const buttonId: string = props.id.toString();
    return (
        <div id={buttonId} className="Component-button">
            <button id={buttonId} 
                type="button" 
                className={props.navBar? "Component-nav-button-input": "Component-button-input"} 
                onClick={toggleClickHandler}>
                {props.link.title ? props.link.title : ''}
            </button>
        </div>
    );
}

export { ButtonAction };