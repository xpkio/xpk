import {withServer, configureToken} from '../request'
import * as types from '../types'

export const setUser = (user)=>({
  type: types.SET_USER,
  user
})

export const login = ({username, password}, cb=()=>{})=>async (dispatch)=>{
  const authRes = await withServer().post('/api/login', {username, password})
  configureToken(authRes.headers.authorization)

  const userRes = await withServer().get('/api/current-user')
  dispatch(setUser(userRes.data))

  cb()
}

export const logout = (cb=()=>{})=>async (dispatch)=>{
  configureToken(undefined)
  dispatch(setUser(null))
  cb()
}
