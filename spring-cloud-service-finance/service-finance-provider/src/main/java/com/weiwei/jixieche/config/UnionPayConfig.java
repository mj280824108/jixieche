package com.weiwei.jixieche.config;

import com.weiwei.jixieche.utils.ChinaPayConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @ClassName UnionPayConfig
 * @Description TODO
 * @Author houji
 * @Date 2019/8/16 10:09
 * @Version 1.0.1
 **/
@Component
@Data
@Configuration
public class UnionPayConfig {

    @ApiModelProperty("银联支付merId")
    @Value("${unionPay.merId}")
    private String merId;

    @ApiModelProperty("银联支付keyUsage")
    @Value("${unionPay.keyUsage}")
    private Integer keyUsage;

    @ApiModelProperty("银联支付key文件地址")
    @Value("${unionPay.keyFilePath}")
    private String keyFilePath;

    @ApiModelProperty("银联支付版本号")
    @Value("${unionPay.version}")
    private String version;

    @ApiModelProperty("银联支付签名")
    @Value("${unionPay.signFlag}")
    private Integer signFlag;

    @ApiModelProperty("银联支付回调地址")
    @Value("${unionPay.transferUrl}")
    private String transferUrl;

    @Bean
    public ChinaPayConstant initChinaPayConstant(){
        ChinaPayConstant chinaPayConstant = new ChinaPayConstant();

        chinaPayConstant.merId = merId;
        chinaPayConstant.keyUsage = keyUsage;
        chinaPayConstant.keyFilePath = keyFilePath;
        chinaPayConstant.version = version;
        chinaPayConstant.signFlag = signFlag;
        chinaPayConstant.transferUrl = transferUrl;

        return chinaPayConstant;
    }
}
