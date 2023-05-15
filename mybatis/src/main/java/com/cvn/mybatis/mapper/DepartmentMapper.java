package com.cvn.mybatis.mapper;

import com.cvn.mybatis.pojo.Department;
import org.apache.ibatis.annotations.CacheNamespace;

import java.util.List;

@CacheNamespace
public interface DepartmentMapper {

    List<Department> findAll();

    int update(Department department);

    Department findById(String id);

    int cleanCache();
}