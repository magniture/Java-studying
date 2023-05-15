package com.cvn;

import com.cvn.mybatis.mapper.DepartmentMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;

public class Main {
    public static void main(String[] args) throws Exception {
        InputStream xml = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(xml);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        DepartmentMapper departmentMapper = sqlSession.getMapper(DepartmentMapper.class);
        System.out.println("第一次执行findAll......");
        departmentMapper.findAll();
        System.out.println("第二次执行findAll......");
        departmentMapper.findAll();
        System.out.println("清空一级缓存......");
        departmentMapper.cleanCache();
        System.out.println("清空缓存后再次执行findAll......");
        departmentMapper.findAll();

        sqlSession.close();
    }

}