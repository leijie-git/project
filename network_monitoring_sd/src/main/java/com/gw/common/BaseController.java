package com.gw.common;

import com.gw.mapper.UtUnitUserMapper;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author 作者 lxy
 * @date 创建时间 2018年3月21日 下午5:32:04
 * @description 扩展控制层
 */
public class BaseController {

	@Resource
	private UtUnitUserMapper utUnitUserMapper;
	

	@InitBinder
	public void initBinder(ServletRequestDataBinder binder) {
		/**
		 * 自动转换日期类型的字段格式
		 */
		binder.registerCustomEditor(Date.class,
				new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
	}

	/**
	 * 获取SessionInfo
	 * 
	 * @param request
	 * @return
	 */
	protected Object getSessinInfo(HttpServletRequest request, String key) {
		Object attribute = request.getSession().getAttribute(key);
		return attribute;
	}

	/**
	 * 设置SessionInfo
	 * 
	 * @param request
	 * @return
	 */
	protected void setSessinInfo(HttpServletRequest request, String key, Object obj) {
		request.getSession().setAttribute(key, obj);
	}

	/**
	 * 导入资源
	 * 
	 * @param uploadExcel
	 * @param no
	 * @param t
	 * @return
	 * @throws IOException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws InstantiationException
	 */
	public <T> List<T> importExcel(MultipartFile uploadExcel, int no, Class<T> t)
			throws IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException, InstantiationException {
		HSSFWorkbook workbook = new HSSFWorkbook(uploadExcel.getInputStream());
		HSSFSheet sheet = workbook.getSheetAt(no);
		List<T> list = new ArrayList<T>();
		for (int i = sheet.getFirstRowNum() + 1; i <= sheet.getLastRowNum(); i++) {
			HSSFRow row = sheet.getRow(i);
			if (row == null) {
				continue;
			}
			T instance = t.newInstance();
			Field[] fields = instance.getClass().getDeclaredFields();
			for (int k = 0; k < fields.length; k++) {
				Method setMethod = instance.getClass().getMethod("set" + StringUtils.capitalize(fields[k].getName()),
						String.class);
				HSSFCell cell = (HSSFCell) row.getCell(k);
				if (cell == null) {
					setMethod.invoke(instance, "");
				} else if (cell.getCellType() == 0) {
					String strCell = "";
					// 判断是否是日期
					if (HSSFDateUtil.isCellDateFormatted(cell)) {
						SimpleDateFormat sdf = null;
						if (cell.getCellStyle().getDataFormat() == HSSFDataFormat.getBuiltinFormat("h:mm")) {
							sdf = new SimpleDateFormat("HH:mm");
						} else {// 日期
							sdf = new SimpleDateFormat("yyyy-MM-dd");
						}
						Date date = cell.getDateCellValue();
						strCell = sdf.format(date);
					} else {
						DecimalFormat df = new DecimalFormat("0");
						strCell = df.format(cell.getNumericCellValue());
					}
					setMethod.invoke(instance, strCell);
				} else if (cell.getCellType() == 1) {
					setMethod.invoke(instance, cell.getStringCellValue());
				}
			}
			list.add(instance);
		}
		return list;
	}

	/**
	 * 导入
	 * 
	 * @param uploadExcel
	 * @param t
	 * @return
	 * @throws IOException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws InstantiationException
	 */
	public <T> List<T> importExcel(MultipartFile uploadExcel, Class<T> t)
			throws IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException, InstantiationException {
		List<T> list = this.importExcel(uploadExcel, 0, t);
		return list;
	}

}
