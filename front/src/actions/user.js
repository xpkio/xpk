import * as types from '../types'

export const login = (user)=>({
  type: types.LOGIN,
  user
})
