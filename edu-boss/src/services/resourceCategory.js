import { baseRequest, PostRequest } from './common'

export function listAllCate () {
  return baseRequest('/boss/resource/category/getAll', {
    url: '/boss/resource/category/getAll',
    method: 'get'
  })
}

export function createResourceCategory (data) {
  return baseRequest({
    url: '/resourceCategory/create',
    method: 'post',
    data: data
  })
}

export function saveOrderUpdate (data) {
  return PostRequest('/boss/resource/category/saveOrderUpdate', data)
}

export function deleteResourceCategory (id) {
  return baseRequest('/boss/resource/category/' + id, {}, 'delete')
}
