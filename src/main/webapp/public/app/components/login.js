import React, {Component} from "react"
import { FormGroup, FormControl, Form, Col, Grid, ControLabel, Checkbox, Button, Image,ButtonGroup,Panel} from "react-bootstrap"
import $ from 'jquery';

class Login extends Component{
	constructor(...args){
		super(...args);
		this.state = {
			saveNP: false,
			getC: false
		};
		this.handleLogin = this.handleLogin.bind(this);
		this.getUserName = this.getUserName.bind(this);
		this.getPassword = this.getPassword.bind(this);
		this.saveNamePassword = this.saveNamePassword.bind(this);
		this.getCookie = this.getCookie.bind(this);
	}	

	handleLogin(){
		
		var userInformation = { userName: '', password: ''};
		var login = false;
		userInformation['userName'] = this.props.loginName;
		userInformation['password'] = this.props.loginPassword;
		// console.log(userInformation['userName']);
		// console.log(userInformation['password']);
		$.ajax({
			url: './login',
			dataType: 'json',
			type: 'POST',
			data: userInformation,
			success: function(){
				login = true;
				console.log("log in successfully!!!");
				var date = new Date();
				date.setDate(date.getDate() + 5);
				document.cookie = "^userName=" + this.props.loginName + "^userPassword=" + this.props.loginPassword + "^;expires=" + date.toGMTString();
				console.log("cookie success");
			}.bind(this),
			error: function(xhr, status, err){
				console.error('./login', status, err.toString());
			}.bind(this)
		});
		if( login ){
			/* 跳转至首页*/
		}
		/*if((this.props.loginName == "ding") && (this.props.loginPassword == "123456"))
		{
			console.log("log in successfully!!!");
			var date = new Date();
			date.setDate(date.getDate() + 5);
			document.cookie = "^userName=" + this.props.loginName + "^userPassword=" + this.props.loginPassword + "^;expires=" + date.toGMTString();
			console.log("cookie success");
		}
		else{
			console.log("error!");
		}*/
	}

	getUserName(e){
		var userName = e.target.value;
		this.props.userNameA(userName);
	}

	getPassword(e){
		var password = e.target.value;
		this.props.userPasswordA(password);
	}

	saveNamePassword(e){
		var state = e.target.checked;
		if(state){
			this.setState({ saveNP: true });
		}
		else{
			this.setState({ saveNP: false });
		}
		
	}

	getCookie(cookieValue){
		var cStart,cEnd;
		if(document.cookie.length > 0){
			cStart = document.cookie.indexOf(cookieValue + "=");
			if(cStart != -1){
				cStart = cStart + cookieValue.length + 1;
				cEnd = document.cookie.indexOf("^", cStart);
				if(cEnd == -1){
					cEnd = document.cookie.length;
				}
				return unescape(document.cookie.substring(cStart,cEnd));
			}
		}
		return "";
	}

	render(){
		const wellStyles = {maxWidth: 600, margin: '20px auto 10px'};
		// alert(document.cookie);
		var userName = this.getCookie('userName');
		var userPassword = this.getCookie('userPassword');
		if(userName != null && userName != "" && userPassword != null && userPassword != ""){
			console.log('Your name:' + userName + 'password' + userPassword);
		}
		return(
			<div>
				<div style={{margin:"20px", textAlign:"left"}}>
				<Image src="public/app/assets/img/logo.png" rounded/>
				</div>
				<div style={{height:"450",backgroundImage:'url(public/app/assets/img/2.png)'}}>
					
					<Col smOffset={7} sm={3}>
					<Form horizontal className="well" style={{maxWidth: 400,minWidth:400,opacity:"1",margin: '100px auto 0px'}}>

						<FormGroup controlId = "formHorizontalUserName">
							{/*<Col style={{ textAlign : 'right'}}componentClass = {ControLabel} sm={4}>
								用户名
							</Col>*/}

							<Col smOffset={1} sm={10}>
								<FormControl type="userName" placeholder="用户名"  onChange={this.getUserName} />
							</Col>
						</FormGroup>

						<FormGroup controlId = "formHorizontalPassword">
							{/*<Col style={{ textAlign : 'right'}} componentClass = {ControLabel} sm={4}>
								密码
							</Col>*/}
							<Col smOffset={1}  sm={10}> 
								<FormControl type="password" placeholder="密码" onChange={this.getPassword} />
							</Col>
						</FormGroup>

						<FormGroup>
							<Col smOffset={1} sm={6}>
								<Checkbox onClick={this.saveNamePassword}>记住密码</Checkbox>
							</Col>
							<Col sm={4}>
								<a href="public/app/components/forget.html">忘记密码？</a>
							</Col>
						</FormGroup>

						<FormGroup>
							<Col smOffset={1} sm={10}>
								<Button bsStyle="success" block onClick={this.handleLogin}>
									登陆
								</Button>
							</Col>
						</FormGroup>
					</Form>
					</Col>
				</div>
			</div>
		);
	}
}

export default Login;