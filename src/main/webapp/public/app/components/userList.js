import React,{ Component } from 'react'
import { FormGroup, FormControl, Form, Col, Grid, ControlLabel, Checkbox, Button, Image,ButtonGroup,Panel,DropdownButton} from "react-bootstrap"
import $ from 'jquery'

class UserList extends Component{
	constructor(...args){
		super(...args);
		this.state={
			usersData: ""
		};
		this.loadUserMsg = this.loadUserMsg.bind(this);
	}
	loadUserMsg(){
		$.ajax({
			url: "user/getAllUsers",
			dataType: "json",
			data: "",
			contentType: "application/json",
			type: "post"
		}).done((jsonData)=>{
			const D = jsonData["data"];
			this.setState({
				usersData : D
			});
		}).fail((err)=>{

		});
	}
	componentDidMount(){
		this.loadUserMsg();
	}
	render(){
		var users = this.state.usersData;
		var list;
		if(users == ""){
			list =(<p>还没有用户信息</p>);
		}else{
			list = users.map(function(e){
				return(
					<p>e["userName"]</p>
				);
			});
		}

		return(
			<div>
				{list}
			</div>
		);
	}
}

export default UserList;