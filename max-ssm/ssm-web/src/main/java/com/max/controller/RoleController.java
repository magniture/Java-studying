package com.max.controller;

import com.max.domain.Menu;
import com.max.domain.ResponseResult;
import com.max.domain.Role;
import com.max.domain.RoleMenuVo;
import com.max.service.MenuService;
import com.max.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private MenuService menuService;

    @RequestMapping("/findAllRole")
    public ResponseResult findAllRole(@RequestBody Role role) {
        List<Role> roles = roleService.findAllAndCondition(role);
        ResponseResult responseResult = new ResponseResult(true, 200, "查询所有角色，或者根据条件进行模糊查询成功！", roles);
        return responseResult;
    }

    /**
     * 点击分配菜单跳转到该controller上
     * @return
     */
    @RequestMapping("/findAllMenu")
    public ResponseResult findParentMenuAndSubMenu() {
        // 第一次先根据 -1 查询父级菜单，然后再根据查询出来的父级菜单id值去查询它具有的子级菜单
        List<Menu> parentMenuAndSubMenu = menuService.findParentMenuAndSubMenu(-1);

        // 响应数据：看接口文档中的响应数据实例，否则前端页面中无法获取到数据
        HashMap<String, Object> map = new HashMap<>();
        map.put("parentMenuList",parentMenuAndSubMenu);

        ResponseResult responseResult = new ResponseResult(true, 200, "查询父子级菜单列表成功！", map);
        return responseResult;
    }

    /**
     * 这是点击分配菜单按钮发起的第二个请求：根据角色id查询当前角色所拥有的菜单信息 id
     * 进行数据的回显，也就是选冲复选框按钮
     * @param roleId
     * @return
     */
    @RequestMapping("/findMenuByRoleId")
    public ResponseResult findMenuIdByRoleId(Integer roleId) {

        List<Integer> menuId = roleService.findMenuIdByRoleId(roleId);

        ResponseResult responseResult = new ResponseResult(true, 200, "菜单信息回显成功!",menuId);
        return responseResult;
    }

    /**
     * 点击保存按钮：完成为当前角色分配菜单的功能
     * 注意：前台页面中复选框传递的是value属性的值
     * @return
     */
    @RequestMapping("/RoleContextMenu")
    public ResponseResult roleContextMenu(@RequestBody RoleMenuVo roleMenuVo) {

        roleService.roleContextMenu(roleMenuVo);

        ResponseResult responseResult = new ResponseResult(true, 200, "为当前角色分配菜单成功！", null);
        return responseResult;
    }

    /**
     * 根据id删除角色信息
     * @return
     */
    @RequestMapping("/deleteRole")
    public ResponseResult deleteRoleByRoleId(Integer id) {
        roleService.deleteRoleByRoleId(id);
        ResponseResult responseResult = new ResponseResult(true, 200, "删除角色信息成功！", null);
        return responseResult;
    }

    /**
     * 保存或修改角色信息
     * 传递id过来就是修改角色信息
     * @param role
     * @return
     */
    @RequestMapping("/saveOrUpdateRole")
    public ResponseResult saveOrUpdateRole(@RequestBody Role role) {
        if (role.getId() == null) {
            // 新增角色信息
            roleService.saveRole(role);

            ResponseResult responseResult = new ResponseResult(true, 200, "保存角色信息成功！", null);
            return responseResult;
        } else {
            // 修改角色信息
            roleService.updateRole(role);
            ResponseResult responseResult = new ResponseResult(true, 200, "修改角色信息成功！", null);
            return responseResult;
        }
    }
}
