import React,{ Component } from 'react'
import { Modal,Table,Navbar, Nav, NavItem, NavDropdown, Button, Image,Collapse,Tab,Row, Glyphicon, ListGroup, ListGroupItem, Col, OverlayTrigger, Popover } from "react-bootstrap"
import $ from 'jquery'

import UserAdd from './userAdd'
import UserUpdate from './userUpdate'
import UserDelete from './userDelete'
class UserManage extends Component{

	constructor(...args){
		super(...args);

		this.state={
			userData: "",
			pageNum: 1,
			pageSize: 10,
			updateUserData: "",
			deleteUserData: "",
			showUserAdd : false,
			showUserUpdate : false,
			showUserDelete : false
		};
		// this.handleClick = this.handleClick.bind(this);
		this.loadUserMsg = this.loadUserMsg.bind(this);
		this.userAdd = this.userAdd.bind(this);
		this.userDelete = this.userDelete.bind(this);
		this.userUpdate = this.userUpdate.bind(this);
		
		this.userAddClose = this.userAddClose.bind(this);
		this.userUpdateClose = this.userUpdateClose.bind(this);
		this.userDeleteClose = this.userDeleteClose.bind(this);
	}
	loadUserMsg(){
		//var getUserData = {pageNum: "1", pageSize: "10"}
		var getUserData = {pageNum: "1", pageSize: "10"}
		// var getUserData = {page: "1", count: "10"}
		$.ajax({
			//url: "user/getDataGrid",
			url: "user/getDataGrid",
			// url: "menu/datagrid",
			dataType: "json",
			//contentType: "application/json",
			// contentType: "application/x-www-form-urlencoded",
			// data: JSON.stringify(getUserData),
			data : getUserData,
			type: "get",
			success: function(res){
				if(res.status == "1"){
					console.log("binggo");
					console.log(res["data"]);
					console.log(res["data"].records);
					console.log(res["data"].records[0]);
					this.setState({
						userData: res["data"]
					});
				}else{
					console.log("失败");
				}
			}
		});
		// .done((jsonData)=>{
		// 	console.log("jsonData"+jsonData);
		// 	const D = jsonData.data;
		// 	console.log("D"+D);
		// 	this.setState({
		// 		userData : D
		// 	});
		// }).fail((err)=>{
		// 	console.log(err);
		// });
	}
	userAdd(){
		this.setState( {showUserAdd : true});
	}

	userUpdate(e){
		var data = e.target.value;
		this.setState( {
			updateUserData: data,
			showUserUpdate : true
		});
	}
	userDelete(e){
		var data = e.target.value;
		this.setState({
			deleteUserData : data,
			showUserDelete : true
		});
	}
	
	userAddClose(){
		this.setState({ showUserAdd: false });
	}
	userUpdateClose(){
		this.setState({ showUserUpdate: false });
	}
	userDeleteClose(){
		this.setState({ showUserDelete: false });
	}
	componentWillMount(){
		console.log("what");
		this.loadUserMsg();
	}
	componentDidMount(){
		this.loadUserMsg();
		console.log("biangbiang");
	}
	render(){
		var getUserData = {pageNum: "1", pageSize: "10"}
		var userMsg;
		// var getUserData = {page: "1", count: "10"}
		// $.ajax({
		// 	//url: "user/getDataGrid",
		// 	url: "user/getDataGrid",
		// 	// url: "menu/datagrid",
		// 	dataType: "json",
		// 	//contentType: "application/json",
		// 	// contentType: "application/x-www-form-urlencoded",
		// 	// data: JSON.stringify(getUserData),
		// 	data : getUserData,
		// 	type: "get",
		// 	success: function(res){
		// 		if(res.status == "1"){
		// 			console.log("binggo");
		// 			console.log(res["data"]);
		// 			console.log(res["data"].records);
		// 			console.log(res["data"].records[0]);
		// 			userMsg = res["data"].records;
		// 		}else{
		// 			console.log("失败");
		// 		}
		// 	}
		// });

		var user = this.state.userData;
		console.log("user"+user);
		var userMsg = user.records;
		console.log("userMsg"+userMsg);
		var arr = [];
		for(var j = 0; j < userMsg.length; j++)
		{
			arr.push({
				userName: userMsg[j]["userName"],
				realName: userMsg[j]["realName"],
				superAdmin: userMsg[j]["superAdmin"],
				enabled: userMsg[j]["enabled"],
				id: userMsg[j]["id"]
			});
		}
		var that = this;
		var num = 0;
		var content = arr.map(function(user){
			num++;
			var role;
			switch(user["superAdmin"]){
				case 0:
					role = "普通用户" ;
					break;
				case 1:
					role = "管理员";
					break;
				default :
					role = "普通用户";
					break;
			}
			var status;
			switch(user["enabled"]){
				case 0:
					status = "未激活";
					break;
				case 1:
					status = "激活";
					break;
				default:
					status = "未激活";
					break;
			}
			return(
				<tr>
					<td>{num}</td>
					<td>{user["userName"]}</td>
					<td>{user["realName"]}</td>
					<td>{role}</td>
					<td>{status}</td>
					<td>操作</td>
				
					<td>
						<label value={user} onClick={that.userUpdate}>编辑</label>|
						<label value={user} onClick={that.userDelete}>删除</label>
					</td>
				</tr>
			);
		});
		return(
			<div>
				<Col sm={10}>
				<ListGroup style={{padding: '0',margin: '0'}}>
					<ListGroupItem>
						用户管理
					</ListGroupItem>
				</ListGroup>
				<Nav bsStyle="pills" activeKey={1} onSelect={this.userAdd}>
				    <NavItem eventKey={1} onClick={this.userAdd}><Glyphicon  glyph="plus" />添加用户</NavItem>
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
				<Modal show = {this.state.showUserAdd} onHide={this.userAddClose}>
					<Modal.Header closeButton>
						<Modal.Title>菜单录入</Modal.Title>
					</Modal.Header>
					<Modal.Body>
						<UserAdd />
					</Modal.Body>
				</Modal>
				<Modal show = {this.state.showUserUpdate} onHide={this.userUpdateClose}>
					<Modal.Header closeButton>
						<Modal.Title>菜单编辑</Modal.Title>
					</Modal.Header>
					<Modal.Body>
						{/*<UserUpdate updateUserData={this.state.updateUserData} />*/}
					</Modal.Body>
				</Modal>
				<Modal show = {this.state.showUserDelete} onHide={this.userDeleteClose}>
					<Modal.Header closeButton>
						<Modal.Title>菜单删除</Modal.Title>
					</Modal.Header>
					<Modal.Body>
						{/*<UserDelete deleteUserData={this.state.deleteUserData} />*/}
					</Modal.Body>
				</Modal>
				</Col>
			</div>
		);
	}

}

export default UserManage;


















