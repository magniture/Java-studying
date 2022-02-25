package com.javapandeng.base;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;


/**
 * 基础dao封装一些简单的方法
 * @author 
 *
 */
public interface BaseDao<T>{
	/**  
     * 插入一个实体  
     * @param entity  
     */  
	int insert(T entity) ; 
      
    /**  
     * 根据实体主键删除一个实体
     */  
    void deleteById(Serializable id);  
    
    /**
     * 通过实体删除
     * @param entity
     */
    void deleteByEntity(T entity);
    
    /**
     * 通过map删除
     * @param params
     */
    void deleteByMap(Map<String,Object> params);
    
    /**  
     * 更新一个实体  
     * @param entity  
     */  
    void update(T entity);   
    
    /**
     * 通过id进行修改
     */
    void updateById(T entity);
    
    
    /**
     * 根据参数查询
     */
    public List<T> listByMap(Map<String,Object> params);
    
    /**  
     * 查询所有实体  
     * @return  
     */  
    List<T> listAll();  
  
    /**  
     * 查询所有实体,根据实体属性值为判断条件查询所有实体，  
     * @param entity  
     * @return  
     */  
    List<T> listAllByEntity(T entity);

    /**
     * 根据主键获取一个实体
     * @param id
     * @return
     */
    T load(Serializable id);

    /**
     * 根据主键获取一个实体
     * @param id
     * @return
     */
    T getById(Serializable id);

    /**
     * 根据map查询--不分页
     * @param params
     * @return
     */
    T getByMap(Map<String,Object> params);

    /**
     * 通过对象查询--不分页
     */
    public T getByEntity(T entity);

    /**
     * 通过map查询分页
     */
    public List<T> findByMap(Map<String,Object> params);

    /**
     * 通过对象查询分页
     */
    public List<T> findByEntity(T entity);

    /**
     * 批量新增
     * @param list
     */
    public void insertBatch(List<T> list);
    
    /**
     * 批量修改
     * @param list
     */
    public void updateBatch(List<T> list);
    
    //==============================封装纯sql语法================================
    
    /**
     * 查询一个对象返回map
     * @param sql
     * @return
     */
    public Map<String,Object> getBySqlReturnMap(@Param("sql")String sql);
    
    /**
     * 查询一个对象返回实体类
     * @param sql
     * @return
     */
    public T getBySqlReturnEntity(@Param("sql")String sql);
    
    /**
     * 查询列表返回map
     * @param sql
     * @return
     */
    public List<Map<String,Object>> listBySqlReturnMap(@Param("sql")String sql);
    
    /**
     * 查询列表返回实体
     * @param sql
     * @return
     */
    public List<T> listBySqlReturnEntity(@Param("sql")String sql);
    
    /**
     * 查询分页
     * @param sql
     * @return
     */
    public List<T> findBySqlRerturnEntity(@Param("sql")String sql);
    
    /**
     * 通过sql修改
     * @param sql
     */
    public void updateBysql(@Param("sql")String sql);
    
    /**
     * 通过sql删除
     * @param sql
     */
    public void deleteBySql(@Param("sql")String sql);
}
