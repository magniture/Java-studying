package com.zq.influxdb.example;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.influxdb.annotations.Column;
import com.influxdb.annotations.Measurement;
import com.influxdb.client.*;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import com.influxdb.exceptions.InfluxException;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.configurationprocessor.json.JSONArray;

public class InfluxDB2Example {

    private static char[] token = "KlOXXtB3iwb-3MSdITL8tS4zJIMnfFsTFUIRaKWqL5dFB8IHQQBvOCpYHGK-Ecwg-UaxhdsdVCzJy4GwzJK1LQ==".toCharArray();
    private static String org = "beyond";
    private static String bucket = "test";

    public static void main(final String[] args) throws InterruptedException {

        InfluxDBClient influxDBClient = InfluxDBClientFactory.create("http://127.0.0.1:8086", token, org, bucket);


        WriteApiBlocking writeApi = influxDBClient.getWriteApiBlocking();

        //1. 根据线路协议进行写入数据
//        try {
//            String record = "temperature,location=north value=110.0,age=6611";
//
//            writeApi.writeRecord(WritePrecision.NS, record);
//        } catch (InfluxException e) {
//            throw new RuntimeException(e);
//        }


/**************************************************************/

         /*2. point写入*/
//        Point point = Point.measurement("temperature")
//                .addTag("location", "west")
//                .addField("value", 55D)
//                .time(Instant.now().toEpochMilli(), WritePrecision.MS);
//        writeApi.writePoint(point);

/**************************************************************/
        /*3.根据实体类写入*/
//        Temperature temperature = new Temperature();
//        temperature.location = "south";
//        temperature.value = 66;
//        temperature.time = Instant.now();
//        writeApi.writeMeasurement( WritePrecision.NS, temperature);

/**************************************************************/


        /*4.批量写入(无封装)*/
        List points = new ArrayList<>();
        for(int i = 0 ; i < 1000 ; i++ ) {
            Point pointOne = Point.measurement("temperature")
                    .addTag("location", "west")
                    .addField("speed", 55+i)
                    .time(Instant.now(),WritePrecision.NS)
                    ;
            points.add(pointOne);
        }
        writeApi.writePoints(points);

/**************************************************************/

        /*5.批量写入（有实体类封装）*/
//        writeApi.writeMeasurements(WritePrecision.NS,
//                IntStream.range(0, 1000)
//                        .mapToObj(i -> Temperature.builder()
//                                .time(Instant.now())
//                                .location(String.valueOf(Math.abs(new Random().nextLong())))
//                                .value(Integer.valueOf(Math.abs(new Random().nextInt())))
//                                .build())
//                        .collect(Collectors.toList())
//                );
//
//
//
        QueryApi queryApi = influxDBClient.getQueryApi();
        /*基本查询*/
//        String flux = "from(bucket: \"test\")\n" +
//                "  |> range(start: 0)\n" +
//                "  |> filter(fn: (r) => r[\"_measurement\"] == \"temperature\")\n" +
//                "  |> filter(fn: (r) => r[\"location\"] =~/.*h.*/)";
//        //我演示的是模糊查询，为了数据好看点
//        QueryApi queryApi = influxDBClient.getQueryApi();
//        List<Map<String,Object>> list = new LinkedList<>();
//        Set<Object> set = new HashSet<>();
//        List<FluxTable> tables = queryApi.query(flux);
//        List<Temperature> query = queryApi.query(flux, Temperature.class);
//        for (FluxTable fluxTable : tables) {
//            List<FluxRecord> records = fluxTable.getRecords();
//            for (FluxRecord fluxRecord : records) {
//                System.out.println(fluxRecord.getTime() + ": " + fluxRecord.getValueByKey("_value"));
//                System.out.println(fluxRecord.getTime() + ": " + fluxRecord.getValueByKey("location"));
//                List<Object> row = fluxRecord.getRow();
//                String field = fluxRecord.getField();
//                Map<String, Object> values = fluxRecord.getValues();
//                list.add(values);
//                System.out.println(values);
//                System.out.println(field);
//                System.out.println(values);


/**************************************************************/

////        //也可以使用实体类
////        List<Temperature> query = queryApi.query(flux, Temperature.class);
////        for (Temperature t : query) {
////            System.out.println(t);
////        }
/**************************************************************/

        //异步查询
//        queryApi.query(flux,
//                (cancellable, fluxRecord) -> {
//                    System.out.println(fluxRecord.getTime() + ": " + fluxRecord.getValueByKey("_value"));
//                },
//                throwable -> {
//                    //invoke when exception
//                    System.out.println("Error");
//                },
//                () -> {
//                    //complete
//                    System.out.println("complete");
//                });
//        System.out.println("Query invoke done");
//        TimeUnit.SECONDS.sleep(3);

    }

}
