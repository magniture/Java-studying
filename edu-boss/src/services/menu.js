import { baseRequest, PostRequest } from './common'

export function fetchList (parentId, params) {
  return baseRequest('/boss/menu/getAll', {
    url: '/menu/list/' + parentId,
    method: 'get',
    params: params
  })
}

export function deleteMenu (id) {
  return baseRequest('/boss/menu/' + id, {}, 'delete')
}

export function saveOrUpdate (data) {
  return PostRequest('/boss/menu/saveOrUpdate', data)
}

export function getMenu (id) {
  return baseRequest('/boss/menu/getEditMenuInfo?id=' + id)
}

export function updateHidden (id, params) {
  return baseRequest({
    url: '/menu/updateHidden/' + id,
    method: 'post',
    params: params
  })
}

export function fetchTreeList () {
  return baseRequest('/boss/menu/getMenuNodeList').then(res => {
    return res.data
  })
}
