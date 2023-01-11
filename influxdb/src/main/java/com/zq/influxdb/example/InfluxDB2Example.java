package com.zq.influxdb.example;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.influxdb.annotations.Column;
import com.influxdb.annotations.Measurement;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.QueryApi;
import com.influxdb.client.WriteApiBlocking;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;

public class InfluxDB2Example {

    private static char[] token = "7RGARyypJ6GZTLL_pCVfIdTrjpsHWKRg5faGAN3XrNB_0v179GYqZlH-ku5gcAM22oGkvCq8hjAKgDbqWw4Cyg==".toCharArray();
    private static String org = "beyond";
    private static String bucket = "zq";

    public static void main(final String[] args) {

        InfluxDBClient influxDBClient = InfluxDBClientFactory.create("http://localhost:8086", token, org, bucket);


        WriteApiBlocking writeApi = influxDBClient.getWriteApiBlocking();

//
//         /*point写入*/
//        Point point = Point.measurement("temperature")
//                .addTag("location", "west")
//                .addField("value", 55D)
//                .time(Instant.now().toEpochMilli(), WritePrecision.MS);
//        writeApi.writePoint(point);
//
//
//        /*根据InfluxDB协议（Line protocol）写入*/
//        writeApi.writeRecord(WritePrecision.NS, "temperature,location=north value=60.0");
//
//
//        /*根据实体类写入*/
//        Temperature temperature = new Temperature();
//        temperature.location = "south";
//        temperature.value = "62D";
//        temperature.time = Instant.now();
//        writeApi.writeMeasurement( WritePrecision.NS, temperature);
//
//        /*1.批量写入(无封装)*/
//        List points = new ArrayList<>();
//        for(int i = 0 ; i < 100 ; i++ ) {
//            Point pointOne = Point.measurement("temperature")
//                    .addTag("location", "west")
//                    .addField("value", "55D"+i);
//            points.add(pointOne);
//        }
//        writeApi.writePoints(points);
//
//
//        /*2.批量写入（有实体类封装）*/
//        writeApi.writeMeasurements(WritePrecision.NS,
//                IntStream.range(0, 1000)
//                        .mapToObj(i -> Temperature.builder()
//                                .time(Instant.now())
//                                .location(String.valueOf(Math.abs(new Random().nextLong())))
//                                .value(String.valueOf(Math.abs(new Random().nextInt())))
//                                .build())
//                        .collect(Collectors.toList())
//                );
//


        /*基本查询*/
        String flux = "from(bucket: \"zq\")\n" +
                "  |> range(start: -1h)\n" +
                "  |> filter(fn: (r) => r[\"_measurement\"] == \"temperature\")\n" +
                "  |> filter(fn: (r) => r[\"location\"] =~/.*1.*/)";
        //我演示的是模糊查询，为了数据好看点
        QueryApi queryApi = influxDBClient.getQueryApi();
//        List<FluxTable> tables = queryApi.query(flux);
//        for (FluxTable fluxTable : tables) {
//            List<FluxRecord> records = fluxTable.getRecords();
//            for (FluxRecord fluxRecord : records) {
//                System.out.println(fluxRecord.getTime() + ": " + fluxRecord.getValueByKey("_value"));
//            }
//        }
        //也可以使用实体类
        List<Temperature> query = queryApi.query(flux, Temperature.class);
        for (Temperature t : query) {
            System.out.println(t);
        }

        influxDBClient.close();
    }

}
