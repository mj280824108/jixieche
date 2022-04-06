package com.weiwei.jixieche.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @ClassName EagleEyesConfig
 * @Description TODO
 * @Author houji
 * @Date 2019/5/27 11:09
 * @Version 1.0.1
 **/
@Component
@Data
@Configuration
public class EagleEyesConfig {

    @Value("${eagle.eyes.ak}")
    private String ak;

    @Value("${eagle.eyes.serviceId}")
    private String serviceId;

    @Value("${eagle.eyes.urlAddEntity}")
    private String urlAddEntity;

    @Value("${eagle.eyes.urlQueryEntity}")
    private String urlQueryEntity;

    @Value("${eagle.eyes.urlGetDistace}")
    private String urlGetDistace;



}
