import { Link, Schema, SchemaProperty, SchemaPropertySet } from "../api/DriveApi";
import { isObject, ResourceAttribute } from "../api/ResourceDataApi";
import { mapFieldGroupRow } from "./FieldGroupLayout";
import { TableCellButton } from "./TableCellButton";

const toggleClickHandler = (link: Link) => {}

const handleDataChange = (attributeName: string, attributeValue: any) => {}

const extractCellContent = (key: string, id: string, itemData: any, resourceSchema: Schema, itemsSchema: Schema, schemaProperty: SchemaProperty) => {
    if (!isObject(itemData[key])) {
        return '' + itemData[key] ? itemData[key] : '';
    }

    return mapFieldGroupRow(key, id, resourceSchema, schemaProperty, itemData[key],
        {}, toggleClickHandler, handleDataChange)
}

export const TableRow = (props: {
    id: string | number;
    item: any;
    itemsSchema: Schema;
    schemaProps: SchemaPropertySet;
    attribute: ResourceAttribute;
    clickHandler: (link: Link) => void;
}) => {
    const links: any = props.item.links;
    const itemData: any = props.item.data ? props.item.data : props.item;
    const itemsSchema: Schema = props.itemsSchema;
    const schemaProps: SchemaPropertySet = props.schemaProps;

    const id = 'row' + props.id;
    return (
        <tr className="Component-table-row-tr">
            {Object.keys(props.schemaProps).map((key: string, i: number) => (
                <td className="Component-table-row-td">
                    {extractCellContent(key, id+i, itemData, props.attribute.schema, itemsSchema, schemaProps[key])}
                </td>
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
