package com.imooc.bilibili.dao;

import com.imooc.bilibili.domain.auth.UserRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserRoleDao {

    List<UserRole> getUserRoleByUserId(Long userId);

    Integer addUserRole(UserRole userRole);
}
