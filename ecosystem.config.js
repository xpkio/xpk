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
  }],

  'deploy': {
    'production': Object.assign(require('./deployment-secrets.json'),{
      'post-deploy': `
        # React Build
        cd front
        npm install
        npm run build
        cd ..

        # Gradle build
        cd back
        ./gradlew clean
        cd ..

        # Restart pm2
        pm2 restart xpk
      ` .trim()
        .split('\n')
        .map(x=>x.trim())
        .filter(x=>x.length && !x.startsWith('#'))
        .join(' && '),
      'env': {
        'NODE_ENV': 'production'
      }
    })
  }
}
