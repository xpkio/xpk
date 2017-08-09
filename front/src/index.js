import React from 'react'
import ReactDOM from 'react-dom'
import {Provider} from 'react-redux'
import App from './app'
import store from './store'
import registerServiceWorker from './registerServiceWorker'
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom'

import './index.css'

//Routes
import Home from './screens/home'
import Login from './screens/login'
import NoMatch from './screens/nomatch'

const routes = [{
  title: 'Home',
  path: '/',
  component: Home,
  exact: true
},{
  title: 'Login',
  path: '/login',
  component: Login,
  exact: true
}]

ReactDOM.render((
  <Provider store={store}>
    <Router>
      <App>
        <Switch>
          {routes.map((route, i) => <Route key={i} {...route} />)}
          <Route component={NoMatch} />
        </Switch>
      </App>
    </Router>
  </Provider>
), document.getElementById('root'))

registerServiceWorker()
