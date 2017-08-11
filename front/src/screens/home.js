import React from 'react'
import {withRouter} from 'react-router-dom'
import {connect} from 'react-redux'
import MessagePanel from '../components/message-panel'
import xpklogo from '../assets/xpk.svg'

const Home = ({history, user})=>{
  if (!user) {
    window.setTimeout(()=>history.push('/login'), 1)
    return null
  }

  return (
    <div className="app-panel">
      <div className="app-panel-main">
        <nav className="app-panel-nav">
          <div className="panel">
            <div className="panel-header">
              <div className="panel-title"><img alt="logo" style={{height: '128px'}} src={xpklogo}/></div>
            </div>
            <div className="panel-nav">
              <ul className="tab tab-block">
                <li className="tab-item active">
                  <a href="#app-panels">
                    Channels
                  </a>
                </li>
                <li className="tab-item">
                  <a href="#app-panels" className="badge">
                    You
                  </a>
                </li>
                <li className="tab-item">
                  <a href="#app-panels">
                    Other
                  </a>
                </li>
              </ul>
            </div>
            <div className="panel-body">
            </div>
          </div>
        </nav>
        <div className="app-panel-body">
          <MessagePanel/>
        </div>
      </div>
    </div>
  )
}

export default connect(
  state=>({user: state.user})
)(withRouter(Home))
