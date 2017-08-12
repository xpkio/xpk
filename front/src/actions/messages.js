import * as types from '../types'
import {withServer, eventSource} from '../request'

export const addMessage = (...messages)=>({
  type: types.SET_MESSAGES,
  messages: messages.reduce((obj, message)=>({...obj, [message.time]: message}), {}) // @TODO use id instead of time
})

export const postMessage = ({type, typeId, body, time}, cb=()=>{})=>async (dispatch)=>{
  const authRes = await withServer().post(`/api/${type}/${typeId}`, {body, time})
  console.log(authRes.data)
  dispatch(addMessage({body, time, local: true}))
  cb()
}

export const listenTo = ({type, typeId})=>async (dispatch)=>{
  const es = eventSource('/api/channel/hello')
  es.onmessage = (e)=>{
    const message = JSON.parse(e.data)
    dispatch(addMessage(message))
  }
}
