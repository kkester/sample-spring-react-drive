const TableHeader = (props: {
    id: string | number;
    headings: string[];
    includeActions: boolean;
}) => {
    const id = 'header' + props.id;
    return (
        <thead>
            <tr>
                {props.headings.map((heading, i) => (
                    <th key={id + i} className="Component-table-header"> {heading}</th>))}
                {props.includeActions && <th className="Component-table-header" key={props.headings.length} />}
            </tr>
        </thead>
    );
}

export { TableHeader };