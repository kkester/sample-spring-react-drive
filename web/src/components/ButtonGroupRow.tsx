import { Link } from "../api/DriveApi";
import { ButtonAction } from "./ButtonAction";

export const ButtonGroupRow = (props: {
    links: Link[];
    navBar?: boolean;
    clickHandler: (link: Link) => void;
}) => {

    return (
        <div className={props.navBar? "Compontent-nav-button-row": "Compontent-button-row"}>
            {props.links.map((link, i) => (
                <ButtonAction key={link.href+i} link={link} id={i} clickHandler={props.clickHandler}/>
            ))}
        </div>
    );
}