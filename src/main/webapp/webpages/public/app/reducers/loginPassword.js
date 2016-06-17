import { USER_PASSWORD } from '../constants/actionTypes.js'

export default function loginPassword(state = false, action){
	switch(action.type){
		case USER_PASSWORD:
			return action.item;
		default:
			return state;
	}
}