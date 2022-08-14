package com.imooc.bilibili.dao;


import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DemoDao {

    public Long query(Long id);
}
