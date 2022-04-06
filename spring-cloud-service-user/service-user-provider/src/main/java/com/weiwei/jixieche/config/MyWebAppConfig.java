package com.weiwei.jixieche.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @ClassName MyWebAppConfig
 * @Description TODO
 * @Author houji
 * @Date 2019/10/14 13:37
 * @Version 1.0.1
 **/
@Configuration
public class MyWebAppConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/staticH5/**").addResourceLocations("classpath:/static/staticH5/");
        super.addResourceHandlers(registry);
    }
}
