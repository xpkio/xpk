import {bindActionCreators} from 'redux'
import * as user from './user'

const actions = {}

export const bindDispatch = (store)=>{
  Object.assign(actions, bindActionCreators({
    ...user
  }, store.dispatch))
}

export default actions
