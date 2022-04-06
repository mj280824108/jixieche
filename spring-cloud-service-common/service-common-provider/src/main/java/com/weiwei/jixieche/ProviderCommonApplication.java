package com.weiwei.jixieche;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.MultipartConfigElement;


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
public class ProviderCommonApplication{

    public static void main(String[] args) {
        SpringApplication.run(ProviderCommonApplication.class, args);
    }

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //  单个数据大小
        factory.setMaxFileSize("102400KB");
        /// 总上传数据大小
        factory.setMaxRequestSize("102400KB");
        return factory.createMultipartConfig();
    }

}
