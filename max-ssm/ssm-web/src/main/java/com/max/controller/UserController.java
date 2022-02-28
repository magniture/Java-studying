package com.max.controller;

import com.github.pagehelper.PageInfo;
import com.max.domain.ResponseResult;
import com.max.domain.Role;
import com.max.domain.User;
import com.max.domain.UserVo;
import com.max.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/findAllUserByPage")
    public ResponseResult findAllUserByPage(@RequestBody UserVo userVo) {
        PageInfo pageInfo = userService.findAllUserByPage(userVo);

        ResponseResult responseResult = new ResponseResult(true,200,"多条件分页查询用户成功！",pageInfo);
        return responseResult;

    }

    /**
     * 修改用户状态
     * @param user
     * @return
     */
    @RequestMapping("/updateUserStatus")
    public ResponseResult updateUserStatus(User user) {
        userService.updateUserStatus(user);
        Map<String,String> map = new HashMap<>();
        map.put("status",user.getStatus());
        ResponseResult responseResult = new ResponseResult(true, 200, "修改用户状态成功!", map);
        return responseResult;
    }

    /**
     * 用户登录
     * @return
     */
    @RequestMapping("/login")
    public ResponseResult login(User user, HttpServletRequest request) throws Exception {
        // 1.调用service
        User loginUser = userService.findUserByPhone(user);
        // 2.判断该用户是否为空
        if (loginUser != null) {
            // 3.将用户id存储到session中
            HttpSession session = request.getSession();
            session.setAttribute("user_id",loginUser.getId());
            // 4.使用uuid生成一个令牌，将令牌值返回给用户。以后每次用户登录时，都需要携带令牌值。否则不让其访问其他页面，强制跳回登录页面
            String access_token = UUID.randomUUID().toString();
            session.setAttribute("access_token",access_token);

            // 需要返回给前端页面的是键值对数据
            Map<String, Object> map = new HashMap<>();
            map.put("user_id",loginUser.getId());
            map.put("access_token",access_token);
            map.put("user",loginUser);  // 用户登出时需要用到这个user对象

            // 5.根据接口文档中的响应实例按要求返回数据
            return new ResponseResult(true,1,"登录成功！",map);
        } else {
            return new ResponseResult(true,400,"用户名或密码错误！",null);
        }
    }

    /**
     * 获取用户拥有的菜单权限
     * @param id
     * @return
     */
    @RequestMapping("/findUserRoleById")
    public ResponseResult findUserRoleById(Integer id) {
        List<Role> roleList = userService.findUserRoleListById(id);

        ResponseResult responseResult = new ResponseResult(true, 200, "用户角色信息回显成功！", roleList);
        return responseResult;
    }

    /**
     * 给当前用户分配角色功能
     * @param userVo
     * @return
     */
    @RequestMapping("/userContextRole")
    public ResponseResult userContextRole(@RequestBody UserVo userVo) {

        userService.userContextRole(userVo);
        return new ResponseResult(true,200,"分配角色成功！",null);
    }

    /**
     * 获取用户权限信息
     * @return
     */
    @RequestMapping("/getUserPermissions")
    public ResponseResult getUserPermission(HttpServletRequest request) {
        // 1.获取用户请求时在请求头携带的令牌值
        String header_token = request.getHeader("Authorization");
        // 2.获取session中存储的令牌值
        HttpSession session = request.getSession();
        String  session_token = (String) session.getAttribute("access_token");

        // 3.比较：判断是否一致
        if (header_token.equals(session_token)) {
            // 3.1 获取session中存储的用户id
            Integer user_id = (Integer) session.getAttribute("user_id");
            // 3.2 调用service查询
            ResponseResult responseResult = userService.getUserPermission(user_id);
            return responseResult;
        } else {
            // 3.2 响应错误信息
            return new ResponseResult(false,400,"获取用户权限信息失败！",null);
        }
    }
}
