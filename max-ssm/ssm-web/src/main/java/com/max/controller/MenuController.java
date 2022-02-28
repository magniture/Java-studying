package com.max.controller;

import com.max.domain.Menu;
import com.max.domain.ResponseResult;
import com.max.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    private MenuService menuService;

    /**
     * 点击菜单列表，查询所有菜单信息
     * @return
     */
    @RequestMapping("/findAllMenu")
    public ResponseResult findAllMenu() {
        List<Menu> allMenu = menuService.findAllMenu();

        ResponseResult responseResult = new ResponseResult(true, 200, "查询所有菜单列表成功！", allMenu);
        return responseResult;
    }

    /**
     * 存在两个功能
     * 1.点击添加菜单按钮后需要在页面上回显当前菜单的上级菜单名称
     * 2.点击某一个菜单的编辑按钮需要在跳转到的页面上进行菜单信息的回显，也需要回显当前菜单的上级菜单
     * 注意:这两个功能中回显当前菜单上级菜单的名称已经编写过了，可以实现方法的复用
     * @param id
     * @return
     */
    @RequestMapping("/findMenuInfoById")
    public ResponseResult findMenuInfoById(Integer id) {
        // 添加菜单：需要回显上级菜单的名称
        if (id == -1) {
            List<Menu> parentMenuAndSubMenu = menuService.findParentMenuAndSubMenu(-1);
            // 根据接口文档进行编写响应数据
            Map<String, Object> map = new HashMap<>();
            map.put("parentMenuList",parentMenuAndSubMenu);

            ResponseResult responseResult = new ResponseResult(true, 200, "添加菜单上级菜单名称回显成功！", map);
            return responseResult;
        } else {
            // 编辑菜单
            // 先根据当前菜单id回显菜单信息
            Menu menu = menuService.findMenuById(id);
            List<Menu> parentMenuAndSubMenu = menuService.findParentMenuAndSubMenu(-1);
            Map<String, Object> map = new HashMap<>();
            map.put("menuInfo",menu);
            map.put("parentMenuList",parentMenuAndSubMenu);

            ResponseResult responseResult = new ResponseResult(true, 200, "修改菜单信息之回显数据成功！", map);
            return responseResult;
        }
    }

    /**
     * 新增菜单信息或者是根据id修改菜单信息
     * @return
     */
    @RequestMapping("/saveOrUpdateMenu")
    public ResponseResult saveOrUpdateMenu(@RequestBody Menu menu) {
        if (menu.getId() == null) {
            // 新增操作
            menuService.saveMenu(menu);
            ResponseResult responseResult = new ResponseResult(true, 200, "新增菜单信息成功!", null);
            return responseResult;
        } else {
            // 修改操作
            menuService.updateMenu(menu);
            ResponseResult responseResult = new ResponseResult(true, 200, "修改菜单信息成功！", null);
            return responseResult;
        }
    }
}
