/**
 * Tokens service
 */

import { Serialize } from '@/utils/index'

import { PostRequest } from './common'


//登录请求
export const userLogin = async (data) => {
  return await PostRequest(`${process.env.VUE_APP_API_FAKE}/user/login?${Serialize(data)}`)
}

// 更新token
export const fetchUpdateToken = async (refreshToken) => {
  await PostRequest(`${process.env.VUE_APP_API_FAKE}/user/refresh_token?refreshtoken=${refreshToken}`)
}
