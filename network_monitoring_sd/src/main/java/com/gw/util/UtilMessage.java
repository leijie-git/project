package com.gw.util;

/**
 * 
 * @author 作者 lxy
 * @date 创建时间 2017年6月7日 下午1：49：39
 * @description 异常定义
 */
public class UtilMessage {
	public static final String SERVICE_ERROR = "提示：服务器出现了一些小状况，正在修复中！";

	public static final String FRONT_APP_ERROR = "服务器出现了状况，请联系管理员！";

	public static final String HTTP_CLIENT_UNRESPONSIVE = "提示：请求无响应！";

	public static final String HTTP_CLIENT_ERROR = "提示：请求出错！";

	public static final String LOGIN_ACCOUNT_ERROR = "账户不存在！";

	public static final String LOGIN_STATUS_ERROR = "账户已失效！";

	public static final String LOGIN_PASSWORD_ERROR = "账户密码不匹配！";

	public static final String RESOURCE_IN_USE = "该权限资源有角色占用，不能删除！";

	public static final String RESOURCE_CHILD_EXISTS_ERR = "该权限资源下存在子节点，不能删除！";

	public static final String ROLE_EXISTS_ERR = "该名称已存在！";

	public static final String ROLE_EMP_EXISTS_ERR = "此角色下存在用户！";
	// 用户登录
	public static final String LOGIN_SUCCESS = "登录成功！";

	public static final String LOGIN_ERROR = "登录失败！";

	public static final String LOGIN_ERROR1 = "账户不存在！";

	public static final String LOGIN_ERROR2 = "账户已失效！";

	public static final String LOGIN_ERROR3 = "账户密码不匹配！";

	public static final String LOGIN_ERROR4 = "账户未注册！";

	public static final String LOGIN_ERROR5 = "您已经注册过,请登陆！";

	public static final String LOGIN_ERROR6 = "员工号与手机号不匹配！";

	public static final String LOGIN_ERROR7 = "账号不能为空！";

	public static final String LOGIN_ERROR8 = "密码不能为空！";

	public static final String LOGIN_ERROR9 = "手机号已存在！";

	public static final String LOGIN_ERROR10 = "此手机号未绑定员工！";

	public static final String UPDATE_ERROR ="更新失败,请重试！";
	// 用户注销
	public static final String LOGOUT_SUCCESS = "注销成功！";

	public static final String LOGOUT_ERROR = "注销失败！";
	
	public static final String ADD_ERROR = "添加用户失败！";
	// 用户修改密码
	public static final String UPDATE_PWD_WRONG2 = "新密码不能为空！";

	public static final String UPDATE_PWD_WRONG3 = "原密码不能为空！";

	public static final String UPDATE_PWD_WRONG4 = "新密码至少6位,请修改！";

	public static final String UPDATE_PWD_WRONG5 = "新密码最多16位,请修改！";

	public static final String UPDATE_PWD_SUCCESS = "修改密码成功！";

	public static final String UPDATE_PWD_ERROR = "修改密码失败！";

	public static final String UPDATE_PWD_WRONG = "原密码错误！";

	public static final String MODIFY_DATA_FAILED = "修改失败!请刷新后重试!";

	public static final String RESET_PASSWORD_SUCCESS = "密码重置成功！";

	public static final String RESET_PASSWORD_ERROR = "密码重置失败！";

	// 新增用户判断是否异常
	public static final String USER_MESSAGE_ERROR = "已存在此账号！";

	// 通用的保存成功提示
	public static final String SAVE_MESSAGE_SUCCESS = "保存成功！";

	public static final String SAVE_MESSAGE_ERROR = "保存失败！";

	public static final String SITE_NAME_ERROR = "分类名称已存在！";

	public static final String SITE_CODE_ERROR = "位置编号已存在！";

	// 通用的删除成功提示
	public static final String DEL_MESSAGE_SUCCESS = "删除成功！";

	public static final String DEL_MESSAGE_ERROR = "删除失败！";

	/**
	 * 操作失败，请重试
	 */
	public static final String ERROR = "操作失败，请重试";

	public static final String GET_MSG_ERROR = "获取数据失败！";

	public static final String GET_MSG_ERROR_WX = "页面出现错误！";

	public static final String GET_MSG_SUCCESS = "获取数据成功！";

	/*--------------接口调用的信息----------------------*/
	public static final String LOGIN_ERROR_FRONT = "登录失败，用户名或密码错误!";

	public static final String NO_PERMISSION = "没有该资源访问权限!";

	// 修改数据
	public static final String MODIFY_DATA_SUCCESS = "修改成功!";

	public static final String REQUEST_DATA_FAILED = "请求数据出错,请刷新后重试!";

	// 通用的授权成功提示
	public static final String AUTH_MESSAGE_SUCCESS = "授权成功！";

	public static final String AUTH_MESSAGE_ERROR = "授权失败！";

	public static final String SET_PID_ERROR = "父节点不能设置为自己!";

	public static final String NAME_EXIST = "该名称已存在! 请修改!";

	/**
	 * 数据字典key值校验
	 */
	public static final String DICT_EXIST_KEY = "同一种类型下，key值不能重复。";

	// 请求参数
	public static final String REQUEST_PARAM_EMPTY = "请求参数不能为空!";
	
	//用户对应角色
	public static final String ROLE_SET_ERROR = "角色设置失败！";

	public static final String YSY_TOKEN_ERROR = "萤石云token获取失败，请检查相关密钥配置";

	public static final String NFC_INVALID = "NFC已被使用";

	//转单
	public static final String IS_CHANGE_ERROR = "转单失败";
}
