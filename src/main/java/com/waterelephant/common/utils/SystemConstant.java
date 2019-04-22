package com.waterelephant.common.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统常量
 * 
 * @author Hui Wang
 */
public class SystemConstant {

	// 超级管理员
	public static final String SUPER_ADMIN = "superadmin";
	// session用户属性名
	public static final String SESSION_USER = "sysuser";
	// session用户所属部门
	public static final String SESSION_DEPT = "dept";
	// session用户所属公司
	public static final String SESSION_ORG = "org";
	// session用户所属角色
	public static final String SESSION_ROLE = "role";
	// session验证码
	public static final String SESSION_VC = "vc";
	// 用户初始密码
	public static final String INIT_PASSWORD = "123456";
	// yyyy-MM-dd HH:mm:ss
	public static final String YMD_HMS = "yyyy-MM-dd HH:mm:ss";
	// yyyy-MM-dd
	public static final String YMD = "yyyy-MM-dd";
	// 手机验证码
	public static final String SESSION_PHONE_CODE = "phoneCode";
	// 手机验证码
	public static final String SESSION_EMAIL_CODE = "emailCode";
	// APP接口token
	public static final Map<String, String> SESSION_APP_TOKEN = new HashMap<String, String>();
	// App接口session用户属性名
	public static final String SESSION_APP_USER = "appuser";
	// app手机验证码
	public static final String SESSION_APP_PHONE_CODE = "appPhoneCode";
	// 缓存
	public static final String COOKIE_PHONE = "phoneCookie";

	// 防止重复提交的code
	public static final String SESSION_TOKEN = "";
	// 防止重复提交的APPcode
	public static final String SESSION_APPTOKEN = "";

	public static final String SESSION_LOGIN_VC = "loginvc";

	// 债权转让利率
	public static double DEBT_RATE = 0.0;
	// 债权转让期限
	public static int DEBT_TERM = 0;

	public static int DEBT_DAYS = 0;

	// 回调URL
	public static String CALLBACK_URL = "";
	// 富友生产商户代码
	public static String FUIOU_MCHNT_CD = "";
	// 平台商户登录账号
	public static String FUIOU_MCHNT_NAME = "";
	// 平台风险备用金账户
	public static String FUIOU_MCHNT_BACKUP = "";

	public static String JZH_RSP_ADDR = "";
	// 图片路径
	public static String IMG_PATH = "";

	// 超过免费提现次数的固定手续费
	public static double WITHDRAW_FIXED_MONEY = 0.0;
	// 超过免费提现次数的利率
	public static double WITHDRAW_RATE = 0.0;

	// 移动端标识 ios_2 安卓_3
	public static String SESSION_APPTAB = "1";

	// 还款风险备用金时间节点
	public static int REPAY_TIME = 0;
	// 短信的环境
	public static int MESSAGE_CIRCUMSTANCE = 0;

	// 微信接口session名微信接口session名
	public static final String SESSION_WEIXIN_USER = "weixinuser";

	/* 日志操作状态 */
	public final static String SUCCESSFUL_OPERATION = "操作成功";
	public final static String OPERATION_FAILURE = "操作失败:";

	/**
	 * 阿里云ACCESS_ID
	 */
	public static String ACCESSKEY_ID = "";
	/**
	 * 阿里云ACCESS_KEY
	 */
	public static String ACCESS_KEY_SECRET = "";
	/**
	 * 阿里云OSS_ENDPOINT 终端地址
	 */
	public static String ENDPOINT = "";

	/**
	 * 阿里云BUCKET_NAME OSS
	 */
	public static String BUCKET_NAME = "";
	/**
	 * 阿里云图片访问前路径
	 */
	public static String IMG_URL = "";

	/*********************** redis pool ************************/
	// redis服务ip
	public static String REDIS_IP = "192.168.0.200";

	public static int REDIS_PORT = 6379;

	public static String REDIS_PASSWORD = "";

	// redis最大的等待时间
	public static int REDIS_MAXWAIT = 0;

	// pool最大空闲数
	public static int REDIS_MAXIDLE = 0;

	// pool最大连接数
	public static int REDIS_MAXACTIVE = 0;

	// 连接超时时间
	public static int REDIS_TIMEOUT = 3000;

	// 免罚息天数
	public static int AVOID_FINE_DATE = 3;

	/************************************* 国政通 *******************************************/
	// 国政通身份验证结果
	public static final String GBOSS_MSG_OK = "一致";
	public static final String GBOSS_MSG_NO = "不一致";

	// 前海征信查询原因
	// 01--贷款审批
	// 02--贷中管理
	// 03—贷后管理
	// 04--本人查询
	// 05--异议查询
	public static final String QHZX_REASONCODE_DQ = "01";
	public static final String QHZX_REASONCODE_DZ = "02";
	public static final String QHZX_REASONCODE_DH = "03";
	public static final String QHZX_REASONCODE_BR = "04";
	public static final String QHZX_REASONCODE_YY = "05";

	// 环境 0 正式环境 1 测试环境
	public static String INSURE_CIRCUMSTANCE = "1";

	// 环境 0 正式环境 1 测试环境 主要用于控制亿美token测试环境不能取token
	public static String MAKESURE_YIMEITOKEN = "1";

	/************************************ 合同 ****************************************/
	// 模板工作路径
	public static String FTL_BASE_PATH = "";
	// pdf转换工具路径
	public static String PDF_TOOL_PATH = "";
	// 签章x坐标
	public static int SEAL_X = 0;
	// 签章y坐标
	public static int SEAL_Y = 0;
	// 签章服务器地址
	public static String SEAL_SERVER_PATH = "";
	// 签章编码
	public static String SEAL_CODE = "";
	// 签章密码
	public static String SEAL_PASSWORD = "";
	// 咨询服务费比率
	public static double CONSULT_COST_RATIO = 0.0;
	// 信用管理费比率
	public static double CREDIT_MANAGE_COST_RATIO = 0.0;
	// 资金使用费比率
	public static double CAPITAL_USE_COST_RATIO = 0.0;

	// 理财端地址
	public static String BEADWALLET_URL = "";

	/************************************ app推送 ****************************************/
	public static final String APP_KEY = "e79ddf42c55d6bdfe3d4fde9";
	public static final String MASTER_SECRET = "428d62b277720a45c03c27a2";

	/*** ElasticSearch配置 ***/
	public static String ES_IP = null;
	public static int ES_PORT = 0;
	public static String ES_CLUSTER_NAME = null;

	public static String ES_INDEX = null;

	public static String YIMEI_TOKEN = "";// 亿美的token

	/**
	 * 续贷redis
	 */
	public static String XUDAI = "xudai:order_id";

	/**
	 * lianlianpay
	 */
	public static String YT_PUB_KEY = "";
	public static String NOTIFY_URL = "";
	public static String NOTIFY_LIANLIAN_PRE = "lianlian:";


	public static String ORDER_CHANNEL_URL = "";
}
