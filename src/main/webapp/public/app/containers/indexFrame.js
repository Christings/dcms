import React,{ Component } from 'react'
import { Router, Route, Link, History } from 'react-router'
import Navbars from '../components/navbars'
import { Navbar, Nav, NavItem, NavDropdown, Button, Image,Collapse,Tab,Row, Glyphicon, ListGroup, ListGroupItem, Col, OverlayTrigger, Popover } from "react-bootstrap"

class IndexFrame extends Component{
	mixins: [History]
	constructor(...args){
		super(...args);
		this.state={ 
			open: true,
			open1: false,
			open1_1_1:false,
			open1_1_2:false,
			open1_1_3:false,
			open1_1_4: false,
			open2: false,
			open3: false,
			open4: false,
			open5: false,
			left_cross: 2,
			right_cross: 10,
			wellStyles: {height:"1500",margin: "0",padding: "0", textAlign:'left'}
		};
		this.handleClick = this.handleClick.bind(this);
	}
	handleClick(){
		if(this.state.open){
			this.setState({
				open: false,
				left_cross: 1,
				right_cross: 11
				// wellStyles: {height:"1500",width:"60",margin: "0",padding: "0", textAlign:'left'}
			});
		}else{
			this.setState({
				open: true,
				left_cross: 2,
				right_cross: 10
				// wellStyles: {height:"1500",width:"200",margin: "0",padding: "0", textAlign:'left'}
			});
		}
	}
	render(){
		const styles = { margin:'0',padding:'0'};
		// const wellStyles = {height:"1500",minWidth:"200",margin: "0",padding: "0", textAlign:'left'};
		const secondListStyles = {margin: "auto auto auto 20px"};
		const thirdListStyles = {margin: "auto auto auto 40px"};
		const constantStyles ={margin: "0"};
		const book = (
			<Popover>
				<strong>
					资产管理
				</strong>
				<ListGroup>
					<ListGroupItem>
						<Glyphicon glyph="home"/>机房列表
					</ListGroupItem>
					<ListGroupItem>
						<Glyphicon glyph="book"/>未上架设备列表
					</ListGroupItem>
					<ListGroupItem>
						<Glyphicon glyph="book"/>待确认设备列表
					</ListGroupItem>
					<ListGroupItem>
						<Glyphicon glyph="book"/>区域列表
					</ListGroupItem>
				</ListGroup>
			</Popover>
		);
		return(
			<div>
			<Col style={styles} sm={12}>
                <Navbars />
            </Col>
            <Col style={styles} sm={this.state.left_cross}>
            	<div className="well" style={this.state.wellStyles}>
					<ListGroup style={constantStyles}>
						<ListGroupItem >
								<Glyphicon style={{textAlign:'left'}} onClick={this.handleClick} glyph="align-justify"/>
						</ListGroupItem>
					</ListGroup>
					<Collapse in={this.state.open}>
						<div style={{margin: "auto auto auto 0"}}>
								<ListGroup>

									<ListGroupItem eventKey="first" onClick={ ()=> this.setState({
												open1: !this.state.open1,
												open2: false,
												open3: false,
												open4: false,
												oepn5: false
											})}>
										<Glyphicon glyph="book"/>资产管理
									</ListGroupItem>
									<Collapse in={this.state.open1}>			
										<ListGroup style={constantStyles}>
											<ListGroupItem eventKey="first_1_1" onClick={ ()=> this.setState({
												open1_1_1: !this.state.open1_1_1,
												open1_1_2: false,
												open1_1_3: false,
												open1_1_4: false
											})}>
												<Glyphicon style={secondListStyles} glyph="home"/>机房列表
											</ListGroupItem>
											
											<Collapse in={this.state.open1_1_1}>
												<ListGroup style={constantStyles}>
													<Link to="/A01"><ListGroupItem href="#link3"><Glyphicon style={thirdListStyles} glyph="tower"/>A01</ListGroupItem></Link>
													<Link to="/A02"><ListGroupItem href="#link2"><Glyphicon style={thirdListStyles} glyph="tower"/>A02</ListGroupItem></Link>
													<Link to="/A03"><ListGroupItem href="#link3"><Glyphicon style={thirdListStyles} glyph="tower"/>A03</ListGroupItem></Link>
												</ListGroup>
											</Collapse>
											
											<ListGroupItem eventKey="first_1_2" onClick={ ()=> this.setState({
												open1_1_1: false,
												open1_1_2: !this.state.open1_1_2,
												open1_1_3: false,
												open1_1_4: false
											})}>
												<Glyphicon style={secondListStyles} glyph="paperclip"/>未上架设备列表
											</ListGroupItem>
											
											<Collapse in={this.state.open1_1_2}>
												<ListGroup style={constantStyles}>
													<ListGroupItem href="#link3"><Glyphicon style={thirdListStyles} glyph="tower"/>A01</ListGroupItem>
													<ListGroupItem href="#link2"><Glyphicon style={thirdListStyles} glyph="tower"/>A02</ListGroupItem>
													<ListGroupItem href="#link3"><Glyphicon style={thirdListStyles} glyph="tower"/>A03</ListGroupItem>
												</ListGroup>
											</Collapse>

											<ListGroupItem eventKey="first_1_3" onClick={ ()=> this.setState({
												open1_1_1: false,
												open1_1_2: false,
												open1_1_3: !this.state.open1_1_3,
												open1_1_4: false
											})}>
												<Glyphicon style={secondListStyles} glyph="user"/>待确认设备列表
											</ListGroupItem>
											
											<Collapse in={this.state.open1_1_3}>
												<ListGroup style={constantStyles}>
													<ListGroupItem href="#link3"><Glyphicon style={thirdListStyles} glyph="tower"/>A01</ListGroupItem>
													<ListGroupItem href="#link2"><Glyphicon style={thirdListStyles} glyph="tower"/>A02</ListGroupItem>
													<ListGroupItem href="#link3"><Glyphicon style={thirdListStyles} glyph="tower"/>A03</ListGroupItem>
												</ListGroup>
											</Collapse>
											
											<ListGroupItem eventKey="first_1_4" onClick={ ()=> this.setState({
												open1_1_1: false,
												open1_1_2: false,
												open1_1_3: false,
												open1_1_4: !this.state.open1_1_4
											})}>
												<Glyphicon style={secondListStyles} glyph="retweet"/>区域列表
											</ListGroupItem>
										
											<Collapse in={this.state.open1_1_4}>
												<ListGroup style={constantStyles}>
													<ListGroupItem href="#link3"><Glyphicon style={thirdListStyles} glyph="tower"/>A01</ListGroupItem>
													<ListGroupItem href="#link2"><Glyphicon style={thirdListStyles} glyph="tower"/>A02</ListGroupItem>
													<ListGroupItem href="#link3"><Glyphicon style={thirdListStyles} glyph="tower"/>A03</ListGroupItem>
												</ListGroup>
											</Collapse>
										</ListGroup>				
									</Collapse>

									<ListGroupItem eventKey="second" onClick={ ()=> this.setState({
												open1: false,
												open2: !this.state.open2,
												open3: false,
												open4: false,
												oepn5: false
											})}>
										<Glyphicon glyph="link"/>布线管理
									</ListGroupItem>
									<Collapse in={this.state.open2}>			
										<ListGroup style={constantStyles}>
											<ListGroupItem href="#link2"><Glyphicon style={secondListStyles} glyph="home"/>机房列表</ListGroupItem>
											<ListGroupItem href="#link3"><Glyphicon style={secondListStyles} glyph="home"/>Link 3</ListGroupItem>
										</ListGroup>				
									</Collapse>

									

									<ListGroupItem eventKey="forth" onClick={ ()=> this.setState({
												open1: false,
												open2: false,
												open3: false,
												open4: !this.state.open4,
												oepn5: false
											})}>
										<Glyphicon glyph="cog"/>系统管理
									</ListGroupItem>
									<Collapse in={this.state.open4}>			
										<ListGroup style={constantStyles}>
											<ListGroupItem href="#link2"><Glyphicon style={secondListStyles} glyph="home"/>机房列表</ListGroupItem>
											<ListGroupItem href="#link3"><Glyphicon style={secondListStyles} glyph="home"/>Link 3</ListGroupItem>
										</ListGroup>				
									</Collapse>

									<ListGroupItem eventKey="third" onClick={ ()=> this.setState({
												open1: false,
												open2: false,
												open3: !this.state.open3,
												open4: false,
												oepn5: false
											})}>
										<Glyphicon glyph="Text-width"/>PPT
									</ListGroupItem>
									<Collapse in={this.state.open3}>			
										<ListGroup style={constantStyles}>
											<ListGroupItem href="#link2"><Glyphicon style={secondListStyles} glyph="home"/>机房列表</ListGroupItem>
											<ListGroupItem href="#link3"><Glyphicon style={secondListStyles} glyph="home"/>Link 3</ListGroupItem>
										</ListGroup>				
									</Collapse>

									<ListGroupItem eventKey="fifth" onClick={ ()=> this.setState({
												open1: false,
												open2: false,
												open3: false,
												open4: false,
												oepn5: !this.state.open5
											})}>
										<Glyphicon glyph="new-window"/>更新模型
									</ListGroupItem>
									<Collapse in={this.state.open5}>			
										<ListGroup style={constantStyles}>
											<ListGroupItem href="#link2"><Glyphicon style={secondListStyles} glyph="home"/>机房列表</ListGroupItem>
											<ListGroupItem href="#link3"><Glyphicon style={secondListStyles} glyph="home"/>Link 3</ListGroupItem>
										</ListGroup>				
									</Collapse>
								</ListGroup>
						</div>
					</Collapse>

					<Collapse in={!this.state.open}>
						<ListGroup>
							<OverlayTrigger container={this} trigger="hover" placement="right" overlay={book}>
								<ListGroupItem>
									<Glyphicon style={{textAlign:'left'}}  glyph="book"/>
								</ListGroupItem>
							</OverlayTrigger>
							<ListGroupItem>
								<Glyphicon style={{textAlign:'left'}}  glyph="link"/>
							</ListGroupItem>
							<ListGroupItem>
								<Glyphicon style={{textAlign:'left'}}  glyph="cog"/>
							</ListGroupItem>
							<ListGroupItem>
								<Glyphicon style={{textAlign:'left'}}  glyph="Text-width"/>
							</ListGroupItem>
							<ListGroupItem>
								<Glyphicon style={{textAlign:'left'}}  glyph="new-window"/>
							</ListGroupItem>
						</ListGroup>
					</Collapse>
				</div>
            </Col>
            <Col style={styles} sm={this.state.right_cross}>
            	{this.props.children}
            </Col>
            </div>
		)
	}
}
export default IndexFrame;
