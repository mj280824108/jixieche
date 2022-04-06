package com.weiwei.jixieche;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @ClassName 类名
 * @Description TODO
 * @Author houji
 * @Date 2019/8/13 18:08
 * @Version 2.0
 **/
@EnableCircuitBreaker
@SpringBootApplication
@EnableSwagger2
@EnableFeignClients
@EnableDiscoveryClient
@MapperScan("com.weiwei.jixieche.mapper")
public class ProviderSystemApplication {
	public static void main(String[] args) {
		SpringApplication.run(ProviderSystemApplication.class, args);
	}
}
