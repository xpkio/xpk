import axios from 'axios'
import * as types from '../types'

export const setUser = (user)=>({
  type: types.SET_USER,
  user
})

export const login = ({username, password})=>async (dispatch)=>{
  const res = await axios.post('/api/login', {
    username,
    password
  })

  dispatch(setUser(res.data))
}


login({
  username: 'username',
  password: 'password'
})(x=>{console.log(x)})
