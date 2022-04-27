const TableHeader = (props: {
    id: string | number;
    headings: string[];
    includeActions: boolean; 
}) => {
    const id = 'header' + props.id;
    return (
        <thead>
            <tr className="Component-table-header">
                {props.headings.map((heading, i) => (
                    <th key={id+i}> {heading}</th>))}
                {props.includeActions && <th key={props.headings.length} />}
            </tr>
        </thead>
    );
}

export { TableHeader };