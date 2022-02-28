import { baseRequest, PostRequest } from './common'

export function fetchList (params) {
  return PostRequest('/boss/resource/getResourcePages', params)
}

export function saveOrUpdate (data) {
  return PostRequest('/boss/resource/saveOrUpdate', data)
}

export function deleteResource (id) {
  return baseRequest('/boss/resource/' + id, {}, 'delete')
}

export function fetchAllResourceList () {
  return baseRequest('/boss/resource/getAll')
}
