import React, { Component } from "react";
import "./navbar.css";


class Navbar extends Component {
  state = {};
  render() {
    return (
      <nav className="navbar navbar-expand-lg navbar-light bgcolorr ">
        <button
          className="navbar-toggler"
          type="button"
          data-toggle="collapse"
          data-target="#navbarTogglerDemo01"
          aria-controls="navbarTogglerDemo01"
          aria-expanded="false"
          aria-label="Toggle navigation"
        >
          <span className="navbar-toggler-icon " style={{fontSize:"1.96vw"}} />
        </button>
        <div className="collapse navbar-collapse navbardif" id="navbarTogglerDemo01">
          <ul className="navbar-nav mr-auto mt-2 mt-lg-0">
            <li className="nav-item active">
              <a className="nav-link" href="#">
                <button 
                  onClick={this.props.onToggle}
                  className="btn btn-outline-info my-2 my-sm-0 btnborder"
                >
                   <i className="fa fa-bars" />
                </button>
              </a>
            </li>
          </ul>
        </div>
      </nav>
    );
  }
}

export default Navbar;
