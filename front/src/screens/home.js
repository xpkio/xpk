import React from 'react'

import MessagePanel from '../components/message-panel'

export default ()=>(
  <div className="app-panel">
    <div className="app-panel-header text-center">
    </div>
    <div className="app-panel-main">
      <nav className="app-panel-nav">
        <div className="panel">
          <div className="panel-header">
            <button className="btn btn-clear float-right"></button>
            <div className="panel-title">Speak</div>
          </div>
          <div className="panel-nav">
            <ul className="tab tab-block">
              <li className="tab-item active">
                <a href="#app-panels">
                  Channels
                </a>
              </li>
              <li className="tab-item">
                <a href="#app-panels">
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
