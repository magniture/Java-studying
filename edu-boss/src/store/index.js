import Vue from 'vue'
import Vuex from 'vuex'

// // Make sure state writeable
// import * as state from './state' // prop readonly
import state from './state'
import getters from './getters'
import mutations from './mutations'
import actions from './actions'
import modules from './modules'
import plugins from './plugins'

Vue.use(Vuex)

const strict = process.env.NODE_ENV !== 'production'

/**
 * @type {import('vuex/types').Store<typeof import('./state').default>}
 */
const store = new Vuex.Store({ state, getters, mutations, actions, modules, plugins, strict })

export default store

// ## Hot module replacement
if (module.hot) {
  module.hot.accept([
    './getters',
    './mutations',
    './actions',
    './modules/courses',
    './modules/sections',
    './modules/lessons',
    './modules/users',
    './modules/options'
  ], () => {
    store.hotUpdate({
      getters: require('./getters'),
      mutations: require('./mutations'),
      actions: require('./actions'),
      modules: {
        courses: require('./modules/courses'),
        sections: require('./modules/sections'),
        lessons: require('./modules/lessons'),
        users: require('./modules/users'),
        options: require('./modules/options')
      }
    })
  })
}
