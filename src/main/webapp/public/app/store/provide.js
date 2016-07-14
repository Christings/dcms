import React from 'react'
import { render } from 'react-dom'
import { Provider } from 'react-redux'

import configureStore from './configureStore'
import App from '../containers/App.js'
import IndexPage from '../containers/indexFrame.js'

const store = configureStore();

const Provide = React.createClass({
    render(){
        return(
            <div>
                <Provider store={store}>
                	<IndexPage />
                </Provider>
            </div>
        );
    }    
});

export default Provide;