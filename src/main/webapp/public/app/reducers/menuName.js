import { MENU_NAME } from '../constants/actionTypes.js'

export default function menuName(state = false, action){
	switch(action.type){
		case MENU_NAME:
			return action.item;
		default:
			return state;
	}
}