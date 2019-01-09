import React from 'react';

const TableHead = props => {
    return (
            <tr >
                {props.headers.map(header =>(
                    <th style={{borderRight:"2px rgba(255, 255, 255,0.3) solid"}} scope="col">{header.label}</th>
                ))}
            </tr>
    );
}
export default TableHead;