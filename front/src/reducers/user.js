import * as types from '../types'

const initialState = null

export default function(state=initialState, action){
  const actions = {
    [types.SET_USER]: ()=>{
      return action.user
    }
  }

  return actions[action.type] === undefined ? state : actions[action.type]()
}
