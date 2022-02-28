package com.max.dao;



import com.max.domain.Menu;

import java.util.List;

/**

 * 实现分配菜单列表功能下的 dao层
 */
public interface MenuMapper {
    /**
     * 查询父子级菜单列表（父级菜单和子级菜单是 1 对 m 的关系）
     * 1.先去查询父级菜单：在menu表中parent_id字段等于 -1 的话，说明就是父级菜单
     * SELECT * FROM menu WHERE parent_id = -1
     * 2.根据查询出来的父级菜单的 id值去查询当前父级菜单下的子级菜单（嵌套查询）
     * SELECT * FROM menu WHERE parent_id = #{id}
     * @param pid 在controller中会传递 -1 到id身上
     * @return
     */
    List<Menu> findParentMenuAndSubMenu(Integer pid);

    /**
     * 查询所有菜单列表
     * @return
     */
    List<Menu> findAllMenu();

    /**
     * 根据菜单id查询菜单信息进行菜单信息的回显
     * @param id
     * @return
     */
    Menu findMenuById(Integer id);

    /**
     * 保存菜单信息
     * @param menu
     */
    void saveMenu(Menu menu);

    /**
     * 根据id修改菜单信息
     * @param menu
     */
    void updateMenu(Menu menu);
}

