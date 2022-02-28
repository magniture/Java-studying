import { axios } from '../utils'

export const bossBase = process.env.VUE_APP_API_BASE
export const frontBase = process.env.VUE_APP_API_BASE

export const request = async (endpoint, options, method = 'get') => {
  const res = await axios[method](endpoint, options)
  if (res.data.code !== '000000') {
    throw Error(res.data.mesg)
  }
  return res.data.data
}

export const postRequest = async (endpoint, data) => {
  return await request(endpoint, data, 'post')
}

export const PostRequest = async (endpoint, data) => {
  return await baseRequest(endpoint, data, 'post')
}

export const baseRequest = async (endpoint, options, method = 'get') => {
  const res = await axios[method](endpoint, options)
  return res.data
}
