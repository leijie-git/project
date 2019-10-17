package com.gw.util;

/**
 * 
 * @author 作者 lxy
 * @date 创建时间 2017年11月24日 下午4:32:26
 * @description 常数定义
 */
public class UtilConst {
	// 空字符串
	public static final String EMPTY_STRING = "";

	/** -----------状态码start----------- **/
	// 状态码
	public static final Integer CODE_200 = 200;

	// 状态码-失效
	public static final Integer ERROR_CODE_400 = 400;

	// 状态码-账户已失效
	public static final Integer ERROR_CODE_401 = 401;

	// 状态码-错误
	public static final Integer ERROR_CODE_500 = 500;
	/** -----------状态码end----------- **/
	
	// 创建时的默认密码
	public static final String DEFAULT_PASS_WORD = "123456";
	
	//异常队列需要的destination
	public static final String JMS_QUEUE_EXCEPTION = "execption";
	
	//日志队列需要的destination
	public static final String JMS_QUEUE_LOG = "log";
	
	//第三方接口返回存放消息队列
	public static final String JMS_QUEUE_HTTP_RETURN = "httpreturn";
	
	/**状态是*/
	public static final String STATUS_ENABLE = "1";
	/**状态否*/
	public static final String STATUS_DISABLE = "0";

	// session的key
	public static final String USER_SESSION = "userSession";
	// 前台session的key
	public static final String FRONT_USER_SESSION = "frontUserSession";
	// 前台session的key
	public static final String WECHAT_USER_SESSION = "wechatUserSession";

	//默认排序
    public static final Integer DEFAULT_SEQ = 99;

	public static final String STATUS_TRUE = "true";// 是

	public static final String STATUS_FALSE = "false";// 否
	
	// 顶级树
	public static final String TREE_ROOT = "0";

	/* -----------资源类型----------- */
	public static final String TYPE_MODULE = "1";
	/* -----------角色状态----------- */
	public static final String ROLE_STATUS_OFF = "0";
	public static final String ROLE_STATUS_OFF_CN = "无效";

	public static final String ROLE_STATUS_ON = "1";
	public static final String ROLE_STATUS_ON_CN = "有效";

	public static final String ERRO_SYSTEM = "系统异常,请联系技术人员处理.";
	public static final String ERRO_KEYPARAM_NULL = "关键查询数据为空.";
	public static final String ERRO_ACCOUNT_PASSWORD = "用户名或密码不正确。";
	public static final String ERRO_ACCOUNT_UNIT = "用户无关联单位。";
	public static final String ERRO_ALARM_NULL = "告警关键数据为空。";
	public static final String ERRO_LOOKUP_NULL = "查岗关键数据为空。";
	public static final String ERRO_LOOKUP_LOG_NULL = "无查岗记录。";
	public static final String ERRO_NETDEVICE_UPDATE = "无查岗记录。";

	//存在缓存中的key
	public static final String UT_HD_SITERWELL = "utHdSiterwell";
}
