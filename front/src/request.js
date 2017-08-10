import axios from 'axios'

let instance = axios.create({
  headers: {
    'Authorization': localStorage.getItem('token') || undefined
  }
})
export const withServer = ()=>instance

export const configureToken = (token)=>{
  localStorage.setItem('token', token)
  instance = axios.create({
    headers: {
      'Authorization': token
    }
  })
}
