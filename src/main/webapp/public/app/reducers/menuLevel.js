import { MENU_LEVEL } from '../constants/actionTypes.js'

export default function menuLevel(state = false, action){
	switch(action.type){
		case MENU_LEVEL:
			return action.item;
		default:
			return state;
	}
}