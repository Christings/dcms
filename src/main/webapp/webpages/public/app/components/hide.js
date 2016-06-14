import React, { Component } from 'react'
import _ from 'lodash' //lodash API: https://lodash.com/docs#matches
import classNames from 'classnames'

class Hide extends Component {
    render() {
        //let items = this.props.items.toArray();
        var options;
        if(this.props.change)
        {
            console.log('yo hoo!'+this.props.change);
        }
        else
        {
            console.log(this.props.change);
        }
        
        if(this.props.change){
            options = (  
            <select classname = "options">
                <option value="A">A区</option>
                <option value="B">B区</option>
                <option value="C">C区</option>
                <option value="D">D区</option>
            </select>
            ); 
        }
        return (
            <div>
                <form action = "" method = "get">
                    <p>
                        <input type = "text" name = "jigui" placeholder = "请输入机柜名称"/>
                        <input type = "submit" value = "Submit"/>    
                    </p>    
                </form>
                <button className="pure-button button-error button-small" onClick={this.props.changeMessage}>changeMessage</button>.{options}
            </div>
        )
    }
}

// class LiItem extends Component {
//     render() {
//         let liClass = classNames({ hidden: !_.isEmpty(this.props.filter) && this.props.filter != this.props.item})
//         console.log('item:' + this.props.item);
//         return (
//             <li className={liClass}>
//                 <span style={{marginRight: '4px'}}>{this.props.item}</span>
//                 <button onClick={this.props.deleteItem.bind(this, this.props.item)}>delete</button>
//             </li>
//         )
//     }
// }

export default Hide
