module.exports = {
  'apps' : [{
    'name': 'caddy',
    'script': 'caddy',
    'exec_interpreter': 'none',
  },{
    'name': 'xpk-front',
    'script': 'npm',
    'args': 'start',
    'exec_interpreter': 'none',
    'cwd': './front',
    'env': {
      'PORT': 3000,
      'XPK_HOST': 'http://xpk.dev'
    }
  },{
    'name': 'xpk-back',
    'script': './gradlew',
    'args': 'bootRun',
    'exec_interpreter': 'none',
    'cwd': './back'
  }]

  'deploy': {
    'production': Object.assign(require('./deployment-secrets.json'),{
      'post-deploy': `
        npm install
        npm run build
        pm2 restart xpk
        git checkout production
        git rebase master
        git push origin production
        git checkout master
      `.trim().split('\n').map(x=>x.trim()).join(' && ').trim(),
      'env': {
        'NODE_ENV': 'production'
      }
    })
  }
}
