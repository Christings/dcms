import React,{ Component } from 'react'
import { FormGroup, FormControl, Form, Col, Grid, ControlLabel, Checkbox, Button, Image,ButtonGroup,Panel} from "react-bootstrap"
import $ from 'jquery'

class MenuChildSearch extends Component{
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
					$("#alert").text("请输入父菜单名称");
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
							console.log("查找子菜单成功");
						}else{
							console.log("查找子菜单失败"+res.msg);
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
				<FormGroup>
					<ControlLabel>父菜单ID</ControlLabel>
					<FormControl id="id" type="text" placeholder="id"/>
					<span id="alert"></span>
				</FormGroup>
				<Button type="submit" onClick={this.handleClick}>
					提交
				</Button>
			</form>
		)
	}
}

export default MenuChildSearch;