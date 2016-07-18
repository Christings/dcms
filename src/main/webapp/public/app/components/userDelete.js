import React,{ Component } from 'react'
import { FormGroup, FormControl, Form, Col, Grid, ControlLabel, Checkbox, Button, Image,ButtonGroup,Panel,DropdownButton} from "react-bootstrap"
import $ from 'jquery'

class UserDelete extends Component{
	constructor(...args){
		super(args);
		this.state=({

		});
		this.handleClick = this.handleClick.bind(this);
	}

	handleClick(){
		$(document).ready(function(){
			$("#form").submit(function(){
				var id = $("#id").val();
				
				if (id == ""){
					$("#alert").text("请输入用户Id");
					return false;
				}

				var userInfo = [];
				userInfo['id'] = id;

				$.ajax({
					type:"post",
					url: "user/updateUserDelete",
					contentType: "application/json",
					data: JSON.stringify(userInfo),
					success: function(res){
						if(res.status == "1"){
							console.log("删除用户成功");
						}else{
							console.log("删除用户失败" + res.msg);
						}
					}
				});
				console.log(userInfo);
				return true;
			});
		});
	}

	render(){
		var deleUserData = this.props.deleteUserData;
		return(
			<form id = "form">
				<FormGroup >
					<ControlLabel>删除用户信息</ControlLabel>
					<ControlLabel>{deleUserData["realName"]}</ControlLabel>
					<FormControl id="id" type="text" defaultValue = {deleUserData["id"]} />
					<span id="alert"></span>
				</FormGroup>
				<Button type="submit" onClick={this.handleClick}>
					确认删除
				</Button>
			</form>
		);
	}
}

export default UserDelete;