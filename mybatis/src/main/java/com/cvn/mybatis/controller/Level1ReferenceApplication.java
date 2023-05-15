package com.cvn.mybatis.controller;

import com.cvn.mybatis.mapper.DepartmentMapper;
import com.cvn.mybatis.pojo.Department;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;

/**
 * &#064; com.cvn.mybatis.controller
 *
 * @author: zq
 * &#064; 2023-05-15  14:18
 * &#064; TODO
 */
public class Level1ReferenceApplication {

    public static void main(String[] args) throws Exception {
        InputStream xml = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(xml);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        DepartmentMapper departmentMapper = sqlSession.getMapper(DepartmentMapper.class);
        Department department = departmentMapper.findById("18ec781fbefd727923b0d35740b177ab");
        System.out.println("department: " + department);
        department.setName("哈哈哈哈");
        System.out.println("department: " + department);

        Department department2 = departmentMapper.findById("18ec781fbefd727923b0d35740b177ab");
        System.out.println("department2: " + department2);
        System.out.println(department == department2);
    }
}
