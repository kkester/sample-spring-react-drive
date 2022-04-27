import { Link, SchemaPropertySet } from "../api/DriveApi";
import { TableCellButton } from "./TableCellButton";

const TableRow = (props: {
    id: string | number;
    item: any;
    schemaProps: SchemaPropertySet;
    clickHandler: (link: Link) => void;
}) => {
    const links: any = props.item.links;
    const itemData: any = props.item.data ? props.item.data : props.item; 
    const id = 'row' + props.id;
    return (
        <tr id={id} className="Component-table-row">
            {Object.keys(props.schemaProps).map((key: string) => (
                <td>{'' + itemData[key] ? itemData[key] : ''}</td>
            ))}
            {links &&
                <td id={id} className="Component-cell-button">
                    {Object.keys(links).map(linkName => (
                        <TableCellButton id={id} link={links[linkName] as Link} clickHandler={props.clickHandler} />
                    ))}
                </td>}
        </tr>
    );
}

export { TableRow };