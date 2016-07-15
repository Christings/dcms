import React,{ Component } from 'react'
import { FormGroup, FormControl, Form, Col, Grid, ControlLabel, Checkbox, Button, Image,ButtonGroup,Panel,DropdownButton} from "react-bootstrap"
import $ from 'jquery'

class UserAdd extends Component{
	constructor(...args){
		super(...args);
		this.state={

		};
		this.handleClick = this.handleClick.bind(this);
	}
	handleClick(){
		console.log("1");
		$(document).ready(function(){
			console.log("2");
			$("#form").submit(function(){
				console.log("3");
				var userName = $("#userName").val();
				var account = $("#account").val();
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
				if (account == ""){
					$("#alert_2").text("请输入用户帐号");
					return false;
				}
				if (password == ""){
					$("#alert_3").text("请输入用户密码");
					return false;
				}
				var userInfo = {userName: '', account: '', password: '', identificationNo:'',mobile:'',sex:'',enabled: '',remark:'',superAdmin:''};
				userInfo['userName'] = userName;
				userInfo['account'] = account;
				userInfo['password'] = password;
				userInfo['identificationNo'] = identificationNo;
				userInfo['mobile'] = mobile;
				userInfo['sex'] = sex;
				userInfo['enabled'] = enabled;
				userInfo['remark'] = remark;
				userInfo['superAdmin'] = superAdmin;
				console.log(userInfo);
				$.ajax({
					type:"post",
					url: "user/addUser",
					dataType: 'json',
					contentType: "application/json",
					data: JSON.stringify(userInfo),
					data: userInfo,
					success: function(res){
						if(res.status == "1"){
							console.log("添加用户成功" + res.data);
						}else{
							console.log("添加用户失败" + res.msg);
						}
					}
				});
				console.log(userInfo);
				return true;
			});
		});
	}

	render(){
		var y = 1;
		var n = 0;
		return(
			<form id = "form">
				<FormGroup >
					<ControlLabel>添加用户</ControlLabel>
					<ControlLabel>用户名</ControlLabel>
					<FormControl id="userName" type="text" placeholder="userName"/>
					<span id="alert_1"></span>
				</FormGroup>
				<FormGroup >
					<ControlLabel>用户帐号</ControlLabel>
					<FormControl id="account" type="text" placeholder="account"/>
					<span id="alert_2"></span>
				</FormGroup>
				<FormGroup >
					<ControlLabel>密码</ControlLabel>
					<FormControl id="password" type="text" placeholder="password"/>
					<span id="alert_3"></span>
				</FormGroup>
				<FormGroup >
					<ControlLabel>身份证</ControlLabel>
					<FormControl id="identificationNo" type="text" placeholder="identificationNo"/>
				</FormGroup>
				<FormGroup >
					<ControlLabel>电话</ControlLabel>
					<FormControl id="mobile" type="text" placeholder="mobile"/>
				</FormGroup>
				<FormGroup >
					<ControlLabel>性别</ControlLabel>
					<select id="sex">
						<option value = {y}>男</option>
						<option value = {n}>女</option>
					</select>
				</FormGroup>

				<FormGroup >
					<ControlLabel>是否启用</ControlLabel>
					<select id="enabled">
						<option value = {y}>是</option>
						<option selected = "selected" value = {n}>否</option>
					</select>
				</FormGroup>
				<FormGroup >
					<ControlLabel>备注</ControlLabel>
					<FormControl id="remark" type="text" placeholder="remark"/>
				</FormGroup>
				
				<FormGroup>
					<ControlLabel>超级管理员</ControlLabel>
					<FormControl id="superAdmin" type="number" placeholder="0or1"/>
				</FormGroup>
				<Button type="submit" onClick={this.handleClick}>
					提交
				</Button>
			</form>
		);
	}
}

export default UserAdd;




