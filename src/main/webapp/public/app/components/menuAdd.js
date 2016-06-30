import React,{ Component } from 'react'
import { FormGroup, FormControl, Form, Col, Grid, ControlLabel, Checkbox, Button, Image,ButtonGroup,Panel} from "react-bootstrap"
import $ from 'jquery'

class MenuAdd extends Component{
	constructor(...args){
		super(...args);
		this.state={

		};
		this.handleClick = this.handleClick.bind(this);
	}
	handleClick(){
		$(document).ready(function(){
			console.log(1);
			$("#form").submit(function(){
				var name = $("#name").val();
				var order = $("#order").val();
				var level = $("#level").val();
				var url = $("#url").val();
				var parentId = $("#parentId").val();
				var iconId = $("#iconId").val();
				var type = $("#type").val();
				if(name == "")
				{
					$("#alert").text("请输入菜单名称");
					return false;
				}
				var menuInfo = { name:'', order:'',parentId:'', level:'', url:'', iconId:'', type:''};
				menuInfo['name'] = name;
				menuInfo['order'] = order;
				if(level == ''){
					menuInfo['level'] = 1;
				}
				else{
					menuInfo['level'] = level;
				}
				menuInfo['url'] = url;
				menuInfo['parentId'] = parentId;
				menuInfo['iconId'] = iconId;
				menuInfo['type'] = type;

				$.ajax({
					type:"post",
					url:"menu/add",
					dataType: 'json',
					data: menuInfo,
					success:function(res){
						if(res.status == "1"){
							console.log("添加菜单成功");
						}else{
							console.log("添加菜单失败"+res.msg);
						}
					}
				});
				console.log(menuInfo);
				return true;
			});
		});
	}
	
	render(){
		return(	
			<form id = "form">
				<FormGroup controlId="formControlsName">
					<ControlLabel>Name</ControlLabel>
					<FormControl id="name" type="text" placeholder="name"/>
					<span id="alert"></span>
				</FormGroup>
				<FormGroup controlId="formControlOrder">
					<ControlLabel>Order</ControlLabel>
					<FormControl id="order" type="text" placeholder="order"/>
				</FormGroup>
				<FormGroup controlId="formControlLevel">
					<ControlLabel>Level</ControlLabel>
					<FormControl id="level" type="number" placeholder="level"/>
				</FormGroup>
				<FormGroup controlId="formControlUrl">
					<ControlLabel>Url</ControlLabel>
					<FormControl id="url" type="text" placeholder="url"/>
				</FormGroup>
				<FormGroup controlId="formControlParentId">
					<ControlLabel>ParentId</ControlLabel>
					<FormControl id="parentId" type="text" placeholder="parentId"/>
				</FormGroup>
				<FormGroup controlId="formControlIconId">
					<ControlLabel>iconId</ControlLabel>
					<FormControl id="iconId" type="text" placeholder="1"/>
				</FormGroup>
				<FormGroup controlId="formControlType">
					<ControlLabel>Type</ControlLabel>
					<FormControl id="type" type="number" placeholder="type"/>
				</FormGroup>
				<Button type="submit" onClick={this.handleClick}>
					提交
				</Button>
			</form>
		)
	}
}

export default MenuAdd;