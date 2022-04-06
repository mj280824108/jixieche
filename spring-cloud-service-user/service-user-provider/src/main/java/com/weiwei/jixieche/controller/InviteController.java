package com.weiwei.jixieche.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ClassName 类名
 * @Description html5页面
 * @Author houji
 * @Date 2019/8/13 21:32
 * @Version 2.0
 **/
@Controller
@RequestMapping("/staticHtml")
public class InviteController {

	@RequestMapping("/inviteOwner")
	public String inviteOwner() {
		return "owner";
	}

	@RequestMapping("/inviteOwnerV2")
	public String inviteOwnerV2() {
		return "ownerV2";
	}

	@RequestMapping("/inviteTenantry")
	public String inviteTenantry() {
		return "tenantry";
	}

	@RequestMapping("/inviteTenantryV2")
	public String inviteTenantryV2() {
		return "tenantryV2";
	}

	@RequestMapping("/rules")
	public String rule() {
		return "rules";
	}

	/**
	 * 计费规则
	 * 示例
	 * https://test.api.app.vvjx.cn/user/staticHtml/chargeRules?areaCode=420100&userType=1
	 * @return
	 */
	@RequestMapping("/chargeRules")
	public String chargeRules() {
		return "chargeRules";
	}

	/**
	 * 分享
	 * @return
	 */
	@RequestMapping("/share")
	public String share() {
		return "share";
	}

	/**
	 * 司机须知
	 * @return
	 */
	@RequestMapping("/driverKnow")
	public String driverKnow() {
		return "driverReading";
	}

	/**
	 * 分享资讯内容详情
	 * 资讯内容
	 * @return
	 */
	@RequestMapping("/information")
	public String information() {
		return "information";
	}

	/**
	 * app内h5页面
	 * @return
	 */
	@RequestMapping("/appInformation")
	public String appInformation() {
		return "AppInformation";
	}

	/**
	 * 交接班
	 */
	@RequestMapping("/takeTeam")
	public String takeTeam() {
		return "jiaoban";
	}

	/**
	 * 解绑
	 */
	@RequestMapping("/unBundling")
	public String unBundling() {
		return "jiebang";
	}

	/**
	 * 司机操作
	 */
	@RequestMapping("/driverHandle")
	public String driverHandle() {
		return "drivercaozuo";
	}

	/**
	 * 交易市场详情
	 * 必传参数  id , userType ,realeseType   三个参数
	 * id:发布市场机械id
	 * userType :  0：承租方  1机主
	 * realeseType: 1--机械求购 2--机械出售 3--机械求租 4--机械出租 5--资源求购 6--资源出售
	 *
	 * https://test.api.app.vvjx.cn/user/staticHtml/marketReleaseDetails?id=125&userType=1&realeseType=1
	 */
	@RequestMapping("/marketReleaseDetails")
	public String marketReleaseDetails() {
		return "machineDetail";
	}

	/**
	 * 店铺分享
     * id:店铺id   userType :  0：承租方  1机主
     * https://test.api.app.vvjx.cn/user/staticHtml/marketShopShare?id=37&userType=1
     * @return
	 */
	@RequestMapping("/marketShopShare")
	public String marketShopShare() {
		return "shopInfo";
	}

}
