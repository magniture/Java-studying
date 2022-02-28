/**
 * Custom axios instance
 */

import axios from "axios";

//
// /**
//  * Custom axios instance
//  */
//
// import axios from 'axios'
import { storage } from "@/utils/index";
// //
axios.interceptors.response.use(
  response => {
    // 如果返回的状态码为200，说明接口请求成功，可以正常拿到数据
    // 否则的话抛出错误
    if (response.status === 200) {
      return Promise.resolve(response);
    } else {
      return Promise.reject(response);
    }
  },
  // 服务器状态码不是2开头的的情况
  // 这里可以跟你们的后台开发人员协商好统一的错误状态码
  // 然后根据返回的状态码进行一些操作，例如登录过期提示，错误提示等等
  // 下面列举几个常见的操作，其他需求可自行扩展
  error => {
    if (error.response.status) {
      switch (error.response.status) {
        // 401: 未登录
        // 未登录则跳转登录页面，并携带当前页面的路径
        // 在登录成功后返回当前页面，这一步需要在登录页操作。
        case 401: // Unauthorized
          storage.set("session", { accessToken: null, refreshToken: null });
          window.location.reload();
          break;

        // 403 token过期
        // 登录过期对用户进行提示
        // 清除本地token和清空vuex中token对象
        // 跳转登录页面
        case 403: // 403
          // vant.Toast.fail("登录过期，请关闭重新进入。");
          // 清除token
          break;

        // 404请求不存在
        case 404:
          // vant.Toast.fail("您访问的网页不存在。");
          break;
        // 其他错误，直接抛出错误提示
        default:
        // vant.Toast.fail(error.response.data.message);
      }
      return Promise.reject(error.response);
    }
  }
);
axios.defaults.withCredentials = true; // carry cookie（携带 cookie）
export default axios.create({
  baseURL: process.env.VUE_APP_API_BASE,
  timeout: 5 * 1000, // 5s
  headers: {
    // 'X-Custom-Header': 'foobar',
    // 'Authorization': true,
    "X-Requested-With": "XMLHttpRequest"
  }
});
//
//export default axios
// export default axios.create({
//   timeout: 5 * 1000, // 5s
//   headers: {
//     // 'X-Custom-Header': 'foobar',
//     // 'Authorization': true,
//     'X-Requested-With': 'XMLHttpRequest'
//   }
// })
