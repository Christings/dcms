import { combineReducers } from 'redux'
import items from './items'
import filter from './filter'
import change from './change'

const rootReducer = combineReducers({
    items,
    filter,
    change
})

export default rootReducer
