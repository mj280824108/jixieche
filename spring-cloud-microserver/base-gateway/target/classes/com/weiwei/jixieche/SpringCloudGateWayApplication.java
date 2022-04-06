package com.weiwei.jixieche;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @ClassName ss
 * @Description TODO
 * @Author houji
 * @Date 2019/5/7 10:47
 * @Version 1.0.1
 **/
@SpringBootApplication
@EnableDiscoveryClient
public class SpringCloudGateWayApplication {

    public static void main(String[] args) {

        SpringApplication.run(SpringCloudGateWayApplication.class, args);
    }


}
