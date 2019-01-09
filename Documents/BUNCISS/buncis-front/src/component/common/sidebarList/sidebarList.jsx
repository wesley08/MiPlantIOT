import React from "react";
import "./sidebarList.css";

const SidebarList = props => {
  return ( 
    <div className="row sidebar-list marginoff">
      <div className="col-3">
        <h6 className="middle" >
          <i className={"fa fa-" + props.sidebar.icon} />
        </h6>
      </div>
      <div className="col-9">
        <h6 style={{ fontSize:"1.3vw"}}>{props.sidebar.name}</h6>
      </div>
    </div>
  );
};

export default SidebarList;
