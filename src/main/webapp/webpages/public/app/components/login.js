import React, {Component} from "react"
import { FormGroup, FormControl, Form, Col, Grid, ControLabel, Checkbox, Button, Image,ButtonGroup} from "react-bootstrap"

class Login extends Component{
	constructor(...args){
		super(...args);
		this.state = {
		};
	}
	render(){
		const wellStyles = {maxWidth: 600, margin: '20px auto 10px'};

		return(
			<div style={{padding:'220px',height:"650",backgroundImage:'url(public/app/assets/2.png)'}}>

				<Form horizontal className="well" style={{maxWidth: 600,margin: '0px auto 0px'}}>
					<FormGroup>
						<Col smOffset={0} sm={12}>
							<Image src="public/app/assets/logo.jpg" rounded/>
							{/*<input type="button" style={{width:"500",backgroundImage:'url(app/assets/logo.jpg)'}} id="Search" />*/}
						</Col>
					</FormGroup>
					<FormGroup controlId = "formHorizontalUserName">
						{/*<Col style={{ textAlign : 'right'}}componentClass = {ControLabel} sm={4}>
							用户名
						</Col>*/}

						<Col smOffset={3} sm={6}>
							<FormControl type="userName" placeholder="用户名" />
						</Col>
					</FormGroup>

					<FormGroup controlId = "formHorizontalPassword">
						{/*<Col style={{ textAlign : 'right'}} componentClass = {ControLabel} sm={4}>
							密码
						</Col>*/}
						<Col smOffset={3} sm={6}> 
							<FormControl type="password" placeholder="密码" />
						</Col>
					</FormGroup>

					<FormGroup>
						<Col smOffset={2} sm={4}>
							<Checkbox>记住密码</Checkbox>
						</Col>
						<Col sm={4}>
							<a href="">忘记密码？</a>
						</Col>
					</FormGroup>

					<FormGroup>
						<Col smOffset={3} sm={6}>
								<Button bsStyle="success" block type="submit">
									登陆
								</Button>
						</Col>
					</FormGroup>
				</Form>
			</div>
		);
	}
}

export default Login;