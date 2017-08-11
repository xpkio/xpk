import EventSource from 'eventsource'
import axios from 'axios'

let instance = axios

export const withServer = ()=>instance

export const eventSource = (path)=>{
  const headers = {}

  if (localStorage.getItem('token'))
    headers['Authorization'] = localStorage.getItem('token')

  return new EventSource(path, {headers})
}

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
