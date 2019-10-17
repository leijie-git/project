package com.gw.util;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.binary.Base64;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * 
 * @author 作者 lxy
 * @date 创建时间 2017年11月24日 下午4:32:33
 * @description 类型转换
 */
public class UtilConv {

	public static final String DATE_YYYY_MM_DD_JP = "yyyy/MM/dd";

	public static final String DATE_YYYY_MM_DD_CHN = "yyyy-MM-dd";

	public static final String DATE_YYYY_MM_DD = "yyyy-MM-dd HH:mm";

	public static final String DATE_YYYY_MM_DD_SS = "yyyy-MM-dd HH:mm:ss";

	public static final String TIME_HOUR24_MI_SS = "HH:mm:ss";

	public static final String DATE_TIME_PAT_19 = "yyyy/MM/dd HH:mm:ss";

	public static final String DATE_PAT_10 = "yyyy/MM/dd";

	public static final String DATE_TIME_FULL = "yyyy年MM月dd日 HH时mm分";

	public static final String DATE_PAT_8 = "yyyyMMdd";

	public static final String DATE_PAT_11 = "yyyyMM";

	public static final String DATE_PAT_12 = "yyyy";

	public static final String DATE_PAT_13 = "MM";

	public static final String DATE_PAT_14 = "dd";

	public static final String DATE_PAT_15 = "d";

	public static final String DATE_PAT_KANJI = "yyyy年MM月dd日";

	public static final String DATE_SHT_PAT_8 = "yy/MM/dd";

	public static final String DATE_SHT_PAT_6 = "yyMMdd";

	public static final String DATE_SHT_PAT_KANJI = "yy年MM月dd日";

	public static final String DATE_MONTH_DAY = "MM月dd日";

	public static final String DATE_MONTH_DAY_ = "MM/dd";

	public static final String YEAR_MONTH = "yyyy/MM";
	
	public static final String YEAR_MONTHS = "yyyy年MM月";

	public static final String YEAR_MONTH_ = "yyyy-MM";

	public static final String DATE_TIME_FULL_WITH_SECOND = "yyyy年MM月dd日 HH時mm分ss秒";

	public static final String DATE_TIME_FULL_WITH_SECOND_ = "yyyy年MM月dd日 HH:mm:ss";

	public static final String DATE_TIME_FULL1_ = "MM月dd日 HH:mm";

	public static final String DATE_TIME_FULL2_ = "MM-dd HH:mm";

	public static final String DATE_TIME_FULL_PAT_24 = "yyyy/MM/dd HH:mm:ss SSS";

	public static final String DATE_TIME_FULL_PAT_24_ = "yyyy-MM-dd HH:mm:ss SSS";

	public static final String DATE_TIME_FULL_PAT_17 = "yyyyMMddHHmmssSSS";

	public static final String DATE_TIME_PAT_19_ = "yyyy-MM-dd HH:mm:ss";

	public static final String DATE_TIME_PAT_CALENDER = "yyyy/MM/dd HH:mm";

	public static final String DATE_TIME_PAT_14 = "yyyyMMddHHmmss";

	public static final String DATE_TIME_PAT_SHOW = "yyyy/MM/dd HH:mm";

	public static final String HOUR_TIME_PAT_5 = "HH:mm";

	public static final String HOUR_TIME_PAT_4 = "HHmm";

	public static final String NUMBER_PAT = "###############.#";

	private static final DecimalFormat numFormatterWithComma = new DecimalFormat("###,###,###,###,###");

	private static final DecimalFormat numFormatter = new DecimalFormat("###############");

	private static final DecimalFormat moneyFormat = new DecimalFormat("###############");

	public static final int DEFAULT_SHORT_STRING_LEN = 14;

	protected static final Integer INTEGER_ZERO = 0;

	protected static final String DB_NULL_STR = "null";

	protected static final String DB_NULL_NUMBER = Integer.toString(Integer.MIN_VALUE);

	private static double DEF_PI = 3.14159265359; // PI
	private static double DEF_2PI = 6.28318530712; // 2*PI
	private static double DEF_PI180 = 0.01745329252; // PI/180.0
	private static double DEF_R = 6370693.5; // radius of earth

	/**
	 * @param value
	 *            日期
	 * @param pattern
	 *            转变格式
	 * @return 变化的结果
	 */
	public static String date2Str(Date value, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);

		try {
			return sdf.format(value);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * String转换为Date类型
	 * 
	 * @param value
	 *            字符串
	 * @param pattern
	 *            转换格式
	 * @return 转换后的结果
	 */
	public static Date str2Date(String value, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);

		try {
			return sdf.parse(value);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Date类型
	 * 
	 * @param value
	 *            字符串
	 * @param pattern
	 *            转换格式
	 * @return 转换后的结果
	 */
	public static Date Dateformat(Date value, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);

		try {
			String index = sdf.format(value);
			Date date = sdf.parse(index);
			return date;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * @description :时间戳转换为时间
	 * @author yq 2017年9月22日 下午2:08:06
	 * @param value
	 * @return
	 */
	public static Date long2Date(Long value) {
		Date date = new Date(value);
		return date;
	}

	/**
	 * String转换为Timestamp类型
	 * 
	 * @param value
	 *            字符串
	 * @param pattern
	 *            转换格式
	 * @return 转换后的结果
	 */
	public static Timestamp str2Timestamp(String value, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);

		try {
			if (sdf.parse(value) != null) {
				return new Timestamp(sdf.parse(value).getTime());
			}
			return null;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * String转换为Date类型
	 * 
	 * @param value
	 *            字符串
	 * @param pattern
	 *            转换格式
	 * @return 转换后的结果
	 */
	public static java.sql.Date str2SqlDate(String value, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);

		try {
			return new java.sql.Date(sdf.parse(value).getTime());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * String转换为Integer
	 * 
	 * @param value
	 *            字符串
	 * @return 返回值（不正确的情况返回值为NULL）
	 */
	public static Integer str2Int(String value) {
		if (Util.isEmpty(value)) {
			return null;
		}

		try {
			return Integer.parseInt(value);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * String转换为Float
	 * 
	 * @param value
	 *            字符串
	 * @return 返回值（不正确的情况返回值为NULL）
	 */
	public static Float str2Float(String value) {
		if (Util.isEmpty(value)) {
			return null;
		}

		try {
			return Float.parseFloat(value);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * String转换为Long
	 * 
	 * @param value
	 *            输入值
	 * @return 返回值（不正确的情况返回值为NULL）
	 */
	public static Long str2Long(String value) {
		if (value == null) {
			return null;
		}
		try {
			return Long.parseLong(value);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 字符串转换为DOUBLE
	 * 
	 * @param value
	 *            文字列
	 * @return 转换后的文字（错误情况，返回值为NULL）
	 */
	public static Double str2Double(String value) {
		if (Util.isEmpty(value)) {
			return null;
		}

		try {
			return Double.parseDouble(value);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 数值文字的转换
	 * 
	 * @param value
	 *            输入的字符串
	 * @return 转换后的文字
	 */
	public static String numToStringWithComma(Object value) {
		if (value == null) {
			return null;
		}

		return numFormatterWithComma.format(value);
	}

	/**
	 * 数值文字的转换
	 * 
	 * @param value
	 *            输入的字符串
	 * @return 转换后的文字
	 */
	public static String numToString(Object value) {
		if (value == null) {
			return null;
		}
		return numFormatter.format(value);
	}

	/**
	 * 数值文字的转换
	 * 
	 * @param value
	 *            输入的字符串
	 * @return 转换后的文字
	 */
	public static String numToStrForMoney(Object value) {
		if (value == null) {
			return null;
		}
		return moneyFormat.format(value);
	}

	/**
	 * 数值文字的转换
	 * 
	 * @param value
	 *            输入的字符串
	 * @return 转换后的文字
	 */
	public static Number stringToNumWithComma(String value) {
		if (Util.isEmpty(value)) {
			return null;
		}

		try {
			return numFormatterWithComma.parse(value);
		} catch (Exception e) {
			return null;
		}

	}

	/**
	 * 将阿拉伯文字转成中文（一、二..十）
	 * 
	 * @param obj
	 *            输入的阿拉伯数字
	 * @return 转换后的中文一、二、三..十
	 */
	public static String albToZW(int obj) {
		String[] str1 = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" };
		String[] str2 = { "一", "二", "三", "四", "五", "六", "七", "八", "九", "十" };
		for (int i = 0; i < 10; i++) {
			if (obj == str2Int(str1[i])) {
				return str2[i];
			}
		}
		return null;
	}

	/**
	 * Object转换为String
	 * 
	 * @param value
	 *            输入值
	 * @return 返回值（不正确的场合，返回值为NULL）
	 */
	public static String objToStr(Object value) {
		if (value == null) {
			return null;
		} else {
			return value.toString();
		}
	}

	public static String numToStringNoZero(Object value) {
		if (value == null) {
			return null;
		} else if (value instanceof Integer) {
			Integer data = (Integer) value;
			if (data.intValue() == 0) {
				return null;
			}
		} else if (value instanceof Long) {
			Long data = (Long) value;
			if (data.longValue() == 0) {
				return null;
			}
		} else if (value instanceof Double) {
			Double data = (Double) value;
			if (data.doubleValue() == 0) {
				return null;
			}
		} else if (value instanceof Float) {
			Float data = (Float) value;
			if (data.floatValue() == 0) {
				return null;
			}
		}

		return value.toString();
	}

	/**
	 * Null字符串的转换
	 * 
	 * @param value
	 *            输入值
	 * @return 返回值
	 */
	public static String null2Empty(String value) {
		if (value == null) {
			return UtilConst.EMPTY_STRING;
		} else {
			return value;
		}
	}

	/**
	 * 字符串长度的取得
	 * 
	 * @param value
	 *            原字符串的长度
	 * @param length
	 *            指定的文字长度
	 * @return 截取后的结果
	 */
	public static String cutStr(String value, int len) {
		if (Util.isEmpty(value)) {
			return value;
		}
		if (value.length() <= len) {
			return value;
		}

		value = value.substring(0, len);
		return value;
	}

	/**
	 * 截取字符串(Byte)
	 * 
	 * @param value
	 *            原字符串
	 * @param length
	 *            指定byte长度
	 * @return 截取后的结果
	 */
	public static String cutStrByByte(String value, int length) {
		if ((value == null) || (length < 0)) {
			return value;
		}

		byte byteArr[] = value.getBytes();
		if (byteArr.length <= length) {
			return value;
		}

		String strNew = new String(byteArr, 0, length);
		if (value.indexOf(strNew) == 0) {
			return strNew;
		} else {
			return strNew.substring(0, strNew.length() - 1);
		}
	}

	/**
	 * Ajax输出字符串的转换
	 * 
	 * @param value
	 *            输入值
	 * @return 输出值
	 */
	public static String convForAjax(String value) {
		if (value == null) {
			return UtilConst.EMPTY_STRING;
		} else {
			return value.replaceAll("\\|", "||");
		}
	}

	public static final String STR_62 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

	/**
	 * 10进制到62进制的转换（0-9, A-Z,a-z)
	 * 
	 * @param value
	 *            输入十进制的数字
	 * @return 62进制的字符串
	 */
	public static String changeTo62(Long value) {
		if (value == null) {
			return null;
		}

		StringBuffer sb = new StringBuffer();

		long tmpValue = (value >= 0 ? value : -value);

		while (true) {
			if (tmpValue == 0) {
				break;
			}
			int aLeft = (int) (tmpValue % 62);

			sb.insert(0, STR_62.charAt(aLeft));
			tmpValue = (tmpValue - aLeft) / 62;
		}

		if (sb.length() == 0) {
			return "0";
		} else {
			if (value < 0) {
				sb.insert(0, "-");
			}
			return sb.toString();
		}
	}

	/**
	 * SQL语句生成时、NULL对象的处理（文字列）
	 * 
	 * @param value
	 *            输入值
	 * @return 返回结果值
	 */
	public static String convForSql(String value) {
		if (value == null) {
			return DB_NULL_STR;
		} else {
			value = value.trim();

			return value;
		}
	}

	public static String convForSqlHasQuot(String value) {
		if (value == null) {
			return DB_NULL_STR;
		} else {
			value = value.trim();

			if (value.indexOf("'") >= 0) {
				value = value.replace("'", "''");
			}
			return value;

		}
	}

	public static String convForSqlNoQuot(String value) {
		if (value == null) {
			return DB_NULL_STR;
		} else {
			value = value.trim();

			if (value.indexOf("'") >= 0) {
				value = value.replace("'", "''");
			}

			if (value.indexOf("\\") >= 0) {
				value = value.replace("\\", "\\\\");
			}

			if (value.indexOf("%") >= 0) {
				return value.replace("%", "\\%");
			} else {
				return value;
			}

		}
	}

	/**
	 * SQL语句生成时、NULL对象的处理（Long）
	 * 
	 * @param value
	 *            输入值
	 * @return 返回结果值
	 */
	public static String convForSql(Long value) {
		if (value == null) {
			return DB_NULL_NUMBER;
		} else {
			return value.toString();
		}
	}

	/**
	 * SQL语句生成时、NULL对象的处理（Double）
	 * 
	 * @param value
	 *            输入值
	 * @return 返回结果值
	 */
	public static String convForSql(Double value) {
		if (value == null) {
			return DB_NULL_NUMBER;
		} else {
			return value.toString();
		}
	}

	/**
	 * SQL语句生成时、NULL对象的处理（Integer）
	 * 
	 * @param value
	 *            输入值
	 * @return 返回结果值
	 */
	public static String convForSql(Integer value) {
		if (value == null) {
			return DB_NULL_NUMBER;
		} else {
			return value.toString();
		}
	}

	/**
	 * NULL对象的处理（Integer）
	 * 
	 * @param value
	 *            输入值
	 * @return 返回结果值
	 */
	public static Integer convForInteger(Integer value) {
		if (value == null) {
			return INTEGER_ZERO;
		} else {
			return value;
		}
	}

	/**
	 * SQL语句生成时、日期格式的转换(Timestamp)
	 * 
	 * @param value
	 *            输入值
	 * @return 返回的结果值
	 */
	public static String convForSql(java.sql.Timestamp value) {
		if (value == null) {
			return DB_NULL_STR;
		} else {
			String strValue = UtilConv.date2Str(new java.util.Date(value.getTime()), DATE_TIME_PAT_19);
			return "to_date('" + strValue + "', 'yyyy/mm/dd hh24:mi:ss')";
		}
	}

	/**
	 * SQL语句生成时、日期格式的转换
	 * 
	 * @param value
	 *            输入值
	 * @return 返回的结果值
	 */
	public static String convForSql(java.sql.Date value) {
		if (value == null) {
			return DB_NULL_STR;
		} else {
			String strValue = UtilConv.date2Str(new java.util.Date(value.getTime()), DATE_PAT_10);
			return "to_date('" + strValue + "', 'yyyy/mm/dd')";
		}
	}

	/**
	 * exception 字符串的转换
	 * 
	 * @param e
	 *            Exception
	 * @return 返回文字列
	 */
	public static String convException2Str(Exception e) {
		if (e == null) {
			return UtilConst.EMPTY_STRING;
		}

		try {
			StringWriter strWriter = new StringWriter();
			PrintWriter prnWriter = new PrintWriter(strWriter);
			e.printStackTrace(prnWriter);
			prnWriter.close();
			strWriter.close();
			return strWriter.toString();

		} catch (Exception e1) {
			return UtilConst.EMPTY_STRING;
		}
	}

	/**
	 * 特殊字符转换为URL
	 * 
	 * @param value
	 * @return
	 */
	public static String strEncode(String value) {
		String result = "";
		if (Util.isEmpty(value)) {
			return null;
		}

		result = value;

		result = result.replace("%3C", "<");
		result = result.replace("%3E", ">");
		result = result.replace("%26", "&");
		result = result.replace("%22", "\"");
		result = result.replace("%27", "\'");
		result = result.replace("%3F", "?");
		result = result.replace("%20", " ");
		result = result.replace("%23", "#");
		result = result.replace("%5C", "\\");
		result = result.replace("%5D", "+");
		result = result.replace("%25", "%");

		return result;
	}

	/**
	 * HTML字符串转换
	 * 
	 * @param value
	 *            输入的字符串
	 * @return
	 */
	public static String convForHtml(String value) {
		if (value == null) {
			return "";
		}
		String result = value.replaceAll("&", "&amp;");
		result = result.replaceAll("<", "&lt;");
		result = result.replaceAll(">", "&gt;");
		result = result.replaceAll("\"", "&quot;");

		return result;
	}

	/**
	 * HTML字符转换
	 */
	public static String reconvForHtml(String value) {
		if (value == null) {
			return "";
		}
		String result = value.replaceAll("&lt;", "<");
		result = result.replaceAll("&gt;", ">");
		result = result.replaceAll("&quot;", "\"");
		result = result.replaceAll("&amp;", "&");

		return result;
	}

	public static java.sql.Date timestampToDate(Timestamp p) {
		if (p == null) {
			return null;
		}
		return new java.sql.Date(p.getTime());
	}

	public static String timestamp2String(Timestamp stamp, String pattern) {
		if (stamp == null) {
			return UtilConst.EMPTY_STRING;
		}
		if ((Util.isEmpty(pattern))) {
			pattern = DATE_TIME_FULL;
		}
		Date date = new Date(stamp.getTime());
		String showStr = date2Str(date, pattern);
		if (showStr == null) {
			return UtilConst.EMPTY_STRING;
		}
		return showStr;
	}

	public static String numToStr(Object value, String pattern) {
		if (value == null || Util.isEmpty(pattern)) {
			return null;
		}

		DecimalFormat df = new DecimalFormat(pattern);

		return df.format(value);
	}

	public static String dateToDay(String value, int day, String pattern) {
		if (Util.isEmpty(value)) {
			return null;
		}
		if ((Util.isEmpty(pattern))) {
			pattern = DATE_TIME_FULL;
		}

		Date date = str2Date(value, pattern);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, day);
		date = calendar.getTime();

		return date2Str(date, pattern);
	}

	/**
	 * 将驼峰式命名转换为下划线型
	 * 
	 * @param str
	 * @return
	 * @author L
	 * @date 2016年1月26日
	 * @modified L
	 */
	public static String convCamel2Underline(String str) {
		if (Util.isEmpty(str)) {
			return "";
		}

		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < str.length(); i++) {
			String iStr = str.substring(i, i + 1);
			if (iStr.toUpperCase().equals(iStr)) {
				sb.append("_");
				sb.append(iStr.toLowerCase());
			} else {
				sb.append(iStr);
			}
		}

		return sb.toString();
	}

	/**
	 * 将下划线型命名改为驼峰型
	 * 
	 * @param str
	 * @return
	 * @author L
	 * @date 2016年1月26日
	 * @modified L
	 */
	public static String convUnderline2Camel(String str) {
		if (Util.isEmpty(str)) {
			return "";
		}

		String[] strs = str.split("_");

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < strs.length; i++) {
			String iStr = strs[i];
			if (i == 0) {
				sb.append(iStr.toLowerCase());
			} else {
				sb.append(iStr.substring(0, 1).toUpperCase());
				sb.append(iStr.substring(1).toLowerCase());
			}
		}
		return sb.toString();
	}

	/**
	 * 获取当前网络ip
	 * 
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ipAddress = request.getHeader("x-forwarded-for");
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getRemoteAddr();
			if (ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")) {
				// 根据网卡取本机配置的IP
				InetAddress inet = null;
				try {
					inet = InetAddress.getLocalHost();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
				ipAddress = inet.getHostAddress();
			}
		}
		// 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
		if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length() = 15
			if (ipAddress.indexOf(",") > 0) {
				ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
			}
		}
		System.out.println(ipAddress);
		return ipAddress;
	}

	/**
	 * 根据IP地址获取详细的地域信息 淘宝API :
	 * http://ip.taobao.com/service/getIpInfo.php?ip=218.192.3.42 新浪API :
	 * http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=json&ip=218.192.3.42
	 */
	@SuppressWarnings("unchecked")
	public static String getIpInfoByTaobao(String ip) {
		if (Util.isEmpty(ip)) {
			return null;
		}
		String url = String.format(ReqApiConst.GET_TAOBAO_GETIPLOCAL_URL,ip);
		HttpJson httpGet = HttpClientUtil.httpGet(url);
		if (!httpGet.isSuccess()) {
			return "";
		}
		String msg = httpGet.getMsg();
		if (Util.isEmpty(msg)) {
			return "";
		}
		Map<String, Object> map = (Map<String, Object>) JSONObject.parse(msg);
		JSONObject data = (JSONObject) map.get("data");
		if (Util.isEmpty(data)) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		// sb.append(data.get("country"));//国家
		// Object area = data.get("area");
		// if (!Util.isEmpty(area)) {
		// sb.append(area).append("(区)");
		// }
		Object region = data.get("region");
		if (!Util.isEmpty(region)) {
			sb.append(region).append(" ");
		}
		Object city = data.get("city");
		if (!Util.isEmpty(city)) {
			sb.append(city).append(" ");
		}
		Object county = data.get("county");
		if (!Util.isEmpty(county)) {
			sb.append(county).append(" ");
		}
		Object isp = data.get("isp");
		if (!Util.isEmpty(isp)) {// 网络
			sb.append(isp);
		}
		return sb.toString();
	}

	/**
	 * 百度获取城市信息
	 * 
	 * @param ip
	 * @return
	 * @throws JSONException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public static String baiduGetCityCode(String ip) throws Exception {

		// 百度ak，自己去申请就可以
		String ak = "uAVQruFnlAevcIBVA89lt02GH5kLkUXd";
		// 这里调用百度的ip定位api服务 详见 http://api.map.baidu.com/lbsapi/cloud/ip-location-api.htm
		HttpJson httpGet = HttpClientUtil.httpGet(String.format(ReqApiConst.GET_BAIDU_GETIPLOCAL_URL,ip,ak));
		if (!httpGet.isSuccess()) {
			return "";
		}
		String msg = httpGet.getMsg();
		if (Util.isEmpty(msg)) {
			return "";
		}
		Map<String, Object> map = (Map<String, Object>) JSONObject.parse(msg);
		JSONObject data = (JSONObject) map.get("content");
		if (Util.isEmpty(data)) {
			return "";
		}
		String address = (String) data.get("address");
		return address;
	}

	/**
	 * 通过经纬度获取距离(单位：米)
	 * 
	 * @param lat1
	 * @param lng1
	 * @param lat2
	 * @param lng2
	 * @return
	 */
	public static double getDistance(double lon1, double lat1, double lon2, double lat2) {
		double ew1, ns1, ew2, ns2;
		double dx, dy, dew;
		double distance;
		// 角度转换为弧度
		ew1 = lon1 * DEF_PI180;
		ns1 = lat1 * DEF_PI180;
		ew2 = lon2 * DEF_PI180;
		ns2 = lat2 * DEF_PI180;
		// 经度差
		dew = ew1 - ew2;
		// 若跨东经和西经180 度，进行调整
		if (dew > DEF_PI)
			dew = DEF_2PI - dew;
		else if (dew < -DEF_PI)
			dew = DEF_2PI + dew;
		dx = DEF_R * Math.cos(ns1) * dew; // 东西方向长度(在纬度圈上的投影长度)
		dy = DEF_R * (ns1 - ns2); // 南北方向长度(在经度圈上的投影长度)
		// 勾股定理求斜边长
		distance = Math.sqrt(dx * dx + dy * dy);
		return distance;

	}

	public static String base64ToPicFile(String imgStr, String mImagesPath) {

		if (Util.isEmpty(imgStr)) {
			return "";
		}
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd/HH");
		/** 构建图片保存的目录 **/
		String picPathDir = "pictures/" + dateformat.format(new Date());
		// UUID
		String uuid = UUID.randomUUID().toString().replace("-", "");
		/** 得到图片保存目录的真实路径.tomcat下webapp路径 **/
		String picRealPathDir = mImagesPath + picPathDir + "/" + uuid + "/";
		/** 根据真实路径创建目录 **/
		File picSaveFile = new File(picRealPathDir);
		if (!picSaveFile.exists())
			picSaveFile.mkdirs();

		/** 使用UUID生成文件名称 **/
		String saveFileName = new Date().getTime() + ".jpg";// 构建文件名称
		// String saveFileName = picName;
		/** 拼成完整的文件保存路径加文件 **/
		String fileName = picRealPathDir + saveFileName;
		// File file = new File(fileName);
		String picDirPath = "/file/upload/" + picPathDir + "/" + uuid + "/";

		byte[] b = decode(imgStr);
		for (int i = 0; i < b.length; ++i) {
			if (b[i] < 0) {// 调整异常数据
				b[i] += 256;
			}
		}
		try {
			// Base64解码

			OutputStream out = new FileOutputStream(fileName);
			out.write(b);
			out.flush();
			out.close();

			return picDirPath + saveFileName;
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * BASE64字符串转二进制
	 * 
	 * @param bytes
	 * @return
	 */
	public static byte[] decode(final byte[] bytes) {
		return Base64.decodeBase64(bytes);
	}

	/**
	 * BASE64字符串转二进制
	 * 
	 * @param bytes
	 * @return
	 */
	public static byte[] decode(String base64String) {
		return Base64.decodeBase64(base64String);
	}

	/**
	 * 二进制数据编码为BASE64字符串
	 *
	 * @param bytes
	 * @return
	 * @throws Exception
	 */
	public static String encode(final byte[] bytes) {
		return new String(Base64.encodeBase64(bytes));
	}

	/**
	 * 科学计数法转字符串
	 * 
	 * @param data
	 * @return
	 */
	public static String scientificToString(String data) {
		if (Util.isEmpty(data)) {
			return "";
		}
		BigDecimal bd = new BigDecimal(data);
		return bd.toPlainString();
	}
}
