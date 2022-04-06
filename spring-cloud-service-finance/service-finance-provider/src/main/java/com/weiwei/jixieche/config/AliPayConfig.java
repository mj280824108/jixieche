package com.weiwei.jixieche.config;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @ClassName AlipayConfig
 * @Description TODO
 * @Author houji
 * @Date 2019/8/16 10:08
 * @Version 1.0.1
 **/
@Component
@Data
@Configuration
public class AliPayConfig {

    @ApiModelProperty("机主支付网关url")
    @Value("${alipay.owner.gatewayUrl}")
    private String ownerGatewayUrl;

    @ApiModelProperty("机主支付appId")
    @Value("${alipay.owner.appId}")
    private String ownerAppId;

    @ApiModelProperty("机主支付商户私钥")
    @Value("${alipay.owner.appPrivateKey}")
    private String ownerAppPrivateKey;

    @ApiModelProperty("机主支付公钥")
    @Value("${alipay.owner.alipayPublicKey}")
    private String ownerAlipayPublicKey;

    @ApiModelProperty("机主支付回调地址")
    @Value("${alipay.owner.notifyUrl}")
    private String ownerNotifyUrl;

    @ApiModelProperty("承租方支付网关")
    @Value("${alipay.tenantry.gatewayUrl}")
    private String tenantryGatewayUrl;

    @ApiModelProperty("承租方支付appId")
    @Value("${alipay.tenantry.appId}")
    private String tenantryAppId;

    @ApiModelProperty("承租方支付私钥")
    @Value("${alipay.tenantry.appPrivateKey}")
    private String tenantryAppPrivateKey;

    @ApiModelProperty("承租方支付公钥")
    @Value("${alipay.tenantry.alipayPublicKey}")
    private String tenantryAlipayPublicKey;

    @ApiModelProperty("承租方支付回调地址")
    @Value("${alipay.tenantry.notifyUrl}")
    private String tenantryNotifyUrl;

    @ApiModelProperty("最大查询次数")
    @Value("${alipay.maxQueryRetry}")
    private int maxQueryRetry;

    @ApiModelProperty("查询间隔（毫秒）")
    @Value("${alipay.queryDuration}")
    private int queryDuration;

    @ApiModelProperty("最大撤销次数")
    @Value("${alipay.maxCancelRetry}")
    private int maxCancelRetry;

    @ApiModelProperty("撤销间隔（毫秒）")
    @Value("${alipay.cancelDuration}")
    private long cancelDuration;

    @ApiModelProperty("格式")
    @Value("${alipay.formate}")
    private String formate;

    @ApiModelProperty("编码")
    @Value("${alipay.charset}")
    private String charset;

    @ApiModelProperty("签名类型")
    @Value("${alipay.signType}")
    private String signType;

}
