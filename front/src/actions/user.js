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

export const restoreUser = (token)=>async (dispatch)=>{
  const userRes = await withServer().get('/api/current-user')
  dispatch(setUser(userRes.data))
}

export const register = (
  {username, password, firstName, lastName, email},
  cb=()=>{}
)=>async (dispatch)=>{
  await withServer().post('/api/user', {username, password, firstName, lastName, email})
  await login({username, password}, cb)(dispatch)
}

export const logout = (cb=()=>{})=>async (dispatch)=>{
  configureToken(undefined)
  dispatch(setUser(null))
  cb()
}
