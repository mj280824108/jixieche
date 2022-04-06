package com.weiwei.jixieche.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @ClassName JpushConfig
 * @Description 信鸽推送服务的配置
 * @Author houji
 * @Date 2019/5/17 17:22
 * @Version 1.0.1
 **/
@Component
@Data
@Configuration
public class PushXgConfig {

    @Value("${pushxg.owner.secret}")
    private String clientSecret;

    @Value("${pushxg.owner.id}")
    private String clientId;

    @Value("${pushxg.url.push}")
    private String pushUrl;

}
