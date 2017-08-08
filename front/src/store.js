import { createStore, applyMiddleware } from 'redux'

import thunk from 'redux-thunk'
import reducer from './reducers'

import {bindDispatch} from './actions'

const store = createStore(reducer, applyMiddleware(thunk))
export default store

bindDispatch(store)
