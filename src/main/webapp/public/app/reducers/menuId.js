import { MENU_ID } from '../constants/actionTypes.js'

export default function menuId(state = false, action){
	switch(action.type){
		case MENU_ID:
			return action.item;
		default:
			return state;
	}
}