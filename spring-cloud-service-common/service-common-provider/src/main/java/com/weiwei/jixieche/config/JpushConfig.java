package com.weiwei.jixieche.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @ClassName JpushConfig
 * @Description 极光推送设置
 * @Author houji
 * @Date 2019/5/17 17:22
 * @Version 1.0.1
 **/
@Component
@Data
@Configuration
public class JpushConfig {

    @Value("${jpush.app.ownerKey}")
    private String APP_KEY_OWNER;

    @Value("${jpush.app.ownerSecret}")
    private String MASTER_SECRET_OWNER;

    @Value("${jpush.app.tenantryKey}")
    private String APP_KEY_TENANTRY;

    @Value("${jpush.app.tenantrySecret}")
    private String MASTER_SECRET_TENANTRY;

    @Value("${jpush.app.batchSendSize}")
    private int BATCH_SEND_SIZE;

    @Value("${jpush.flag}")
    private Boolean flag;

    public static final String OPTION_KEY_APP_KEY_OWNER = "jpush_app_key_owner";
    public static final String OPTION_KEY_MASTER_SECRET_OWNER = "jpush_master_secret_owner";

    public static final String OPTION_KEY_APP_KEY_TENANTRY = "jpush_app_key_tenantry";
    public static final String OPTION_KEY_MASTER_SECRET_TENANTRY = "jpush_master_secret_tenantry";

    public static final String OPTION_KEY_BATCH_SEND_SIZE = "jpush_batch_send_size";

}
