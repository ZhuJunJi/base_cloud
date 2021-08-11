package com.zhujunji.base.service.config;

import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.connection.ClusterType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Configuration
public class MongoConfig {

    @Bean
    public MongoClientSettings mongoClientSettings(MongoSettingsProperties properties) {


        String dataBase = properties.getDatabase();

        MongoCredential credential = MongoCredential.createCredential(properties.getUsername(), dataBase, properties.getPassword().toCharArray());


        List<String> address = properties.getAddress();
        List<ServerAddress> hosts = address.stream()
                .map(host -> {
                    String[] inetAddress = host.split(":");
                    return new ServerAddress(inetAddress[0],Integer.parseInt(inetAddress[1]));
                })
                .collect(Collectors.toList());


        return MongoClientSettings.builder()
                .credential(credential)
                // pool
                .applyToConnectionPoolSettings(builder -> builder
                        .maxSize(properties.getMaxSize())
                        .minSize(properties.getMinSize())
                        .maxWaitTime(properties.getMaxWaitTime(), TimeUnit.MILLISECONDS)
                        .maxConnectionLifeTime(properties.getMaxConnectionLifeTime(), TimeUnit.MILLISECONDS)
                        .maxConnectionIdleTime(properties.getMaxConnectionIdleTime(), TimeUnit.MILLISECONDS)
                        .maintenanceInitialDelay(properties.getMinHeartbeatFrequency(), TimeUnit.MILLISECONDS)
                        .maintenanceFrequency(properties.getMaintenanceFrequency(), TimeUnit.MILLISECONDS)
                )
                // socket
                .applyToSocketSettings(builder -> builder
                        .connectTimeout(properties.getConnectTimeout(), TimeUnit.MILLISECONDS)
                        .readTimeout(properties.getReadTimeout(), TimeUnit.MILLISECONDS)
                        .receiveBufferSize(properties.getReceiveBufferSize())
                        .sendBufferSize(properties.getSendBufferSize())
                )
                // ssl
                .applyToSslSettings(builder -> builder
                        .enabled(properties.getSslEnabled())
                        .invalidHostNameAllowed(properties.getSslInvalidHostNameAllowed())
                )
                // cluster
                .applyToClusterSettings(builder -> builder
                        .localThreshold(properties.getLocalThreshold(), TimeUnit.MILLISECONDS)
                        .hosts(hosts)
                        .requiredClusterType(ClusterType.STANDALONE)
                        .serverSelectionTimeout(properties.getServerSelectionTimeout(), TimeUnit.MILLISECONDS)
                )
                // server
                .applyToServerSettings(builder -> builder
                        .heartbeatFrequency(properties.getHeartbeatFrequency(),TimeUnit.MILLISECONDS)
                        .minHeartbeatFrequency(properties.getMinHeartbeatFrequency(),TimeUnit.MILLISECONDS)
                )
                .build();
    }
}
