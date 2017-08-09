import * as types from '../types'

const initialState = {}

export default function(state=initialState, action){
  const actions = {
    [types.SET_MESSAGES]: ()=>{
      return {
        ...state,
        ...action.messages
      }
    }
  }

  return actions[action.type] === undefined ? state : actions[action.type]()
}
