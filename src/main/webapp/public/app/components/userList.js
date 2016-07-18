import React,{ Component } from 'react'
import { FormGroup, FormControl, Form, Col, Grid, ControlLabel, Checkbox, Button, Image,ButtonGroup,Panel,DropdownButton} from "react-bootstrap"
import $ from 'jquery'

class UserList extends Component{
	constructor(...args){
		super(...args);
		this.state={
			page: 1,
			count: 10,
			usersData: ""
		};
		this.loadUserMsg = this.loadUserMsg.bind(this);
	}
	loadUserMsg(){
        var userLControl = { page: this.state.page, count: this.state.count};
		$.ajax({
			url: "user/scroll",
			dataType: "json",
			data: JSON.stringify(userLControl),
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
		var totalRecord = this.state.usersData["totalRecord"];
		var totalPage = this.state.usersData["totalPage"];
		console.log("Page" + totalPage + "Record" + totalRecord);
		return(
			<div>
				{list}
			</div>
		);
	}
}

export default UserList;