package com.weiwei.jixieche.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @ClassName JpushConfig
 * @Description 华为推送服务的配置
 * @Author houji
 * @Date 2019/5/17 17:22
 * @Version 1.0.1
 **/
@Component
@Data
@Configuration
public class PushKitConfig {

    @Value("${pushkit.owner.secret}")
    private String CLIENT_SECRET;

    @Value("${pushkit.owner.id}")
    private String CLIENT_ID;

    @Value("${pushkit.owner.page}")
    private String ownerPage;

    @Value("${pushkit.url.token}")
    private String TOKEN_URL;

    @Value("${pushkit.url.push}")
    private String PUSH_URL;

}
