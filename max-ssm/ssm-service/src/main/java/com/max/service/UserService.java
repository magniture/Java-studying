package com.max.service;

import com.github.pagehelper.PageInfo;
import com.max.domain.ResponseResult;
import com.max.domain.Role;
import com.max.domain.User;
import com.max.domain.UserVo;

import java.util.List;

public interface UserService {
    /**
     * 多条件查询用户并且进行分页设置
     * @param userVo
     * @return
     */
    PageInfo findAllUserByPage(UserVo userVo);

    /**
     * 根据用户id修改用户信息
     * @param user
     */
    void updateUserStatus(User user);

    /**
     * 用户登录
     * @param user
     * @return
     */
    User findUserByPhone(User user) throws Exception;

    /**
     * 根据用户id查询用户所具有的角色信息，进行角色信息的回显
     * @return
     */
    List<Role> findUserRoleListById(Integer id);

    /**
     * 为用户分配角色
     * @param userVo
     */
    void userContextRole(UserVo userVo);

    /**
     * 获取当前登录用户的权限信息
     * （先根据用户id获取当前用户关联的角色信息，然后根据获取到的角色信息获取当前角色下的
     *  父级菜单信息，然后根据父级菜单的信息获取当前父级菜单下的子级菜单，
     *  最后，根据角色信息获取角色下的资源信息）
     *  数据的封装在这个完成
     * @param userId 登录成功之后将用户的id存储到了session中
     * @return
     */
    ResponseResult getUserPermission(Integer userId);
}
