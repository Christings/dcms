import '../scss/pure.scss'
import React from 'react'
import { render } from 'react-dom'

import IndexPage from './containers/indexFrame'
import Provide from './store/provide'
import Login from './components/login'
import configureStore from './store/configureStore'
import { Provider } from 'react-redux'
import { Router, Route, Link, IndexRoute, Redirect, hashHistory} from 'react-router';


import MenuAdd from './components/menuAdd'
import MenuDelete from './components/menuDelete'
import MenuUpdate from './components/menuUpdate'
import RoutesComponent from './containers/routes'
function renderDevTools(store) {
    if (__DEBUG__) {
        let {DevTools, DebugPanel, LogMonitor} = require('redux-devtools/lib/react')

        return (
            <DebugPanel top right bottom>
            <DevTools store={store} monitor={LogMonitor} />
            </DebugPanel>
        )
    }

    return null
}

const store = configureStore();
// var s = (<Route path="7ae0c17395de43de8159fa6b85c300c8" component={MenuAdd} />);
render((
    <Provider store={store}>
        <RoutesComponent />
    </Provider>
    ),
    document.getElementById('app'))
