import React, { Component } from 'react';
import Table from './table/table/table'

class AdminUserContent extends Component {
    state = { 
        table : {
            tableHead: [
             {
                 id:"1",
                 path:"id",
                 label : "No"
             },
             {
                 id:"2",
                 path:"name",
                 label : "name"
             },
             {
                 id:"3",
                 path:"kelas",
                 label :"Kelas"
             },
             {
                 id:"4",
                 path:"nim",
                 label : "NIM"
             },
             {
                 id:"5",
                 path:"gender",
                 label : "Gender"
             },{
                id:"6",
                path:"value",
                label : "Value" 
            },
             {
                 id:"7",
                 content: user => (
                     <div>
                         <button className="btn btn-success btn-sm" onClick={this.handleClick} style={{ width: "100px", marginRight:"10px" }}
                         >Edit
                         </button>
                         <button className="btn btn-danger btn-sm" onClick={this.handleClick2} style={{ width: "100px", marginRight:"10px" }}
                         >Delete
                         </button>
                         <button className="btn btn-info btn-sm" onClick={this.plus} style={{ width: "100px", marginRight:"10px" }}
                         >add value
                         </button>
                     </div>
                 )
             },
         ],
         tableBody :[
             {
                 id: '1',
                 name : 'a 1',
                 kelas: "LB22",
                 nim: "2222222",
                 gender: "a",
                 value:"10"
             },{
                 id: '2',
                 name : 'b 2',
                 kelas: "LB22",
                 nim: "1111111",
                 gender: "a",
                 value:"10"
             }
         ],colors: "bg-info"
         }
     }
     constructor(props) {
        super(props);
        this.handleClick = this.handleClick.bind(this);
      }
      handleClick() {
        var nama = prompt("Edit Profile");
      }
      handleClick2() {
        var nama = prompt("Delete Profile", "");
      }
    render() { 
        return (  
            <div>

                <Table users={this.state.table}/>
            </div>
        );
    }
}
 
export default AdminUserContent;