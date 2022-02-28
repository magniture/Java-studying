/**
 * Users service
 */

//
import { baseRequest, postRequest, bossBase } from './common'

export const getUserInfo = async (courseId) => {
  return await baseRequest(`${process.env.VUE_APP_API_FAKE}/user/getInfo`)
}

export const getUserList = async (params) => {
  return await postRequest(`${bossBase}/user/getUserPages`, params)
}

export const getUserPermissions = async () => {
  return await baseRequest(`${bossBase}/permission/getUserPermissions`)
}

export const forbidUser = async (userId) => {
  return await postRequest(`${bossBase}/user/forbidUser`, { userId })
}
