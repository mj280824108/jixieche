package com.weiwei.jixieche.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @ClassName AliShortMsgConfig
 * @Description 阿里云短信配置
 * @Author houji
 * @Date 2019/8/28 20:33
 * @Version 1.0.1
 **/
@Component
@Data
@Configuration
public class AliShortMsgConfig {

    @Value("${alimsg.key}")
    private String key;

    @Value("${alimsg.secret}")
    private String secret;

    @Value("${alimsg.flag}")
    private boolean flag;
}
