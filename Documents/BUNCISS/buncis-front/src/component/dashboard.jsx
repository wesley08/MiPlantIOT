import React, { Component } from 'react';
import Navbar from './common/Navbar/navbar';
import Sidebar from './common/sidebar/sidebar';
import AdminUserContent from './common/adminUserContent'
import "./styles.css";

class Dashboard extends Component {
    state = { 
        status: {
            sidebar: "block",
            content: "col-10",
            content2: "col-10 modiff"
        }, 
        sidebars: [
            {
              id: 1,
              name: "Dashboard",
              icon: "dashboard",
              nameClass: "dasboard"
            },
            {
              id: 2,
              name: "Corporate",
              icon: "building",
              nameClass: "corporate"
            },
            {
              id: 2,
              name: "Collaboration",
              icon: "id-card",
              nameClass: "collaboration"
            }
        ]
    }
    constructor() {
        super();
        this.toggleSidebar = this.toggleSidebar.bind(this);
      }
    
      toggleSidebar() {
        const status = {
          sidebar: this.state.status.sidebar === "block" ? "none" : "block",
          content: this.state.status.content === "col-10" ? "col-12" : "col-10"
        };
        this.setState({ status: status });
      }
    render() { 
        return (
            <div id="wrapper" className="row modifrow">
                <Sidebar
                    status={this.state.status.sidebar}
                    sidebars={this.state.sidebars}
                />
                <div className={this.state.status.content +  " bgcolor"}>
                 <Navbar onToggle={this.toggleSidebar} />
                   <div className="container-fluid">
                        <div className="mt-4">
                            <AdminUserContent />
                        </div>
                    </div>    
                </div>
            </div>
        );
    }
}
 
export default Dashboard;