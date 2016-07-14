import { MENU_ICONID } from '../constants/actionTypes.js'

export default function menuIconId(state = false, action){
	switch(action.type){
		case MENU_ICONID:
			return action.item;
		default:
			return state;
	}
}