import { baseRequest, PostRequest } from './common'

export function login (username, password) {
  return baseRequest({
    url: '/admin/login',
    method: 'post',
    data: {
      username,
      password
    }
  })
}

export function getInfo () {
  return baseRequest({
    url: '/admin/info',
    method: 'get'
  })
}

export function logout () {
  return baseRequest({
    url: '/admin/logout',
    method: 'post'
  })
}

export function fetchList (params) {
  return baseRequest({
    url: '/admin/list',
    method: 'get',
    params: params
  })
}

export function createAdmin (data) {
  return baseRequest({
    url: '/admin/register',
    method: 'post',
    data: data
  })
}

export function updateAdmin (id, data) {
  return baseRequest({
    url: '/admin/update/' + id,
    method: 'post',
    data: data
  })
}

export function updateStatus (id, params) {
  return baseRequest({
    url: '/admin/updateStatus/' + id,
    method: 'post',
    params: params
  })
}

export function deleteAdmin (id) {
  return baseRequest({
    url: '/admin/delete/' + id,
    method: 'post'
  })
}

export function getRoleByAdmin (id) {
  return baseRequest('/boss/role/all', {
    url: '/boss/role/all',
    method: 'get'
  })
}

export function getRolesById (id) {
  return baseRequest('/boss/role/user/' + id)
}

export function allocRole (data) {
  return PostRequest('/boss/role/allocateUserRoles', data)
}
