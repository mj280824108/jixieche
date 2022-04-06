package com.weiwei.jixieche.config;

/**
 * @ClassName AppConfig
 * @Description TODO
 * @Author houji
 * @Date 2019/5/23 14:32
 * @Version 1.0.1
 **/
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public JwtCheckGatewayFilterFactory jwtCheckGatewayFilterFactory(){
        return new JwtCheckGatewayFilterFactory();
    }
}
