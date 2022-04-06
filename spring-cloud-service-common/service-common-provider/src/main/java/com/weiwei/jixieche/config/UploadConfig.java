package com.weiwei.jixieche.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @ClassName UploadConfig
 * @Description TODO
 * @Author houji
 * @Date 2019/5/16 16:53
 * @Version 2.0
 **/
@Component
@Data
@Configuration
public class UploadConfig {

    @Value("${oss.image.endpoint}")
    private  String OSS_END_POINT;

    @Value("${oss.image.keyid}")
    private  String OSS_ACCESS_KEY_ID;

    @Value("${oss.image.keysecret}")
    private  String OSS_ACCESS_KEY_SECRET;

    @Value("${oss.image.filehost}")
    private  String OSS_FILE_HOST;

    @Value("${oss.image.bucketname1}")
    private  String OSS_BUCKET_NAME1;
}
