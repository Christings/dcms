import { combineReducers } from 'redux'
import items from './items'
import filter from './filter'
import change from './change'
import loginName from './loginName'
import loginPassword from './loginPassword'
import menuId from './menuId'
import menuName from './menuName'
import menuOrder from './menuOrder'
import menuLevel from './menuLevel'
import menuUrl from './menuUrl'
import menuParentId from './menuParentId'
import menuIconId from './menuIconId'
import menuType from './menuType'
import menuOpen from './menuOpen'

const rootReducer = combineReducers({
    items,
    filter,
    change,
    loginName,
    loginPassword,
    menuId,
    menuName,
    menuOrder,
    menuLevel,
    menuUrl,
    menuParentId,
    menuIconId,
    menuType,
    menuOpen
})

export default rootReducer
