import React from 'react'

export default ()=>(
  <div>
    <div className="modal active">
      <div className="modal-container" style={{
        minWidth: '320px',
        maxWidth: '400px',
        width: '50vw',
      }}>
        <div className="modal-header">
          <div className="modal-title">xpk.io</div>
        </div>
        <div className="modal-body">
          <div className="content">
            <form>
              <div className="form-group">
                <label className="form-label">Username</label>
                <input className="form-input" type="text" placeholder="Username"/>
              </div>
              <div className="form-group">
                <label className="form-label">Password</label>
                <input className="form-input" type="password" placeholder="Password"/>
              </div>
            </form>
          </div>
        </div>
        <div className="modal-footer">
          <button className="btn btn-link">Close</button>
          {' '}
          <button className="btn btn-primary">Login</button>
        </div>
      </div>
    </div>
  </div>
)
