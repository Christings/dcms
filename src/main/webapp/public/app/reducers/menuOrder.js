import { MENU_ORDER } from '../constants/actionTypes.js'

export default function menuOrder(state = false, action){
	switch(action.type){
		case MENU_ORDER:
			return action.item;
		default:
			return state;
	}
}