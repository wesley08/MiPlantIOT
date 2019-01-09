import React, { Component } from "react";
import SidebarList from "../sidebarList/sidebarList";
import  "./sidebar.css";

class Sidebar extends Component {
  render() {
    return (
      <div className="col-2 sidebarcol" style={{ display: this.props.status ,height:"100vh"}}>
        <div className="sidebar " style={{height:"100%"}}>
          <div style={{height:"10%"}}>
            <span className="logoBuncis">
              Buncis
            </span>
          </div>
            <div className="mt-5" style={{height:"90%"}}>
              {this.props.sidebars.map(sidebar => (
                <SidebarList sidebar={sidebar} />
              ))}
            </div>
        </div>
      </div>
    );
  }
}

export default Sidebar;
