import { CHANGE_TITLE, CHANGE_SESSION, TOGGLE_SIDEBAR_COLLAPSE, CHANGE_BREADCRUMBS, INCREMENT, DECREMENT, CHANGE_SIDERBAR_MENU } from './mutation-types'

/**
 * @type {import('vuex/types').MutationTree<typeof import('./state').default>}
 */
const mutations = {
  /**
   * 改变页面标题
   */
  [CHANGE_TITLE]: (state, title) => {
    state.title = title
  },

  /**
   * 改变客户端会话信息
   */
  [CHANGE_SESSION]: (state, session) => {
    // TODO: new session mixin
    Object.assign(state.session, session)
  },

  /**
   * 改变菜单权限
   */
  [CHANGE_SIDERBAR_MENU]: (state, menus) => {
    // TODO: new session mixin
    Object.assign(state.sidebar, { menus })
  },

  /**
   * 切换边栏的展开收起
   */
  [TOGGLE_SIDEBAR_COLLAPSE]: state => {
    state.sidebar.collapse = !state.sidebar.collapse
  },

  /**
   * 修改面包屑导航
   */
  [CHANGE_BREADCRUMBS]: (state, breadcrumbs) => {
    state.navbar.breadcrumbs = breadcrumbs
  },

  // ==================== DEMO ====================

  /**
   * 增加计数器计数
   */
  [INCREMENT]: state => {
    state.count++
  },

  /**
   * 减少计数器计数
   */
  [DECREMENT]: state => {
    state.count--
  }
}

export default mutations
