
import { CHANGE_MESSAGE } from '../constants/actionTypes'

// const initialSt = {
//     st : false
// }

export default function change(state = false, action) {
    
    switch(action.type) {
        case CHANGE_MESSAGE:
        console.log(!state)
            return !state
        default:
            return state
    }
}
