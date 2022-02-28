import { baseRequest, PostRequest } from './common'

/**
 * 获取广告列表
 * @param params
 * @returns {Promise<*>}
 */
export function fetchADList (params) {
  return baseRequest('/front/ad/getAdList', {
    method: 'get',
    params: params
  })
}

/**
 * 广告位列表
 * @param params
 * @returns {Promise<*>}
 */
export function fetchADSpaceList (params) {
  return baseRequest('/front/ad/space/getAllSpaces', {
    method: 'get',
    params: params
  })
}

/**
 * 广告状态变更
 * @param id
 * @param params
 * @returns {Promise<*>}
 */
export function updateADStatus (data) {
  return baseRequest(`/front/ad/updateStatus?id=${data.id}&status=${data.status}`)
}

/**
 * 删除广告
 * @param data
 * @returns {Promise<*>}
 */
export function deleteAdvertise (data) {
  return baseRequest({
    url: '/home/advertise/delete',
    method: 'post',
    data: data
  })
}

/**
 * 新增更新广告信息
 * @param id
 * @param data
 * @returns {Promise<*>}
 */
export function saveOrUpdateAdvertise (data) {
  return PostRequest('/front/ad/saveOrUpdate', data)
}

/**
 * 获取广告信息
 * @param data
 * @returns {Promise<*>}
 */
export function getAdvertise (id) {
  return baseRequest('/front/ad/getAdById?id=' + id, {
    method: 'get'
  })
}

/**
 * 新增更新广告位信息
 * @param id
 * @param data
 * @returns {Promise<*>}
 */
export function saveOrUpdateAdvertiseSpace (data) {
  return PostRequest('/front/ad/space/saveOrUpdate', data)
}

/**
 * 获取广告位信息
 * @param data
 * @returns {Promise<*>}
 */
export function getAdvertiseSpace (id) {
  return baseRequest('/front/ad/space/getSpaceById?id=' + id, {
    method: 'get'
  })
}
