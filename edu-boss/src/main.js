import Vue from 'vue'
import Element from 'element-ui'
import plugins from './plugins'
import router from './router'
import store from './store'
import App from './App.vue'

// global styles
import './style.scss'

// global config
Vue.config.silent = process.env.NODE_ENV === 'production'
Vue.config.productionTip = false

// plugins
Vue.use(Element)
Vue.use(plugins)

// root components
const app = new Vue({
  router,
  store,
  render: h => h(App)
})

// mount to dom
app.$mount('#app')
