import React,{ Component } from 'react'
import { FormGroup, FormControl, Form, Col, Grid, ControlLabel, Checkbox, Button, Image,ButtonGroup,Panel} from "react-bootstrap"
import $ from 'jquery'

class MenuUpdate extends Component{
	constructor(...args){
		super(...args);
		this.state={
		};
		this.handleClick = this.handleClick.bind(this);
	}
	handleClick(){
		var updateId = this.props.updateMenuData;
		$(document).ready(function(){
			console.log(1);
			$("#form").submit(function(){
				var id = updateId;
				var name = $("#name").val();
				var rank = $("#rank").val();
				var level = $("#level").val();
				var url = $("#url").val();
				var parentId = $("#parentId").val();
				var iconId = $("#iconId").val();
				var type = $("#type").val();
				if(name == "")
				{
					$("#alertName").text("请输入菜单名称");
					return false;
				}
				var menuInfo = { id:'', name:'', rank:'', level:'', url:'', parentId:'', iconId:'', type:''};
				menuInfo['id'] = id;
				menuInfo['name'] = name;
				menuInfo['rank'] = rank;
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
					url:"menu/update",
					dataType: 'json',
					data: menuInfo,
					success:function(res){
						if(res.status == "1"){
							console.log("修改菜单成功");
						}else{
							console.log("修改菜单失败"+res.msg);
						}
					}
				});
				console.log(menuInfo);
				return true;
			});
		});
	}

	render(){
		var arr = [];
		var menuMsg = this.props.updateMenuData;
		console.log("menuMsg" + menuMsg);
		return(	
			<form id = "form">
				<FormGroup controlId="formControlsName">
					<ControlLabel>修改菜单</ControlLabel>
					<ControlLabel>Name</ControlLabel>
					<FormControl id="name" type="text" defaultValue={menuMsg["name"]}/>
					<span id="alertName"></span>
				</FormGroup>
				<FormGroup controlId="formControlrank">
					<ControlLabel>Rank</ControlLabel>
					<FormControl id="rank" type="text" defaultValue={menuMsg["rank"]}/>
				</FormGroup>
				<FormGroup controlId="formControlLevel">
					<ControlLabel>Level</ControlLabel>
					<FormControl id="level" type="number" defaultValue={menuMsg["level"]}/>
				</FormGroup>
				<FormGroup controlId="formControlUrl">
					<ControlLabel>Url</ControlLabel>
					<FormControl id="url" type="text" defaultValue={menuMsg["url"]}/>
				</FormGroup>
				<FormGroup controlId="formControlParentId">
					<ControlLabel>ParentId</ControlLabel>
					<FormControl id="parentId" type="text" defaultValue={menuMsg["parentId"]} />
				</FormGroup>
				<FormGroup controlId="formControlIconId">
					<ControlLabel>iconId</ControlLabel>
					<FormControl id="iconId" type="text" defaultValue={menuMsg["iconId"]} />
				</FormGroup>
				<FormGroup controlId="formControlType">
					<ControlLabel>Type</ControlLabel>
					<FormControl id="type" type="number" defaultValue={menuMsg["type"]} />
				</FormGroup>
				<Button type="submit" onClick={this.handleClick}>
					确认修改
				</Button>
			</form>
		)
	}
}

export default MenuUpdate;