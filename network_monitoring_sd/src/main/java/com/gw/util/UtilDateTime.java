package com.gw.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 
 * @author  作者  lxy
 * @date    创建时间  2017年11月24日 下午4:32:43  
 * @description 日期计算
 */
public class UtilDateTime implements java.io.Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 4862014133333127900L;

	public static String DATE_TIME_PAT_19 = "yyyy/MM/dd HH:mm:ss";

	public static String DATE_PAT_10 = "yyyy/MM/dd";

	public static String DATE_TIME_FULL_PAT_14 = "yyyyMMddHHmmss";

	public static String DATE_TIME_FULL_PAT_17 = "yyyyMMddHHmmssSSS";
	public static String DATE_YEAR_MONTH_DAY = "yyMMdd";//年月日
	

	/**
	 * 年龄计算
	 * 
	 * @param baseDate 基本日期
	 * @param birthday 生日
	 * @return 年龄
	 */
	public static int calAge(Date baseDate, Date birthday) {
		if ((baseDate == null) || (birthday == null)) {
			return 0;
		}

		long longNow = Long.parseLong(UtilConv.date2Str(baseDate, "yyyyMMdd"));
		long longBirth = Long.parseLong(UtilConv.date2Str(birthday, "yyyyMMdd"));
		int age = (int) ((longNow - longBirth) / 10000);

		if (age < 0) {
			return 0;
		} else {
			return age;
		}
	}

	/**
	 * 年龄计算
	 * 
	 * @param baseDate 基本日期
	 * @param birthday 生日
	 * @param month 月
	 * @param day 日
	 * @return 年龄
	 */
	public static int calAge(Date baseDate, Date birthday, int month, int day) {
		if ((month < 0) || (12 < month)) {
			return 0;
		}

		if ((day < 0) || (31 < day)) {
			return 0;
		}

		long longNow = Long.parseLong(UtilConv.date2Str(baseDate, "yyyy")) * 10000 + month * 100 + day;
		long longBirth = Long.parseLong(UtilConv.date2Str(birthday, "yyyyMMdd"));
		int age = (int) ((longNow - longBirth) / 10000);

		if (age < 0) {
			return 0;
		} else {
			return age;
		}
	}

	/**
	 * 根据年龄算出出生日期的范围、[0]：开始日期、１：结束日期
	 * 
	 * @param baseDate 基本日期
	 * @param age 年龄
	 * @return 日期范围
	 */
	public static Date[] getBirthdayIntervalByAge(Date baseDate, String age) {
		int aAge = -1;

		try {
			aAge = Integer.parseInt(age);
		} catch (Exception e) {
			aAge = -1;
		}

		if (aAge < 0) {
			return null;
		} else {
			Date result[] = new Date[2];

			Calendar cal = Calendar.getInstance();
			cal.setTime(baseDate);
			cal.add(Calendar.YEAR, -aAge);
			result[1] = cal.getTime();
			cal.add(Calendar.YEAR, -1);
			cal.add(Calendar.DAY_OF_YEAR, 1);
			result[0] = cal.getTime();

			return result;
		}
	}

	/**
	 * 上一个月的最后一天
	 * 
	 * @param date 指定日期
	 * @return
	 */
	public static Date getFinalDayOfLastMonth(Date date) {
		if (date == null) {
			return null;
		}

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, -1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.roll(Calendar.DATE, -1);

		return calendar.getTime();
	}

	/**
	 * 上一个月的第一天
	 * 
	 * @param date 指定日期
	 * @return
	 */
	public static Date getFirstDayOfLastMonth(Date date) {
		if (date == null) {
			return null;
		}

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, -1);
		calendar.set(Calendar.DATE, 1);

		return calendar.getTime();
	}

	/**
	 * 获取当前系统日期
	 * 
	 * @return
	 */
	public static java.sql.Date getCurrentSqlDate() {
		Date date = new Date();

		return new java.sql.Date(date.getTime());
	}

	/**
	 * 当前时间 Timestamp
	 * 
	 * @return
	 */
	public static Timestamp getCurrentTimestamp() {
		return new Timestamp(System.currentTimeMillis());
	}

	/**
	 * 前一个月的取得
	 * 
	 * @param date 指定日期
	 */
	public static Date getMonthBefore(Date date) {
		if (date == null) {
			return null;
		}

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, -1);
		calendar.add(Calendar.DAY_OF_MONTH, 1);

		return calendar.getTime();
	}

	/**
	 * 前一年的取得
	 * 
	 * @param date 指定日期
	 */
	public static Date getYearBefore(Date date) {
		if (date == null) {
			return null;
		}

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.YEAR, -1);
		calendar.add(Calendar.DAY_OF_MONTH, 1);

		return calendar.getTime();
	}

	/**
	 * 日期前后比较
	 * 
	 * @param date1 日期1
	 * @param date2 日期2
	 * @return true: １:date1 < date2、０：相同、－１：date1 > date2
	 */
	public static int compare(Date date1, Date date2) {
		if ((date1 == null) || (date2 == null)) {
			return 0;
		}

		String strDate1 = UtilConv.date2Str(date1, UtilConv.DATE_YYYY_MM_DD_JP);
		String strDate2 = UtilConv.date2Str(date2, UtilConv.DATE_YYYY_MM_DD_JP);

		return strDate1.compareTo(strDate2);
	}

	/**
	 * 计算日期（添加）
	 * 
	 * @param baseDate 基本日期
	 * @param addYear 增减年数
	 * @param addMonth 增减月数
	 * @param addDay 增减天数
	 * @return 计算结果
	 */
	public static Date calcDate(Date baseDate, int addYear, int addMonth, int addDay) {
		if (baseDate == null) {
			return null;
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(baseDate);
		cal.add(Calendar.YEAR, addYear);
		cal.add(Calendar.MONTH, addMonth);
		cal.add(Calendar.DATE, addDay);
		return cal.getTime();
	}

	/**
	 * 计算时间（添加）
	 * 
	 * @param baseDate 原时间
	 * @param addVal 需添加的数量
	 * @param calendarType 添加的时间类型
	 * @return 添加完的时间
	 * @author NJT
	 * @date 2015年11月20日
	 * @modified NJT
	 */
	public static Date calcDatetime(Date baseDate, int addVal, int calendarType) {
		if (baseDate == null) {
			return null;
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(baseDate);
		cal.add(calendarType, addVal);

		return cal.getTime();
	}

	/**
	 * 获取当前时间
	 * 
	 * @param pPattern 时间格式
	 * @return
	 */
	public static String getNowStr(String pPattern) {
		if (Util.isEmpty(pPattern)) {
			pPattern = DATE_PAT_10;
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat();
		dateFormat.applyPattern(pPattern);
		return dateFormat.format(new Date());
	}

	/**
	 * 获取两个日期的差
	 * 
	 * @param date1
	 * @param date2
	 * @param timeUnit 可以是天、小时、分钟、秒、毫秒
	 * @return
	 * @return long
	 * @author LIUQI
	 * @date 2015年8月6日
	 * @version 1.0
	 */
	public static long getDateMinusVal(Date date1, Date date2, TimeUnit timeUnit) {
		double minusVal = 0;
		long result = 0L;
		if (date1.before(date2)) {
			minusVal = date2.getTime() - date1.getTime();
		} else {
			minusVal = date1.getTime() - date2.getTime();
		}

		if (TimeUnit.DAYS.equals(timeUnit)) {
			result = (long) Math.ceil(minusVal / (24 * 60 * 60 * 1000));
		} else if (TimeUnit.HOURS.equals(timeUnit)) {
			result = (long) Math.ceil(minusVal / (60 * 60 * 1000));
		} else if (TimeUnit.MINUTES.equals(timeUnit)) {
			result = (long) Math.ceil(minusVal / (60 * 1000));
		} else if (TimeUnit.SECONDS.equals(timeUnit)) {
			result = (long) Math.ceil(minusVal / 1000);
		} else if (TimeUnit.MILLISECONDS.equals(timeUnit)) {
			result = (long) minusVal;
		}

		return result;
	}

	public static void main(String[] args) {
		
		Date baseDate = new Date();
		System.out.println(baseDate);
		Date calcDatetime = UtilDateTime.calcDatetime(baseDate, 120, Calendar.MINUTE);
		System.out.println(calcDatetime);
	}
}
