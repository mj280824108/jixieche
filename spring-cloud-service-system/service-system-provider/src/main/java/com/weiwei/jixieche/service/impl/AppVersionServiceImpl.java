package com.weiwei.jixieche.service.impl;

import com.weiwei.jixieche.bean.JxcAndroidAppVersion;
import com.weiwei.jixieche.bean.JxcIosAppVersion;
import com.weiwei.jixieche.config.ApkDownloadUrl;
import com.weiwei.jixieche.constant.ErrorCodeConstants;
import com.weiwei.jixieche.mapper.JxcAndroidAppVersionMapper;
import com.weiwei.jixieche.mapper.JxcIosAppVersionMapper;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.service.AppVersionService;
import com.weiwei.jixieche.verify.VerifyStr;
import com.weiwei.jixieche.vo.AppVersionFormVo;
import com.weiwei.jixieche.vo.AppVersionResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author 钟焕 
 * @Description
 * @Date 14:12 2019-10-14
 **/
@Service("appVersionService")
public class AppVersionServiceImpl implements AppVersionService {

	@Autowired
	private JxcIosAppVersionMapper iosVersionMapper;

	@Autowired
	private JxcAndroidAppVersionMapper androidVersionMapper;

	@Autowired
	private ApkDownloadUrl apkDownloadUrl;


	@Override
	public ResponseMessage checkIosVersion(AppVersionFormVo formVo) {
		String version = formVo.getVersion();
		int clientType = formVo.getAppClient();
		int isTest = formVo.getIsTest();

		if (VerifyStr.isEmpty(version)) {
			return new ResponseMessage<>(ErrorCodeConstants.PARAM_EMPTY.getId(), "版本号不能为空");
		}

		if (!versionReg(version)) {
			return new ResponseMessage<>(ErrorCodeConstants.PARAM_FORMAT_ERROR.getId(), "版本号格式不正确");
		}

		if (clientType != 1 && clientType != 2) {
			return new ResponseMessage<>(ErrorCodeConstants.PARAM_EMPTY.getId(), "客户端版本错误");
		}

		if (isTest != 0 && isTest != 1) {
			return new ResponseMessage<>(ErrorCodeConstants.PARAM_FORMAT_ERROR.getId(), "是否测试渠道格式不正确");
		}

		AppVersionResponseVo response = new AppVersionResponseVo();
		// 是否需要更新。0：不需要，1：可选，2：强制
		response.setNeedUpdate(0);

		JxcIosAppVersion latestVersion = iosVersionMapper.selectLatest(version, clientType, isTest);

		// 查不到版本，不更新
		if (latestVersion == null) {
			return new ResponseMessage<>(response);
		}

		// 客户端版本与最新版本一致，不更新
		if (version.equals(latestVersion.getVersion())) {
			return new ResponseMessage<>(response);
		}

		// 如果服务器当前版本要求所有用户强制更新
		Boolean isForce = latestVersion.getIsForce();
		if (isForce != null && isForce.booleanValue()) {
			response.setNeedUpdate(2);
			response.setIosDesc(latestVersion.getDescription().replace("\\n","\n"));
			return new ResponseMessage<>(response);
		}

		// 非强制更新
		JxcIosAppVersion currentVersion = iosVersionMapper.selectOne(version, clientType, isTest);
		// 此处为后端数据库误操作清除了某个历史版本作容错处理，进行可选更新
		if (currentVersion == null) {
			response.setNeedUpdate(1);
			response.setIosDesc(currentVersion.getDescription().replace("\\n","\n"));
			return new ResponseMessage<>(response);
		}

		// 当客户端传入版本号存在时，查找前端传入的版本号和服务器当前版本号之间是否存在强制更新版本
		Map<String, Object> params = initParamsOfCheckExistForceVersion(version, clientType, isTest,
				latestVersion.getVersion());
		int forceVersionCount = iosVersionMapper.countForceVersionBetween(params);
		if (forceVersionCount > 0) {
			response.setNeedUpdate(2);
			response.setIosDesc(latestVersion.getDescription().replace("\\n","\n"));
			return new ResponseMessage<>(response);
		}

		// 如属于非强制更新版本而且无有效历史强制更新版本的情况下
		response.setNeedUpdate(1);
		response.setIosDesc(latestVersion.getDescription().replace("\\n","\n"));
		return new ResponseMessage(response);
	}

	@Override
	public ResponseMessage checkAndroidVersion(AppVersionFormVo formVo) {
		String version = formVo.getVersion();
		int clientType = formVo.getAppClient();
		int isTest = formVo.getIsTest();

		if (VerifyStr.isEmpty(version)) {
			return new ResponseMessage<>(ErrorCodeConstants.PARAM_EMPTY.getId(), "版本号不能为空");
		}

		if (!versionReg(version)) {
			return new ResponseMessage<>(ErrorCodeConstants.PARAM_FORMAT_ERROR.getId(), "版本号格式不正确");
		}

		if (clientType != 1 && clientType != 2) {
			return new ResponseMessage<>(ErrorCodeConstants.PARAM_EMPTY.getId(), "客户端版本错误");
		}

		if (isTest != 0 && isTest != 1) {
			return new ResponseMessage<>(ErrorCodeConstants.PARAM_FORMAT_ERROR.getId(), "是否测试渠道格式不正确");
		}

		int v = Integer.parseInt(version);
		AppVersionResponseVo response = new AppVersionResponseVo();
		// 是否需要更新。0：不需要，1：可选，2：强制
		response.setNeedUpdate(0);

		JxcAndroidAppVersion latestVersion = androidVersionMapper.selectLatest(v, clientType, isTest);

		// 查不到版本，不更新
		if (latestVersion == null) {
			return new ResponseMessage<>(response);
		}

		// 将更新描述文字由字符串转变为list
		List<String> desc = null;
		String description = latestVersion.getDescription();
		if (!VerifyStr.isEmpty(description)) {
			desc = Arrays.asList(description.split(","));
		}

		// 客户端版本与最新版本一致，不更新
		if (v == latestVersion.getVersion()) {
			return new ResponseMessage<>(response);
		}

		// 如果服务器当前版本要求所有用户强制更新
		Boolean isForce = latestVersion.getIsForce();
		if (isForce != null && isForce.booleanValue()) {
			response.setNeedUpdate(2);
			response.setDownloadUrl(getDownloadUrl(clientType, isTest));
			response.setDesc(desc);
			response.setApkSize(latestVersion.getApkSize());
			return new ResponseMessage<>(response);
		}

		// 非强制更新
		JxcAndroidAppVersion currentVersion = androidVersionMapper.selectOne(v, clientType, isTest);
		List<String> desc2 = null;
		// 此处为后端数据库误操作清除了某个历史版本作容错处理，进行可选更新
		if (currentVersion == null) {
			response.setNeedUpdate(1);
			response.setDownloadUrl(getDownloadUrl(clientType, isTest));
			return new ResponseMessage<>(response);
		} else {
			String description2 = currentVersion.getDescription();
			if (!VerifyStr.isEmpty(description2)) {
				desc2 = Arrays.asList(description2.split(","));
			}
		}

		// 当客户端传入版本号存在时，查找前端传入的版本号和服务器当前版本号之间是否存在强制更新版本
		int forceVersionCount = androidVersionMapper.countForceVersionBetween(v, latestVersion.getVersion(),
				clientType, isTest);
		if (forceVersionCount > 0) {
			response.setNeedUpdate(2);
			response.setDownloadUrl(getDownloadUrl(clientType, isTest));
			response.setDesc(desc2);
			response.setApkSize(currentVersion.getApkSize());
			return new ResponseMessage<>(response);
		}

		// 如属于非强制更新版本而且无有效历史强制更新版本的情况下
		response.setNeedUpdate(1);
		response.setDownloadUrl(getDownloadUrl(clientType, isTest));
		response.setDesc(desc2);
		response.setApkSize(currentVersion.getApkSize());
		return new ResponseMessage<>(response);
	}
	
	/**
	 * 检查两个版本之前是否存在强制更新的参数初始化
	 *
	 * @param version
	 * @param appClient
	 * @param isTest
	 * @param latestVersion
	 * @return
	 */
	private Map<String, Object> initParamsOfCheckExistForceVersion(String version, int appClient, int isTest,
																   String latestVersion) {
		Map<String, Object> params = new HashMap<>();
		params.put("frontVersion", version);
		params.put("appClient", appClient);
		params.put("isTest", isTest);

		String[] arr_v_front = version.split("\\.");
		params.put("frontV1", arr_v_front[0]);
		params.put("frontV2", arr_v_front[1]);
		params.put("frontV3", arr_v_front[2]);

		String[] arr_v_latest = latestVersion.split("\\.");
		params.put("latestV1", arr_v_latest[0]);
		params.put("latestV2", arr_v_latest[1]);
		params.put("latestV3", arr_v_latest[2]);

		return params;
	}

	private boolean versionReg(String version) {
		return version.matches("\\d+.\\d+.\\d+");
	}

	private String getDownloadUrl(int appClient, int isTest) {
		if (appClient == 1 && isTest == 1) {
			return apkDownloadUrl.getDownloadUrlOwnerTest();
		}
		if (appClient == 1 && isTest == 0) {
			return apkDownloadUrl.getDownloadUrlOwner();
		}
		if (appClient == 2 && isTest == 1) {
			return apkDownloadUrl.getDownloadUrlTenantryTest();
		}
		if (appClient == 2 && isTest == 0) {
			return apkDownloadUrl.getDownloadUrlTenantry();
		}

		return null;
	}

}
