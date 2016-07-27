import React,{ Component } from 'react'
import { FormGroup, FormControl, Form, Col, Grid, ControlLabel, Checkbox, Button, Image,ButtonGroup,Panel,DropdownButton} from "react-bootstrap"
import $ from 'jquery'

class UserUpdate extends Component{
	constructor(...args){
		super(...args);
		this.state={
			userData: ""
		};
		this.handleClick = this.handleClick.bind(this);
	}

	handleClick(){
		$(document).ready(function(){
			$("#form").submit(function(){
				// var id = $("#id").val();
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
				userInfo['id'] = this.state.userData["id"];
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
					data: userInfo,
					// contentType: "application/json",
					// data: JSON.stringify(userInfo),
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
	componentDidMount(){
		this.setState({
			userData: this.props.updateUserData
		});
	}
	render(){
		var userData = this.state.updateUserData;
		console.log("userData"+userData);
		return(
			<form id = "form">
				<FormGroup >
					<ControlLabel>修改用户信息</ControlLabel>
				</FormGroup>
				<FormGroup >
					<ControlLabel>用户名</ControlLabel>
					<FormControl id="userName" type="text" defaultValue={userData["userName"]} placeholder="userName"/>
					<span id="alert_1"></span>
				</FormGroup>
				<FormGroup >
					<ControlLabel>用户帐号</ControlLabel>
					<FormControl id="realName" type="text" defaultValue={userData["realName"]} placeholder="realName"/>
					<span id="alert_2"></span>
				</FormGroup>
				<FormGroup >
					<ControlLabel>密码</ControlLabel>
					<FormControl id="password" type="text" defaultValue={userData["password"]} placeholder="password"/>
					<span id="alert_3"></span>
				</FormGroup>
				<FormGroup >
					<ControlLabel>身份证</ControlLabel>
					<FormControl id="identificationNo" type="text" defaultValue={userData["identificationNo"]} placeholder="identificationNo"/>
				</FormGroup>
				<FormGroup >
					<ControlLabel>电话</ControlLabel>
					<FormControl id="mobile" type="text" defaultValue={userData["mobile"]} placeholder="mobile"/>
				</FormGroup>
				<FormGroup >
					<ControlLabel>性别</ControlLabel>
					<select id="sex" defaultValue={userData["sex"]}>
						<option value = "1">男</option>
						<option value = "0">女</option>
					</select>
				</FormGroup>

				<FormGroup >
					<ControlLabel>是否启用</ControlLabel>
					<select id="enabled" defaultValue={userData["enabled"]}>
						<option value = "1">是</option>
						<option value = "0">否</option>
					</select>
				</FormGroup>
				<FormGroup >
					<ControlLabel>备注</ControlLabel>
					<FormControl id="remark" type="text" defaultValue={userData["remark"]} placeholder="remark"/>
				</FormGroup>
				<FormGroup >
					<ControlLabel>超级管理员</ControlLabel>
					<select id="superAdmin" defaultValue={userData["superAdmin"]}>
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




