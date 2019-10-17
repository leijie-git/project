package com.gw.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.gw.common.Json;


/**
 * 
 * @author 作者 lxy
 * @date 创建时间 2017年11月24日 下午4:32:19
 * @description 共通方法的定义
 */
public class Util {
	/**
	 * 空检查
	 */
	public static boolean isEmpty(Object value) {
		if (value == null) {
			return true;
		} else {
			if (value instanceof String) {
				String strTmp = (String) value;
				if (strTmp.trim().length() == 0) {
					return true;
				} else {
					return false;
				}
			} else if (value instanceof Collection) {
				Collection<?> datalist = (Collection<?>) value;
				if (datalist.size() == 0) {
					return true;
				} else {
					return false;
				}
			} else if (value instanceof Map) {
				Map<?, ?> datalist = (Map<?, ?>) value;
				if (datalist.size() == 0) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		}
	}
	/**
	 * 字符串转日期
	 * @param str
	 * @return
	 */
	public static Date StringToDate(String str) {
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        //SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //加上时间
        Date date = new Date();
        //必须捕获异常
        try {
            date=simpleDateFormat.parse(str);
        } catch(Exception px) {
            px.printStackTrace();
        }
        return  date;
	}
	/**
	 * 字符串转日期
	 * @param str
	 * @return
	 */
	public static Date StringToDateTime(String str) {

		if (Util.isEmpty(str))
			return new Date();

		//SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //加上时间
        Date date = new Date();
        //必须捕获异常
        try {
            date=sDateFormat.parse(str);
        } catch(Exception px) {
            px.printStackTrace();
        }
        return  date;
	}
	/**
	 * 解析xml
	 * @param json
	 * @return
	 */
	public static InterfaceData analysisXML(Json json) {
		InterfaceData outData = new InterfaceData();
		try {
			SAXReader reader = new SAXReader();
			Document d = reader.read(new StringReader(json.getObj().toString()));
			Element rootElt = d.getRootElement(); // 获取根节点
            System.out.println("根节点：" + rootElt.getName()); // 拿到根节点的名称
            Element addressElement = rootElt.element("Code");
            outData.setCode(addressElement.getText());
            Element msgElement = rootElt.element("Msg");
            outData.setMsg(msgElement.getText());
		} catch (DocumentException e) {
			e.printStackTrace();
		} 
		
		return outData;
	}
	
	/**
	 * 根据开始结束时间拆分为年月map
	 * @param a
	 * @param b
	 * @return
	 */
	public static Map<Integer,List<Integer>> getMap(String a,String b){
		Date startDate = UtilConv.str2Date(a,"yyyy-mm-dd");
		Calendar cl = Calendar.getInstance();   
		cl.setTime(startDate);   
		int startYear = cl.get(Calendar.YEAR);
		int startMonth = cl.get(Calendar.MONTH);
		Date endDate = UtilConv.str2Date(b,"yyyy-mm-dd");
		cl.setTime(endDate);
		int endYear = cl.get(Calendar.YEAR);
		int endMonth = cl.get(Calendar.MONTH);
		Map<Integer,List<Integer>> map = new HashMap<Integer,List<Integer>>();
		if(startYear == endYear) {
			List<Integer> allMonth = new ArrayList<Integer>();
			for(int i = startMonth;i<=endMonth;i++) {
				allMonth.add(i);
			}
			map.put(startYear, allMonth);
		}else {
			for(int i = startYear;i<endYear;i++) {
				List<Integer> allMonth = new ArrayList<Integer>();
				for(int j = startMonth;j<=12;j++) {
					allMonth.add(j);
				}
				map.put(i, allMonth);
			}
			List<Integer> allMonth = new ArrayList<Integer>();
			for(int n = 1;n<=endMonth;n++) {
				allMonth.add(n);
			}
			map.put(endYear, allMonth);
		}
		return map;
	}
	
	/**
	 * 根据当前时间按天分组
	 * @param a
	 * @param b
	 * @return
	 * @throws ParseException
	 */
	public static List<String> getDay(String a, String b) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat dd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<String> list = new ArrayList<>();
		Date sd = df.parse(a);
		Date ed = df.parse(b);
		long days = (ed.getTime() - sd.getTime()) / 3600 / 24 / 1000;
		Calendar instance = Calendar.getInstance();
		instance.setTime(sd);
		instance.add(Calendar.DAY_OF_YEAR, 1);
		long endtimes = sd.getTime()+(3600*24*1000)-1000;
		Date endDate = new Date(endtimes);
		list.add(new String(dd.format(sd) + "~" + dd.format(endDate)));
		String ne;
		for (long k = 0; k < days-1; k++) {
			if(k!=0) {
				instance.add(Calendar.DAY_OF_YEAR, 1);
			}
			Date start = instance.getTime();
			Date end = instance.getTime();
			long endtime = end.getTime()+(3600*24*1000)-1000;
			Date endDates = new Date(endtime);
			ne = new String(dd.format(start) + "~" + dd.format(endDates));
			list.add(ne);
		}
		Calendar instances = Calendar.getInstance();
		instances.setTime(ed);
		long end = ed.getTime()+(3600*24*1000)-1000;
		Date endDatess = new Date(end);
		list.add(new String(dd.format(instances.getTime()) + "~" + dd.format(endDatess)));
		return list;
	}

	/**
	 * 根据当前时间按周分组
	 * @param a
	 * @param b
	 * @return
	 * @throws ParseException
	 */
	public static List<String> getWeek(String a, String b) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat dd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<String> list = new ArrayList<>();
		Date sd = df.parse(a);
		Date ed = df.parse(b);
		long days = (ed.getTime() - sd.getTime()) / 3600 / 24 / 1000;
		if(days<=7) {
			Calendar instancess = Calendar.getInstance();
			instancess.setTime(ed);
			instancess.add(Calendar.DAY_OF_YEAR, -1);
			long endtimes = ed.getTime()+(3600*24*1000)-1000;
			Date endDate = new Date(endtimes);
			list.add(dd.format(sd)+"/"+dd.format(instancess.getTime())+"~"+dd.format(endDate));
			return list;
		}
		Calendar instance = Calendar.getInstance();
		instance.setTime(sd);
		long d = days / 7;
		String ne;
		for (long k = 0; k < d; k++) {
			//list.add(new String(df.format(sd) + "~" + df.format(instance.getTime())));
			if(k!=0) {
				instance.add(Calendar.DAY_OF_YEAR, 1);
			}
			Date start = instance.getTime();
			instance.add(Calendar.DAY_OF_YEAR, 6);
			Date end = instance.getTime();
			long endtimes = end.getTime()+(3600*24*1000)-1000;
			Date endDate = new Date(endtimes);
			String epirationTime = dd.format(instance.getTime());
			ne = new String(dd.format(start) +"/"+epirationTime+ "~" + dd.format(endDate));
			list.add(ne);
		}
		Calendar instances = Calendar.getInstance();
		instances.setTime(ed);
		String epirationTime = dd.format(instances.getTime());
		Date end = instances.getTime();
		long endtimes = end.getTime()+(3600*24*1000)-1000;
		Date endDate = new Date(endtimes);
		instance.add(Calendar.DAY_OF_YEAR, 1);
		list.add(new String(dd.format(instance.getTime()) +"/"+epirationTime+ "~" + dd.format(endDate)));
		return list;
	}

	/**
	 * 根据当前时间按月分组
	 * @param a
	 * @param b
	 * @return
	 * @throws ParseException
	 */
	public static List<String> getMouth(String a, String b) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat dd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<String> list = new ArrayList<>();
		Date sd = df.parse(a);
		Date ed = df.parse(b);
		long days = (ed.getTime() - sd.getTime()) / 3600 / 24 / 1000;
		if(days<=30) {
			Calendar instancess = Calendar.getInstance();
			instancess.setTime(ed);
			instancess.add(Calendar.DAY_OF_YEAR, -3);
			long endtimes = ed.getTime()+(3600*24*1000)-1000;
			Date endDate = new Date(endtimes);
			list.add(dd.format(sd)+"/"+dd.format(instancess.getTime())+"~"+dd.format(endDate));
			return list;
		}
		Calendar instance = Calendar.getInstance();
		instance.setTime(sd);
		//int i = instance.get(Calendar.DAY_OF_MONTH);
		//int fsi = 30 - i + 1;
		//long iiv = days - fsi;
		// 天数
		long d = days / 30;
		//instance.add(Calendar.DAY_OF_YEAR, fsi+1);
		//list.add(new String(df.format(sd) + "~" + df.format(instance.getTime())));
		String ne;
		for (long k = 0; k < d; k++) {
			if(k!=0) {
				instance.add(Calendar.DAY_OF_YEAR, 1);
			}
			Date start = instance.getTime();
			instance.add(Calendar.DAY_OF_YEAR, 29);
			Date end = instance.getTime();
			long endtimes = end.getTime()+(3600*24*1000)-1000;
			long epirationTimes = end.getTime()-(3600*24*1000*3);
			Date endDate = new Date(endtimes);
			Date epirationDate = new Date(epirationTimes);
			String epirationTime = dd.format(epirationDate);
			ne = new String(dd.format(start) +"/"+epirationTime+ "~" + dd.format(endDate));
			list.add(ne);
		}
		//instance.add(Calendar.DAY_OF_YEAR, 1);
		Calendar instances = Calendar.getInstance();
		instances.setTime(ed);
		instances.add(Calendar.DAY_OF_YEAR, -2);
		String epirationTime = dd.format(instances.getTime());
		long endtimes = ed.getTime()+(3600*24*1000)-1000;
		Date endDate = new Date(endtimes);
		instance.add(Calendar.DAY_OF_YEAR, 1);
		list.add(new String(dd.format(instance.getTime()) +"/"+epirationTime+ "~" + dd.format(endDate)));
		return list;
	}
	/**
	 * 根据当前时间按年分组
	 * @param a
	 * @param b
	 * @return
	 * @throws ParseException
	 */
	public static List<String> getYear(String a, String b) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat dd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<String> list = new ArrayList<>();
		Date sd = df.parse(a);
		Date ed = df.parse(b);
		long days = (ed.getTime() - sd.getTime()) / 3600 / 24 / 1000;
		if(days<=365) {
			Calendar instancess = Calendar.getInstance();
			instancess.setTime(ed);
			instancess.add(Calendar.DAY_OF_YEAR, -7);
			long endtimes = ed.getTime()+(3600*24*1000)-1000;
			Date endDate = new Date(endtimes);
			list.add(dd.format(sd)+"/"+dd.format(instancess.getTime())+"~"+dd.format(endDate));
			return list;
		}
		Calendar instance = Calendar.getInstance();
		instance.setTime(sd);
		//int i = instance.get(Calendar.DAY_OF_YEAR);
		//int fsi = 365 - i + 1;
		//long iiv = days - fsi;
		// 天数
		long d = days / 365;
		//instance.add(Calendar.DAY_OF_YEAR, fsi);
		//list.add(new String(df.format(sd) + "~" + df.format(instance.getTime())));
		String ne;
		for (long k = 0; k < d; k++) {
			if(k!=0) {
				instance.add(Calendar.DAY_OF_YEAR, 1);
			}
			Date start = instance.getTime();
			instance.add(Calendar.DAY_OF_YEAR, 364);
			Date end = instance.getTime();
			long endtimes = end.getTime()+(3600*24*1000)-1000;
			long epirationTimes = end.getTime()-(3600*24*1000*7);
			Date endDate = new Date(endtimes);
			Date epirationDate = new Date(epirationTimes);
			String epirationTime = dd.format(epirationDate);
			ne = new String(dd.format(start) +"/"+epirationTime+ "~" + dd.format(endDate));
			list.add(ne);
		}
		//instance.add(Calendar.DAY_OF_YEAR, 1);
		Calendar instances = Calendar.getInstance();
		instances.setTime(ed);
		instances.add(Calendar.DAY_OF_YEAR, -7);
		String epirationTime = dd.format(instances.getTime());
		long endtimes = ed.getTime()+(3600*24*1000)-1000;
		Date endDate = new Date(endtimes);
		instance.add(Calendar.DAY_OF_YEAR, 1);
		list.add(new String(dd.format(instance.getTime()) +"/"+epirationTime+ "~" + dd.format(endDate)));
		return list;
	}
	
	/**
	 * 非空验证
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isNotEmpty(Object value) {
		return !isEmpty(value);
	}

	public static boolean isEmptyList(Collection<?> list) {
		if (list == null) {
			return true;
		} else if (list.size() <= 0) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isEmptyMap(Map<?, ?> list) {
		if (list == null) {
			return true;
		} else if (list.size() <= 0) {
			return true;
		} else {
			return false;
		}
	}

	private static final String REG_RXPRE_RESV[] = { "\\.", "\\^", "\\$", "\\[", "\\]", "\\*", "\\+", "\\?", "\\|", "\\(", "\\)" };

	/**
	 * 正则表达式ReplaceAll
	 * 
	 * @param value
	 *            要替换的字符
	 * @param origStr
	 *            替换的原字符串
	 * @param replStr
	 *            替换的字符串
	 * @return
	 */
	public static String replaceAll(String value, String origStr, String replStr) {
		if ((value == null) || (origStr == null)) {
			return value;
		} else {
			if (replStr == null) {
				replStr = UtilConst.EMPTY_STRING;
			}
			for (String tmp : REG_RXPRE_RESV) {
				origStr = origStr.replaceAll(tmp, "\\" + tmp);
			}
			return value.replaceAll(origStr, replStr);
		}
	}

	/**
	 * project路径取得
	 */
	public static String getProjectPath() {
		return System.getProperty("user.dir");
	}

	/**
	 * 换行文字
	 * 
	 * @return
	 */
	public static String getLineSep() {
		return System.getProperty("line.separator");
	}

	/**
	 * 系统路径取得
	 */
	public static String getRuntimePath() {
		String strTmp = Util.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		int pos = strTmp.indexOf("classes");
		if (pos >= 0) {
			return strTmp.substring(0, pos + 8);
		} else {
			return UtilConst.EMPTY_STRING;
		}
	}

	/**
	 * 
	 * @param totalRecNum
	 * @param perPageNum
	 * @return 总页数
	 */
	public static int getTotalPage(int totalRecNum, int perPageNum) {
		if ((totalRecNum <= 0) || (perPageNum <= 0)) {
			return 0;
		} else {
			int result = totalRecNum / perPageNum;
			if ((result * perPageNum) == totalRecNum) {
				return result;
			} else {
				return result + 1;
			}
		}
	}

	/**
	 * 指定长度
	 * 
	 * @param size
	 * @return Random
	 */
	public static String getRandomStr(int size) {
		if (size <= 0) {
			return UtilConst.EMPTY_STRING;
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < size; i++) {
			int pos = (int) (Math.random() * 62);
			if (pos < 0) {
				pos = 0;
			} else if (pos >= 62) {
				pos = 61;
			}
			sb.append(UtilConv.STR_62.charAt(pos));
		}
		return sb.toString();
	}

	/**
	 * 获取byte数
	 * 
	 * @param value
	 * @return
	 */
	public static int getByteNum(String value) {
		if (value == null) {
			return 0;
		}
		return value.getBytes().length;
	}

	/**
	 * 替换：[+][/] → [_][-]
	 * 
	 * @param sessId
	 * @return
	 */
	public static String convSessionId(String sessId) {
		if (sessId == null) {
			return UtilConst.EMPTY_STRING;
		}
		String result = sessId.replaceAll("\\+", "_");
		return result.replaceAll("/", "-");
	}

	/**
	 * 替换：[_][-] → [+][/]
	 * 
	 * @param sessId
	 * @return
	 */
	public static String convSessionIdRev(String sessId) {
		if (sessId == null) {
			return UtilConst.EMPTY_STRING;
		}
		String result = sessId.replaceAll("_", "+");
		return result.replaceAll("-", "/");
	}

	static final String CERT_BASE_LIST = "0123456789ABCDEFGHIJKLMNOPQRSTUWXYZabcdefghijklmnopqrstuwxyz_-";

	public static String getOcxCertifyPassword(int pwdLen) {
		StringBuffer sbCertifyPassword = new StringBuffer();
		int listLen = CERT_BASE_LIST.length();
		for (int iNum = 0; iNum < pwdLen; iNum++) {
			Random rand = new Random();
			int iPos = (int) (listLen * rand.nextDouble());
			if (iPos < 0) {
				iPos = 0;
			}
			if (iPos >= listLen) {
				iPos = listLen - 1;
			}
			sbCertifyPassword.append(CERT_BASE_LIST.charAt(iPos));
		}
		return sbCertifyPassword.toString();
	}

	/**
	 * @param param
	 * @param value
	 *            [|]
	 * @return
	 */
	public static List<String> splitString(String value, String param) {
		List<String> result = new ArrayList<String>();
		if (value != null) {
			String list[] = value.split("\\" + param);
			for (int i = 0; i < list.length; i++) {
				result.add(list[i]);
			}
		}
		return result;
	}

	/**
	 * @param param
	 * @param value
	 *            [|]
	 * @return
	 */
	public static List<String> splitFullString(String value, String param) {
		List<String> result = new ArrayList<String>();
		if (isEmpty(value)) {
			return result;
		} else if (isEmpty(param)) {
			result.add(value);
			return result;
		} else {
			while (true) {
				int iPos = value.indexOf(param);
				if (iPos < 0) {
					result.add(value);
					break;
				} else {
					result.add(value.substring(0, iPos));
					value = value.substring(iPos + param.length());
				}
			}
			return result;
		}
	}

	/**
	 * 换行文字替换 过期，可使用patch.css中的t_wrap标记页面上你需要\r\n换行的元素
	 * 
	 * @param value
	 * @return
	 */
	@Deprecated
	public static String replaceReturnWithBR(String value) {
		if (Util.isEmpty(value)) {
			return null;
		}
		String result = value.trim();
		result = result.replaceAll("\r\n", "<br/>");
		result = result.replaceAll("\n", "<br/>");
		return result;
	}

	/**
	 * CharArray
	 *
	 * @param array
	 * @param beginIndex
	 * @param endIndex
	 */
	public static char[] getCharArray(char[] array, int beginIndex, int endIndex) {
		if (array == null) {
			return null;
		}
		char[] tmp = new char[endIndex - beginIndex];
		int count = 0;
		for (int i = beginIndex; i < endIndex; i++) {
			if (i < array.length) {
				tmp[i - beginIndex] = array[i];
				count++;
			} else {
				break;
			}
		}
		char[] ret = new char[count];
		for (int i = 0; i < count; i++) {
			ret[i] = tmp[i];
		}
		return ret;
	}

	public static java.sql.Date timestampToDate(Timestamp p) {
		if (p == null) {
			return null;
		}
		return new java.sql.Date(p.getTime());
	}

	/**
	 * 保存文件
	 * 
	 * @param is
	 *            输入流
	 * @param filepath
	 *            输出文件地址
	 * @throws Exception
	 */
	public static void saveFile(BufferedInputStream is, String filepath) throws Exception {
		int bytesRead = 0;
		byte[] buffer = new byte[1024];
		FileOutputStream fos = new FileOutputStream(filepath);
		BufferedOutputStream bos = new BufferedOutputStream(fos);
		while ((bytesRead = is.read(buffer, 0, 1024)) != -1) {
			bos.write(buffer, 0, bytesRead);
		}
		bos.close();
		fos.close();
	}

	public static List<String> findDates(Date dBegin, Date dEnd) {
		List<String> lDate = new ArrayList<String>();
		lDate.add(UtilConv.date2Str(dBegin, UtilConv.DATE_YYYY_MM_DD_CHN));
		Calendar calBegin = Calendar.getInstance();
		calBegin.setTime(dBegin);
		Calendar calEnd = Calendar.getInstance();
		calEnd.setTime(dEnd);
		while (dEnd.after(calBegin.getTime())) {
			calBegin.add(Calendar.DAY_OF_MONTH, 1);
			lDate.add(UtilConv.date2Str(calBegin.getTime(), UtilConv.DATE_YYYY_MM_DD_CHN));
		}
		return lDate;
	}

	public static List<String> findMonths(Date dBegin, Date dEnd) {
		List<String> lDate = new ArrayList<String>();
		lDate.add(UtilConv.date2Str(dBegin, UtilConv.YEAR_MONTH_));
		Calendar calBegin = Calendar.getInstance();
		calBegin.setTime(dBegin);
		Calendar calEnd = Calendar.getInstance();
		calEnd.setTime(dEnd);
		while (dEnd.after(calBegin.getTime())) {
			calBegin.add(Calendar.MONTH, 1);
			lDate.add(UtilConv.date2Str(calBegin.getTime(), UtilConv.YEAR_MONTH_));
		}
		return lDate;
	}

	/**
	 * 删除文件夹中的文件（递归删除）
	 * 
	 * @param path
	 */
	public static void clearFolder(String path) {
		File file = new File(path);
		if (file.isDirectory()) {
			String[] list = file.list();
			for (String str : list) {
				clearFolder(path + "/" + str);
			}
		} else {
			file.delete();
		}
	}

	/**
	 * 删除文件夹中的文件（不递归删除）
	 * 
	 * @param path
	 */
	public static void clearFolderL1(String path) {
		File file = new File(path);
		if (file.isDirectory()) {
			String[] list = file.list();
			for (String str : list) {
				file = new File(path + "/" + str);
				if (!file.isDirectory()) {
					file.delete();
				}
			}
		}
	}

	/**
	 * 获取扩展名
	 * 
	 * @param filePath
	 * @return
	 */
	public static String getFileExt(String filePath) {
		String fileExt = UtilConst.EMPTY_STRING;
		if (filePath == null) {
			return fileExt;
		}
		int iPos = filePath.lastIndexOf(".");
		if (iPos >= 0) {
			fileExt = filePath.substring(iPos + 1);
		}
		return fileExt;
	}

	/**
	 * 拼接字符串
	 * 
	 * @param obj1
	 *            字符串1（可以是Object类型，会自动转换）
	 * @param obj2
	 *            字符串1（可以是Object类型，会自动转换）
	 * @param combineSymbol
	 *            拼接标识字符串
	 * @return
	 * @return String
	 */
	public static String combineStr(String combineSymbol, Object... objs) {
		StringBuffer sb = new StringBuffer();
		for (Object obj : objs) {
			sb.append(UtilConv.objToStr(obj));
			sb.append(UtilConv.objToStr(combineSymbol));
		}
		return sb.substring(0, sb.length() - combineSymbol.length());
	}

	/**
	 * 去除头和尾的字符串
	 * 
	 * @param value
	 *            传入的字符串
	 * @param replaceFirst
	 *            去除第一个字符串
	 * @param subStringLast
	 *            去除的最后一个字符串
	 * @return
	 */
	public static String removeFirstAndLastString(String value, String replaceFirst, String subStringLast) {
		if (!Util.isEmpty(value)) {
			String newValue = value.substring(0, value.lastIndexOf(subStringLast));
			newValue = newValue.replaceFirst(replaceFirst, "");
			return newValue;
		} else {
			return null;
		}
	}

	/**
	 * 视频转换
	 * 
	 * @param pInputFile
	 * @param pOutputFile
	 * @param pConvToolPath
	 * @return
	 */
	public static boolean convVideoToFlv(String pInputFile, String pOutputFile, String pConvToolPath) {
		if (Util.isEmpty(pInputFile)) {
			return false;
		}
		if (isNotValidFile(pInputFile)) {
			return false;
		}
		List<String> command = new ArrayList<String>();
		command.add(pConvToolPath);
		command.add("-y");
		command.add("-i");
		command.add(pInputFile);
		command.add("-ab");
		command.add("5600");
		command.add("-ac");
		command.add("1");
		command.add("-ar");
		command.add("44100");
		command.add("-vcodec");
		command.add("libx264");
		command.add("-vprofile");
		command.add("baseline");
		command.add("-r");
		command.add("24");
		command.add("-level");
		command.add("30");
		// command.add("-sameq");
		// command.add("-s");
		// command.add("1280*960");
		command.add(pOutputFile);
		try {
			ProcessBuilder builder = new ProcessBuilder();
			builder.command(command);
			builder.redirectErrorStream(true);
			Process pro = builder.start();
			BufferedReader buf = null;
			String line = null;
			buf = new BufferedReader(new InputStreamReader(pro.getInputStream()));
			StringBuffer sb = new StringBuffer();
			while ((line = buf.readLine()) != null) {
				System.out.println(line);
				sb.append(line);
				continue;
			}
			pro.waitFor();
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return false;
	}

	// 检查文件是否存在
	public static boolean isNotValidFile(String value) {
		if (Util.isEmpty(value)) {
			return false;
		}
		File file = new File(value);
		if (!file.exists() || !file.isFile()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 根据当前时间创建时间点
	 * 
	 * @param qzzm
	 *            前置字母
	 * @return
	 */
	public static String getTimeDot(String qzzm) {
		StringBuffer timeDot = new StringBuffer();
		String nowStr = UtilDateTime.getNowStr(UtilDateTime.DATE_TIME_FULL_PAT_14);
		if (!Util.isEmpty(qzzm)) {
			timeDot.append(qzzm);
			timeDot.append("_");
		}
		timeDot.append(nowStr);
		return timeDot.toString();
	}

	/**
	 * 判断字符串是否匹配正则
	 * 
	 * @param str
	 * @param regStr
	 * @return
	 */
	public static boolean isMatch(String str, String regStr) {
		Pattern pattern = Pattern.compile(regStr);
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}

	/**
	 * 获取登录用户IP地址
	 * 
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if (ip.equals("0:0:0:0:0:0:0:1")) {
			ip = "127.0.0.1";
		}
		return ip;
	}

	/**
	 * 获取随机数（-6到6）
	 * 
	 * @return
	 */
	public static int getRandomNum() {
		int max = 6;
		int min = -6;
		Random random = new Random();
		int s = random.nextInt(max - min + 1) - max;
		return s;
	}

	/**
	 * @description :补0，补足3位
	 * @author yq 2017年9月13日 下午3:55:07
	 * @param code
	 * @return
	 */
	public static String autoGenericCode3(String code) {
		return autoGenericCode(code, 3);
	}

	/**
	 * @description :不够位数的在前面补0，保留num的长度位数字
	 * @author yq 2017年9月13日 下午3:51:56
	 * @param code
	 * @param num
	 * @return
	 */
	public static String autoGenericCode(String code, int num) {
		String result = "";
		// 保留num的位数
		// 0 代表前面补充0
		// num 代表长度
		// d 代表参数为正数型
		result = String.format("%0" + num + "d", Integer.parseInt(code));
		return result;
	}

	/**
	 * @description :获取过去第几天的日期
	 * @author yq 2017年9月25日 上午10:08:59
	 * @param past
	 * @return
	 */
	public static String getPastDate(int past) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
		Date today = calendar.getTime();
		String result = UtilConv.date2Str(today, UtilConv.DATE_YYYY_MM_DD_CHN);
		return result;
	}

	/**
	 * @description :double相加
	 * @author yq 2017年10月9日 上午10:40:19
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static Double doubleAdd(Double v1, Double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.add(b2).doubleValue();
	}

	/**
	 * @description :多个double相加
	 * @author yq 2017年10月9日 上午10:40:19
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static Double doubleAdd(Double... v1) {
		BigDecimal b = new BigDecimal(Double.toString(0));
		for (Double d : v1) {
			BigDecimal b1 = new BigDecimal(Double.toString(d));
			b = b.add(b1);
		}
		return b.doubleValue();
	}

	/**
	 * @description :double相减
	 * @author yq 2017年10月9日 上午10:40:19
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static Double doubleSub(Double v1, Double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.subtract(b2).doubleValue();
	}

	/**
	 * @description :多个double相减
	 * @author yq 2017年10月9日 上午10:40:19
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static Double doubleSub(Double... v1) {
		BigDecimal b = new BigDecimal(Double.toString(v1[0]));
		int count = 0;
		for (Double d : v1) {
			count++;
			if (count == 1) {
				continue;
			}
			BigDecimal b1 = new BigDecimal(Double.toString(d));
			b = b.subtract(b1);
		}
		return b.doubleValue();
	}

	/**
	 * @description :double相乘
	 * @author yq 2017年10月9日 上午10:40:19
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static Double doubleMultiply(Double v1, Double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.multiply(b2).doubleValue();
	}

	/**
	 * @description :double相除
	 * @author yq 2017年10月9日 上午10:40:19
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static Double doubleDivide(Double v1, Double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.divide(b2, 2, BigDecimal.ROUND_HALF_EVEN).doubleValue();
	}

	/**
	 * @description :四舍五入 len=保留小数位数
	 * @author yq 2018年1月25日 上午10:46:24
	 * @param v
	 * @return
	 */
	public static Double mathRound(Double v, Double len) {
		Double num = Math.pow(10d, len);
		return Math.round(v * num) / num;
	}

	/**
	 * @Author yq
	 * @Description url地址拼接
	 * @Date 2017/12/7 14:05
	 */
	public static String getFullUrl(String url, Map<String, String> params) throws Exception {
		for (Map.Entry<String, String> entry : params.entrySet()) {
			if (url.indexOf("?") > -1) {
				url += "&" + entry.getKey() + "=" + entry.getValue();
			} else {
				url += "?" + entry.getKey() + "=" + entry.getValue();
			}
		}
		return url;
	}
	
	/**
     * 获取指定年月的第一天
     * @param year
     * @param month
     * @return
     */
    public static String getFirstDayOfMonth1(int year, int month) {     
        Calendar cal = Calendar.getInstance();   
        //设置年份
        cal.set(Calendar.YEAR, year);
        //设置月份 
        cal.set(Calendar.MONTH, month-1); 
        //获取某月最小天数
        int firstDay = cal.getMinimum(Calendar.DATE);
        //设置日历中月份的最小天数 
        cal.set(Calendar.DAY_OF_MONTH,firstDay);  
        //格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(cal.getTime())+" 00:00:00";  
    }
    /**
     * 获取指定年月的最后一天
     * @param year
     * @param month
     * @return
     */
     public static String getLastDayOfMonth1(int year, int month) {     
         Calendar cal = Calendar.getInstance();     
         //设置年份  
         cal.set(Calendar.YEAR, year);  
         //设置月份  
         cal.set(Calendar.MONTH, month-1); 
         //获取某月最大天数
         int lastDay = cal.getActualMaximum(Calendar.DATE);
         //设置日历中月份的最大天数  
         cal.set(Calendar.DAY_OF_MONTH, lastDay);  
         //格式化日期
         SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
         return sdf.format(cal.getTime())+" 23:59:59";
     }
     
     /**
      * 十进制转十六进制
      * @param n
      * @return
      */
     public static String intToHex(Integer n) {
         StringBuffer s = new StringBuffer();
         String a;
         char []b = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
         while(n != 0){
             s = s.append(b[n%16]);
             n = n/16;
         }
         a = s.reverse().toString();
         return a;
     }
}
