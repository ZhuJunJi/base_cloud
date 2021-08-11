package com.zhujunji.base.service.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@PropertySource(value = "classpath:mongo-pool.yaml", encoding = "utf-8", factory = YamlPropertyResourceFactory.class)
@ConfigurationProperties(prefix = "spring.data.mongodb")
public class MongoSettingsProperties {

    // credential
    private List<String> address;
    private String replicaSet;
    private String database;
    private String username;
    private String password;

    /**
     * pool settings start
     */
    private Integer maxSize = 100;
    private Integer minSize = 0;
    private Long maxWaitTime = 120000L;
    private Long maxConnectionLifeTime = 0L;
    private Long maxConnectionIdleTime = 0L;
    private Long maintenanceInitialDelay = 0L;
    private Long maintenanceFrequency = 1000L;

    /**
     * socket settings
     */
    private Integer connectTimeout = 10000;
    private Integer readTimeout = 10000;
    private Integer receiveBufferSize = 64 * 1024 * 1024;
    private Integer sendBufferSize = 64 * 1024 * 1024;

    /**
     * ssl
     */
    private Boolean sslEnabled = false;
    private Boolean sslInvalidHostNameAllowed = false;

    // cluster
    private Integer localThreshold = 15;
    private Long serverSelectionTimeout = 30000L;

    // server
    private Integer heartbeatFrequency = 10000;
    private Integer minHeartbeatFrequency = 500;

    private Integer threadsAllowedToBlockForConnectionMultiplier = 5;
    private Long socketTimeout = 0L;
    private Boolean socketKeepAlive = false;
    private Boolean alwaysUseMBeans = false;
    private Integer heartbeatConnectTimeout = 20000;
    private Integer heartbeatSocketTimeout = 20000;
    private String authenticationDatabase;
}
