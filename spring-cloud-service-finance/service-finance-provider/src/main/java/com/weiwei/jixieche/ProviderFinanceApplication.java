package com.weiwei.jixieche;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * @ClassName ProviderCommonApplication
 * @Description TODO
 * @Author houji
 * @Date 2019/5/7 17:08
 * @Version 1.0.1
 **/
@SpringBootApplication
@EnableSwagger2
@MapperScan("com.weiwei.jixieche.mapper")
@EnableFeignClients
@EnableDiscoveryClient
public class ProviderFinanceApplication {

    public static void main(String[] args) {

        SpringApplication.run(ProviderFinanceApplication.class, args);

    }

}
