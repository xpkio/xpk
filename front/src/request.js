import axios from 'axios'

let instance = axios

export const withServer = ()=>instance

export const configureToken = (token)=>{
  localStorage.setItem('token', token)
  instance = axios.create({
    headers: {
      'Authorization': token
    }
  })
}

if (localStorage.getItem('token'))
  configureToken(localStorage.getItem('token'))
