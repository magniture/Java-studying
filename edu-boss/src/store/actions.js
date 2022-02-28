import { TokenService, UserService } from "../services";
import {
  CHANGE_TITLE,
  CHANGE_SESSION,
  TOGGLE_SIDEBAR_COLLAPSE,
  CHANGE_BREADCRUMBS,
  CHANGE_SIDERBAR_MENU
} from "./mutation-types";

import { axios } from "../utils";

/**
 * @type {import('vuex/types').ActionTree<typeof import('./state').default>}
 */
const actions = {
  /**
   * 改变页面标题
   */
  changeTitle: ({ commit }, title) => {
    commit(CHANGE_TITLE, title);
  },

  /**
   * 创建新的客户端令牌
   */
  createToken: async ({ commit }, { username, password }) => {
    //请求后台登录接口
    // const res = await TokenService.userLogin({
    //   phone: username.trim(),
    //   password: password.trim()
    // });

    const res = login();
    //console.log(res);

    //判断结果不等于1,登录失败
    if (res.state !== 1) {
      return Promise.resolve(res);
    }

    //获取到content
    const result = JSON.parse(res.content);
    //const result = res.content;

    //将token保存在 session
    commit(CHANGE_SESSION, {
      accessToken: result.access_token
      //refreshToken: result.refresh_token
    });

    return res;
  },

  /**
   * 获取当前登录用户权限
   */
  getUserPermissions: async ({ commit }) => {
    //console.log("访问任何页面都会经过我");
    const res = fetchUserPermissions();
    const { menuList, resourceList } = res.content;
    //debugger;
    let menus = [];
    const formatMenu = treeData => {
      if (treeData.length > 0) {
        return treeData.map(item => formatMenu(item));
      }
      const result = {};
      if (treeData.shown) {
        result.id = treeData.id;
        result.text = treeData.name;
        result.label = treeData.name;
        result.name = treeData.href;
        result.icon = treeData.icon;
        result.shown = treeData.shown;
      } else {
        return "";
      }

      if (treeData.subMenuList) {
        result.children = [];
        treeData.subMenuList.forEach(item => {
          formatMenu(item) && result.children.push(formatMenu(item));
        });
        if (result.children.length === 0) {
          delete result.children;
        }
      }
      return result;
    };

    const memusMap = {};
    const splapMenu = treeData => {
      if (treeData.length > 0) {
        return treeData.map(item => splapMenu(item));
      }
      const result = {};
      result.id = treeData.id;
      result.text = treeData.name;
      result.label = treeData.name;
      result.name = treeData.href;
      result.icon = treeData.icon;
      result.shown = treeData.shown;
      result.name && (memusMap[result.name] = result);
      if (treeData.subMenuList) {
        result.children = [];
        treeData.subMenuList.forEach(item => {
          result.children.push(splapMenu(item));
        });
      }
      return result;
    };

    splapMenu(menuList);

    menus = formatMenu(menuList);
    commit(CHANGE_SIDERBAR_MENU, menus);
    return { menus, resourceList, menuList, memusMap };
  },

  /**
   * 检查客户端令牌是否可用
   */
  checkToken: async ({ commit, getters }) => {
    // validate local store
    if (!getters.session.accessToken) {
      return Promise.resolve(false);
    }

    return Promise.resolve(true);
    // remote
    // try {
    //   await TokenService.get()
    //   return true
    // } catch (err) {
    //   console.error(err)
    //   commit(CHANGE_SESSION, { accessToken: null, refreshToken: null })
    //   return false
    // }
  },

  /**
   * 删除客户端令牌
   */
  deleteToken: async ({ commit, getters }) => {
    // await TokenService.delete(getters.session.accessToken)
    commit(CHANGE_SESSION, { accessToken: null, refreshToken: null });
    await Promise.resolve();
  },

  /**
   * 获取当前登录用户信息
   */
  getCurrentUser: async ({ commit }) => {
    //const res = await UserService.getUserInfo();
    const res = getUserInfo();
    commit(CHANGE_SESSION, { user: res.content });
    return res.data;
  },

  /**
   * 切换边栏的展开收起
   */
  toggleSidebarCollapse: ({ commit }) => {
    commit(TOGGLE_SIDEBAR_COLLAPSE);
  },

  /**
   * 修改面包屑导航
   */
  changeBreadcrumbs: ({ commit }, breadcrumbs) => {
    commit(CHANGE_BREADCRUMBS, breadcrumbs);
  }

  /**
   * 本地定时更新token
   */
  // refreshToken: async ({ commit, state }) => {
  //   const { refreshToken } = state.session;
  //   //const res = await TokenService.fetchUpdateToken(refreshToken);
  //   const res = getResourceToken();
  //   if (!res) {
  //     return Promise.resolve();
  //   }
  //   const result = JSON.parse(res.content);
  //   commit(CHANGE_SESSION, {
  //     accessToken: result.access_token,
  //     refreshToken: result.refresh_token
  //   });
  //   return Promise.resolve();
  // }
};

/**
 * 替换权限管理json
 */
function fetchUserPermissions() {
  return {
    state: 1,
    message: "success",
    content: {
      menuList: [
        {
          id: 1,
          createdBy: "system",
          updatedBy: "system",
          createdTime: 1595259698000,
          updatedTime: 1595259698000,
          parentId: -1,
          name: "权限管理",
          href: "",
          icon: "lock",
          orderNum: 1,
          description: "管理系统角色、菜单、资源",
          shown: true,
          level: 0,
          selected: false,
          subMenuList: [
            {
              id: 2,
              createdBy: "system",
              updatedBy: "system",
              createdTime: 1595259698000,
              updatedTime: 1595259698000,
              parentId: 1,
              name: "角色列表",
              href: "Role",
              icon: "lock",
              orderNum: 1,
              description: "管理系统角色",
              shown: true,
              level: 1,
              selected: false,
              subMenuList: null
            },
            {
              id: 3,
              createdBy: "system",
              updatedBy: "system",
              createdTime: 1595259698000,
              updatedTime: 1595259698000,
              parentId: 1,
              name: "菜单列表",
              href: "Menu",
              icon: "lock",
              orderNum: 2,
              description: "管理系统菜单",
              shown: true,
              level: 1,
              selected: false,
              subMenuList: null
            },
            {
              id: 4,
              createdBy: "system",
              updatedBy: "system",
              createdTime: 1595259698000,
              updatedTime: 1595259698000,
              parentId: 1,
              name: "资源列表",
              href: "Resource",
              icon: "lock",
              orderNum: 3,
              description: "管理系统资源",
              shown: true,
              level: 1,
              selected: false,
              subMenuList: null
            },
            {
              id: 10,
              createdBy: "system",
              updatedBy: "system",
              createdTime: 1595259698000,
              updatedTime: 1595259698000,
              parentId: 1,
              name: "给角色分配菜单页面",
              href: "AllocMenu",
              icon: "setting",
              orderNum: 4,
              description: "给角色分配菜单页面路由",
              shown: false,
              level: 1,
              selected: false,
              subMenuList: null
            },
            {
              id: 11,
              createdBy: "system",
              updatedBy: "system",
              createdTime: 1595259698000,
              updatedTime: 1595259698000,
              parentId: 1,
              name: "给角色分配资源页面",
              href: "AllocResource",
              icon: "setting",
              orderNum: 5,
              description: "给角色分配资源页面路由",
              shown: false,
              level: 1,
              selected: false,
              subMenuList: null
            },
            {
              id: 12,
              createdBy: "system",
              updatedBy: "system",
              createdTime: 1595259698000,
              updatedTime: 1595259698000,
              parentId: 1,
              name: "添加菜单页面",
              href: "AddMenu",
              icon: "setting",
              orderNum: 6,
              description: "添加菜单页路由",
              shown: false,
              level: 1,
              selected: false,
              subMenuList: null
            },
            {
              id: 13,
              createdBy: "system",
              updatedBy: "system",
              createdTime: 1595259698000,
              updatedTime: 1595259698000,
              parentId: 1,
              name: "更新菜单页面",
              href: "UpdateMenu",
              icon: "setting",
              orderNum: 7,
              description: "更新菜单页路由",
              shown: false,
              level: 1,
              selected: false,
              subMenuList: null
            },
            {
              id: 14,
              createdBy: "system",
              updatedBy: "system",
              createdTime: 1595259698000,
              updatedTime: 1595259698000,
              parentId: 1,
              name: "资源分类列表页面",
              href: "ResourceCategory",
              icon: "setting",
              orderNum: 8,
              description: "资源分类列表页面路由",
              shown: false,
              level: 1,
              selected: false,
              subMenuList: null
            }
          ]
        },
        {
          id: 5,
          createdBy: "system",
          updatedBy: "system",
          createdTime: 1595259698000,
          updatedTime: 1595259698000,
          parentId: -1,
          name: "课程管理",
          href: "Courses",
          icon: "film",
          orderNum: 2,
          description: "课程的新增、修改、查看、发布、上下架",
          shown: true,
          level: 0,
          selected: false,
          subMenuList: [
            {
              id: 19,
              createdBy: "system",
              updatedBy: "system",
              createdTime: 1595259698000,
              updatedTime: 1595259698000,
              parentId: 5,
              name: "课程详情页面",
              href: "CourseItem",
              icon: "setting",
              orderNum: 1,
              description: "课程详情页面路由",
              shown: false,
              level: 1,
              selected: false,
              subMenuList: null
            },
            {
              id: 20,
              createdBy: "system",
              updatedBy: "system",
              createdTime: 1595259698000,
              updatedTime: 1595259698000,
              parentId: 5,
              name: "课时信息页面",
              href: "CourseSections",
              icon: "setting",
              orderNum: 2,
              description: "课时信息页面路由",
              shown: false,
              level: 1,
              selected: false,
              subMenuList: null
            },
            {
              id: 21,
              createdBy: "system",
              updatedBy: "system",
              createdTime: 1595259698000,
              updatedTime: 1595259698000,
              parentId: 5,
              name: "课时上传视频",
              href: "VideoOptions",
              icon: "setting",
              orderNum: 3,
              description: "课时上传视频页面路由",
              shown: false,
              level: 1,
              selected: false,
              subMenuList: null
            }
          ]
        },
        {
          id: 6,
          createdBy: "system",
          updatedBy: "system",
          createdTime: 1595259698000,
          updatedTime: 1595259698000,
          parentId: -1,
          name: "用户管理",
          href: "Users",
          icon: "user",
          orderNum: 3,
          description: "用户的查询、禁用、启用",
          shown: true,
          level: 0,
          selected: false,
          subMenuList: null
        },
        {
          id: 7,
          createdBy: "system",
          updatedBy: "system",
          createdTime: 1595259698000,
          updatedTime: 1595259698000,
          parentId: -1,
          name: "广告管理",
          href: "",
          icon: "setting",
          orderNum: 4,
          description: "广告、广告位管理",
          shown: true,
          level: 0,
          selected: false,
          subMenuList: [
            {
              id: 8,
              createdBy: "system",
              updatedBy: "system",
              createdTime: 1595259698000,
              updatedTime: 1595259698000,
              parentId: 7,
              name: "广告列表",
              href: "Advertise",
              icon: "setting",
              orderNum: 1,
              description: "广告管理",
              shown: true,
              level: 1,
              selected: false,
              subMenuList: null
            },
            {
              id: 9,
              createdBy: "system",
              updatedBy: "system",
              createdTime: 1595259698000,
              updatedTime: 1595259698000,
              parentId: 7,
              name: "广告位列表",
              href: "AdvertiseSpace",
              icon: "setting",
              orderNum: 2,
              description: "广告位管理",
              shown: true,
              level: 1,
              selected: false,
              subMenuList: null
            },
            {
              id: 15,
              createdBy: "system",
              updatedBy: "system",
              createdTime: 1595259698000,
              updatedTime: 1595259698000,
              parentId: 7,
              name: "添加广告页面",
              href: "AddAdvertise",
              icon: "setting",
              orderNum: 3,
              description: "添加广告页面路由",
              shown: false,
              level: 1,
              selected: false,
              subMenuList: null
            },
            {
              id: 16,
              createdBy: "system",
              updatedBy: "system",
              createdTime: 1595259698000,
              updatedTime: 1595259698000,
              parentId: 7,
              name: "编辑广告页面",
              href: "UpdateAdvertise",
              icon: "setting",
              orderNum: 4,
              description: "编辑广告页面路由",
              shown: false,
              level: 1,
              selected: false,
              subMenuList: null
            },
            {
              id: 17,
              createdBy: "system",
              updatedBy: "system",
              createdTime: 1595259698000,
              updatedTime: 1595259698000,
              parentId: 7,
              name: "添加广告位页面",
              href: "AddAdvertiseSpace",
              icon: "setting",
              orderNum: 5,
              description: "添加广告位页面路由",
              shown: false,
              level: 1,
              selected: false,
              subMenuList: null
            },
            {
              id: 18,
              createdBy: "system",
              updatedBy: "system",
              createdTime: 1595259698000,
              updatedTime: 1595259698000,
              parentId: 7,
              name: "更新广告位页面",
              href: "UpdateAdvertiseSpace",
              icon: "setting",
              orderNum: 6,
              description: "更新广告位页面路由",
              shown: false,
              level: 1,
              selected: false,
              subMenuList: null
            }
          ]
        }
      ],
      resourceList: [
        {
          id: 1,
          createdBy: "system",
          updatedBy: "system",
          createdTime: 1595259717000,
          updatedTime: 1595259717000,
          name: "获取所有角色",
          categoryId: 1,
          url: "/boss/role/all",
          description: "获取所有角色",
          selected: false
        },
        {
          id: 2,
          createdBy: "system",
          updatedBy: "system",
          createdTime: 1595259717000,
          updatedTime: 1595259717000,
          name: "给用户分配角色",
          categoryId: 1,
          url: "/boss/role/allocateUserRoles",
          description: "给用户分配角色",
          selected: false
        },
        {
          id: 3,
          createdBy: "system",
          updatedBy: "system",
          createdTime: 1595259717000,
          updatedTime: 1595259717000,
          name: "按条件查询角色",
          categoryId: 1,
          url: "/boss/role/getRolePages",
          description: "按条件查询角色",
          selected: false
        },
        {
          id: 4,
          createdBy: "system",
          updatedBy: "system",
          createdTime: 1595259717000,
          updatedTime: 1595259717000,
          name: "列出所有角色并标记用户是否拥有",
          categoryId: 1,
          url: "/boss/role/getRolesWithUserPermission",
          description: "列出所有角色并标记用户是否拥有",
          selected: false
        },
        {
          id: 5,
          createdBy: "system",
          updatedBy: "system",
          createdTime: 1595259717000,
          updatedTime: 1595259717000,
          name: "保存或者更新角色",
          categoryId: 1,
          url: "/boss/role/saveOrUpdate",
          description: "保存或者更新角色",
          selected: false
        },
        {
          id: 6,
          createdBy: "system",
          updatedBy: "system",
          createdTime: 1595259717000,
          updatedTime: 1595259717000,
          name: "查询用户角色",
          categoryId: 1,
          url: "/boss/role/user/{userId}",
          description: "查询用户角色",
          selected: false
        },
        {
          id: 7,
          createdBy: "system",
          updatedBy: "system",
          createdTime: 1595259717000,
          updatedTime: 1595259717000,
          name: "获取角色",
          categoryId: 1,
          url: "/boss/role/{id}",
          description: "获取角色",
          selected: false
        },
        {
          id: 8,
          createdBy: "system",
          updatedBy: "system",
          createdTime: 1595259717000,
          updatedTime: 1595259717000,
          name: "删除角色",
          categoryId: 1,
          url: "/boss/role/{id}",
          description: "删除角色",
          selected: false
        },
        {
          id: 9,
          createdBy: "system",
          updatedBy: "system",
          createdTime: 1595259717000,
          updatedTime: 1595259717000,
          name: "给角色分配菜单",
          categoryId: 2,
          url: "/boss/menu/allocateRoleMenus",
          description: "给角色分配菜单",
          selected: false
        },
        {
          id: 10,
          createdBy: "system",
          updatedBy: "system",
          createdTime: 1595259717000,
          updatedTime: 1595259717000,
          name: "获取所有菜单",
          categoryId: 2,
          url: "/boss/menu/getAll",
          description: "获取所有菜单",
          selected: false
        },
        {
          id: 11,
          createdBy: "system",
          updatedBy: "system",
          createdTime: 1595259717000,
          updatedTime: 1595259717000,
          name: "获取编辑菜单页面信息",
          categoryId: 2,
          url: "/boss/menu/getEditMenuInfo",
          description: "获取编辑菜单页面信息",
          selected: false
        },
        {
          id: 12,
          createdBy: "system",
          updatedBy: "system",
          createdTime: 1595259717000,
          updatedTime: 1595259717000,
          name: "获取所有菜单并按层级展示",
          categoryId: 2,
          url: "/boss/menu/getMenuNodeList",
          description: "获取所有菜单并按层级展示",
          selected: false
        },
        {
          id: 13,
          createdBy: "system",
          updatedBy: "system",
          createdTime: 1595259717000,
          updatedTime: 1595259717000,
          name: "按条件分页查询菜单",
          categoryId: 2,
          url: "/boss/menu/getMenuPages",
          description: "按条件分页查询菜单",
          selected: false
        },
        {
          id: 14,
          createdBy: "system",
          updatedBy: "system",
          createdTime: 1595259717000,
          updatedTime: 1595259717000,
          name: "获取角色拥有的菜单列表",
          categoryId: 2,
          url: "/boss/menu/getRoleMenus",
          description: "获取角色拥有的菜单列表",
          selected: false
        },
        {
          id: 15,
          createdBy: "system",
          updatedBy: "system",
          createdTime: 1595259717000,
          updatedTime: 1595259717000,
          name: "保存或新增菜单",
          categoryId: 2,
          url: "/boss/menu/saveOrUpdate",
          description: "保存或新增菜单",
          selected: false
        },
        {
          id: 16,
          createdBy: "system",
          updatedBy: "system",
          createdTime: 1595259717000,
          updatedTime: 1595259717000,
          name: "是否显示开关",
          categoryId: 2,
          url: "/boss/menu/switchShown",
          description: "是否显示开关",
          selected: false
        },
        {
          id: 17,
          createdBy: "system",
          updatedBy: "system",
          createdTime: 1595259717000,
          updatedTime: 1595259717000,
          name: "根据ID查询菜单",
          categoryId: 2,
          url: "/boss/menu/{id}",
          description: "根据ID查询菜单",
          selected: false
        },
        {
          id: 18,
          createdBy: "system",
          updatedBy: "system",
          createdTime: 1595259717000,
          updatedTime: 1595259717000,
          name: "删除菜单",
          categoryId: 2,
          url: "/boss/menu/{id}",
          description: "删除菜单",
          selected: false
        },
        {
          id: 19,
          createdBy: "system",
          updatedBy: "system",
          createdTime: 1595259717000,
          updatedTime: 1595259717000,
          name: "给角色分配资源",
          categoryId: 3,
          url: "/boss/resource/allocateRoleResources",
          description: "给角色分配资源",
          selected: false
        },
        {
          id: 20,
          createdBy: "system",
          updatedBy: "system",
          createdTime: 1595259717000,
          updatedTime: 1595259717000,
          name: "查询资源分类列表",
          categoryId: 3,
          url: "/boss/resource/category/getAll",
          description: "查询资源分类列表",
          selected: false
        },
        {
          id: 21,
          createdBy: "system",
          updatedBy: "system",
          createdTime: 1595259717000,
          updatedTime: 1595259717000,
          name: "保存或更新资源分类",
          categoryId: 3,
          url: "/boss/resource/category/saveOrderUpdate",
          description: "保存或更新资源分类",
          selected: false
        },
        {
          id: 22,
          createdBy: "system",
          updatedBy: "system",
          createdTime: 1595259717000,
          updatedTime: 1595259717000,
          name: "删除资源分类",
          categoryId: 3,
          url: "/boss/resource/category/{id}",
          description: "删除资源分类",
          selected: false
        },
        {
          id: 23,
          createdBy: "system",
          updatedBy: "system",
          createdTime: 1595259717000,
          updatedTime: 1595259717000,
          name: "获取所有资源",
          categoryId: 3,
          url: "/boss/resource/getAll",
          description: "获取所有资源",
          selected: false
        },
        {
          id: 24,
          createdBy: "system",
          updatedBy: "system",
          createdTime: 1595259717000,
          updatedTime: 1595259717000,
          name: "按条件分页查询资源",
          categoryId: 3,
          url: "/boss/resource/getResourcePages",
          description: "按条件分页查询资源",
          selected: false
        },
        {
          id: 25,
          createdBy: "system",
          updatedBy: "system",
          createdTime: 1595259717000,
          updatedTime: 1595259717000,
          name: "获取角色拥有的资源列表",
          categoryId: 3,
          url: "/boss/resource/getRoleResources",
          description: "获取角色拥有的资源列表",
          selected: false
        },
        {
          id: 26,
          createdBy: "system",
          updatedBy: "system",
          createdTime: 1595259717000,
          updatedTime: 1595259717000,
          name: "保存或者更新资源",
          categoryId: 3,
          url: "/boss/resource/saveOrUpdate",
          description: "保存或者更新资源",
          selected: false
        },
        {
          id: 27,
          createdBy: "system",
          updatedBy: "system",
          createdTime: 1595259717000,
          updatedTime: 1595259717000,
          name: "获取资源",
          categoryId: 3,
          url: "/boss/resource/{id}",
          description: "获取资源",
          selected: false
        },
        {
          id: 28,
          createdBy: "system",
          updatedBy: "system",
          createdTime: 1595259717000,
          updatedTime: 1595259717000,
          name: "删除资源",
          categoryId: 3,
          url: "/boss/resource/{id}",
          description: "删除资源",
          selected: false
        },
        {
          id: 29,
          createdBy: "system",
          updatedBy: "system",
          createdTime: 1595259717000,
          updatedTime: 1595259717000,
          name: "封禁用户",
          categoryId: 5,
          url: "/boss/user/forbidUser",
          description: "封禁用户",
          selected: false
        },
        {
          id: 30,
          createdBy: "system",
          updatedBy: "system",
          createdTime: 1595259717000,
          updatedTime: 1595259717000,
          name: "分页查询用户信息",
          categoryId: 5,
          url: "/boss/user/getUserPages",
          description: "分页查询用户信息",
          selected: false
        },
        {
          id: 31,
          createdBy: "system",
          updatedBy: "system",
          createdTime: 1595259717000,
          updatedTime: 1595259717000,
          name: "获取用户菜单和资源权限列表",
          categoryId: 5,
          url: "/boss/permission/getUserPermissions",
          description: "获取用户菜单和资源权限列表",
          selected: false
        },
        {
          id: 32,
          createdBy: "system",
          updatedBy: "system",
          createdTime: 1595259717000,
          updatedTime: 1595259717000,
          name: "查询用户角色",
          categoryId: 1,
          url: "/boss/role/user/{userId}",
          description: "查询用户角色",
          selected: false
        },
        {
          id: 33,
          createdBy: "system",
          updatedBy: "system",
          createdTime: 1595259717000,
          updatedTime: 1595259717000,
          name: "课程上下架",
          categoryId: 4,
          url: "/boss/course/changeState",
          description: "课程上下架",
          selected: false
        },
        {
          id: 34,
          createdBy: "system",
          updatedBy: "system",
          createdTime: 1595259717000,
          updatedTime: 1595259717000,
          name: "新建课程页面路由",
          categoryId: 4,
          url: "/#/courses/new",
          description: "新建课程页面路由",
          selected: false
        },
        {
          id: 35,
          createdBy: "system",
          updatedBy: "system",
          createdTime: 1595259717000,
          updatedTime: 1595259717000,
          name: "通过课程Id获取课程信息",
          categoryId: 4,
          url: "/boss/course/getCourseById",
          description: "通过课程Id获取课程信息",
          selected: false
        },
        {
          id: 36,
          createdBy: "system",
          updatedBy: "system",
          createdTime: 1595259717000,
          updatedTime: 1595259717000,
          name: "分页查询课程信息",
          categoryId: 4,
          url: "/boss/course/getQueryCourses",
          description: "分页查询课程信息",
          selected: false
        },
        {
          id: 37,
          createdBy: "system",
          updatedBy: "system",
          createdTime: 1595259717000,
          updatedTime: 1595259717000,
          name: "保存或者更新课程信息",
          categoryId: 4,
          url: "/boss/course/saveOrUpdateCourse",
          description: "保存或者更新课程信息",
          selected: false
        },
        {
          id: 38,
          createdBy: "system",
          updatedBy: "system",
          createdTime: 1595259717000,
          updatedTime: 1595259717000,
          name: "上传图片",
          categoryId: 4,
          url: "/boss/course/upload",
          description: "上传图片",
          selected: false
        },
        {
          id: 39,
          createdBy: "system",
          updatedBy: "system",
          createdTime: 1595259717000,
          updatedTime: 1595259717000,
          name: "保存活动商品",
          categoryId: 4,
          url: "/boss/activityCourse/save",
          description: "保存活动商品",
          selected: false
        },
        {
          id: 40,
          createdBy: "system",
          updatedBy: "system",
          createdTime: 1595259717000,
          updatedTime: 1595259717000,
          name: "更新活动商品状态",
          categoryId: 4,
          url: "/boss/activityCourse/updateStatus",
          description: "更新活动商品状态",
          selected: false
        },
        {
          id: 41,
          createdBy: "system",
          updatedBy: "system",
          createdTime: 1595259717000,
          updatedTime: 1595259717000,
          name: "获取章节",
          categoryId: 4,
          url: "/boss/course/section/getBySectionId",
          description: "获取章节",
          selected: false
        },
        {
          id: 42,
          createdBy: "system",
          updatedBy: "system",
          createdTime: 1595259717000,
          updatedTime: 1595259717000,
          name: "获取章节和课时",
          categoryId: 4,
          url: "/boss/course/section/getSectionAndLesson",
          description: "获取章节和课时",
          selected: false
        },
        {
          id: 43,
          createdBy: "system",
          updatedBy: "system",
          createdTime: 1595259717000,
          updatedTime: 1595259717000,
          name: "保存或更新章节",
          categoryId: 4,
          url: "/boss/course/section/saveOrUpdateSection",
          description: "保存或更新章节",
          selected: false
        },
        {
          id: 44,
          createdBy: "system",
          updatedBy: "system",
          createdTime: 1595259717000,
          updatedTime: 1595259717000,
          name: "获取课时内容",
          categoryId: 4,
          url: "/boss/course/lesson/getById",
          description: "获取课时内容",
          selected: false
        },
        {
          id: 45,
          createdBy: "system",
          updatedBy: "system",
          createdTime: 1595259717000,
          updatedTime: 1595259717000,
          name: "保存或更新课时",
          categoryId: 4,
          url: "/boss/course/lesson/saveOrUpdate",
          description: "保存或更新课时",
          selected: false
        },
        {
          id: 46,
          createdBy: "system",
          updatedBy: "system",
          createdTime: 1595259717000,
          updatedTime: 1595259717000,
          name: "获取阿里云图片上传凭证",
          categoryId: 6,
          url: "/boss/course/upload/aliyunImagUploadAddressAdnAuth.json",
          description: "获取阿里云图片上传凭证",
          selected: false
        },
        {
          id: 47,
          createdBy: "system",
          updatedBy: "system",
          createdTime: 1595259717000,
          updatedTime: 1595259717000,
          name: "阿里云转码请求",
          categoryId: 6,
          url: "/boss/course/upload/aliyunTransCode.json",
          description: "阿里云转码请求",
          selected: false
        },
        {
          id: 48,
          createdBy: "system",
          updatedBy: "system",
          createdTime: 1595259717000,
          updatedTime: 1595259717000,
          name: "阿里云转码进度",
          categoryId: 6,
          url: "/boss/course/upload/aliyunTransCodePercent.json",
          description: "阿里云转码进度",
          selected: false
        },
        {
          id: 49,
          createdBy: "system",
          updatedBy: "system",
          createdTime: 1595259717000,
          updatedTime: 1595259717000,
          name: "获取阿里云视频上传凭证",
          categoryId: 6,
          url: "/boss/course/upload/aliyunVideoUploadAddressAdnAuth.json",
          description: "获取阿里云视频上传凭证",
          selected: false
        },
        {
          id: 50,
          createdBy: "system",
          updatedBy: "system",
          createdTime: 1595259717000,
          updatedTime: 1595259717000,
          name: "获取媒体信息",
          categoryId: 6,
          url: "/boss/course/upload/getMediaByLessonId.json",
          description: "获取媒体信息",
          selected: false
        },
        {
          id: 51,
          createdBy: "system",
          updatedBy: "system",
          createdTime: 1595259717000,
          updatedTime: 1595259717000,
          name: "刷新阿里云视频上传凭证",
          categoryId: 6,
          url:
            "/boss/course/upload/refreshAliyunVideoUploadAddressAdnAuth.json",
          description: "刷新阿里云视频上传凭证",
          selected: false
        }
      ]
    },
    success: true
  };
}

function getUserInfo() {
  return {
    state: 1,
    message: "success",
    content: {
      isUpdatedPassword: true,
      userName: "15510792995",
      portrait: null
    },
    success: true
  };
}

function getResourceToken() {
  return {
    state: 1,
    message: "success",
    content:
      '{"access_token":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoiMTAwMDMwMDE2IiwidXNlcl9uYW1lIjoiMTU1MTA3OTI5OTUiLCJzY29wZSI6WyJyZWFkIl0sIm9yZ2FuaXphdGlvbiI6IjE1NTEwNzkyOTk1IiwiZXhwIjoxNTk3MjA2MDY2LCJhdXRob3JpdGllcyI6WyJBRE1JTiJdLCJqdGkiOiJUV0Fxbm9GZnZwNi1UMFlMNXpaLTlUOE8yLW8iLCJjbGllbnRfaWQiOiJ0ZXN0X2NsaWVudCJ9.IQywo8Gbg8OTYRM-X8N-HTCg-739rXaeP7V7UySz41E","token_type":"bearer","refresh_token":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoiMTAwMDMwMDE2IiwidXNlcl9uYW1lIjoiMTU1MTA3OTI5OTUiLCJzY29wZSI6WyJyZWFkIl0sIm9yZ2FuaXphdGlvbiI6IjE1NTEwNzkyOTk1IiwiYXRpIjoiVFdBcW5vRmZ2cDYtVDBZTDV6Wi05VDhPMi1vIiwiZXhwIjoxNTk3MzA2ODY2LCJhdXRob3JpdGllcyI6WyJBRE1JTiJdLCJqdGkiOiJqQWQ4QzJvTHRrRXhMUVNmZUVLVHN4WHNpeEEiLCJjbGllbnRfaWQiOiJ0ZXN0X2NsaWVudCJ9.xoabCnmW_ltJn4IMCOvK_FlvuSdpgeblyxOAFm18xW4","expires_in":7199,"scope":"read","user_id":"100030016","organization":"15510792995","jti":"TWAqnoFfvp6-T0YL5zZ-9T8O2-o"}',
    success: true
  };
}

function login() {
  return {
    state: 1,
    message: "success",
    content:
      '{"access_token":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoiMTAwMDMwMDE2IiwidXNlcl9uYW1lIjoiMTU1MTA3OTI5OTUiLCJzY29wZSI6WyJyZWFkIl0sIm9yZ2FuaXphdGlvbiI6IjE1NTEwNzkyOTk1IiwiZXhwIjoxNTk3MjA4NjAwLCJhdXRob3JpdGllcyI6WyJBRE1JTiJdLCJqdGkiOiIwbWwxc1VyN2dOdnIxaFRvM09EY3NvNklfMTAiLCJjbGllbnRfaWQiOiJ0ZXN0X2NsaWVudCJ9.mQ5nWspQHLlsgOhDhwPRX3mbSnsWHuBkmgXhbVb5JmQ","token_type":"bearer","refresh_token":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoiMTAwMDMwMDE2IiwidXNlcl9uYW1lIjoiMTU1MTA3OTI5OTUiLCJzY29wZSI6WyJyZWFkIl0sIm9yZ2FuaXphdGlvbiI6IjE1NTEwNzkyOTk1IiwiYXRpIjoiMG1sMXNVcjdnTnZyMWhUbzNPRGNzbzZJXzEwIiwiZXhwIjoxNTk3MzA5NDAwLCJhdXRob3JpdGllcyI6WyJBRE1JTiJdLCJqdGkiOiJNQ1hiYjBTckdyU1ZUTjB6Q1Y2a3RpUk9jUTQiLCJjbGllbnRfaWQiOiJ0ZXN0X2NsaWVudCJ9.2t2J8lC3ulQ_ytEHTZ2qSxW8XLlcFgThbdFwHk7RcZA","expires_in":6389,"scope":"read","user_id":"100030016","organization":"15510792995","jti":"0ml1sUr7gNvr1hTo3ODcso6I_10"}',
    success: true
  };
}

export default actions;
