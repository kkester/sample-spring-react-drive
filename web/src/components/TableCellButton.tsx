import { Link } from "../api/DriveApi";

const TableCellButton = (props: {
    id: string | number;
    link: Link;
    clickHandler: (link: Link) => void;
}) => {
    const toggleClickHandler = () => {
        props.clickHandler(props.link);
    }

    const buttonId: string = props.id.toString();
    return (
        <button id={buttonId} type="button" className="Component-cell-button-input" onClick={toggleClickHandler}>
            {props.link.title ? props.link.title : ''}
        </button>
    );
}

export { TableCellButton };