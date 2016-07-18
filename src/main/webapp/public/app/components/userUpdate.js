import React,{ Component } from 'react'
import { FormGroup, FormControl, Form, Col, Grid, ControlLabel, Checkbox, Button, Image,ButtonGroup,Panel,DropdownButton} from "react-bootstrap"
import $ from 'jquery'

class UserUpdate extends Component{
	constructor(...args){
		super(...args);
		this.state={
			usersData: "",
			userData: ""
		};
		this.handleClick = this.handleClick.bind(this);
		this.loadUserMsg = this.loadUserMsg.bind(this);
		this.searchUser = this.searchUser.bind(this);
	}
	loadUserMsg(){
		$.ajax({
			url: "user/getAllUsers",
			dataType: "json",
			data: "",
			type: "post"
		}).done((jsonData)=>{
			const D = jsonData["data"];
			this.setState({
				usersData : D
			});
		}).fail((err)=>{

		});
	}

	handleClick(){
		$(document).ready(function(){
			$("#form").submit(function(){
				var userName = $("#userName").val();
				var realName = $("#realName").val();
				var password = $("#password").val();
				var identificationNo = $("#identificationNo").val();
				var mobile = $("#mobile").val();
				var sex = $("#sex").val();
				var enabled = $("#enabled").val();
				var remark = $("#remark").val();
				var superAdmin = $("#superAdmin").val();
				if (userName == "") {
					$("#alert_1").text("请输入用户名");
					return false;
				};
				if (realName == ""){
					$("#alert_2").text("请输入用户帐号");
					return false;
				}
				if (password == ""){
					$("#alert_3").text("请输入用户密码");
					return false;
				}
				var userInfo = [];
				userInfo['userName'] = userName;
				userInfo['realName'] = realName;
				userInfo['password'] = password;
				userInfo['identificationNo'] = identificationNo;
				userInfo['mobile'] = mobile;
				userInfo['sex'] = sex;
				userInfo['enabled'] = enabled;
				userInfo['remark'] = remark;
				userInfo['superAdmin'] = superAdmin;

				$.ajax({
					type:"post",
					url: "user/updateUser",
					dataType: 'json',
					contentType: "application/json",
					data: JSON.stringify(userInfo),
					success: function(res){
						if(res.status == "1"){
							console.log("修改用户成功");
						}else{
							console.log("修改用户失败" + res.msg);
						}
					}
				});
				console.log(userInfo);
				return true;
			});
		});
	}
	searchUser(e){
		var id = e.target.value;
		var usersData = this.state.usersData;
		for(var i=0,len=usersData.length; i<len; i++){
			if(usersData[i]["id"] == id)
			{
				this.setState({
					userData: usersData[i]
				});
			}
		}
	}
	componentDidMount(){
		this.loadUserMsg();
	}
	render(){

		return(
			<form id = "form">
				<FormGroup >
					<ControlLabel>修改用户信息</ControlLabel>
					<ControlLabel>输入用户Id</ControlLabel>
					<FormControl id="id" type="text" placeholder="id" onChange={this.searchUser()}/>
					<span id="alert_1"></span>
				</FormGroup>
				<FormGroup >
					<ControlLabel>修改用户信息</ControlLabel>
					<ControlLabel>用户名</ControlLabel>
					<FormControl id="userName" type="text" value={this.state.userData["userName"]} placeholder="userName"/>
					<span id="alert_1"></span>
				</FormGroup>
				<FormGroup >
					<ControlLabel>用户帐号</ControlLabel>
					<FormControl id="realName" type="text" value={this.state.userData["realName"]} placeholder="realName"/>
					<span id="alert_2"></span>
				</FormGroup>
				<FormGroup >
					<ControlLabel>密码</ControlLabel>
					<FormControl id="password" type="text" value={this.state.userData["password"]} placeholder="password"/>
					<span id="alert_3"></span>
				</FormGroup>
				<FormGroup >
					<ControlLabel>身份证</ControlLabel>
					<FormControl id="identificationNo" type="text" value={this.state.userData["identificationNo"]} placeholder="identificationNo"/>
				</FormGroup>
				<FormGroup >
					<ControlLabel>电话</ControlLabel>
					<FormControl id="mobile" type="text" value={this.state.userData["mobile"]} placeholder="mobile"/>
				</FormGroup>
				<FormGroup >
					<ControlLabel>性别</ControlLabel>
					<select id="sex" value={this.state.userData["sex"]}>
						<option value = "1">男</option>
						<option value = "0">女</option>
					</select>
				</FormGroup>

				<FormGroup >
					<ControlLabel>是否启用</ControlLabel>
					<select id="enabled" value={this.state.userData["enabled"]}>
						<option value = "1">是</option>
						<option value = "0">否</option>
					</select>
				</FormGroup>
				<FormGroup >
					<ControlLabel>备注</ControlLabel>
					<FormControl id="remark" type="text" value={this.state.userData["remark"]} placeholder="remark"/>
				</FormGroup>
				<FormGroup >
					<ControlLabel>超级管理员</ControlLabel>
					<select id="superAdmin" value={this.state.userData["superAdmin"]}>
						<option value = "1">是</option>
						<option value = "0">否</option>
					</select>
				</FormGroup>
				<Button type="submit" onClick={this.handleClick}>
					提交
				</Button>
			</form>
		);
	}
}

export default UserUpdate;




