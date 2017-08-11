import React from 'react'
import {compose, lifecycle} from 'recompose'
import actions from './actions'

const App = ({children})=>{
  return (
    <div id="app">
      {children}
    </div>
  )
}

export default compose(
  lifecycle({
    componentDidMount: ()=>{
      if (localStorage.getItem('token')) {
        actions.restoreUser(localStorage.getItem('token'))
      }
    }
  })
)(App)
