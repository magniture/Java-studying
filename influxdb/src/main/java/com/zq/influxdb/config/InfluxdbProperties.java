package com.zq.influxdb.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("spring.influx")
@Data
public class InfluxdbProperties {
    private String url;
    private String username;
    private String password;
    private String database;
    private String retention;//保留策略
}
