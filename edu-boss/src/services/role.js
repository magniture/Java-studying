import { baseRequest, PostRequest } from './common'

export function fetchList (params) {
  return PostRequest('/boss/role/getRolePages', params)
}

export function saveOrUpdate (data) {
  const params = {}
  if (data.id) {
    params.id = data.id
  }
  params.code = data.code
  params.name = data.name
  params.description = data.description
  params.status = data.status
  return PostRequest('/boss/role/saveOrUpdate', params)
}

export function deleteRole (id) {
  return baseRequest('/boss/role/' + id, {}, 'delete')
}

export function fetchAllRoleList () {
  return baseRequest({
    url: '/role/listAll',
    method: 'get'
  })
}

//获取当前角色拥有的 菜单列表
export function listMenuByRole (roleId) {
  return baseRequest('/boss/menu/getRoleMenus?roleId=' + roleId).then(res => {
    //调用SelectTreeData方法
    return SelectTreeData(res.data)
  })
}

export function listResourceByRole (roleId) {
  return baseRequest('/boss/resource/getRoleResources?roleId=' + roleId)
}

//获取被选中的菜单列表id
function SelectTreeData (treeData) {
  let result = []
  if (treeData.length > 0) {
    
    treeData.forEach(item => {
      result = result.concat(SelectTreeData(item))
      return item
    })
    return result
  }
  if (treeData.selected) {
    result.push(treeData.id)
  }
  // text: item.name,
  //   icon: 's-marketing',
  //   name: item.href
  if (treeData.subMenuList) {
    let childrens = []
    childrens = childrens.concat(SelectTreeData(treeData.subMenuList))
    if (childrens.length > 0) {
      result.pop()
      result = result.concat(childrens)
    }
  }
  return result
}

export function allocMenu (data) {
  return PostRequest('/boss/menu/allocateRoleMenus', data)
}

export function allocResource (data) {
  return PostRequest('/boss/resource/allocateRoleResources', data)
}
