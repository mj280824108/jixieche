package com.weiwei.jixieche.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
/**
 * @Author 钟焕 
 * @Description
 * @Date 13:53 2019-10-14
 **/
@Component
@Data
@Configuration
public class ApkDownloadUrl {
	@Value("${android.downloadUrlOwner}")
	private String downloadUrlOwner;

	@Value("${android.downloadUrlTenantry}")
	private String downloadUrlTenantry;

	@Value("${android.downloadUrlOwnerTest}")
	private String downloadUrlOwnerTest;

	@Value("${android.downloadUrlTenantryTest}")
	private String downloadUrlTenantryTest;

}
