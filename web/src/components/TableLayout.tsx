import { Link, Schema, SchemaProperty, SchemaPropertySet } from "../api/DriveApi";
import { resolveSchema, ResourceAttribute } from "../api/ResourceFunctions";
import { TableHeader } from "./TableHeader";
import { TableRow } from "./TableRow";

export type RowItemCell = {
    name: string;
    data: any;
    link?: Link;
}

export type RowItem = {
    links: Link[];
    cells: Map<String,RowItemCell>;
}

const extractRowCell = (key: string, data: any, links: any): RowItemCell => {
    return {
        name: key,
        data: data,
        link: links[key]
    };
}

const extractRowItem = (item: any): RowItem => {
    const itemData: any = item.data ? item.data : item;
    const links: any = item.links ? item.links : {};
    const cells: Map<String,RowItemCell> = new Map();
    Object.keys(itemData).forEach(key => {
        cells.set(key, extractRowCell(key, itemData[key], links));
    });
    const actionColumnLinks: Link[] = links && Object.keys(links)
        .filter((linkName) => (itemData[linkName] === undefined))
        .map(linkName => links[linkName]);
    return {
        links: actionColumnLinks,
        cells: cells
    };
}

const extractTitle = (
    key: string,
    schemaProps: SchemaPropertySet): string => {
    const title: string | undefined = schemaProps[key].title;
    return title === undefined ? '' : title;
}

const TableLayout = (props: {
    id: string | number;
    attribute: ResourceAttribute;
    dataChangeHandler: (name: string, value: any) => void;
    clickHandler: (link: Link) => void;
}) => {
    const schemaProp: SchemaProperty = props.attribute.schemaProperty;
    const itemsSchema: Schema = resolveSchema(props.attribute.schema, schemaProp);
    const schemaProps: SchemaPropertySet = itemsSchema.properties ? itemsSchema.properties : {};
    const headings: string[] = Object.keys(schemaProps)
        .map(key => extractTitle(key, schemaProps))
        .filter(title => title !== '' );

    const items: object[] = props.attribute.value as object[];
    const rowItems: RowItem[] = items.map(item => extractRowItem(item));
    const includeActions: boolean = rowItems && rowItems.filter(rowItem => rowItem.links.length > 0).length > 0;

    const id = props.attribute.name + props.id;
    const labelId = id + '-label';

    const rows: React.ReactNode[] = rowItems && rowItems.map((rowItem, i) => (
          <TableRow key={id + '-row-' + i}
            id={id + '-row-' + i}
            item={rowItem}
            schemaProps={schemaProps}
            attribute={props.attribute}
            clickHandler={props.clickHandler}
        />
    ));

    return (
        <div className="Component-table-layout">
            {schemaProp.title && <label id={labelId} className="Component-table-layout-label">{schemaProp.title}</label>}
            <table className="Component-table">
                {headings.length > 1 && <TableHeader key={id + '-header'} id={id + '-header'} headings={headings} includeActions={includeActions} />}
                <tbody>
                    {rows}
                </tbody>
            </table>
        </div>
    );
}

export { TableLayout };