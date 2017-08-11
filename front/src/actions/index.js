import {bindActionCreators} from 'redux'
import * as user from './user'
import * as messages from './messages'

const actions = {}

export const bindDispatch = (store)=>{
  Object.assign(actions, bindActionCreators({
    ...user,
    ...messages
  }, store.dispatch))
}

export default actions
