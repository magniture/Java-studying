package com.zq.spring.data.jpa.dao;

import com.zq.spring.data.jpa.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentDao extends JpaRepository<Department, Integer> {

}
