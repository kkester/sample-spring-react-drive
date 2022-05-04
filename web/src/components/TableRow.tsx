import { Link, Schema, SchemaProperty, SchemaPropertySet } from "../api/DriveApi";
import { isObject, ResourceAttribute } from "../api/ResourceFunctions";
import { mapFieldGroupRow } from "./FieldGroupLayout";
import { TableCellButton } from "./TableCellButton";
import { RowItem, RowItemCell } from "./TableLayout";

const toggleClickHandler = (link: Link) => { }

const handleDataChange = (attributeName: string, attributeValue: any) => { }

const extractCellContent = (key: string, id: string, rowItemCell: RowItemCell | undefined, resourceSchema: Schema, schemaProperty: SchemaProperty, clickHandler: (link: Link) => void) => {
    const itemData = rowItemCell?.data;
    if (itemData === undefined) {
        return '';
    }

    const link = rowItemCell?.link;
    if (link !== undefined) {
        return <TableCellButton key={id} id={id} link={link} clickHandler={clickHandler} />;
    }

    if (!isObject(itemData)) {
        return itemData;
    }

    return mapFieldGroupRow(key, id, resourceSchema, schemaProperty, itemData,
        {}, toggleClickHandler, handleDataChange);
}

export const TableRow = (props: {
    id: string | number;
    item: RowItem;
    schemaProps: SchemaPropertySet;
    attribute: ResourceAttribute;
    clickHandler: (link: Link) => void;
}) => {
    const links: Link[] = props.item.links;
    const schemaProps: SchemaPropertySet = props.schemaProps;

    const id = 'row' + props.id;
    return (
        <tr className="Component-table-row-tr">
            {Object.keys(schemaProps).map((key: string, i: number) => (
                <td className="Component-table-row-td">
                    {extractCellContent(key, id + i, props.item.cells.get(key), props.attribute.schema, schemaProps[key], props.clickHandler)}
                </td>
            ))}
            {links && links.length > 0 &&
                <td className="Component-cell-button">
                    {links.map((link, i) => (
                        <TableCellButton key={id + i} id={id} link={link} clickHandler={props.clickHandler} />
                    ))}
                </td>}
        </tr>
    );
}
