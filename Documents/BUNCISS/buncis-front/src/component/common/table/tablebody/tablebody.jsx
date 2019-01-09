 import React, { Component } from 'react';
 import _ from"lodash";
class TableBody extends Component {
    state = {  };
    renderCell=(item, column)=>{
        if(column.content) return column.content(item);

        return _.get(item, column.path);
    };

    render() { 
        return ( 
        <tbody >
            {this.props.datas.map(data =>(
                <tr >
                    {this.props.collumns.map(collumn =>(
                        <td className="borderbody">{this.renderCell(data, collumn)}</td>
                    ))}
                </tr>
            ))}
        </tbody>
            );
    }
}
 
export default TableBody;
