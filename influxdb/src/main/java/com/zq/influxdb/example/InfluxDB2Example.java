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


//        WriteApiBlocking writeApi = influxDBClient.getWriteApiBlocking();

        try (WriteApi writeApi = influxDBClient.makeWriteApi()) {

            //
            // Write by LineProtocol
            //
            String record = "temperature,location=north value=110.0,age=6611";

            writeApi.writeRecord(WritePrecision.NS, record);
        }


//
////
////         /*point写入*/
////        Point point = Point.measurement("temperature")
////                .addTag("location", "west")
////                .addField("value", 55D)
////                .time(Instant.now().toEpochMilli(), WritePrecision.MS);
////        writeApi.writePoint(point);
////
////
////        /*根据InfluxDB协议（Line protocol）写入*/
////        writeApi.writeRecord(WritePrecision.NS, "temperature,location=north value=60.0");
////
////
////        /*根据实体类写入*/
////        Temperature temperature = new Temperature();
////        temperature.location = "south";
////        temperature.value = "62D";
////        temperature.time = Instant.now();
////        writeApi.writeMeasurement( WritePrecision.NS, temperature);
////
////        /*1.批量写入(无封装)*/
////        List points = new ArrayList<>();
////        for(int i = 0 ; i < 100 ; i++ ) {
////            Point pointOne = Point.measurement("temperature")
////                    .addTag("location", "west")
////                    .addField("value", "55D"+i);
////            points.add(pointOne);
////        }
////        writeApi.writePoints(points);
////
////
//        /*2.批量写入（有实体类封装）*/
////        writeApi.writeMeasurements(WritePrecision.NS,
////                IntStream.range(0, 1000)
////                        .mapToObj(i -> Temperature.builder()
////                                .time(Instant.now())
////                                .location(String.valueOf(Math.abs(new Random().nextLong())))
////                                .value(String.valueOf(Math.abs(new Random().nextInt())))
////                                .build())
////                        .collect(Collectors.toList())
////                );
//
//
//
        /*基本查询*/
        String flux = "from(bucket: \"test\")\n" +
                "  |> range(start: 0)\n" +
                "  |> filter(fn: (r) => r[\"_measurement\"] == \"temperature\")\n" +
                "  |> filter(fn: (r) => r[\"location\"] =~/.*h.*/)";
        //我演示的是模糊查询，为了数据好看点
        QueryApi queryApi = influxDBClient.getQueryApi();
        List<Map<String,Object>> list = new LinkedList<>();
        Set<Object> set = new HashSet<>();
        List<FluxTable> tables = queryApi.query(flux);
        List<Temperature> query = queryApi.query(flux, Temperature.class);
        for (FluxTable fluxTable : tables) {
            List<FluxRecord> records = fluxTable.getRecords();
            for (FluxRecord fluxRecord : records) {
//                System.out.println(fluxRecord.getTime() + ": " + fluxRecord.getValueByKey("_value"));
//                System.out.println(fluxRecord.getTime() + ": " + fluxRecord.getValueByKey("location"));
                List<Object> row = fluxRecord.getRow();
                String field = fluxRecord.getField();
                Map<String, Object> values = fluxRecord.getValues();
                list.add(values);
//                System.out.println(values);
//                System.out.println(field);
//                System.out.println(values);





            }

        }

        Map<String, List<Map<String, Object>>> collect = list.stream().filter(map -> map.get("_time") != null)
                .collect(Collectors.groupingBy(o -> {
                    set.add(o.get("_time"));
                    return o.get("_time").toString();
                }));
        for (Map.Entry entry : collect.entrySet()) {
            List<Map<String, Object>> maps = (List<Map<String, Object>>) entry.getValue();
            Map<Object,Object> map = new HashMap<>();
            maps.forEach(s -> {
                map.put(s.get("_field"),s.get("_value"));
                map.put("time",s.get("_time"));
                map.put("location",s.get("location"));
            });
            System.out.println(map);
        }
//2、object转换为map



//                .entrySet().stream().map(o -> {
//                    //合并
//                    Map<String, Object> map = o.getValue().stream().flatMap(m -> {
//                        return m.entrySet().stream();
//                    }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> b));
//
//                    return map;
//                }).collect(Collectors.toList());
        influxDBClient.close();

////        //也可以使用实体类
////        List<Temperature> query = queryApi.query(flux, Temperature.class);
////        for (Temperature t : query) {
////            System.out.println(t);
////        }
//
//        //异步查询
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

        influxDBClient.close();
    }

}
