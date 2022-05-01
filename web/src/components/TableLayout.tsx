import { Link, Schema, SchemaProperty, SchemaPropertySet } from "../api/DriveApi";
import { resolveSchema, ResourceAttribute } from "../api/ResourceDataApi";
import { TableHeader } from "./TableHeader";
import { TableRow } from "./TableRow";

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
    const includeActions: boolean = items.filter(item => Object.keys(item).includes('links')).length > 0;

    const id = props.attribute.name + props.id;
    const labelId = id + '-label';

    const rows: React.ReactNode[] = items && items.map((item, i) => (
        <TableRow key={id + '-row-' + i}
            id={id + '-row-' + i}
            item={item}
            itemsSchema={itemsSchema}
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