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

		var userId = this.props.deleteUserData["id"];
		var deleted = this.props.deleteUserData["deleted"];
		// if (id == ""){
		// 	$("#alert").text("请输入用户Id");
		// 	return false;
		// }

		var userInfo = [];
		userInfo['userId'] = userId;
		userInfo['deleted'] = deleted;
		$.ajax({
			type:"post",
			url: "user/updateUserDelete",
			dataType: "json",
			data: userInfo,
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
	}

	render(){
		var userData = this.props.deleteUserData;
		console.log("de-userData"+userData);
		return(
			<div>
				<div>
					<ControlLabel>删除-{userData["realName"]}-信息</ControlLabel>
				</div>
				<Button onClick={this.handleClick}>
					确认删除
				</Button>
			</div>
		);
	}
}

export default UserDelete;