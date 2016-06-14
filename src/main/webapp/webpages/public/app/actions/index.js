import { ADD_ITEM, DELETE_ITEM, DELETE_ALL, FILTER_ITEM, CHANGE_MESSAGE} from '../constants/actionTypes'

export function addItem() {
    // return dispatch => {
    //     setTimeout(() => dispatch({type: ADD_ITEM}), 1000)
    // }
    return {
        type: ADD_ITEM
    }
}
export function deleteItem(item) {
    return {
        type: DELETE_ITEM,
        item
    }
}
export function deleteAll() {
    return {
        type: DELETE_ALL
    }
}
export function filterItem(e) {
    let filterItem = e.target.value

    return {
        type: FILTER_ITEM,
        filterItem
    }
}

export function changeMessage(){

    return{
        type: CHANGE_MESSAGE
    }
}

// export function searchEquipment(){
//     return {
//         type: SEARCH_EQUIPMENT
//     }
// }
