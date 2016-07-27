import React,{ Component } from 'react'
import { Pagination,Modal,Table,Navbar, Nav, NavItem, NavDropdown, Button, Image,Collapse,Tab,Row, Glyphicon, ListGroup, ListGroupItem, Col, OverlayTrigger, Popover } from "react-bootstrap"
import $ from 'jquery'

import UserAdd from './userAdd'
import UserUpdate from './userUpdate'
import UserDelete from './userDelete'
class UserGridManage extends Component{

	constructor(...args){
		super(...args);

		this.state={
			menuData: "",
			maxButton: 5,
			pageNum: 1,
			pageSize: 10,
			totalPage: "",
			activePage: 1,
			updateMenuData: "",
			deleteMenuData: "",
			showMenuAdd : false,
			showMenuUpdate : false,
			showMenuDelete : false
		};
		// this.handleClick = this.handleClick.bind(this);
		this.loadMenuMsg = this.loadMenuMsg.bind(this);
		this.handleSelect = this.handleSelect.bind(this);
		this.handleSelectPageSize = this.handleSelectPageSize.bind(this);
		this.menuAdd = this.menuAdd.bind(this);
		this.menuDelete = this.menuDelete.bind(this);
		this.menuUpdate = this.menuUpdate.bind(this);
		
		this.menuAddClose = this.menuAddClose.bind(this);
		this.menuUpdateClose = this.menuUpdateClose.bind(this);
		this.menuDeleteClose = this.menuDeleteClose.bind(this);
	}
	loadMenuMsg(pageNum,pageSize){
		var getMenuData = {pageNum: pageNum, pageSize: pageSize};
		$.ajax({
			url: "user/getDataGrid",
			dataType: "json",
			data: getMenuData,
			type: "get"
		}).done((jsonData)=>{
			const D = jsonData["data"]["records"];
			const totalPage = jsonData["data"]["pageCount"];
			this.setState({
				menuData : D,
				totalPage: totalPage
			});
		}).fail((err)=>{

		});
	}
	handleSelect(eventKey){
		this.setState({
			activePage: eventKey,
			pageNum: eventKey
		});
		this.loadMenuMsg(eventKey,this.state.pageSize);

	}
	handleSelectPageSize(e){
		var eventKey = e.target.value;
		console.log("selected"+eventKey);
		this.setState({
			pageSize: eventKey
		});
		this.loadMenuMsg(this.state.pageNum,eventKey);
	}
	menuAdd(){
		this.setState( {showMenuAdd : true});
	}

	menuUpdate(e){
		var data = e.target.value;
		console.log("data"+data);
		this.setState( {
			updateMenuData: data,
			showMenuUpdate : true
		});
	}
	menuDelete(e){
		var data = e.target.value;
		this.setState({
			deleteMenuData : data,
			showMenuDelete : true
		});
	}
	
	menuAddClose(){
		this.setState({ showMenuAdd: false });
	}
	menuUpdateClose(){
		this.setState({ showMenuUpdate: false });
	}
	menuDeleteClose(){
		this.setState({ showMenuDelete: false });
	}
	componentWillMount(){
		console.log("what");
	}
	componentDidMount(){
		this.loadMenuMsg(this.state.pageNum,this.state.pageSize);
		console.log("biangbiang");
	}
	componentWillReceive(){
		// this.loadMenuMsg(this.state.pageNum,this.state.pageSize);
	}
	render(){
		var menuMsg = this.state.menuData;
		console.log("gridMsg:"+menuMsg);
		console.log("totalPage"+this.state.totalPage);
		var arr = [];
		for(var j = 0; j < menuMsg.length; j++)
		{
			arr.push({
				realName: menuMsg[j]["realName"],
				userName: menuMsg[j]["userName"],
				password: menuMsg[j]["password"],
				identificationNo: menuMsg[j]["identificationNo"],
				id: menuMsg[j]["id"],
				enabled: menuMsg[j]["enabled"],
				superAdmin : menuMsg[j]["superAdmin"],
				deleted: menuMsg[j]["deleted"],
				sex: menuMsg[j]["sex"],
				mobile: menuMsg[j]["mobile"],
				remark: menuMsg[j]["remark"]
			});
		
		}
		var that = this;
		var num = 0;
		var content = arr.map(function(menu){
			num++;
			var userRole;
			var userState;
			switch(menu["superAdmin"]){
				case 0:
					userRole = "普通用户" ;
					break;
				case 1:
					userRole = "管理员";
					break;
				default :
					userRole = "普通用户";
					break;
			}
			switch(menu["enabled"]){
				case 0:
					userState = "未激活";
					break;
				case 1:
					userState = "激活";
					break;
				default:
					userState = "未激活";
					break;
			}
			return(
				<tr>
					<td>{num}</td>
					<td>{menu["userName"]}</td>
					<td>{menu["realName"]}</td>
					<td>{userRole}</td>
					<td>{userState}</td>
					<td>
						<label value={menu} onClick={that.menuUpdate}>编辑</label>|
						<label value={menu} onClick={that.menuDelete}>删除</label>
					</td>
				</tr>
			);
		});
		// console.log();
		console.log("state-update:"+this.state.updateMenuData);
		console.log("open-update:"+this.state.showMenuUpdate);
		var userUpdateElement = (<UserUpdate updateUserData={this.state.updateMenuData} />);
		var userDeleteElement = (<UserDelete deleteUserData={this.state.deleteMenuData} />);
		return(
			<div>
				<Col sm={10}>
				<ListGroup style={{padding: '0',margin: '0'}}>
					<ListGroupItem>
						用户管理
					</ListGroupItem>
				</ListGroup>
				<Nav bsStyle="pills" activeKey={1} onSelect={this.menuAdd}>
				    <NavItem eventKey={1} onClick={this.menuAdd}><Glyphicon  glyph="plus" />添加用户</NavItem>
				</Nav>
				<Table striped bordered condensed hover>
					<thead>
						<tr>
							<th></th>
							<th>用户名称</th>
							<th>真实姓名</th>
							<th>角色</th>
							<th>状态</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						{content}
					</tbody>
				</Table>
				<div>
					<Col sm={2}>
						<label>每页显示
							<select onChange={this.handleSelectPageSize}>
								<option selected = "selected" value = {this.state.pageSize}>{this.state.pageSize}</option>
								<option value = '2'>2</option>
								<option value = '5'>5</option>
								<option value = '10'>10</option>
								<option value = '15'>15</option>
							</select>行
						</label>
					</Col>
					<Col sm={8}>
						<Pagination prev next first last boundaryLinks items={this.state.totalPage} maxButtons={this.state.maxButton} activePage={this.state.activePage} onSelect={this.handleSelect}/>
					</Col>
				</div>
				<Modal show = {this.state.showMenuAdd} onHide={this.menuAddClose}>
					<Modal.Header closeButton>
						<Modal.Title>菜单录入</Modal.Title>
					</Modal.Header>
					<Modal.Body>
						<UserAdd />
					</Modal.Body>
				</Modal>
				<Modal show = {this.state.showMenuUpdate} onHide={this.menuUpdateClose}>
					<Modal.Header closeButton>
						<Modal.Title>菜单编辑</Modal.Title>
					</Modal.Header>
					<Modal.Body>
						{userUpdateElement}
					</Modal.Body>
				</Modal>
				<Modal show = {this.state.showMenuDelete} onHide={this.menuDeleteClose}>
					<Modal.Header closeButton>
						<Modal.Title>菜单删除</Modal.Title>
					</Modal.Header>
					<Modal.Body>
						{userDeleteElement}
					</Modal.Body>
				</Modal>

				</Col>
			</div>
		);
	}

}

export default UserGridManage;


















