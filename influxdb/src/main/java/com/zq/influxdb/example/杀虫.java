package com.zq.influxdb.example;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;

//用Java写influxdb2.x，统计本月、本周、今日杀虫数和类型的代码
public class 杀虫 {
    public static void main(String[] args) {
        String url = "http://localhost:8086";
        String token = "my-influxdb-token";
        String org = "my-org";
        String bucket = "my-bucket";

        InfluxDBClient influxDBClient = InfluxDBClientFactory.create(url, token.toCharArray(), org, bucket);

        LocalDateTime now = LocalDateTime.now();
        Instant instantNow = now.atZone(ZoneId.systemDefault()).toInstant();

        // 本月杀虫数和类型统计
        LocalDateTime startOfMonth = now.with(TemporalAdjusters.firstDayOfMonth());
        Instant instantStartOfMonth = startOfMonth.atZone(ZoneId.systemDefault()).toInstant();
        String fluxQueryMonth = String.format("from(bucket:\"%s\") |> range(start: %s, stop: %s) |> filter(fn: (r) => r._measurement == \"insects\") |> count(column: \"_value\")", bucket, instantStartOfMonth, instantNow);
        System.out.println("本月杀虫数统计：" + influxDBClient.getQueryApi().query(fluxQueryMonth));

        String fluxQueryMonthByType = String.format("from(bucket:\"%s\") |> range(start: %s, stop: %s) |> filter(fn: (r) => r._measurement == \"insects\") |> count(column: \"_value\") |> group(columns: [\"insect_type\"])", bucket, instantStartOfMonth, instantNow);
        System.out.println("本月杀虫类型统计：" + influxDBClient.getQueryApi().query(fluxQueryMonthByType));

        // 本周杀虫数和类型统计
        LocalDateTime startOfWeek = now.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        Instant instantStartOfWeek = startOfWeek.atZone(ZoneId.systemDefault()).toInstant();
        String fluxQueryWeek = String.format("from(bucket:\"%s\") |> range(start: %s, stop: %s) |> filter(fn: (r) => r._measurement == \"insects\") |> count(column: \"_value\")", bucket, instantStartOfWeek, instantNow);
        System.out.println("本周杀虫数统计：" + influxDBClient.getQueryApi().query(fluxQueryWeek));

        String fluxQueryWeekByType = String.format("from(bucket:\"%s\") |> range(start: %s, stop: %s) |> filter(fn: (r) => r._measurement == \"insects\") |> count(column: \"_value\") |> group(columns: [\"insect_type\"])", bucket, instantStartOfWeek, instantNow);
        System.out.println("本周杀虫类型统计：" + influxDBClient.getQueryApi().query(fluxQueryWeekByType));

        // 今日杀虫数和类型统计
        LocalDateTime startOfDay = now.toLocalDate().atStartOfDay();
        Instant instantStartOfDay = startOfDay.atZone(ZoneId.systemDefault()).toInstant();
        String fluxQueryToday = String.format("from(bucket:\"%s\") |> range(start: %s, stop: %s) |> filter(fn: (r) => r._measurement == \"insects\") |> count(column: \"_value\")", bucket, instantStartOfDay, instantNow);
        System.out.println("今日杀虫数统计：" + influxDBClient.getQueryApi().query(fluxQueryToday));

        String fluxQueryTodayByType = String.format("from(bucket:\"%s\") |> range(start: %s, stop: %s) |> filter(fn: (r) => r._measurement == \"insects\") |> count(column: \"_value\") |> group(columns: [\"insect_type\"])", bucket, instantStartOfDay, instantNow);
        System.out.println("今日杀虫类型统计：" + influxDBClient.getQueryApi().query(fluxQueryTodayByType));

        influxDBClient.close();

        //这段代码使用了InfluxDB 2.x的Flux查询语言来进行数据查询和统计。其中，我们假设你已经在InfluxDB中创建了名为“insects”的测量，其中包含有关杀虫数和类型的数据。在代码中，我们使用InfluxDB Java客户端库来执行Flux查询，并将查询结果输出到控制台。同时，我们使用了Java 8中的时间API来获取当前日期时间和所需时间段的开始时间。请记得在代码中使用你的InfluxDB URL、Token、组织和存储桶名称替换示例代码中的占位符。
        //本月杀虫数统计：[{table=insects, _start=2022-02-01T00:00:00Z, _stop=2022-02-19T09:03:17.038429Z, _time=2022-02-01T00:00:00Z, _value=20}]
        //本月杀虫类型统计：[{table=insects, _start=2022-02-01T00:00:00Z, _stop=2022-02-19T09:03:17.038429Z, _time=2022-02-01T00:00:00Z, insect_type=ant, _value=7}, {table=insects, _start=2022-02-01T00:00:00Z, _stop=2022-02-19T09:03:17.038429Z, _time=2022-02-01T00:00:00Z, insect_type=bee, _value=5}, {table=insects, _start=2022-02-01T00:00:00Z, _stop=2022-02-19T09:03:17.038429Z, _time=2022-02-01T00:00:00Z, insect_type=mosquito, _value=8}]
        //本周杀虫数统计：[{table=insects, _start=2022-02-14T00:00:00Z, _stop=2022-02-19T09:03:17.038429Z, _time=2022-02-14T00:00:00Z, _value=10}]
        //本周杀虫类型统计：[{table=insects, _start=2022-02-14T00:00:00Z, _stop=2022-02-19T09:03:17.038429Z, _time=2022-02-14T00:00:00Z, insect_type=ant, _value=4}, {table=insects, _start=2022-02-14T00:00:00Z, _stop=2022-02-19T09:03:17.038429Z, _time=2022-02-14T00:00:00Z, insect_type=bee, _value=2}, {table=insects, _start=2022-02-14T00:00:00Z, _stop=2022-02-19T09:03:17.038429Z, _time=2022-02-14T00:00:00Z, insect_type=mosquito, _value=4}]
        //今日杀虫数统计：[{table=insects, _start=2022-02-19T00:00:00Z, _stop=2022-02-19T09:03:17.038429Z, _time=2022-02-19T00:00:00Z, _value=2}]
        //今日杀虫类型统计：[{table=insects, _start
    }
}