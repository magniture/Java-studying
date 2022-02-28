package com.max.service.impl;

import com.max.dao.RoleMapper;
import com.max.domain.Role;
import com.max.domain.RoleMenuVo;
import com.max.domain.Role_menu_relation;
import com.max.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    /**
     * 查询所有角色信息 并且也可以实现根据角色名称实现模糊查询
     * @param role
     * @return
     */
    @Override
    public List<Role> findAllAndCondition(Role role) {

        List<Role> roles = roleMapper.findAllAndCondition(role);
        return roles;
    }

    /**
     * 根据角色id查询当前角色所具有的菜单信息
     * 目的是在页面上进行菜单信息的回显
     * @param id
     * @return
     */
    @Override
    public List<Integer> findMenuIdByRoleId(Integer id) {
        List<Integer> menuId = roleMapper.findMenuIdByRoleId(id);

        return menuId;
    }

    /**
     * 完成为角色分配菜单的功能
     * @param roleMenuVo
     */
    @Override
    public void roleContextMenu(RoleMenuVo roleMenuVo) {
        // 1.先清空当前角色所关联的菜单信息
        roleMapper.deleteMenuListByRoleId(roleMenuVo.getRoleId());

        // 2.为当前角色保存新关联的菜单信息
        List<Integer> menuId = roleMenuVo.getMenuIdList();
        for (Integer mid : menuId) {
            // 创建角色菜单中间表的实体类
            Role_menu_relation role_menu_relation = new Role_menu_relation();
            role_menu_relation.setMenuId(mid);
            role_menu_relation.setRoleId(roleMenuVo.getRoleId());

            // 补全信息
            Date date = new Date();
            role_menu_relation.setCreatedTime(date);
            role_menu_relation.setUpdatedTime(date);
            role_menu_relation.setCreatedBy("system");
            role_menu_relation.setUpdatedby("system");
            // 插入操作
            roleMapper.insertRoleMenuRelation(role_menu_relation);
        }
    }

    /**
     * 根据id删除角色信息
     * 注意：删除某个角色的时候一定要先删除当前角色所关联的菜单信息，然后再删除这个角色
     * @param roleId
     */
    @Override
    public void deleteRoleByRoleId(Integer roleId) {
        // 1.先去删除当前角色关联的菜单信息
        roleMapper.deleteMenuListByRoleId(roleId);

        // 2.再去删除当前这个角色
        roleMapper.deleteRoleByRoleId(roleId);
    }

    /**
     * 保存角色信息：新增一个角色
     * @param role
     */
    @Override
    public void saveRole(Role role) {
        Date date = new Date();
        role.setCreatedTime(date);
        role.setUpdatedTime(date);
        role.setCreatedBy("徐国文");
        role.setUpdatedBy("徐国文");

        roleMapper.saveRole(role);
    }

    /**
     * 根据角色id修改角色信息
     * @param role
     */
    @Override
    public void updateRole(Role role) {
        role.setUpdatedTime(new Date());
        roleMapper.updateRole(role);
    }
}
