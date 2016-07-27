import React,{ Component } from 'react'
import { Router, Route, Link, History } from 'react-router'
import Navbars from '../components/navbars'

import MenuAdd from '../components/menuAdd'
import MenuUpdate from '../components/menuUpdate'
import MenuDelete from '../components/menuDelete'
import { Navbar, Nav, NavItem, NavDropdown, Button, Image,Collapse,Tab,Row, Glyphicon, ListGroup, ListGroupItem, Col, OverlayTrigger, Popover } from "react-bootstrap"
import $ from 'jquery'
import { Provider, connect } from 'react-redux'
import configureStore from '../store/configureStore'
import { bindActionCreators } from 'redux'
import * as ItemsActions from '../actions'

const store = configureStore();

var opens = [];
class IndexPage extends Component{
	mixins: [History]
	constructor(...args){
		super(...args);
		this.state={
			open: true,
			open1: false,
			open1_1_1:false,
			open1_1_2:false,
			open1_1_3:false,
			open1_1_4:false,
			open2: false,
			open3: false,
			open4: false,
			open5: false,
			opens: [],
			left_cross: 2,
			right_cross: 10,
			wellStyles: {margin: "0",padding: "0", textAlign:'left'},
			menuData: "",
			menuTree: ""
		};
		this.handleClick = this.handleClick.bind(this);
		this.loadMenuMsg = this.loadMenuMsg.bind(this);
		this.loadMenuTree = this.loadMenuTree.bind(this);
		this.loadUserList = this.loadUserList.bind(this);
		this.callBack = this.callBack.bind(this);
		this.initiateOpen = this.initiateOpen.bind(this);
	}
	handleClick(){
		if(this.state.open){
			this.setState({
				open: false,
				left_cross: 1,
				right_cross: 11
				//wellStyles: {height:"1500",width:"60",margin: "0",padding: "0", textAlign:'left'}
			});
		}else{
			this.setState({
				open: true,
				left_cross: 2,
				right_cross: 10
				// wellStyles: {height:"1500",width:"200",margin: "0",padding: "0", textAlign:'left'}
			});
		}
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
	loadMenuMsg(){
		$.ajax({
			url: "menu/getAll",
			dataType: "json",
			data: "",
			type: "post"
		}).done((jsonData)=>{
			const D = jsonData["data"];
			this.setState({
				menuData : D
			});
			this.props.actions.menuName(D);
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
	loadUserList(){
		
	}
	initiateOpen(menuData){
		var openStates = [];
		for(var i=0,len = menuData.length; i < len; i++){
			if(menuData[i]["childMenu"] != null){
				var id = menuData[i]["id"];
				openStates[id] = false;
			}
		}
		this.setState({
			opens: openStates
		});
	}
	componentDidMount(){
		this.loadMenuMsg();
		this.loadMenuTree();
		this.initiateOpen(this.state.menuData);
	}
	render(){
		// console.log(this.state.menuData);
		// console.log(this.state.menuTree);
		var menuData = this.state.menuData;
		var menuTree = this.state.menuTree;
		console.log(menuTree);

		const styles = { margin:'0',padding:'0',};
		const contentStyles = { margin: '0', padding: '0',height: "500"};
		// const wellStyles = {height:"1500",minWidth:"200",margin: "0",padding: "0", textAlign:'left'};
		const secondListStyles = {margin: "auto auto auto 20px"};
		// const thirdListStyles = {margin: "auto auto auto 40px"};
		const thirdListStyles = {margin: "auto auto auto 40px"};
		const constantStyles ={margin: "0"};
		var that = this;
		// const actions = this.props.actions;
		
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
					level: treeNodes[j]["level"],
					id: treeNodes[j]["id"],
					childMenu: treeNodes[j]["childMenu"]	
				});
			
				var childs = treeNodes[j]["childMenu"];
				childElement[j] = parseTreeJson(childs);
			}
			var distance = arr[0]["level"]*20 - 20;
			// console.log("distance::"+distance);
			var left = "auto auto auto "+ distance +"px";
			var listStyles = {margin: left};
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
			var element = arr.map(function(e){
				temp++;
				var url = "/" + e["id"];
				// console.log("Collapse" + that.props.menuOpen[e["id"]]);

				if(e["childMenu"] != null){
					opens[e["id"]] = false;
					var index = e["id"];
					return(
						<div style={{ margin:'0',padding:'0'}}>
							<ListGroupItem onClick={callH.call(that, e, menuData)} key={index}>
								<Glyphicon style={listStyles} glyph={e["iconId"]} />{e["name"]}
							</ListGroupItem>
							<Collapse in={that.state.opens[e["id"]]}>
								<ListGroup style={{ margin:'0',padding:'0'}}>
									{childElement[temp]}
								</ListGroup>
							</Collapse>
						</div>
					);
				}
				else{
					return(
						<div style={{ margin:'0',padding:'0'}}>
							<Link to={url}>
							<ListGroupItem key={e["id"]}>
								<Glyphicon style={listStyles} glyph={e["iconId"]} />{e["name"]}
							</ListGroupItem>
							</Link>
							
						</div>);
				}
			});
			return element;
		}
		// var parseIconTreeJson = function(treeNodes){
		// 	if(treeNodes == null || treeNodes.length == 0){
		// 		return;
		// 	}
		// 	var arr = [];
		// 	var childElement = [];

		// 	for(var j = 0; j < treeNodes.length; j++)
		// 	{
		// 		arr.push({
		// 			name: treeNodes[j]["name"],
		// 			iconId: treeNodes[j]["iconId"],
		// 			level: treeNodes[j]["level"],
		// 			id: treeNodes[j]["id"],
		// 			childMenu: treeNodes[j]["childMenu"]	
		// 		});
			
		// 		var childs = treeNodes[j]["childMenu"];
		// 		childElement[j] = parseTreeJson(childs);
		// 	}
		// 	var distance = arr[0]["level"]*20 - 20;
		// 	// console.log("distance::"+distance);
		// 	var left = "auto auto auto "+ distance +"px";
		// 	var listStyles = {margin: left};
		// 	var temp = -1;
		// 	function callH_(menu, menus){
		// 		// console.log("callH_"+menu);
		// 		that.callBack(menu, menus);
		// 		// console.log(id);
		// 		// console.log(that.props.menuOpen[id]);
		// 		// console.log(that.props.menuOpen);
		// 	}
		// 	function callH(menu, menus){
		// 		// console.log("callH"+id);
		// 		const that = this;
		// 		return function(){
		// 			callH_.call(that, menu, menus);
		// 		}
		// 	};
		// 	var element = arr.map(function(e){
		// 		temp++;
		// 		var url = "/" + e["id"];
		// 		// console.log("Collapse" + that.props.menuOpen[e["id"]]);

		// 		if(e["childMenu"] != null){
		// 			opens[e["id"]] = false;
		// 			var index = e["id"];
		// 			return(
		// 				<div style={{ margin:'0',padding:'0'}}>
		// 					<ListGroupItem onClick={callH.call(that, e, menuData)} key={index}>
		// 						<Glyphicon style={listStyles} glyph={e["iconId"]} />{e["name"]}
		// 					</ListGroupItem>
		// 					<Collapse in={that.state.opens[e["id"]]}>
		// 						<ListGroup style={{ margin:'0',padding:'0'}}>
		// 							{childElement[temp]}
		// 						</ListGroup>
		// 					</Collapse>
		// 				</div>
		// 			);
		// 		}
		// 		else{
		// 			return(
		// 				<div style={{ margin:'0',padding:'0'}}>
		// 					<Link to={url}>
		// 					<ListGroupItem key={e["id"]}>
		// 						<Glyphicon style={listStyles} glyph={e["iconId"]} />{e["name"]}
		// 					</ListGroupItem>
		// 					</Link>
							
		// 				</div>);
		// 		}
		// 	});
		// 	return element;
		// }

		var elements = parseTreeJson(menuTree);
		// var elementIcons = parseIconTreeJson(menuTree);
		
		// this.props.actions.menuOpen(opens);
		// this.callBack(opens);
		// console.log("call");
		// console.log(this.props.menuOpen);


		// for(var i = 0; i < menuTree.length; i++)
		// {
		// 	arrLevel2[i] = [];
		// 	elements3[i] = [];
		// 	console.log("look down");
		// 	if(menuTree[i]["childMenu"] != null)
		// 	{
		// 		var menuTreeChild = menuTree[i]["childMenu"];
		// 		console.log("child:"+menuTreeChild);
		// 		for(var j = 0; j < menuTreeChild.length; j++)
		// 		{
		// 			arrLevel3[j] = [];
		// 			if(menuTreeChild[j]["childMenu"] != null)
		// 			{
		// 				var menuTreeGrandChild = menuTreeChild[j]["childMenu"];
		// 				for(var h = 0; h < menuTreeGrandChild.length; h++)
		// 				{
		// 					arrLevel3[j].push({
		// 						name: menuTreeGrandChild[h]["name"],
		// 						iconId: menuTreeGrandChild[h]["iconId"]
		// 					});
		// 				}
		// 			}
		// 			arrLevel2[i].push({
		// 				name: menuTreeChild[j]["name"],
		// 				iconId: menuTreeChild[j]["iconId"]
		// 			});
		// 			elements3[i][j] = arrLevel3[j].map(function(e){
		// 				return(
		// 					<ListGroupItem>
		// 						<Glyphicon glyph={e["iconId"]} />{e["name"]}
		// 					</ListGroupItem>);
		// 			});
		// 		}
		// 	}
		// 	arrLevel1.push({
		// 		name: menuTree[i]["name"],
		// 		iconId: menuTree[i]["iconId"]
		// 	});
		// 	var temp2 = -1;
		// 	elements2[i] = arrLevel2[i].map(function(e){
		// 		console.log(e["name"]);
		// 		temp2++;
		// 		return(
		// 			<div>
		// 				<ListGroupItem>
		// 					<Glyphicon glyph={e["iconId"]} />{e["name"]}
		// 				</ListGroupItem>
		// 				{elements3[i][temp2]}
		// 			</div>);
		// 	});
		// }

		// var temp1 = -1;
		// var elements1 = arrLevel1.map(function(e){
		// 	temp1++;
		// 	return(
		// 		<div>
		// 			<ListGroupItem>
		// 				<Glyphicon glyph={e["iconId"]} />{e["name"]}
		// 			</ListGroupItem>
		// 			{elements2[temp1]}	
		// 		</div>);
		// });

		// console.log("duang!!!");
		

		const book = (
			<Popover>
				<strong>
					资产管理
				</strong>
				<ListGroup>
					<ListGroupItem>
						<Glyphicon glyph="home"/>机房列表
					</ListGroupItem>
					<ListGroupItem>
						<Glyphicon glyph="book"/>未上架设备列表
					</ListGroupItem>
					<ListGroupItem>
						<Glyphicon glyph="book"/>待确认设备列表
					</ListGroupItem>
					<ListGroupItem>
						<Glyphicon glyph="book"/>区域列表
					</ListGroupItem>
					<ListGroupItem>
						<Glyphicon glyph="book"/>测试列表
					</ListGroupItem>
				</ListGroup>
			</Popover>
		);
		const lllll = (
			<div>
				<label onClick={this.lalala}>click</label>
			</div>
			);

		return(
			<div>
			<Col style={styles} sm={12}>
                <Navbars />
            </Col>
            <Col style={styles} sm={this.state.left_cross}>
            	<div className="well" style={this.state.wellStyles}>
					<ListGroup style={constantStyles}>
						<ListGroupItem>
								<Glyphicon style={{textAlign:'left'}} onClick={this.handleClick} glyph="align-justify"/>
						</ListGroupItem>
					</ListGroup>
					{/*<Collapse in={this.state.open}>
						<div style={{margin: "auto auto auto 0"}}>
								<ListGroup>

									<ListGroupItem eventKey="first" onClick={ ()=> this.setState({
												open1: !this.state.open1,
												open2: false,
												open3: false,
												open4: false,
												oepn5: false
											})}>
										<Glyphicon glyph="book"/>资产管理
									</ListGroupItem>
									<Collapse in={this.state.open1}>			
										<ListGroup style={constantStyles}>
											<ListGroupItem eventKey="first_1_1" onClick={ ()=> this.setState({
												open1_1_1: !this.state.open1_1_1,
												open1_1_2: false,
												open1_1_3: false,
												open1_1_4: false
											})}>
												<Glyphicon style={secondListStyles} glyph="home"/>机房列表
											</ListGroupItem>
											
											<Collapse in={this.state.open1_1_1}>
												<ListGroup style={constantStyles}>
													<Link to="/A01"><ListGroupItem href="#link3"><Glyphicon style={thirdListStyles} glyph="tower"/>A01</ListGroupItem></Link>
													<Link to="/A02"><ListGroupItem href="#link2"><Glyphicon style={thirdListStyles} glyph="tower"/>A02</ListGroupItem></Link>
													<Link to="/A03"><ListGroupItem href="#link3"><Glyphicon style={thirdListStyles} glyph="tower"/>A03</ListGroupItem></Link>
												</ListGroup>
											</Collapse>
											
											<ListGroupItem eventKey="first_1_2" onClick={ ()=> this.setState({
												open1_1_1: false,
												open1_1_2: !this.state.open1_1_2,
												open1_1_3: false,
												open1_1_4: false
											})}>
												<Glyphicon style={secondListStyles} glyph="paperclip"/>未上架设备列表
											</ListGroupItem>
											
											<Collapse in={this.state.open1_1_2}>
												<ListGroup style={constantStyles}>
													<ListGroupItem href="#link3"><Glyphicon style={thirdListStyles} glyph="tower"/>A01</ListGroupItem>
													<ListGroupItem href="#link2"><Glyphicon style={thirdListStyles} glyph="tower"/>A02</ListGroupItem>
													<ListGroupItem href="#link3"><Glyphicon style={thirdListStyles} glyph="tower"/>A03</ListGroupItem>
												</ListGroup>
											</Collapse>

											<ListGroupItem eventKey="first_1_3" onClick={ ()=> this.setState({
												open1_1_1: false,
												open1_1_2: false,
												open1_1_3: !this.state.open1_1_3,
												open1_1_4: false
											})}>
												<Glyphicon style={secondListStyles} glyph="user"/>待确认设备列表
											</ListGroupItem>
											
											<Collapse in={this.state.open1_1_3}>
												<ListGroup style={constantStyles}>
													<ListGroupItem href="#link3"><Glyphicon style={thirdListStyles} glyph="tower"/>A01</ListGroupItem>
													<ListGroupItem href="#link2"><Glyphicon style={thirdListStyles} glyph="tower"/>A02</ListGroupItem>
													<ListGroupItem href="#link3"><Glyphicon style={thirdListStyles} glyph="tower"/>A03</ListGroupItem>
												</ListGroup>
											</Collapse>
											
											<ListGroupItem eventKey="first_1_4" onClick={ ()=> this.setState({
												open1_1_1: false,
												open1_1_2: false,
												open1_1_3: false,
												open1_1_4: !this.state.open1_1_4
											})}>
												<Glyphicon style={secondListStyles} glyph="retweet"/>区域列表
											</ListGroupItem>
										
											<Collapse in={this.state.open1_1_4}>
												<ListGroup style={constantStyles}>
													<ListGroupItem href="#link3"><Glyphicon style={thirdListStyles} glyph="tower"/>A01</ListGroupItem>
													<ListGroupItem href="#link2"><Glyphicon style={thirdListStyles} glyph="tower"/>A02</ListGroupItem>
													<ListGroupItem href="#link3"><Glyphicon style={thirdListStyles} glyph="tower"/>A03</ListGroupItem>
												</ListGroup>
											</Collapse>
										</ListGroup>				
									</Collapse>

									<ListGroupItem eventKey="second" onClick={ ()=> this.setState({
												open1: false,
												open2: !this.state.open2,
												open3: false,
												open4: false,
												oepn5: false
											})}>
										<Glyphicon glyph="link"/>布线管理
									</ListGroupItem>
									<Collapse in={this.state.open2}>			
										<ListGroup style={constantStyles}>
											<ListGroupItem href="#link2"><Glyphicon style={secondListStyles} glyph="home"/>机房列表</ListGroupItem>
											<ListGroupItem href="#link3"><Glyphicon style={secondListStyles} glyph="home"/>Link 3</ListGroupItem>
										</ListGroup>				
									</Collapse>

									

									<ListGroupItem eventKey="forth" onClick={ ()=> this.setState({
												open1: false,
												open2: false,
												open3: false,
												open4: !this.state.open4,
												oepn5: false
											})}>
										<Glyphicon glyph="cog"/>系统管理
									</ListGroupItem>
									<Collapse in={this.state.open4}>			
										<ListGroup style={constantStyles}>
											<ListGroupItem href="#link2"><Glyphicon style={secondListStyles} glyph="home"/>机房列表</ListGroupItem>
											<ListGroupItem href="#link3"><Glyphicon style={secondListStyles} glyph="home"/>Link 3</ListGroupItem>
										</ListGroup>				
									</Collapse>

									<ListGroupItem eventKey="third" onClick={ ()=> this.setState({
												open1: false,
												open2: false,
												open3: !this.state.open3,
												open4: false,
												oepn5: false
											})}>
										<Glyphicon glyph="Text-width"/>PPT
									</ListGroupItem>
									<Collapse in={this.state.open3}>			
										<ListGroup style={constantStyles}>
											<ListGroupItem href="#link2"><Glyphicon style={secondListStyles} glyph="home"/>机房列表</ListGroupItem>
											<ListGroupItem href="#link3"><Glyphicon style={secondListStyles} glyph="home"/>Link 3</ListGroupItem>
										</ListGroup>				
									</Collapse>

									<ListGroupItem eventKey="fifth" onClick={ ()=> this.setState({
												open1: false,
												open2: false,
												open3: false,
												open4: false,
												oepn5: !this.state.open5
											})}>
										<Glyphicon glyph="new-window"/>更新模型
									</ListGroupItem>
									<Collapse in={this.state.open5}>			
										<ListGroup style={constantStyles}>
											<ListGroupItem href="#link2"><Glyphicon style={secondListStyles} glyph="home"/>机房列表</ListGroupItem>
											<ListGroupItem href="#link3"><Glyphicon style={secondListStyles} glyph="home"/>Link 3</ListGroupItem>
										</ListGroup>				
									</Collapse>
								</ListGroup>
						</div>
					</Collapse>

					<Collapse in={!this.state.open}>
						<ListGroup>
							<OverlayTrigger container={this} trigger="hover " placement="right" overlay={book}>
								<ListGroupItem>
									<Glyphicon style={{textAlign:'left'}}  glyph="book"/>
								</ListGroupItem>
							</OverlayTrigger>
							<ListGroupItem>
								<Glyphicon style={{textAlign:'left'}}  glyph="link"/>
							</ListGroupItem>
							<ListGroupItem>
								<Glyphicon style={{textAlign:'left'}}  glyph="cog"/>
							</ListGroupItem>
							<ListGroupItem>
								<Glyphicon style={{textAlign:'left'}}  glyph="Text-width"/>
							</ListGroupItem>
							<ListGroupItem>
								<Glyphicon style={{textAlign:'left'}}  glyph="new-window"/>
							</ListGroupItem>

						</ListGroup>
					</Collapse>*/}
					<ListGroup>
						<div style={styles}>
						{elements}
						</div>
					</ListGroup>
				</div>
            </Col>
            <Col style={contentStyles} sm={this.state.right_cross}>
            	{this.props.children}
            </Col>
            </div>
		)
	}
}

export default connect(state => ({
	loginName: state.loginName,
    menuId: state.menuId,
    menuName: state.menuName,
    menuOrder: state.menuOrder,
    menuLevel: state.menuLevel,
    menuUrl: state.menuUrl,
    menuParentId: state.menuParentId,
    menuIconId: state.menuIconId,
    menuType: state.menuType,
    menuOpen: state.menuOpen
}), dispatch => ({
    actions: bindActionCreators(ItemsActions, dispatch)
}))(IndexPage) 

// const IndexFrame = React.createClass({
// 	render(){
// 		return(
// 			<div>
// 				<Provider store={store}>
// 					<IndexPage />
// 				</Provider>
// 			</div>
// 		);
// 	}
// });
// export default IndexPage;
