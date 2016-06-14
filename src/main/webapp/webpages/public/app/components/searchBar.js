import React from 'react'
import ImmutableRenderMixin from 'react-immutable-render-mixin'
import Button from 'react-bootstrap/lib/Button'
import ButtonToolbar from 'react-bootstrap/lib/ButtonToolbar'
let SearchBar = React.createClass({
    mixins: [ImmutableRenderMixin],
    render() {
        return (
            <div className="pure-form">
                <input type="text" onKeyUp={this.props.filterItem} placeholder="请输入查找的item" />
            	<ButtonToolbar>
   	 				<Button bsStyle="primary" bsSize="large" active>Primary button</Button>
   	 				<Button bsStyle="success" bsSize="small" active>Success button</Button>
    			</ButtonToolbar>	
            </div>
        )
    }
})

export default SearchBar;
