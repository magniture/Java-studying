package org.example.test;


import org.influxdb.BatchOptions;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.*;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @BelongsProject: Java-studying
 * @BelongsPackage: org.example.test
 * @Author: zhangq
 * @CreateTime: 2022-12-20  15:36
 * @Description: TODO
 */
public class TestInfluxDBClient {

    private  InfluxDB influxDB;
    @Before
    public void getConn() {
        //获取连接
        influxDB = InfluxDBFactory.connect("http://127.0.0.1:8086","root","root");

        Pong pong = influxDB.ping();
        System.out.println(pong);
        //关闭连接
        //influxDB.close();
    }

    @Test
    public void after() {
        influxDB.close();
    }

    @Test
    public void testDataBase() {
        // 1.创建一个库 database ，
        QueryResult create_database_lags = influxDB.query(new Query("create database lags"));
        System.out.println(create_database_lags);

        //2. 使用一个库
        influxDB.setDatabase("lags");

        //3.查询存在哪些库
        QueryResult show_databases = influxDB.query(new Query("show databases"));
        System.out.println(show_databases);

        //4.删除一个库
        //influxDB.query(new Query("drop database lags"));

        //判断库是否存在，使用query查询存在不，再进行判断
        List<QueryResult.Result> results = show_databases.getResults();
        results.forEach(res -> {
            List<QueryResult.Series> series = res.getSeries();
            for (int i = 0; i <series.size(); i++) {
                List<List<Object>> values = series.get(i).getValues();
                values.forEach(System.out::println);
            }
        });
    }

    @Test
    public void getMeasurement() {
        //1.选择一个库
        influxDB.setDatabase("lags");
        //2.查询所有表
        QueryResult show_measurements = influxDB.query(new Query("show measurements"));
        System.out.println(show_measurements);
        //3. 删除表
//        influxDB.query(new Query("drop measurement act"));


    }

    @Test
    public void testInsert() {

        influxDB.setDatabase("lags");

        // 创建point
        Point time = Point.measurement("act")
                .tag("id", "11")
                .tag("origin", "master")
                .addField("win", "11")
                .addField("power", "12.112")
                .addField("cpu", "110")
                .time(new Date().getTime(), TimeUnit.MILLISECONDS).build();

        Point time1 = Point.measurement("act")
                .tag("id", "111")
                .tag("origin", "master")
                .addField("win", "11")
                .addField("power", "12.112")
                .addField("cpu", "110")
                .time(new Date().getTime(), TimeUnit.MILLISECONDS).build();
        // 单条插入
        influxDB.write(time);

        // 批量插入
        BatchPoints build = BatchPoints.builder().points(time, time1).build();
        influxDB.write(build);

    }

    @Test
    public void testSelect() {
        influxDB.setDatabase("lags");
        QueryResult query = influxDB.query(new Query("select * from act"));
        System.out.println(query);
        List<QueryResult.Result> results = query.getResults();
        results.forEach(res->{
            List<QueryResult.Series> series = res.getSeries();
            series.forEach(ser->{
                List<String> columns = ser.getColumns();
                List<List<Object>> values = ser.getValues();
                values.forEach(System.out::println);
            });
        });
    }



    /*
    *  保留策略
    * */
    @Test
    public void testPolicy() {
        influxDB.setDatabase("lags");
        //2.查询当前库策略
        QueryResult show_retention_policies = influxDB.query(new Query("show retention policies"));
        System.out.println(show_retention_policies);

        //3.创建一个保留策略
        /*# 新建一个策略
        CREATE RETENTION POLICY "策略名称" ON 数据库名 DURATION 时长 REPLICATION 副本个数;

        # 新建一个策略并且直接设置为默认策略
        CREATE RETENTION POLICY "策略名称" ON 数据库名 DURATION 时长 REPLICATION 副本个数 DEFAULT;
        */
        influxDB.query(new Query("create retention policy history_policy on lags duration 168h  replication 1 shard duration 168h default"));

        //4。查询当前库策略
        QueryResult show_retention_policies1 = influxDB.query(new Query("show retention policies"));
        System.out.println(show_retention_policies1);

        //5.修改策略
        /*
        * ALTER RETENTION POLICY "策略名称" ON "数据库名" DURATION 时长
        ALTER RETENTION POLICY "策略名称" ON "数据库名" DURATION 时长 DEFAULT
        * */
        influxDB.query(new Query("ALTER RETENTION POLICY history_policy ON lags DEFAULT"));
        QueryResult show_retention_policies2 = influxDB.query(new Query("show retention policies"));
        System.out.println(show_retention_policies2);

        //6.删除策略   drop retention POLICY "策略名" ON "数据库名"
        influxDB.query(new Query("drop retention POLICY history_policy ON lags "));
        QueryResult show_retention_policies3 = influxDB.query(new Query("show retention policies"));
        System.out.println(show_retention_policies3);
    }
}
