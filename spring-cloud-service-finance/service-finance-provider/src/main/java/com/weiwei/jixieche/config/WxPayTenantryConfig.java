package com.weiwei.jixieche.config;

import com.github.wxpay.sdk.WXPayConfig;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * @ClassName WxPayConfig
 * @Description TODO
 * @Author houji
 * @Date 2019/8/16 10:09
 * @Version 1.0.1
 **/
@Component
@Data
@Configuration
public class WxPayTenantryConfig implements WXPayConfig {

    private static Logger log = LoggerFactory.getLogger(WxPayTenantryConfig.class);

    @ApiModelProperty("公众账号ID")
    @Value("${wxpay.tenantry.appId}")
    private String appID;

    @ApiModelProperty("商户号")
    @Value("${wxpay.tenantry.mchId}")
    private String mchID;

    @ApiModelProperty("密钥")
    @Value("${wxpay.tenantry.key}")
    private String key;

    @ApiModelProperty("API证书绝对路径")
    @Value("${wxpay.tenantry.certPath}")
    private String certPath;

    @ApiModelProperty("退款异步通知地址")
    @Value("${wxpay.tenantry.notifyUrl}")
    private String notifyUrl;

    @ApiModelProperty("HTTP(S) 连接超时时间，单位毫秒")
    @Value("${wxpay.httpConnectTimeoutMs}")
    private int httpConnectTimeoutMs;

    @ApiModelProperty("HTTP(S) 读数据超时时间，单位毫秒")
    @Value("${wxpay.httpReadTimeoutMs}")
    private int httpReadTimeoutMs;

    /**
     * 获取商户证书内容
     * @return 商户证书内容
     */
    @Override
    public InputStream getCertStream()  {
        File certFile = new File(certPath);
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(certFile);
        } catch (FileNotFoundException e) {
            log.error("cert file not found, path={}, exception is:{}", certPath, e);
        }
        return inputStream;
    }
}
