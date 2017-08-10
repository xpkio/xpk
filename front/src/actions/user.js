import {withServer, configureToken} from '../request'
import * as types from '../types'

export const setUser = (user)=>({
  type: types.SET_USER,
  user
})

export const login = ({username, password}, cb=()=>{})=>async (dispatch)=>{
  const res = await withServer().post('/api/login', {username, password})

  configureToken(res.headers.authorization)
  dispatch(setUser(res.data))
  cb()
}

export const logout = (cb=()=>{})=>async (dispatch)=>{
  configureToken(undefined)
  dispatch(setUser(null))
  cb()
}
