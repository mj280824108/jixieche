package com.weiwei.jixieche.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @ClassName JixiecheEurekaApplication
 * @Description spingCloud注册中心
 * @Author houji
 * @Date 2019/7/29 17:53
 * @Version 1.0.1
 **/
@SpringBootApplication
@EnableEurekaServer
public class JixiecheEurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(JixiecheEurekaApplication.class, args);
    }

}
