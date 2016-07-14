import { MENU_URL } from '../constants/actionTypes.js'

export default function menuUrl(state = false, action){
	switch(action.type){
		case MENU_URL:
			return action.item;
		default:
			return state;
	}
}