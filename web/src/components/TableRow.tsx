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
        <tr className="Component-table-row-tr">
            {Object.keys(props.schemaProps).map((key: string) => (
                <td className="Component-table-row-td">{'' + itemData[key] ? itemData[key] : ''}</td>
            ))}
            {links &&
                <td className="Component-cell-button">
                    {Object.keys(links).map((linkName,i) => (
                        <TableCellButton key={id + i} id={id} link={links[linkName] as Link} clickHandler={props.clickHandler} />
                    ))}
                </td>}
        </tr>
    );
}

export { TableRow };