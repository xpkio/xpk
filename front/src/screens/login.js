import React from 'react'
import {withRouter} from 'react-router-dom'
import {connect} from 'react-redux'
import {compose, withState} from 'recompose'
import classNames from 'classnames'
import actions from '../actions'
import xpklogo from '../assets/xpk.svg'

const Login = ({history, user, isLoading, setLoading})=>{
  if (user) {
    window.setTimeout(()=>history.push('/'), 1)
    return null
  }

  const refs = {
    username: null,
    password: null
  }

  const submit = (e)=>{
    e.preventDefault()
    setLoading(true)
    actions.login({
      username: refs.username.value,
      password: refs.password.value
    }, ()=>{
      history.push('/')
    })
  }

  return (
    <form onSubmit={submit}>
      <div>
        <div className="modal active">
          <div className="modal-container" style={{
            minWidth: '320px',
            maxWidth: '400px',
            width: '50vw',
          }}>
            <div className="modal-header text-center">
              <div className="modal-title"><img alt="logo" style={{height: '128px'}} src={xpklogo}/></div>
            </div>
            <div className="modal-body">
              <div className="content">
                <div className="form-group">
                  <label className="form-label">Username</label>
                  <input
                    ref={e=>refs.username = e}
                    className="form-input"
                    type="text"
                    placeholder="Username"/>
                </div>
                <div className="form-group">
                  <label className="form-label">Password</label>
                  <input
                    ref={e=>refs.password = e}
                    className="form-input"
                    type="password"
                    placeholder="Password"/>
                </div>
              </div>
            </div>
            <div className="modal-footer">
              <button type="button" className="btn btn-link" onClick={e=>history.push('/register')}>Register</button>
              {' '}
              <button className={classNames('btn btn-primary', {loading: isLoading})}>Login</button>
            </div>
          </div>
        </div>
      </div>
    </form>
  )
}

export default compose(
  connect(state=>({user: state.user})),
  withRouter,
  withState('isLoading', 'setLoading', false)
)(Login)
