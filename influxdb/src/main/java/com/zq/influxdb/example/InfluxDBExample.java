package com.zq.influxdb.example;


import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;

public class InfluxDBExample {

    public static void main(String[] agrs){

        //获取连接
        InfluxDB influxDB = InfluxDBFactory.connect("http://175.178.111.160:8086","root","root");


        //关闭连接
        influxDB.close();
    }


}
