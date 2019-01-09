import React, { Component } from 'react';
import TableHead from '../tablehead/tablehead';
import TableBody from '../tablebody/tablebody';
class Table extends Component {
    state = {  }
    render() { 
        return (
        <div>
            <table className={"table "}>
                <thead style={{color:"white",backgroundColor:this.props.users.colors}} className={this.props.users.colors}>
                    <TableHead headers={this.props.users.tableHead}/>
                </thead> 
                <TableBody 
                datas={this.props.users.tableBody} 
                collumns={this.props.users.tableHead}
                />
            </table>
        </div>
        );
    }
}
 
export default Table;