package com.github.nanoyou.akariyumetabackend.socketio;

import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.SpringAnnotationScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class SocketIOServerConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(SocketIOServerConfig.class);

    @Value("${socketio.server.host}")
    private String host;

    @Value("${socketio.server.port}")
    private Integer port;

    @Value("${socketio.server.host.linux}")
    private String linuxHost;

    @Bean
    public SocketIOServer socketIOServer() {
        com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();

        String os = System.getProperty("os.name");
        if(os.toLowerCase().startsWith("win")){   //在本地window环境测试时用localhost
            LOGGER.info("本地环境");
            config.setHostname(host);
        } else {
            config.setHostname(linuxHost);
        }
        config.setPort(port);

        final SocketIOServer server = new SocketIOServer(config);
        return server;
    }

    /**
     * 用于扫描netty-socketio的注解，比如 @OnConnect、@OnEvent
     */
    @Bean
    public SpringAnnotationScanner springAnnotationScanner() {
        return new SpringAnnotationScanner(socketIOServer());
    }
}