import React,{ Component } from 'react'
import { FormGroup, FormControl, Form, Col, Grid, ControlLabel, Checkbox, Button, Image,ButtonGroup,Panel} from "react-bootstrap"
import $ from 'jquery'

class MenuDelete extends Component{
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
				var id = $("#id").val();
				
				if(id == "")
				{
					$("#alert").text("请输入菜单名称");
					return false;
				}
				var menuInfo = {key:''};
				menuInfo["key"] = id;
				$.ajax({
					type:"post",
					url:"menu/delete",
					dataType: 'json',
					data: menuInfo,
					success:function(res){
						if(res.status == "1"){
							console.log("删除菜单成功");
						}else{
							console.log("删除菜单失败"+res.msg);
						}
					}
				});
				console.log(menuInfo);
				return true;
			});
		});
	}
	
	render(){
		// var data = this.props.data;
		return(	
			<form id = "form">
				<FormGroup>
					<ControlLabel>删除菜单{this.props.deleteMenuData["name"]}</ControlLabel>
					<FormControl id="id" type="text" defaultValue={this.props.deleteMenuData["id"]}/>
					<span id="alert"></span>
				</FormGroup>
				<Button type="submit" onClick={this.handleClick}>
					确认删除
				</Button>
			</form>
		)
	}
}

export default MenuDelete;