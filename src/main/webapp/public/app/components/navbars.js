import React, { Component } from 'react'
import { Navbar, Nav, NavItem, NavDropdown, MenuItem } from "react-bootstrap"

const Navbars = React.createClass({
	render(){
		return(
			<Navbar inverse>
				<Navbar.Header>
				    <Navbar.Brand >
				        <a href="#">3S DCIM 数据中心基础设施可视化管理平台</a>
				    </Navbar.Brand>
				    <Navbar.Toggle />
			    </Navbar.Header>
			    <Navbar.Collapse>
			      	<Nav pullRight>
			        	<NavItem eventKey={1} href="#">Link Right</NavItem>
			        	<NavItem eventKey={2} href="#">Link Right</NavItem>

			        	<NavDropdown eventKey={3} title="admin" id="basic-nav-dropdown">
					        <MenuItem eventKey={3.1}>修改密码</MenuItem>
				          	<MenuItem eventKey={3.2}>退出</MenuItem>
				          	<MenuItem eventKey={3.3}>Something else here</MenuItem>
				          	<MenuItem divider />
				          	<MenuItem eventKey={3.3}>Separated link</MenuItem>
			        	</NavDropdown>
			        	<NavItem eventKey={4} href="#">Link Right</NavItem>
			        	<NavItem eventKey={5} href="#">Link Right</NavItem>
			      	</Nav>
			    </Navbar.Collapse>
			</Navbar>
		);
	}
});

export default Navbars;