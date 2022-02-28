package com.max.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.max.dao.UserMapper;
import com.max.domain.*;
import com.max.service.UserService;
import com.max.utils.Md5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public PageInfo findAllUserByPage(UserVo userVo) {
        PageHelper.startPage(userVo.getCurrentPage(),userVo.getPageSize());
        List<User> allUserByPage = userMapper.findAllUserByPage(userVo);
        PageInfo<User> pageInfo = new PageInfo<>(allUserByPage);

        return pageInfo;
    }

    /**
     * 根据用户id修改用户信息
     * @param user
     */
    @Override
    public void updateUserStatus(User user) {
        // 补全信息
        user.setUpdate_time(new Date());

        userMapper.updateUserStatus(user);
    }

    /**
     * 用户登录
     * @param user
     * @return
     */
    @Override
    public User findUserByPhone(User user) throws Exception {
        // 1.调用mapper查询
        User loginUser = userMapper.findUserByPhone(user);
        // 2.判断loginUser是否为空
        if (loginUser != null && Md5.verify(user.getPassword(),Md5.md5key,loginUser.getPassword())) {
            // 如果查询出来的用户不为null，
            // 然后进行密码的校验：如果通过明文加密之后的密码和查询出来的用户密码（密文）相同，说明密码一致，可以登录
            return loginUser;

        } else {
            return null;
        }
    }

    /**
     * 根据用户id查询用户所具有的角色信息，进行角色信息的回显
     * @return
     */
    @Override
    public List<Role> findUserRoleListById(Integer id) {
        // 调用mapper
        List<Role> roleList = userMapper.findUserRoleListById(id);

        return roleList;
    }

    /**
     * 为用户分配角色
     * @param userVo
     */
    @Override
    public void userContextRole(UserVo userVo) {
        // 1.先根据用户id删除用户关联的角色信息
        userMapper.deleteUserRoleRelationById(userVo.getUserId());

        // 2.然后获取角色信息id
        List<Integer> roleIdList = userVo.getRoleIdList();
        for (Integer roleId : roleIdList) {
            User_Role_relation user_role_relation = new User_Role_relation();
            user_role_relation.setUserId(userVo.getUserId());
            user_role_relation.setRoleId(roleId);

            // 补全信息
            Date date = new Date();
            user_role_relation.setCreatedTime(date);
            user_role_relation.setUpdatedTime(date);

            user_role_relation.setCreatedBy("system");
            user_role_relation.setUpdatedby("system");

            userMapper.userContextRole(user_role_relation);
        }

    }

    /**
     * 获取当前登录用户的权限信息
     * （先根据用户id获取当前用户关联的角色信息，然后根据获取到的角色信息获取当前角色下的
     *  父级菜单信息，然后根据父级菜单的信息获取当前父级菜单下的子级菜单，
     *  最后，根据角色信息获取角色下的资源信息）
     *  数据的封装在这个完成
     * @param userId 登录成功之后将用户的id存储到了session中
     * @return
     */
    @Override
    public ResponseResult getUserPermission(Integer userId) {
        // 1.首先根据用户id查询角色信息
        List<Role> roleList = userMapper.findUserRoleListById(userId);
        // 2.上一步查询了roles表的所有信息，将所有信息封装到了role实体类中
        // 由于下一步只需要根据角色id查询当前角色关联的父级菜单信息，
        // 所以，创建一个集合保存角色id信息就好
        List<Integer> roleIds = new ArrayList<>();
        for (Role role : roleList) {
            roleIds.add(role.getId());
        }
        // 查询父级菜单信息
        List<Menu> parentMenus = userMapper.findParentMenuByRoleId(roleIds);
        // 3.根据父级菜单信息查询子级菜单信息
        for (Menu parentMenu : parentMenus) {
            List<Menu> subMenuS = userMapper.findSubMenuByParentId(parentMenu.getId());
            // 切记，菜单表是自关联的关系，将子级菜单封装到父级菜单中
            parentMenu.setSubMenuList(subMenuS);
        }

        // 4.根据角色id查询当前角色下的资源信息
        List<Resource> resources = userMapper.findResourceByRoleId(roleIds);

        // 5.封装数据，根据接口文档写
        Map<String, Object> map = new HashMap<>();
        map.put("menuList",parentMenus);
        map.put("resourceList",resources);

        return new ResponseResult(true,200,"获取用户权限信息成功！",map);

    }
}
