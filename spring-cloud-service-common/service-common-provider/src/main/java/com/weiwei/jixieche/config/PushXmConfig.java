package com.weiwei.jixieche.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @ClassName JpushConfig
 * @Description 小米推送服务的配置
 * @Author houji
 * @Date 2019/5/17 17:22
 * @Version 1.0.1
 **/
@Component
@Data
@Configuration
public class PushXmConfig {

    @Value("${pushmi.owner.id}")
    private String ownerId;

    @Value("${pushmi.owner.key}")
    private String ownerKey;

    @Value("${pushmi.owner.secret}")
    private String ownerSecret;

    @Value("${pushmi.owner.page}")
    private String ownerPage;

    @Value("${pushmi.ten.id}")
    private String tenId;

    @Value("${pushmi.ten.key}")
    private String tenKey;

    @Value("${pushmi.ten.secret}")
    private String tenSecret;

    @Value("${pushmi.ten.page}")
    private String tenPage;

}
