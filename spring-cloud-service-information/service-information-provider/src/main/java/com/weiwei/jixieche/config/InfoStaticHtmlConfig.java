package com.weiwei.jixieche.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;


/**
 * @ClassName UnionBankCardConfig
 * @Description 资讯详情HTML页面
 * @Author houji
 * @Date 2019/9/2 10:11
 * @Version 1.0.1
 **/
@Component
@Data
@Configuration
public class InfoStaticHtmlConfig {


    @Value("${html.infor.appurl}")
    private String htmlInforUrl;

    /*分享资讯url*/
    @Value("${html.infor.detailurl}")
    private String detailInforUrl;

}
