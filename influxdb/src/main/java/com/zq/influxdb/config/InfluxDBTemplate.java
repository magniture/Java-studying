package com.zq.influxdb.config;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ObjectUtils;

import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Configuration
public class InfluxDBTemplate {


    //1.influxDBTemplate ---> influxdb---> 数据库 获取 influxdb 连接


    private final InfluxdbProperties influxdbProperties;

    private InfluxDB influxDB;

    @Autowired
    public InfluxDBTemplate(InfluxdbProperties influxdbProperties) {
        this.influxdbProperties = influxdbProperties;
        getInfluxDB();
    }

    /**
     * 获取 influxdb 连接
     */
    public void getInfluxDB() {
        if (influxDB == null) {
            influxDB = InfluxDBFactory.connect(influxdbProperties.getUrl(),
                    influxdbProperties.getUsername(),
                    influxdbProperties.getPassword());
            //设置使用数据库  保证库存在
            influxDB.setDatabase(influxdbProperties.getDatabase());
            //设置数据库保留策略 保证策略存在
            if (ObjectUtils.isEmpty(influxdbProperties.getRetention())) {
                influxDB.setRetentionPolicy(influxdbProperties.getRetention());
            }
        }
    }

    /**
     * 关闭连接
     */
    public void close() {
        if (influxDB != null) {
            influxDB.close();
        }
    }


    /**
     * 指定时间插入
     *
     * @param measurement 表
     * @param tags        标签
     * @param fields      字段
     * @param time        时间
     * @param unit        单位
     */
    public void write(String measurement, Map<String, String> tags, Map<String, Object> fields, long time, TimeUnit unit) {
        Point point = Point.measurement(measurement).tag(tags).fields(fields).time(time, unit).build();
        influxDB.write(point);
        close();
    }

    /**
     * 插入数据-自动生成时间
     *
     * @param measurement 表
     * @param tags        标签
     * @param fields      字段
     */
    public void write(String measurement, Map<String, String> tags, Map<String, Object> fields) {
        write(measurement, tags, fields, System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    /**
     * 批量插入
     *
     * @param points 批量记录  推荐 1000 条作为一个批
     */
    public void writeBatch(BatchPoints points) {
        influxDB.write(points);
        close();
    }


    /**
     * 用来执行相关操作
     *
     * @param command 执行命令
     * @return 返回结果
     */
    public QueryResult query(String command) {
        return influxDB.query(new Query(command));
    }


    /**
     * 创建数据库
     *
     * @param name 库名
     */
    public void createDataBase(String name) {
        query("create database " + name);
    }

    /**
     * 删除数据库
     *
     * @param name 库名
     */
    public void dropDataBase(String name) {
        query("drop database " + name);
    }

    /**
     * select 查询封装
     *
     * @param queryResult 查询返回结果
     * @param clazz       封装对象类型
     * @param <T>         泛型
     * @return 返回处理回收结果
     */
    public <T> List<T> handleQueryResult(QueryResult queryResult, Class<T> clazz) {
        //0.定义保存结果集合
        List<T> lists = new ArrayList<>();
        //1.获取结果
        List<QueryResult.Result> results = queryResult.getResults();
        //2.遍历结果
        results.forEach(result -> {
            //3.获取 series
            List<QueryResult.Series> seriesList = result.getSeries();
            //4.遍历 series
            seriesList.forEach(series -> {
                //5.获取的所有列
                List<String> columns = series.getColumns();
                //6.获取所有值
                List<List<Object>> values = series.getValues();
                //7.遍历数据 获取结果
                for (int i = 0; i < values.size(); i++) {
                    try {
                        //8.根据 clazz 进行封装
                        T instance = clazz.newInstance();
                        //9.通过 spring 框架提供反射类进行处理
                        BeanWrapperImpl beanWrapper = new BeanWrapperImpl(instance);
                        HashMap<String, Object> fields = new HashMap<>();
                        for (int j = 0; j < columns.size(); j++) {
                            String column = columns.get(j);
                            Object val = values.get(i).get(j);
                            if ("time".equals(column)) {
                                beanWrapper.setPropertyValue("time", Timestamp.from(ZonedDateTime.parse(String.valueOf(val)).toInstant()).getTime());
                            } else {
                                //保存当前列和值到 field map 中 //注意: 返回结果无须在知道是 tags 还是 fields  认为就是字段和值 可以将所有字段作为 field 进行返回
                                fields.put(column, val);
                            }
                        }
                        //10.通过反射完成 fields 赋值操作
                        beanWrapper.setPropertyValue("fields", fields);
                        lists.add(instance);
                    } catch (InstantiationException | IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        });
        return lists;
    }


    /**
     * 查询返回指定对象
     *
     * @param selectCommand select 语句
     * @param clazz         类型
     * @param <T>           泛型
     * @return 结果
     */
    public <T> List<T> query(String selectCommand, Class<T> clazz) {
        return handleQueryResult(query(selectCommand), clazz);
    }
}
