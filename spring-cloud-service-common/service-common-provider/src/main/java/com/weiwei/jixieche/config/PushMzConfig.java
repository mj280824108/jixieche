package com.weiwei.jixieche.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @ClassName JpushConfig
 * @Description 魅族推送服务的配置
 * @Author houji
 * @Date 2019/5/17 17:22
 * @Version 1.0.1
 **/
@Component
@Data
@Configuration
public class PushMzConfig {

    @Value("${pushmz.owner.appid}")
    private Long ownerId;

    @Value("${pushmz.owner.appkey}")
    private String ownerKey;

    @Value("${pushmz.owner.appsecret}")
    private String ownerSecret;

    @Value("${pushmz.owner.page}")
    private String ownerPage;

    @Value("${pushmz.ten.appid}")
    private Long tenId;

    @Value("${pushmz.ten.appkey}")
    private String tenKey;

    @Value("${pushmz.ten.appsecret}")
    private String tenSecret;

    @Value("${pushmz.ten.page}")
    private String tenPage;

}
