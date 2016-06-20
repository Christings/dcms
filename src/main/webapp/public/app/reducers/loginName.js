import { USER_NAME } from '../constants/actionTypes.js'

export default function loginName(state = false, action){
	switch(action.type){
		case USER_NAME:
			return action.item;
		default:
			return state;
	}
}