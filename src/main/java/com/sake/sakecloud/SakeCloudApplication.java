package com.sake.sakecloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication
public class SakeCloudApplication {

    public static void main(String[] args) {
        try {
            ConfigurableApplicationContext context = SpringApplication.run(SakeCloudApplication.class, args);
            Environment env = context.getEnvironment();
            String host = InetAddress.getLocalHost().getHostAddress();
            String port = env.getProperty("server.port");
            String contextPath = env.getProperty("server.servlet.context-path") ;
            contextPath = contextPath == null ? "" : contextPath;
            System.out.println("项目启动完成,地址：http://" + host + ":" + port + contextPath + "/");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

}
