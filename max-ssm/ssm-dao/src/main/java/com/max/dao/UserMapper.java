package com.max.dao;


import com.max.domain.*;

import java.util.List;

public interface UserMapper {
    /**
     * 多条件查询用户信息并且进行分页设置
     * @param userVo
     * @return
     */
    List<User> findAllUserByPage(UserVo userVo);

    /**
     * 根据用户id修改用户的状态信息
     * @param user
     */
    void updateUserStatus(User user);

    /**
     * 完成用户登录
     * 根据用户名phone查询用户信息
     * @param user
     * @return
     */
    User findUserByPhone(User user);

    /**
     * 根据当前用户的id删除当前用户关联的所有角色信息
     * @param userId
     */
    void deleteUserRoleRelationById(Integer userId);

    /**
     * 根据用户id分配用户角色
     * 我知道用户和角色的关系是多对多的关系：存在一张中间表维护
     * 这个操作的本质就是：向中间表插入数据
     * 但是需要注意的是：在进行这个操作的前提是：先将当前用户关联的角色信息都删除之后，再去建立关联关系
     * 记住一点：以后但凡是向中间表插入关联数据时，都先去将之间的关联关系删除之后再去操作
     * @param user_role_relation
     */
    void userContextRole(User_Role_relation user_role_relation);

    /**
     * 根据用户id查询当前用户具有的角色信息
     * 用户与角色是多对多的关系，多表查询
     * @param id
     * @return
     */
    List<Role> findUserRoleListById(Integer id);

    /**
     * 根据角色id查询该角色所具有的父级菜单信息（多对多的关系）
     * 由于用户可能具有多个角色，那么意味着这个用户可能具有多个父级菜单
     * 用户具有多个角色，所以需要根据多个角色id查询父级菜单
     * 注意：有可能这多个菜单中有同一个菜单信息，要进行去重
     * @param ids
     * @return
     */
    List<Menu> findParentMenuByRoleId(List<Integer> ids);

    /**
     * 根据父级菜单的id之查询当前父级菜单下所具有的子级菜单信息（一对多的关系）
     *
     * @param id
     * @return
     */
    List<Menu> findSubMenuByParentId(Integer id);

    /**
     * 根据角色id查询当前角色下关联的资源信息
     * 注意：当前用户可能存在角色，也就意味着角色id也存在多个，所以用数据接收参数
     * @param ids
     * @return
     */
    List<Resource> findResourceByRoleId(List<Integer> ids);
}
