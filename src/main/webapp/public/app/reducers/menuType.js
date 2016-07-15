import { MENU_TYPE } from '../constants/actionTypes.js'

export default function menuType(state = false, action){
	switch(action.type){
		case MENU_TYPE:
			return action.item;
		default:
			return state;
	}
}