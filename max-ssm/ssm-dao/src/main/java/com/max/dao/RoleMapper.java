package com.max.dao;


import com.max.domain.Role;
import com.max.domain.Role_menu_relation;

import java.util.List;

/**
 * 角色模块 dao层
 */
public interface RoleMapper {

    /**
     * 查询所有并且也可以实现根据角色名称进行模糊查询
     * @param role
     * @return
     */
    List<Role> findAllAndCondition(Role role);

    /**
     * 根据角色id查询当前角色所关联的菜单id，进行页面上复选框数据的回显
     * @param id
     * @return
     */
    List<Integer> findMenuIdByRoleId(Integer id);

    /**
     * 根据角色id删除当前角色所关联的菜单信息
     * 这个操作在为角色分配菜单时的前提操作：在点击保存按钮之后，
     * 先将当前角色之前关联的菜单信息删除之后
     * 再去添加新的关联信息
     * @param rid
     */
    void deleteMenuListByRoleId(Integer rid);

    /**
     * 插入角色新关联的菜单信息
     * 在完成这部操作的时候，存在一个前提操作就是
     * 先去删除当前角色所关联的菜单信息
     * 这两个操作都是在角色菜单中间表完成的
     * @param role_menu_relation
     */
    void insertRoleMenuRelation(Role_menu_relation role_menu_relation);

    /**
     * 根据id删除角色信息
     * @param roleId
     */
    void deleteRoleByRoleId(Integer roleId);

    /**
     * 保存角色信息：向角色表中新插入一条数据
     * @param role
     */
    void saveRole(Role role);

    /**
     * 根据角色id修改角色信息
     * @param role
     */
    void updateRole(Role role);

}
