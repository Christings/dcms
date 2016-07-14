import { MENU_OPEN } from '../constants/actionTypes.js'

export default function menuOpen(state = false, action){
	switch(action.type){
		case MENU_OPEN:
			return action.item;
		default:
			return state;
	}
}