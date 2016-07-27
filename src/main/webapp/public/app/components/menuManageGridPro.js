
import React,{ Component } from 'react'
import { Pagination,Modal,Table,Navbar, Nav, NavItem, NavDropdown, Button, Image,Collapse,Tab,Row, Glyphicon, ListGroup, ListGroupItem, Col, OverlayTrigger, Popover } from "react-bootstrap"
import $ from 'jquery'

import MenuAdd from './menuAdd'
import MenuUpdate from './menuUpdate'
import MenuDelete from './menuDelete'
class MenuGridManagePro extends Component{

	constructor(...args){
		super(...args);

		this.state={
			menuData: "",
			menuTree: "",
			opens: [],//
			maxButton: 5,
			pageNum: 1,
			pageSize: 10,
			totalPage: "",
			activePage: 1,
			updateMenuData: "",
			deleteMenuData: "",
			showMenuAdd : false,
			showMenuUpdate : false,
			showMenuDelete : false
		};
		// this.handleClick = this.handleClick.bind(this);
		this.loadMenuMsg = this.loadMenuMsg.bind(this);
		this.loadMenuTree = this.loadMenuTree.bind(this);
		this.callBack = this.callBack.bind(this);
		this.initiateOpen = this.initiateOpen.bind(this);

		this.handleSelect = this.handleSelect.bind(this);
		this.handleSelectPageSize = this.handleSelectPageSize.bind(this);
		this.menuAdd = this.menuAdd.bind(this);
		this.menuDelete = this.menuDelete.bind(this);
		this.menuUpdate = this.menuUpdate.bind(this);
		
		this.menuAddClose = this.menuAddClose.bind(this);
		this.menuUpdateClose = this.menuUpdateClose.bind(this);
		this.menuDeleteClose = this.menuDeleteClose.bind(this);


	}
	loadMenuMsg(pageNum,pageSize){
		var getMenuData = {pageNum: pageNum, pageSize: pageSize};
		$.ajax({
			url: "menu/datagrid",
			dataType: "json",
			data: getMenuData,
			type: "post"
		}).done((jsonData)=>{
			const D = jsonData["data"]["records"];
			const totalPage = jsonData["data"]["pageCount"];
			this.setState({
				menuData : D,
				totalPage: totalPage
			});
		}).fail((err)=>{

		});
	}

	loadMenuTree(){
		$.ajax({
			url: "menu/tree",
			dataType: "json",
			data: "",
			type: "post"
		}).done((jsonData)=>{
			const D = jsonData["data"];
			this.setState({
				menuTree : D
			})
		}).fail((err)=>{

		});
	}

	callBack(menu, menus){
		var openStates = this.state.opens;
		var id = menu["id"];
		var level = menu["level"];
	 	openStates[id] = !this.state.opens[id];
	 	for(var i=0,len=menus.length; i<len; i++){
	 		var ids = menus[i]["id"];
	 		if((menus[i]["level"] == level)&&(ids != id)){
	 			openStates[ids] = false;
	 		}
	 	}
		// this.props.actions.menuOpen(opens);
		this.setState({
			opens: openStates
		});
		console.log("id:"+id);
		console.log(this.state.opens[id]);
	}

	initiateOpen(menuData){
		var openStates = [];
		for(var i=0,len = menuData.length; i < len; i++){
			if(menuData[i]["childMenu"] != null){
				var id = menuData[i]["id"];
				openStates[id] = true;
			}
		}
		this.setState({
			opens: openStates
		});
	}

	handleSelect(eventKey){
		this.setState({
			activePage: eventKey,
			pageNum: eventKey
		});
		this.loadMenuMsg(eventKey,this.state.pageSize);

	}
	handleSelectPageSize(e){
		var eventKey = e.target.value;
		console.log("selected"+eventKey);
		this.setState({
			pageSize: eventKey
		});
		this.loadMenuMsg(this.state.pageNum,eventKey);
	}
	menuAdd(){
		this.setState({showMenuAdd : true});
	}

	menuUpdate(e){
		var data = e.target.value;
		//console.log("data"+data);
		this.setState( {
			updateMenuData: data,
			showMenuUpdate : true
		});
	}
	menuDelete(e){
		var data = e.target.value;
		this.setState({
			deleteMenuData : data,
			showMenuDelete : true
		});
	}
	
	menuAddClose(){
		this.setState({ showMenuAdd: false });
	}
	menuUpdateClose(){
		this.setState({ showMenuUpdate: false });
	}
	menuDeleteClose(){
		this.setState({ showMenuDelete: false });
	}
	componentWillMount(){
		console.log("what");
	}
	componentDidMount(){
		this.loadMenuMsg(this.state.pageNum,this.state.pageSize);
		this.loadMenuTree();
		this.initiateOpen(this.state.menuData);
		//console.log("biangbiang");
	}
	componentWillReceive(){
		// this.loadMenuMsg(this.state.pageNum,this.state.pageSize);
	}
	render(){
		var menuMsg = this.state.menuData;
		var menuTree = this.state.menuTree;
		console.log("gridMsg:"+menuMsg);
		// var arr = [];
		// for(var j = 0; j < menuMsg.length; j++)
		// {
		// 	arr.push({
		// 		name: menuMsg[j]["name"],
		// 		iconId: menuMsg[j]["iconId"],
		// 		rank: menuMsg[j]["rank"],
		// 		id: menuMsg[j]["id"],
		// 		type: menuMsg[j]["type"],
		// 		parentId : menuMsg[j]["parentId"],
		// 		url: menuMsg[j]["url"],
		// 		level : menuMsg[j]["level"]
		// 	});
		
		// }
		// var that = this;
		// var num = 0;
		// var content = arr.map(function(menu){
		// 	num++;
		// 	return(
		// 		<tr>
		// 			<td>{num}</td>
		// 			<td>{menu["name"]}</td>
		// 			<td>{menu["iconId"]}</td>
		// 			<td>{menu["type"]}</td>
		// 			<td>{menu["id"]}</td>
		// 			<td>{menu["level"]}</td>
		// 			<td>{menu["rank"]}</td>
		// 			<td>
		// 				<label value={menu} onClick={that.menuUpdate}>编辑</label>|
		// 				<label value={menu} onClick={that.menuDelete}>删除</label>
		// 			</td>
		// 		</tr>
		// 	);
		// });
		var that = this;
		var parseTreeJson = function(treeNodes){
			if(treeNodes == null || treeNodes.length == 0){
				return;
			}
			var arr = [];
			var childElement = [];

			for(var j = 0; j < treeNodes.length; j++)
			{
				arr.push({
					name: treeNodes[j]["name"],
					iconId: treeNodes[j]["iconId"],
					rank: treeNodes[j]["rank"],
					id: treeNodes[j]["id"],
					type: treeNodes[j]["type"],
					parentId : treeNodes[j]["parentId"],
					url: treeNodes[j]["url"],
					level : treeNodes[j]["level"],
					childMenu: treeNodes[j]["childMenu"]	
				});
			
				var childs = treeNodes[j]["childMenu"];
				childElement[j] = parseTreeJson(childs);
			}
			var distance = arr[0]["level"]*20 - 20;
			// console.log("distance::"+distance);
			var left = "auto auto auto "+ distance +"px";
			var trStyles = {margin: left};
			var temp = -1;
			function callH_(menu, menus){
				// console.log("callH_"+menu);
				that.callBack(menu, menus);
				// console.log(id);
				// console.log(that.props.menuOpen[id]);
				// console.log(that.props.menuOpen);
			}
			function callH(menu, menus){
				// console.log("callH"+id);
				const that = this;
				return function(){
					callH_.call(that, menu, menus);
				}
			};
			var num = 0;
			// var those = that;
			var element = arr.map(function(e){
				temp++;
				// var url = "/" + e["id"];
				// console.log("Collapse" + that.props.menuOpen[e["id"]]);

				if(e["childMenu"] != null){
					//opens[e["id"]] = false;
					var index = e["id"];
					num++;
					if(e["level"] == 1){
						return(
							<span style={{ display:"inline-block" }}>
							<tr>
								<td>{num}</td>
								<td style={trStyles} onClick={callH.call(that, e, menuMsg)} key={index}>{e["name"]}</td>
								<td>{e["iconId"]}</td>
								<td>{e["type"]}</td>
								<td>{e["id"]}</td>
								<td>{e["level"]}</td>
								<td>{e["rank"]}</td>
								<td>
									<label value={e} onClick={that.menuUpdate}>编辑</label>
									<label value={e} onClick={that.menuDelete}>删除</label>
								</td>
							</tr>
							<Collapse in={that.state.opens[e["id"]]}>
								<span>
								{childElement[temp]}
								</span>
							</Collapse>
							</span>
						);
					}
					else{
						return(
							<span style={{ display:"inline-block" }}>
								<tr>
									<td></td>
									<td style={trStyles} onClick={callH.call(that, e, menuMsg)} key={index}>{e["name"]}</td>
									<td>{e["iconId"]}</td>
									<td>{e["type"]}</td>
									<td>{e["id"]}</td>
									<td>{e["level"]}</td>
									<td>{e["rank"]}</td>
									<td>
										<label value={e} onClick={that.menuUpdate}>编辑</label>
										<label value={e} onClick={that.menuDelete}>删除</label>
									</td>
								</tr>
								<Collapse in={that.state.opens[e["id"]]}>
									<span>
										{childElement[temp]}
									</span>
								</Collapse>
							</span>
						);
					}
				}
				else{
					num++;
					if(e["level"] == 1){
						return(
							
								<tr>
									<td>{num}</td>
									<td style={trStyles} onClick={callH.call(that, e, menuMsg)} key={index}>{e["name"]}</td>
									<td>{e["iconId"]}</td>
									<td>{e["type"]}</td>
									<td>{e["id"]}</td>
									<td>{e["level"]}</td>
									<td>{e["rank"]}</td>
									<td>
										<label value={e} onClick={that.menuUpdate}>编辑</label>
										<label value={e} onClick={that.menuDelete}>删除</label>
									</td>
								</tr>
							
						);
					}
					else{
						return(
							
								<tr>
									<td></td>
									<td style={trStyles} onClick={callH.call(that, e, menuMsg)} key={index}>{e["name"]}</td>
									<td>{e["iconId"]}</td>
									<td>{e["type"]}</td>
									<td>{e["id"]}</td>
									<td>{e["level"]}</td>
									<td>{e["rank"]}</td>
									<td>
										<label value={e} onClick={that.menuUpdate}>编辑</label>
										<label value={e} onClick={that.menuDelete}>删除</label>
									</td>
								</tr>
							
						);
					}
				}
			});
			return element;
		}

		var elements = parseTreeJson(menuTree);

		console.log("state-update:"+that.state.updateMenuData);
		console.log("open-update:"+that.state.showMenuUpdate);
		var menuUpdateElement = (<MenuUpdate updateMenuData={this.state.updateMenuData} />);
		var menuDeleteElement = (<MenuDelete deleteMenuData={this.state.deleteMenuData} />);
		return(
			<div>
				<Col sm={10}>
				<ListGroup style={{padding: '0',margin: '0'}}>
					<ListGroupItem>
						菜单管理
					</ListGroupItem>
				</ListGroup>
				<Nav bsStyle="pills" activeKey={1} onSelect={this.menuAdd}>
				    <NavItem eventKey={1} onClick={this.menuAdd}><Glyphicon  glyph="plus" />菜单录入</NavItem>
				</Nav>
				<Table striped bordered condensed hover>
					<thead>
						<tr>
							<th></th>
							<th>菜单名称</th>
							<th>图标</th>
							<th>菜单类型</th>
							<th>菜单地址</th>
							<th>菜单等级</th>
							<th>菜单顺序</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						{elements}
					</tbody>
				</Table>
				{/*<div>
					<Col sm={2}>
						<label>每页显示
							<select onChange={this.handleSelectPageSize}>
								<option selected = "selected" value = {this.state.pageSize}>{this.state.pageSize}</option>
								<option value = '2'>2</option>
								<option value = '5'>5</option>
								<option value = '10'>10</option>
								<option value = '15'>15</option>
							</select>行
						</label>
					</Col>
					<Col sm={8}>
						<Pagination prev next first last boundaryLinks items={this.state.totalPage} maxButtons={this.state.maxButton} activePage={this.state.activePage} onSelect={this.handleSelect}/>
					</Col>
				</div>*/}
				<Modal show = {this.state.showMenuAdd} onHide={this.menuAddClose}>
					<Modal.Header closeButton>
						<Modal.Title>添加用户</Modal.Title>
					</Modal.Header>
					<Modal.Body>
						<MenuAdd />
					</Modal.Body>
				</Modal>
				<Modal show = {this.state.showMenuUpdate} onHide={this.menuUpdateClose}>
					<Modal.Header closeButton>
						<Modal.Title>用户编辑</Modal.Title>
					</Modal.Header>
					<Modal.Body>
						{menuUpdateElement}
					</Modal.Body>
				</Modal>
				<Modal show = {this.state.showMenuDelete} onHide={this.menuDeleteClose}>
					<Modal.Header closeButton>
						<Modal.Title>用户删除</Modal.Title>
					</Modal.Header>
					<Modal.Body>
						{menuDeleteElement}
					</Modal.Body>
				</Modal>

				</Col>
			</div>
		);
	}

}

export default MenuGridManagePro;


















