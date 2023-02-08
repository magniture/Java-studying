package com.zq.influxdb.example;

import com.influxdb.annotations.Column;
import com.influxdb.annotations.Measurement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Measurement(name = "temperature")
public class Pro {
    @Column(tag = true)
    String location;

    @Column
    String value;

    @Column(timestamp = true)
    Instant time;
}
