import React from 'react'
import actions from '../actions'
import {compose, lifecycle} from 'recompose'
import {connect} from 'react-redux'
import moment from 'moment'
import classNames from 'classnames'

const MessagePanel = ({messages})=>{
  const refs = {
    input: null
  }

  const submit = (e)=>{
    e.preventDefault()
    actions.postMessage({
      type: 'channel',
      typeId: 'hello',
      body: refs.input.value,
      time: Date.now()
    }, ()=>{
    })
    refs.input.value = ''
  }

  return (
    <div className="message-box"style={{
      height: '100%'
    }}>
      <div style={{
        textAlign: 'center',
        position: 'relative',
        width: '100%',
        height: '100%'
      }}>
        <div style={{
          height: '100%',
          padding: '10px'
        }}>
          <div style={{
            display: 'flex',
            flexDirection: 'column-reverse',
            minHeight: '100%'
          }}>
            <form onSubmit={submit}>
              <div className="input-group" style={{
                width: '100%',
                height: '60px'
              }}>
                <input ref={e=>refs.input = e} type="text" className="form-input input-lg" placeholder="Say Something"/>
                <button className="btn btn-primary input-group-btn btn-lg">Submit</button>
              </div>
            </form>
            <div style={{
              height: 'calc(100% - 60px)',
              overflow: 'auto',
              textAlign: 'left',
              display: 'flex',
              flexDirection: 'column-reverse',
            }}>
              {Object.keys(messages).map(key=>messages[key]).sort((a, b)=>b.time - a.time).map(({body, time, local}, index)=>(
                <div className="tile mb-10" key={index}>
                  <div className="tile-icon">
                    <figure className="avatar">
                      <img alt="Profile" src="https://www.gravatar.com/avatar/302888123d51ff8127794978d8ddb416?d=mm"/>
                    </figure>
                  </div>
                  <div className="tile-content">
                    <p className="tile-title">
                      <a href="#username">Ryan Allred</a> &bull; {moment(time).fromNow()}
                    </p>
                    <p className={classNames('tile-subtitle', {'text-italic': local})}>{body}</p>
                  </div>
                </div>
              ))}
            </div>
          </div>
        </div>
      </div>
    </div>
  )
}

export default compose(
  lifecycle({
    componentDidMount: ()=>actions.listenTo({type: 'channel', typeId: 'hello'})
  }),
  connect(state=>({messages: state.messages}))
)(MessagePanel)
