import React, { Component } from 'react'
import { Navbar, Nav, NavItem, NavDropdown, Button, Collapse,Tab,Row, Glyphicon, ListGroup, ListGroupItem, Col } from "react-bootstrap"

class SideBar extends Component{
	constructor(...args){
		super(...args);
		this.state={ 
			open: false,
			open1: false,
			open2: false,
			open3: false,
			open4: false
		};
	}
// <Col sm={2}>
// 				<div style={{textAlign: 'left'}}>
// 					<Button onClick={ ()=> this.setState({ open: !this.state.open })}>
// 						<Glyphicon glyph="align-justify"/>
// 					</Button>
// 					<Collapse in={this.state.open}>
// 						<ListGroup>
// 							<ListGroupItem onClick={ ()=> this.setState({
// 								open1: !this.state.open1,
// 								open2: false,
// 								open3: false
// 							})}>Rank 1</ListGroupItem>
// 							<Collapse in={this.state.open1}>
// 								<ListGroup>
// 									<ListGroupItem href="#link2">Link 2</ListGroupItem>
// 									<ListGroupItem href="#link3">Link 3</ListGroupItem>
// 								</ListGroup>
// 							</Collapse>

// 							<ListGroupItem onClick={ ()=> this.setState({
// 								open2: !this.state.open2,
// 								open1: false,
// 								open3: false
// 							})}>Rank 2</ListGroupItem>
// 							<Collapse in={this.state.open2}>
// 								<ListGroup>
// 									<ListGroupItem href="#link2">Link 2</ListGroupItem>
// 									<ListGroupItem href="#link3">Link 3</ListGroupItem>
// 								</ListGroup>
// 							</Collapse>

// 							<ListGroupItem onClick={ ()=> this.setState({
// 								open3: !this.state.open3,
// 								open2: false,
// 								open1: false
// 							})}>Rank 3</ListGroupItem>
// 							<Collapse in={this.state.open3}>
// 								<ListGroup>
// 									<ListGroupItem href="#link2">Link 2</ListGroupItem>
// 									<ListGroupItem href="#link3">Link 3</ListGroupItem>
// 								</ListGroup>
// 							</Collapse>
							
// 						</ListGroup>
// 					</Collapse>
// 				</div>
// 			</Col>

	render(){
		const wellStyles = {height:"1500",width:"200",margin: '0 auto 10px',float: 'left', textAlign:'left'};
		return(
			<div className="well" style={wellStyles}>
				<Button onClick={ ()=> this.setState({ open: !this.state.open })}>
					<Glyphicon glyph="align-justify"/>
				</Button>
				<Collapse in={this.state.open}>
					<Tab.Container id ="left-tabs-example" defaultActiveKey="first">
						<Row className="clearfix">
							<Col sm={12}>
								<Nav bsStyle="pills" stacked>
									<NavItem eventKey="first" onClick={ ()=> this.setState({
										open1: !this.state.open1,
										open2: false,
										open3: false,
										open4: false
									})}>
										<Glyphicon glyph="book"/>资产管理
									</NavItem>
									<Collapse in={this.state.open1}>
										<Nav >
											<NavItem bsStyle="success" href="#link2"><Glyphicon glyph="home"/>机房列表</NavItem>
											<NavItem href="#link3">Link 3</NavItem>
										</Nav>
									</Collapse>

									<NavItem eventKey="second" onClick={ ()=> this.setState({
										open1: false,
										open2: !this.state.open2,
										open3: false,
										open4: false
									})}>
										<Glyphicon glyph="link"/>配线管理
									</NavItem>
									<Collapse in={this.state.open2}>
										<Nav>
											<NavItem href="#link2">Link 2</NavItem>
											<NavItem href="#link3">Link 3</NavItem>
										</Nav>
									</Collapse>

									<NavItem eventKey="third" onClick={ ()=> this.setState({
										open1: false,
										open2: false,
										open3: !this.state.open3,
										open4: false
									})}>
										<Glyphicon glyph="user"/>设备上架管理
									</NavItem>
									<Collapse in={this.state.open3}>
										<Nav>
											<NavItem href="#link2">Link 2</NavItem>
											<NavItem href="#link3">Link 3</NavItem>
										</Nav>
									</Collapse>

									<NavItem eventKey="forth" onClick={ ()=> this.setState({
										open1: false,
										open2: false,
										open3: false,
										open4: !this.state.open4
									})}>
										<Glyphicon glyph="cog"/>系统管理
									</NavItem>
									<Collapse in={this.state.open4}>
										<Nav>
											<NavItem href="#link2">Link 2</NavItem>
											<NavItem href="#link3">Link 3</NavItem>
										</Nav>
									</Collapse>
								</Nav>
							</Col>
						</Row>
					</Tab.Container>
				</Collapse>
			</div>
			
		);
	}
}

export default SideBar;