import Layout from "../views/Layout.vue";

/**
 * @type {import('vue-router/types/router').RouteConfig[]}
 */
const routes = [
  {
    path: "/login",
    name: "Login",
    meta: { requireAuth: false, title: process.env.VUE_APP_NAME },
    component: () =>
      import(/* webpackChunkName: 'login' */ "../views/Login.vue")
  },
  {
    path: "/",
    component: Layout,
    children: [
      // {
      //   path: '/',
      //   name: 'Home',
      //   meta: { requireAuth: true, title: process.env.VUE_APP_NAME },
      //   component: () => import(/* webpackChunkName: 'home' */ '../views/Home.vue')
      // },
      {
        path: "/",
        name: "Home",
        meta: { requireAuth: true, title: process.env.VUE_APP_NAME },
        component: () =>
          import(
            /* webpackChunkName: 'home' */ "../views/CourseManage/Courses.vue"
          )
      },
      {
        path: "/courses",
        name: "Courses",
        meta: { requireAuth: true, title: "课程管理" },
        component: () =>
          import(
            /* webpackChunkName: 'courses' */ "../views/CourseManage/Courses.vue"
          )
      },
      {
        path: "/courses/:courseId",
        name: "CourseItem",
        meta: { requireAuth: true, title: "课程详情" },
        component: () =>
          import(
            /* webpackChunkName: 'courses' */ "../views/CourseManage/CourseItem.vue"
          )
      },
      {
        path: "/courses/:courseId/sections",
        name: "CourseSections",
        meta: { requireAuth: true, title: "课时信息" },
        component: () =>
          import(
            /* webpackChunkName: 'courses' */ "../views/CourseManage/CourseSections.vue"
          )
      },
      {
        path: "/courses/:courseId/video",
        name: "VideoOptions",
        meta: { requireAuth: true, title: "课时上传视频" },
        component: () =>
          import(
            /* webpackChunkName: 'courses' */ "../views/CourseManage/VideoOptions.vue"
          )
      },
      {
        path: "/users",
        name: "Users",
        meta: { requireAuth: true, title: "用户列表" },
        component: () =>
          import(/* webpackChunkName: 'users' */ "../views/Users.vue")
      },
      {
        path: "/comments",
        name: "Comments",
        meta: { requireAuth: true, title: "留言管理" },
        component: () =>
          import(
            /* webpackChunkName: 'comments' */ "../views/CommentManage/Comments.vue"
          )
      },
      {
        path: "/comments/:commentId",
        name: "CommentItem",
        meta: { requireAuth: true, title: "留言详情" },
        component: () =>
          import(
            /* webpackChunkName: 'comments' */ "../views/CommentManage/CommentItem.vue"
          )
      },
      {
        path: "role",
        name: "Role",
        component: () =>
          import(
            /* webpackChunkName: 'role' */ "../views/PermissionManage/Roles"
          ),
        meta: { requireAuth: true, title: "角色管理" }
      },
      {
        path: "allocMenu",
        name: "AllocMenu",
        component: () =>
          import(
            /* webpackChunkName: 'allocMenu' */ "../views/PermissionManage/AllocMenu"
          ),
        meta: { requireAuth: true, title: "角色菜单管理" }
      },
      {
        path: "allocResource",
        name: "AllocResource",
        component: () =>
          import(
            /* webpackChunkName: 'allocResource' */ "../views/PermissionManage/AllocResource"
          ),
        meta: { requireAuth: true, title: "角色资源管理" }
      },
      {
        path: "menu",
        name: "Menu",
        component: () =>
          import(
            /* webpackChunkName: 'menu' */ "../views/PermissionManage/Menus"
          ),
        meta: { requireAuth: true, title: "菜单管理" }
      },
      {
        path: "addMenu",
        name: "AddMenu",
        component: () =>
          import(
            /* webpackChunkName: 'menuAdd' */ "../views/PermissionManage/AddMenu"
          ),
        meta: { requireAuth: true, title: "添加菜单" }
      },
      {
        path: "updateMenu",
        name: "UpdateMenu",
        component: () =>
          import(
            /* webpackChunkName: 'menuUpdate' */ "../views/PermissionManage/UpdateMenu"
          ),
        meta: { requireAuth: true, title: "编辑菜单" }
      },
      {
        path: "resource",
        name: "Resource",
        component: () =>
          import(
            /* webpackChunkName: 'resource' */ "../views/PermissionManage/Resources"
          ),
        meta: { requireAuth: true, title: "资源管理" }
      },
      {
        path: "resourceCategory",
        name: "ResourceCategory",
        component: () =>
          import(
            /* webpackChunkName: 'categoryList' */ "../views/PermissionManage/CategoryList"
          ),
        meta: { requireAuth: true, title: "资源分类" }
      },
      {
        path: "advertise",
        name: "Advertise",
        component: () => import("../views/AdvertiseManage/Advertises"),
        meta: { requireAuth: true, title: "广告管理" }
      },
      
      {
        path: "addAdvertise",
        name: "AddAdvertise",
        component: () => import("../views/AdvertiseManage/AddAdvertise"),
        meta: { requireAuth: true, title: "添加广告" }
      },
      {
        path: "updateAdvertise",
        name: "UpdateAdvertise",
        component: () => import("../views/AdvertiseManage/UpdateAdvertise"),
        meta: { requireAuth: true, title: "编辑广告" }
      },
      {
        path: "advertiseSpace",
        name: "AdvertiseSpace",
        component: () => import("../views/AdvertiseManage/AdvertiseSpaces"),
        meta: { requireAuth: true, title: "广告位管理" }
      },
      {
        path: "addAdvertiseSpace",
        name: "AddAdvertiseSpace",
        component: () => import("../views/AdvertiseManage/AddAdvertiseSpace"),
        meta: { requireAuth: true, title: "添加广告位" }
      },
      {
        path: "updateAdvertiseSpace",
        name: "UpdateAdvertiseSpace",
        component: () =>
          import("../views/AdvertiseManage/UpdateAdvertiseSpace"),
        meta: { requireAuth: true, title: "编辑广告位" }
      }
    ]
  },
  {
    path: "*",
    name: "NotFound",
    meta: { requireAuth: false },
    component: () =>
      import(/* webpackChunkName: 'common' */ "../views/Error.vue")
  },
  {
    path: "/permission-denied",
    name: "PermissionDenied",
    meta: { requireAuth: true, title: "没权限" },
    component: () =>
      import(/* webpackChunkName: 'common' */ "../views/PermissionDenied.vue")
  }
];

export default routes;
