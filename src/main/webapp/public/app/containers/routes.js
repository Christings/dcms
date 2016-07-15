import { Provider, connect } from 'react-redux'
import configureStore from '../store/configureStore'
import { bindActionCreators } from 'redux'
import * as ItemsActions from '../actions'
import React,{ Component } from 'react'
import { Router, Route, Link, History, hashHistory } from 'react-router'

import MenuAdd from '../components/menuAdd'
import MenuDelete from '../components/menuDelete'
import MenuUpdate from '../components/menuUpdate'
import UserAdd from '../components/userAdd'
import UserUpdate from '../components/userUpdate'
import UserDelete from '../components/userDelete'
import UserList from '../components/userList'
import IndexPage from './indexFrame'
class RoutesComponent extends Component{
    constructor(...args){
            super(...args);
            this.state={};
    }
    render(){
        var menu = [];
        if(this.props.menuName == false){
            console.log("this.props.menuName");
        }
        else{
            console.log("wa ka ka");
            for(var i=0,len=this.props.menuName.length; i<len; i++){
                // console.log(this.props.menuName[i]["id"]);
                var t = this.props.menuName[i]["id"] ;
                menu.push(t);
            }
        }
        // console.log(menu);
        var m = [];
        var s = "40769150d85a4c03b1c7df53a4722804";
        // console.log(typeof(s));
        // console.log("s"+s);
        // console.log(typeof(menu));
        // console.log(typeof(menu[2]));
        // console.log("menu2"+menu[2]);
        var temp = (<Route path={menu[2]} component={MenuUpdate} />);
        m.push(temp);
        // console.log("m"+m);
        // console.log(typeof(m));

        // if(menu[2] == s)
        // {
        //     console.log("equal");
        // }else{
        //     console.log("no");
        // }
        return(
            <Router history={hashHistory}>
                <Route path="/" component={IndexPage}>
                    <Route path="d48bc24290f546fd9ed9174f5abd26ca" component={MenuAdd} />
                    <Route path="25de3ed61569418591eba25dc066d1da" component={MenuUpdate} />
                    <Route path="995d2737879a43819386875317c23b2e" component={MenuDelete} />
                    <Route path="a431b5d14bde4a07b4f3611c9dacfc3a" component={UserAdd} />
                    <Route path="ff80bc76b3c54d0f8da29764a0edf69c" component={UserUpdate} />
                    <Route path="90249f6b08a0424f869f11b3abb4cc2d" component={UserDelete} />
                    <Route path="3531420becef4ed193f019029aaf1093" component={UserList} />
                </Route>
            </Router>
        );
    }
}

export default connect(state => ({
    menuName: state.menuName
}), dispatch => ({
    actions: bindActionCreators(ItemsActions, dispatch)
}))(RoutesComponent)