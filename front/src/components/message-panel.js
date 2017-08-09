import React from 'react'

const posts = [1,1,1,1,1,1,1,1,1,1,1,1,1,1]

export default ()=>(
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
          <div className="input-group" style={{
            width: '100%',
            height: '60px'
          }}>
            <input type="text" className="form-input input-lg" placeholder="Say Something"/>
            {/* <button className="btn btn-primary input-group-btn btn-lg">Submit</button> */}
          </div>
          <div style={{
            height: 'calc(100% - 60px)',
            overflow: 'auto',
            textAlign: 'left',
            display: 'flex',
            flexDirection: 'column-reverse',
          }}>
            {posts.map((x, index)=>(
              <div className="tile mb-10" key={index}>
                <div className="tile-icon">
                  <figure className="avatar">
                    <img src="https://www.gravatar.com/avatar/302888123d51ff8127794978d8ddb416?d=mm"/>
                  </figure>
                </div>
                <div className="tile-content">
                  <p className="tile-title">
                    <a href="#">Ryan Allred</a> &bull; 3 minutes ago
                    {' '}
                    <span style={{color: '#d50000'}} className="tooltop tooltip-top" data-tooltip="Tooltip">&bull;</span>
                  </p>
                  <p className="tile-subtitle">Earth's Mightiest Heroes joined forces to take on threats that were too big for any one hero to tackle...</p>
                </div>
              </div>
            ))}

          </div>
        </div>
      </div>
    </div>
  </div>
)
