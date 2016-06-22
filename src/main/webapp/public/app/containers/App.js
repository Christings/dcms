import React from 'react'
import SearchBar from '../components/searchBar'
import Content from '../components/content'
import Footer from '../components/footer'
import Hide from '../components/hide'
import ButtonsInstance from '../components/buttons'
import CustomMenu from '../components/buttons-customToggle'
import Navbars from '../components/navbars'
import SideBar from '../components/sideBars'
import Panels from '../components/panel'
import Login from '../components/login'


import { connect } from 'react-redux'
import ImmutableRenderMixin from 'react-immutable-render-mixin'
import * as ItemsActions from '../actions'
import { bindActionCreators } from 'redux'

let App = React.createClass({
    mixins: [ImmutableRenderMixin],
    propTypes: {
        items: React.PropTypes.object,
        filter: React.PropTypes.string,
        
    },
    render() {
        let styles = {
            // width: '200px',
            // margin: '30px auto 0'
            textAlign: 'center'
        }
        const actions = this.props.actions
        
        return (
            <div style={styles}>
                <Navbars />
                <SideBar />
                {/*
                <Login />
                <SideBar />
                <Iiii />
                <Navbars />
                <Panels />
                <ButtonsInstance />
                <CustomMenu />

                <h2>Manage Items</h2>
                <SearchBar filterItem={actions.filterItem}/>
                <Content items={this.props.items} filter={this.props.filter}  deleteItem={actions.deleteItem}/>
                <Footer addItem={actions.addItem} deleteAll={actions.deleteAll} />
                <Hide change={this.props.change} changeMessage={actions.changeMessage} />
                <StudentScoreTable />*/}
            </div>
        )
    }
})

export default connect(state => ({
    items: state.items,
    filter: state.filter,
    change: state.change,
    loginName: state.loginName,
    loginPassword: state.loginPassword
}), dispatch => ({
    actions: bindActionCreators(ItemsActions, dispatch)
}))(App)
