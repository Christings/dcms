import { MENU_PARENTID } from '../constants/actionTypes.js'

export default function menuParentId(state = false, action){
	switch(action.type){
		case MENU_PARENTID:
			return action.item;
		default:
			return state;
	}
}