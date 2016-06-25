import '../scss/pure.scss'
import React from 'react'
import { render } from 'react-dom'

import IndexFrame from './containers/indexFrame'
import Provide from './store/provide'
import Login from './components/login'
import { Router, Route, Link, IndexRoute, Redirect, hashHistory} from 'react-router';

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

render((
    <Router history={hashHistory}>
        <Route path="/" component={IndexFrame}>
            <Route path="A01" component={Provide} />
            <Route path="A02" component={Login} />
            <Route path="A03" component={Provide} />
        </Route>
    </Router>
    ),
    document.getElementById('app'))
