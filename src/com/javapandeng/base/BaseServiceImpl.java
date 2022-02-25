package com.javapandeng.base;


import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.github.pagehelper.PageHelper;
import com.javapandeng.utils.Pager;
import com.javapandeng.utils.SystemContext;


public abstract  class BaseServiceImpl<T> implements BaseService<T>{

    private BaseDao<T> baseDao;  
    public abstract BaseDao<T> getBaseDao();  
    
    /**  
     * 插入一个实体  
     * @param entity  
     */  
	public int insert(T entity) {
		return this.getBaseDao().insert(entity);
	}
	
    /**  
     * 根据实体主键删除一个实体
     */  
	public void deleteById(Serializable id) {
		this.getBaseDao().deleteById(id);
	}
	
	
	/**
	 * 通过实体删除
	 */
	public void deleteByEntity(T entity) {
		this.getBaseDao().deleteByEntity(entity);		
	}

	/**
	 * 通过map删除
	 */
	public void deleteByMap(Map<String, Object> params) {
		this.getBaseDao().deleteByMap(params);
	}

	/**  
     * 更新一个实体  
     * @param entity  
     */
	public void update(T entity) {
		this.getBaseDao().update(entity);
	}
	
	 
    /**
     * 通过id进行修改
     */
	public void updateById(T entity) {
         this.getBaseDao().updateById(entity);		
	}
	
	 /**
     * 根据参数查询
     */
	public List<T> listByMap(Map<String, Object> params) {
		return this.getBaseDao().listByMap(params);
	}

	 /**  
     * 查询所有实体  
     * @return  
     */  	
	public List<T> listAll() {
		return this.getBaseDao().listAll();
	}
	
	 /**  
     * 查询所有实体,根据实体属性值为判断条件查询所有实体，  
     * @param entity  
     * @return  
     */
	public List<T> listAllByEntity(T entity) {
		return this.getBaseDao().listAllByEntity(entity);
	}
	
	 /**  
     * 根据主键获取一个实体
     */  
	public T load(Serializable id) {
		return this.getBaseDao().load(id);
	}
	
	/**
     * 根据主键获取一个实体
     * @param id
     * @return
     */
	public T getById(Serializable id) {
		return this.getBaseDao().getById(id);
	}

	 /**
     * 通过map查询
     * @param params
     * @return
     */
	public T getByMap(Map<String, Object> params) {
		return this.getBaseDao().getByMap(params);
	}

	/**
     * 通过对象查询
     * @param entity
     * @return
     */
	public T getByEntity(T entity) {
		return this.getBaseDao().getByEntity(entity);
	}
	
	 /**
     * 默认 sqlId find是分页
     */
	public Pager<T> findByMap(Map<String, Object> params) {
		/**
		 * 执行分页
		 */
    	Integer pageSize = SystemContext.getPageSize();
		Integer pageOffset = SystemContext.getPageOffset();
		if(pageOffset==null||pageOffset<0) pageOffset = 0;
		if(pageSize==null||pageSize<0) pageSize = 15;
		String order = SystemContext.getOrder();
		String sort = SystemContext.getSort();
		Integer pageNum = null;
		if(pageOffset == 0){
			pageNum = 1;
		}else{
			pageNum = pageOffset/pageSize+1;
		}
		PageHelper.startPage(pageNum, pageSize);
		Pager<T> pages = new Pager<T>(this.getBaseDao().findByMap(params));
    	return pages;
	}
	
	/**
     * 通过对象查询分页
     * @param entity
     * @return
     */
    public Pager<T> findByEntity(T entity){
    	/**
		 * 执行分页
		 */
    	Integer pageSize = SystemContext.getPageSize();
		Integer pageOffset = SystemContext.getPageOffset();
		if(pageOffset==null||pageOffset<0) pageOffset = 0;
		if(pageSize==null||pageSize<0) pageSize = 15;
		String order = SystemContext.getOrder();
		String sort = SystemContext.getSort();
		Integer pageNum = null;
		if(pageOffset == 0){
			pageNum = 1;
		}else{
			pageNum = pageOffset/pageSize+1;
		}
		PageHelper.startPage(pageNum, pageSize);
		Pager<T> pages = new Pager<T>(this.getBaseDao().findByEntity(entity));
    	return pages;
    }


	/**
	 * 批量新增
	 */
	 public void insertBatch(List<T> list) {
		 this.getBaseDao().insertBatch(list);
	}

	 /**
	  * 批量删除
	  */
	public void updateBatch(List<T> list) {
		 this.getBaseDao().updateBatch(list);
	}

	//=====================自定义sql=========================================
	
	 /**
     * 查询一个对象返回map
     * @param sql
     * @return
     */
	public Map<String, Object> getBySql(String sql) {
		
		return  this.getBaseDao().getBySqlReturnMap(sql);
	}

	/**
     * 查询一个对象返回实体类
     * @param sql
     * @return
     */
	public T getBySqlReturnEntity(String sql) {
		return this.getBaseDao().getBySqlReturnEntity(sql);
	}

	 /**
     * 查询列表返回map
     * @param sql
     * @return
     */
	public List<Map<String, Object>> listBySqlReturnMap(String sql) {
		return this.getBaseDao().listBySqlReturnMap(sql);
	}

	
	 /**
     * 查询列表返回实体
     * @param sql
     * @return
     */
	public List<T> listBySqlReturnEntity(String sql) {
		return this.getBaseDao().listBySqlReturnEntity(sql);
	}

	  /**
     * 查询分页
     * @param sql
     * @return
     */
	public Pager<T> findBySqlRerturnEntity(String sql) {
		/**
		 * 执行分页
		 */
    	Integer pageSize = SystemContext.getPageSize();
		Integer pageOffset = SystemContext.getPageOffset();
		if(pageOffset==null||pageOffset<0) pageOffset = 0;
		if(pageSize==null||pageSize<0) pageSize = 15;
		String order = SystemContext.getOrder();
		String sort = SystemContext.getSort();
		Integer pageNum = null;
		if(pageOffset == 0){
			pageNum = 1;
		}else{
			pageNum = pageOffset/pageSize+1;
		}
		PageHelper.startPage(pageNum, pageSize);
		Pager<T> pages = new Pager<T>(this.getBaseDao().findBySqlRerturnEntity(sql));
    	return pages;
	}

	 
    /**
     * 通过sql修改
     * @param sql
     */
	public void updateBysql(String sql) {
		this.getBaseDao().updateBysql(sql);
	}

	 /**
     * 通过sql删除
     * @param sql
     */
	public void deleteBySql(String sql) {
		this.getBaseDao().deleteBySql(sql);
	}

	//判断空
	public boolean isEmpty(String str) {
		return (null == str) || (str.trim().length() <= 0);
	}

	public boolean isEmpty(Character cha) {
		return (null == cha) || cha.equals(' ');
	}

	public boolean isEmpty(Object obj) {
		return (null == obj);
	}

	public boolean isEmpty(Object[] objs) {
		return (null == objs) || (objs.length <= 0);
	}

	public boolean isEmpty(Collection<?> obj) {
		return (null == obj) || obj.isEmpty();
	}

	public boolean isEmpty(Set<?> set) {
		return (null == set) || set.isEmpty();
	}

	public boolean isEmpty(Serializable obj) {
		return null == obj;
	}

	public boolean isEmpty(Map<?, ?> map) {
		return (null == map) || map.isEmpty();
	}
}
