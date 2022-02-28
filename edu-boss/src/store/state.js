import { storage } from '../utils'

export default {
  /**
   * 页面标题
   */
  title: process.env.VUE_APP_TITLE,

  /**
   * 客户端会话信息
   */
  session: storage.get('session') ?? {},

  /**
   * 侧边导航栏
   */
  sidebar: {
    /**
     * 是否收起边栏
     */
    collapse: storage.get('sidebar_collapse') ?? false,

    /**
     * 侧边菜单
     */
    menus: []
  },

  /**
   * 顶部导航栏
   */
  navbar: {
    breadcrumbs: []
  },

  // ==================== DEMO ====================

  /**
   * 计数器
   */
  count: storage.get('demo_count') ?? 0
}
