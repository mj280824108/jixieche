package com.weiwei.jixieche.lister;

import com.weiwei.jixieche.bean.JxcArea;
import com.weiwei.jixieche.bean.JxcShortMsgTemplate;
import com.weiwei.jixieche.service.JxcAreaService;
import com.weiwei.jixieche.service.JxcPushTemplateService;
import com.weiwei.jixieche.service.JxcShortMsgTemplateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @ClassName JxcAreaApplicationRunner
 * @Description 初始化省市区集合
 * @Author houji
 * @Date 2019/5/8 18:00
 * @Version 1.0.1
 **/
@Component
@Order(value=1)
public class JxcAreaApplicationRunner implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(JxcAreaApplicationRunner.class);

    @Autowired
    private JxcPushTemplateService jxcPushTemplateService;

    @Autowired
    private JxcAreaService jxcAreaService;

    @Autowired
    private JxcShortMsgTemplateService jxcShortMsgTemplateService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("==初始化(jpush配置参数)开始==");
        //jxcKvOptionService.initKvOptionDatas();
        logger.info("==初始化(jpush配置参数)结束==");

        logger.info("==初始化(省市区)数据开始==");
        jxcAreaService.initAreaDatas();
        logger.info("==初始化(省市区)结束==");

        logger.info("==初始化(短信模板)开始==");
       /* jxcShortMsgTemplateService.initShortMsgTemplateDatas();*/
        logger.info("==初始化(短信模板)结束==");

        logger.info("==初始化推送模板开始==");
        jxcPushTemplateService.initPushTemplateData();
        logger.info("==初始化推送模板结束==");
    }
}
