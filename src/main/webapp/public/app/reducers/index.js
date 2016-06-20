import { combineReducers } from 'redux'
import items from './items'
import filter from './filter'
import change from './change'
import loginName from './loginName'
import loginPassword from './loginPassword'

const rootReducer = combineReducers({
    items,
    filter,
    change,
    loginName,
    loginPassword
})

export default rootReducer
