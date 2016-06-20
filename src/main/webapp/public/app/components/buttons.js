import React, { Component } from 'react'

import { Button, ButtonToolbar, Navbar, ButtonGroup, DropdownButton, MenuItem, SplitButton } from 'react-bootstrap'
// import * from 'react-bootstrap'

const ButtonsInstance = React.createClass({
    getInitialState(){
		return{
			isLoading: false
		};
	},
	handleClick(){
	    this.setState({isLoading: true});

	    // This probably where you would have an `ajax` call
	    setTimeout(() => {
	      // Completed of async action, set loading state back
	      this.setState({isLoading: false});
	    }, 2000);
  	},
  	renderDropdownButton1(title,i){
  		return(
  			<DropdownButton bsStyle={title.toLowerCase()} title={title} key={i} id={'dropdown-basic-${i}'}>
  				<MenuItem eventKey="1">Action</MenuItem>
  				<MenuItem eventKey="2">Action</MenuItem>
  				<MenuItem eventKey="3">Action</MenuItem>
  				<MenuItem divider/>
  				<MenuItem eventKey="4">Action</MenuItem>
  			</DropdownButton>
  		);
  	},
  	renderDropdownButton2(title,i){
  		return(
  			<SplitButton bsStyle={title.toLowerCase()} dropup pullRight title={title} key={i} id={'split-basic-${i}'}>
  				<MenuItem eventKey="1">Action</MenuItem>
  				<MenuItem eventKey="2">Action</MenuItem>
  				<MenuItem eventKey="3">Action</MenuItem>
  				<MenuItem divider/>
  				<MenuItem eventKey="4">Action</MenuItem>
  			</SplitButton>
  		);
  	},
    render() {
    	const wellStyles =  {maxWidth: 400, margin: '0 auto 10px'};
    	const BUTTONS = ['Default', 'Primary', 'Success', 'Info', 'Warning', 'Danger', 'Link'];


        return (
        	<div>
            	<ButtonToolbar>
    				{/* Standard button */}
    				<Button>Default</Button>

    				{/* Provides extra visual weight and identifies the primary action in a set of buttons */}
    				<Button bsStyle="primary" disabled={this.state.isLoading} onCLick={!this.state.isLoading ? this.handleClick : null}>{this.state.isLoading ? 'Loading...' : 'Loading state'}</Button>

    				{/* Indicates a successful or positive action */}
    				<Button bsStyle="success" active>Success</Button>

    				{/* Contextual button for informational alert messages */}
				    <Button bsStyle="info">Info</Button>

				    {/* Indicates caution should be taken with this action */}
				    <Button bsStyle="warning">Warning</Button>

				    {/* Indicates a dangerous or potentially negative action */}
				    <Button bsStyle="danger">Danger</Button>

				    {/* Deemphasize a button by making it look like a link while maintaining button behavior */}
				    <Button bsStyle="link">Link</Button>
			    </ButtonToolbar>
			    <div className="well" style={wellStyles}>
			    	<Button bsStyle="primary" bsSize="large" block>Block level button</Button>
    				<Button bsSize="large" block>Block level button</Button>
			    </div>
			    <ButtonGroup vertical block>
			    	<Button>L</Button>
			    	<Button>M</Button>
			    	<Button>R</Button>
			    	<DropdownButton title="Dropdown" id="bg-nested-dropdown">
				      <MenuItem eventKey="1">Dropdown link</MenuItem>
				      <MenuItem eventKey="2">Dropdown link</MenuItem>
				    </DropdownButton>
			    </ButtonGroup>

			    <ButtonToolbar>{BUTTONS.map(this.renderDropdownButton1)}</ButtonToolbar>
			    <ButtonToolbar>{BUTTONS.map(this.renderDropdownButton2)}</ButtonToolbar>
        	</div>
        )
    }
})

export default ButtonsInstance;
