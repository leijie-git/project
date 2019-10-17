package com.gw.generatereport;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

import com.gw.mapper.UtInspectTaskMapper;
import com.gw.mapper.UtInspectTaskdetialMapper;
import com.gw.mapper.UtUnitUserMapper;
import com.gw.util.Util;
import com.gw.util.UtilConv;

@RestController
@RequestMapping("console/wirelessDevice")
public class MailExcel<T> {
	@Resource
	private UtInspectTaskdetialMapper utInspectTaskdetialMapper;
	@Resource
	private GenerateReport generateReport;
	@Resource
	private UtUnitUserMapper utUnitUserMapper;
	@Resource
	private UtInspectTaskMapper utInspectTaskMapper;
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void exportInspectExcel(OutputStream out, GenerateNetWorkOutData outData, List<T> dataset,
			String[] headers) throws Exception {
		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 生成一个表格
		HSSFSheet sheet = workbook.createSheet("sheet1");
		HSSFSheet sheet2 = workbook.createSheet("sheet2");
		// 设置表格默认列宽度为15个字节
		sheet.setDefaultColumnWidth(25);
		sheet2.setDefaultColumnWidth(25);
		// 生成一个样式
		HSSFCellStyle style = workbook.createCellStyle();
		// 设置这些样式
		style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 生成一个字体
		HSSFFont font = workbook.createFont();
		font.setColor(HSSFColor.VIOLET.index);
		font.setFontHeightInPoints((short) 12);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 把字体应用到当前的样式
		style.setFont(font);
		// 生成并设置另一个样式
		HSSFCellStyle style2 = workbook.createCellStyle();
		// style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		// style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		// style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
		// style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
		// 生成另一个字体
		HSSFFont font2 = workbook.createFont();
		font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font2.setFontHeight((short) 300);
		// 把字体应用到当前的样式
		style2.setFont(font2);
		
		HSSFCellStyle style3 = workbook.createCellStyle();
		style3.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
		style3.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直
		style3.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style3.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style3.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style3.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style3.setWrapText(true);
		HSSFFont font3 = workbook.createFont();
		font3.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		font3.setFontHeight((short) 200);
		// 把字体应用到当前的样式
		style3.setFont(font3);
		// 声明一个画图的顶级管理器
		// HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
		// 定义注释的大小和位置,详见文档
		// HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0, 0, 0,
		// 0, (short) 4, 2, (short) 6, 5));
		// 设置注释内容
		// comment.setString(new HSSFRichTextString("可以在POI中添加注释！"));
		// 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.
		// comment.setAuthor("leno");
		// HSSFCellStyle style3 = workbook.createCellStyle();
		// style3.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// style3.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
		//
		// style3.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		// style3.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		// style3.setBorderRight(HSSFCellStyle.BORDER_THIN);
		// style3.setBorderTop(HSSFCellStyle.BORDER_THIN);

		HSSFRow row = sheet.createRow(0);
		HSSFCell cell = row.createCell(0);
		cell.setCellValue("巡查报告书");
		cell.setCellStyle(style2);
		row.createCell(1).setCellStyle(style2);
		row.setHeight((short) 900);
		CellRangeAddress region = new CellRangeAddress(0, 0, (short) 0, (short) 1);
		sheet.addMergedRegion(region);
		HSSFRow row1 = sheet.createRow(1);
		HSSFCell row1_cell0 = row1.createCell(0);
		row1_cell0.setCellValue("单位名称：");
		row1_cell0.setCellStyle(style3);
		HSSFCell row1_cell1 = row1.createCell(1);
		row1_cell1.setCellValue(outData.getUnitName());
		row1_cell1.setCellStyle(style3);
		row1.setHeight((short) 600);
		HSSFRow row2 = sheet.createRow(2);
		HSSFCell row2_cell0 = row2.createCell(0);
		row2_cell0.setCellValue("单位地址：");
		row2_cell0.setCellStyle(style3);
		HSSFCell row2_cell1 = row2.createCell(1);
		row2_cell1.setCellValue(outData.getUnitAddress());
		row2_cell1.setCellStyle(style3);
		row2.setHeight((short) 600);
		HSSFRow row3 = sheet.createRow(3);
		HSSFCell row3_cell0 = row3.createCell(0);
		row3_cell0.setCellValue("报告月份：");
		row3_cell0.setCellStyle(style3);
		HSSFCell row3_cell1 = row3.createCell(1);
		row3_cell1.setCellValue(UtilConv.date2Str(new Date(), UtilConv.YEAR_MONTHS));
		row3_cell1.setCellStyle(style3);
		row3.setHeight((short) 600);
		HSSFRow row5 = sheet.createRow(5);
		HSSFCell row5_cell0 = row5.createCell(0);
		row5_cell0.setCellValue("说明：（内容待修改）");
		row5_cell0.setCellStyle(style2);
		HSSFCell row5_cell1 = row5.createCell(1);
		row5_cell1.setCellStyle(style2);
		row5.setHeight((short) 900);
		CellRangeAddress region1 = new CellRangeAddress(5, 5, (short) 0, (short) 1);
		sheet.addMergedRegion(region1);
		for (int i = 6; i <= 12; i++) {
			HSSFRow row6 = sheet.createRow(i);
			HSSFCell row6_cell0 = row6.createCell(0);
			HSSFCell row6_cell1 = row6.createCell(1);
			row6.setHeight((short) 600);
			if (i == 6) {
				row6_cell0.setCellValue(
						"1、 本 报 告 书 统 一 使 用 国 际 标 准 A 4 型 纸 ， 由 各 维 保 公 司 按 照 规 定的 式 样 制 作 ， 封 面 、 封 底 采 用 2 0 0 g 铜 版 纸 印 刷 ， 其 他 页 电 脑 打 印 ，    装 订 成 册 。 报 告 书 设 定 的 栏 目 应 逐 项 填 写 完 整 、 准 确 ； 不 需 填 写 的 ，    应 在 空 白 处 填 写 “ 无 ” 。\r\n"
								+ "2、 本 报 告 书 包 含 封 面 、 《 维 保 情 况 汇 总 表 》 、 《 维 保 情 况 详 情 表 》及 封 底 。 每 份 报 告 书 应 按 顺 序 编 写 页 码 ， 在 “ 共 页 第 页 ” 处 分 别\r\n"
								+ "填 写 总 页 数 、 页 码 ， 如 ： “ 共 6 页 第 2 页 ” 。\r\n"
								+ "3、 本 报 告 书 中 的 《 维 保 情 况 汇 总 表 》 的 内 容 应 与 维 保 现 场 填 写的 《 维 保 情 况 详 情 表 》 内 容 一 致 ， 由 维 保 人 员 、 维 保 公 司 审 核 确 认 ，    并 加 盖 维 保 公 司 印 章 、 骑 缝 章 。\r\n"
								+ "4、 报 告 书 中 的 “ □ ” ， 表 示 可 供 选 择 ， 在 选 中 内 容 前 的 “ □ ");

			}
			row6_cell0.setCellStyle(style3);
			row6_cell1.setCellStyle(style3);
		}
		CellRangeAddress region2 = new CellRangeAddress(6, 12, (short) 0, (short) 1);
		sheet.addMergedRegion(region2);
		HSSFRow row13 = sheet.createRow(13);
		HSSFCell row13_cell0 = row13.createCell(0);
		HSSFCell row13_cell2 = row13.createCell(2);
		HSSFCell row13_cell3 = row13.createCell(3);
		HSSFCell row13_cell4 = row13.createCell(4);
		row13_cell0.setCellValue("巡查情况汇总表");
		row13_cell0.setCellStyle(style2);
		row13_cell2.setCellStyle(style2);
		row13_cell3.setCellStyle(style2);
		row13_cell4.setCellStyle(style2);
		HSSFCell row13_cell1 = row13.createCell(1);
		row13_cell1.setCellStyle(style2);
		row13.setHeight((short) 900);
		CellRangeAddress region3 = new CellRangeAddress(13, 13, (short) 0, (short) 4);
		sheet.addMergedRegion(region3);

		HSSFCellStyle style4 = workbook.createCellStyle();
		style4.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
		style4.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直
		style4.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style4.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style4.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style4.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style4.setWrapText(true);
		HSSFFont font4 = workbook.createFont();
		font4.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		font4.setFontName("微软雅黑");
		font4.setFontHeight((short) 400);
		// 把字体应用到当前的样式
		style4.setFont(font4);
		CellRangeAddress region6 = new CellRangeAddress(14, 14, (short) 2, (short) 4);
		sheet.addMergedRegion(region6);
		CellRangeAddress region7 = new CellRangeAddress(15, 15, (short) 2, (short) 4);
		sheet.addMergedRegion(region7);
		int userSize = 0;
		int siteSize = 0;
		if (Util.isNotEmpty(outData) && Util.isNotEmpty(outData.getUserData())) {
			userSize = outData.getUserData().size();
		}
		if (Util.isNotEmpty(outData) && Util.isNotEmpty(outData.getSiteData())) {
			siteSize = outData.getSiteData().size();
		}
		for (int i = 14; i < 18 + userSize; i++) {
			HSSFRow rowi = sheet.createRow(i);
			HSSFCell rowi_cell0 = rowi.createCell(0);
			HSSFCell rowi_cell1 = rowi.createCell(1);
			HSSFCell rowi_cell2 = rowi.createCell(2);
			HSSFCell rowi_cell3 = rowi.createCell(3);
			HSSFCell rowi_cell4 = rowi.createCell(4);
			if (i == 14) {
				rowi_cell0.setCellValue("项\r\n目\r\n概\r\n况");
				rowi_cell1.setCellValue("场所名称");
				rowi_cell2.setCellValue(outData.getUnitName());
				if(userSize!=0) {
					CellRangeAddress region4 = new CellRangeAddress(14, userSize + 17, (short) 0, (short) 0);
					sheet.addMergedRegion(region4);
				}else {
					CellRangeAddress region4 = new CellRangeAddress(14, userSize + 18, (short) 0, (short) 0);
					sheet.addMergedRegion(region4);
				}
			}
			if (i == 15) {
				rowi_cell1.setCellValue("场所地址");
				rowi_cell2.setCellValue(outData.getUnitAddress());
			}
			if (i == 16) {
				rowi_cell1.setCellValue("负责人");
				rowi_cell2.setCellValue(outData.getUnitChargePerson());
				rowi_cell3.setCellValue("联系电话");
				rowi_cell4.setCellValue(outData.getPersonPhone());
			}
			if (i == 17) {
				rowi_cell1.setCellValue("巡查人数");
				rowi_cell2.setCellValue(outData.getInspectPersonCount());
				rowi_cell3.setCellValue("巡查次数：" + outData.getInspectCount() + "/" + outData.getTaskCount());
				rowi_cell4.setCellValue("完成率：" + outData.getCompletionRate());
			}
			if (i > 17 && i < 18 + userSize) {
				rowi_cell1.setCellValue("巡查人员");
				rowi_cell2.setCellValue(outData.getUserData().get(i - 18).getUserName());
				rowi_cell3.setCellValue("巡查次数：" + outData.getUserData().get(i - 18).getInspectCount() + "/"
						+ outData.getUserData().get(i - 18).getTaskCount());
				rowi_cell4.setCellValue("完成率：" + outData.getUserData().get(i - 18).getCompletionRate());

			}
			rowi_cell0.setCellStyle(style4);
			rowi_cell1.setCellStyle(style3);
			rowi_cell2.setCellStyle(style3);
			rowi_cell3.setCellStyle(style3);
			rowi_cell4.setCellStyle(style3);
			rowi.setHeight((short) 600);
		}
		if(userSize != 0) {
			CellRangeAddress region8 = new CellRangeAddress(18, userSize + 17, (short) 1, (short) 1);
			sheet.addMergedRegion(region8);
		}
		
		// row14.setHeight((short)900);

		for (int i = 18 + userSize; i < 19 + userSize + siteSize; i++) {
			HSSFRow rowi = sheet.createRow(i);
			HSSFCell rowi_cell0 = rowi.createCell(0);
			HSSFCell rowi_cell1 = rowi.createCell(1);
			HSSFCell rowi_cell2 = rowi.createCell(2);
			HSSFCell rowi_cell3 = rowi.createCell(3);
			HSSFCell rowi_cell4 = rowi.createCell(4);
			if (i == 18 + userSize) {
				rowi_cell0.setCellValue("点\r\n位\r\n概\r\n况");
				rowi_cell1.setCellValue("点位名称");
				rowi_cell2.setCellValue("巡查次数");
				rowi_cell3.setCellValue("异常次数(异常率)");
				rowi_cell4.setCellValue("巡查人员");
				CellRangeAddress region5 = new CellRangeAddress(18 + userSize, 18 + userSize + siteSize, (short) 0,
						(short) 0);
				sheet.addMergedRegion(region5);
			}
			if (i > 18 + userSize && i < 19 + userSize + siteSize) {
				rowi_cell1.setCellValue(outData.getSiteData().get(i - 19 - userSize).getSiteName());
				rowi_cell2.setCellValue(outData.getSiteData().get(i - 19 - userSize).getInspectCount() + "/"
						+ outData.getSiteData().get(i - 19 - userSize).getTaskCount());
				rowi_cell3.setCellValue(outData.getSiteData().get(i - 19 - userSize).getExpectionCount() + "("
						+ outData.getSiteData().get(i - 19 - userSize).getExpectionRate() + ")");
				rowi_cell4.setCellValue(outData.getSiteData().get(i - 19 - userSize).getInspectUserName());
			}
			rowi_cell0.setCellStyle(style4);
			rowi_cell1.setCellStyle(style3);
			rowi_cell2.setCellStyle(style3);
			rowi_cell3.setCellStyle(style3);
			rowi_cell4.setCellStyle(style3);
			rowi.setHeight((short) 600);
		}
		for (int i = 19 + userSize + siteSize; i < 25 + userSize + siteSize; i++) {
			HSSFRow rowi = sheet.createRow(i);
			HSSFCell rowi_cell0 = rowi.createCell(0);
			HSSFCell rowi_cell1 = rowi.createCell(1);
			HSSFCell rowi_cell2 = rowi.createCell(2);
			HSSFCell rowi_cell3 = rowi.createCell(3);
			HSSFCell rowi_cell4 = rowi.createCell(4);
			rowi_cell0.setCellStyle(style4);
			rowi_cell1.setCellStyle(style3);
			rowi_cell2.setCellStyle(style3);
			rowi_cell3.setCellStyle(style3);
			rowi_cell4.setCellStyle(style3);
			if (i == 19 + userSize + siteSize) {
				rowi_cell0.setCellValue("意\r\n见\r\n总\r\n结");
				CellRangeAddress region9 = new CellRangeAddress(19 + userSize + siteSize, 24 + userSize + siteSize,
						(short) 0, (short) 0);
				sheet.addMergedRegion(region9);
				rowi_cell1.setCellValue("1、 本 报 告 书 统 一 使 用 国 际 标 准 A 4 型 纸 ， 由 各 巡 查 单 位 按 照 规 定的 式 样 制 作 ， 封 面 、 封 底 采 用 2 0 0 g 铜 版 纸 印 刷 ， 其 他 页 电 脑 打 印 ，    装 订 成 册 。 报 告 书 设 定 的 栏 目 应 逐 项 填 写 完 整 、 准 确 ； 不 需 填 写 的 ，    应 在 空 白 处 填 写 “ 无 ” 。\r\n" + 
						"2、 本 报 告 书 包 含 封 面 、 《巡 查 情 况 汇 总 表 》 、 《 巡 查 情 况 详 情 表 》及 封 底 。 每 份 报 告 书 应 按 顺 序 编 写 页 码 ， 在 “ 共 页 第 页 ” 处 分 别\r\n" + 
						"填 写 总 页 数 、 页 码 ， 如 ： “ 共 6 页 第 2 页 ” 。\r\n" + 
						"3、 本 报 告 书 中 的 《 巡 查 情 况 汇 总 表 》 的 内 容 应 与 巡 查 现 场 填 写的 《 巡 查 情 况 详 情 表 》 内 容 一 致 ， 由 巡 查 人 员 、 巡 查 单 位 审 核 确 认 ，    并 加 盖 巡 查 单 位 印 章 、 骑 缝 章 。");
			}
			rowi.setHeight((short) 600);
		}
		CellRangeAddress region10 = new CellRangeAddress(19 + userSize + siteSize, 24 + userSize + siteSize,
				(short) 1, (short) 4);
		sheet.addMergedRegion(region10);
		// 产生表格标题行
		HSSFRow sheetRow = sheet2.createRow(0);
		for (int i = 0; i < headers.length; i++) {
			HSSFCell sheetCell = sheetRow.createCell(i);
			sheetCell.setCellStyle(style);
			HSSFRichTextString text = new HSSFRichTextString(headers[i]);
			sheetCell.setCellValue(text);
		}

		// 遍历集合数据，产生数据行
		Iterator<T> it = dataset.iterator();
		int index = 0;

		HSSFFont sheetFont = workbook.createFont();
		sheetFont.setColor(HSSFColor.BLUE.index);
		while (it.hasNext()) {
			index++;
			row = sheet2.createRow(index);
			T t = (T) it.next();
			// 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
			Field[] fields = t.getClass().getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				HSSFCell celli = row.createCell(i);
				celli.setCellStyle(style2);
				Field field = fields[i];
				String fieldName = field.getName();
				String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
				Class tCls = t.getClass();
				Method getMethod = tCls.getMethod(getMethodName, new Class[] {});
				Object value = getMethod.invoke(t, new Object[] {});
				// 判断值的类型后进行强制类型转换
				String textValue = null;
				if (value instanceof Date) {
					Date date = (Date) value;
					textValue = UtilConv.date2Str(date, "yyyy-MM-dd");
				} else {
					// 其它数据类型都当作字符串简单处理
					if (value == null) {
						textValue = "";
					} else {
						textValue = HtmlUtils.htmlUnescape(value.toString());
					}

				}
				// 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
				if (textValue != null) {
					Pattern p = Pattern.compile("^//d+(//.//d+)?$");
					Matcher matcher = p.matcher(textValue);
					if (matcher.matches()) {
						// 是数字当作double处理
						celli.setCellValue(Double.parseDouble(textValue));
					} else {
						HSSFRichTextString richString = new HSSFRichTextString(textValue);
						// HSSFFont font3 = workbook.createFont();
						// font3.setColor(HSSFColor.BLUE.index);
						richString.applyFont(sheetFont);
						celli.setCellValue(richString);
					}
				}
			}

		}

		workbook.write(out);

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void exportMaintenanceExcel(OutputStream out, GenerateMaintenanceOutData outData, List<T> dataset,
			String[] headers) throws Exception {
		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 生成一个表格
		HSSFSheet sheet = workbook.createSheet("sheet1");
		HSSFSheet sheet2 = workbook.createSheet("sheet2");
		// 设置表格默认列宽度为15个字节
		sheet.setDefaultColumnWidth(25);
		sheet2.setDefaultColumnWidth(25);
		// 生成一个样式
		HSSFCellStyle style = workbook.createCellStyle();
		// 设置这些样式
		style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 生成一个字体
		HSSFFont font = workbook.createFont();
		font.setColor(HSSFColor.VIOLET.index);
		font.setFontHeightInPoints((short) 12);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 把字体应用到当前的样式
		style.setFont(font);
		// 生成并设置另一个样式
		HSSFCellStyle style2 = workbook.createCellStyle();
		// style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		// style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		// style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
		// style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
		// 生成另一个字体
		HSSFFont font2 = workbook.createFont();
		font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font2.setFontHeight((short) 300);
		// 把字体应用到当前的样式
		style2.setFont(font2);
		HSSFCellStyle style3 = workbook.createCellStyle();
		style3.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
		style3.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直
		style3.setWrapText(true);
		style3.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style3.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style3.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style3.setBorderRight(HSSFCellStyle.BORDER_THIN);
		HSSFFont font3 = workbook.createFont();
		font3.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		font3.setFontHeight((short) 200);
		// 把字体应用到当前的样式
		style3.setFont(font3);
		// 声明一个画图的顶级管理器
		// HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
		// 定义注释的大小和位置,详见文档
		// HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0, 0, 0,
		// 0, (short) 4, 2, (short) 6, 5));
		// 设置注释内容
		// comment.setString(new HSSFRichTextString("可以在POI中添加注释！"));
		// 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.
		// comment.setAuthor("leno");
		// HSSFCellStyle style3 = workbook.createCellStyle();
		// style3.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// style3.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
		//
		// style3.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		// style3.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		// style3.setBorderRight(HSSFCellStyle.BORDER_THIN);
		// style3.setBorderTop(HSSFCellStyle.BORDER_THIN);
		HSSFCellStyle style4 = workbook.createCellStyle();
		style4.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
		style4.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直
		style4.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style4.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style4.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style4.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style4.setWrapText(true);
		HSSFFont font4 = workbook.createFont();
		font4.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		font4.setFontName("微软雅黑");
		font4.setFontHeight((short) 400);
		// 把字体应用到当前的样式
		style4.setFont(font4);
		HSSFRow row = sheet.createRow(0);
		HSSFCell cell = row.createCell(0);
		cell.setCellValue("维保报告书");
		cell.setCellStyle(style2);
		row.createCell(1).setCellStyle(style2);
		row.setHeight((short) 900);
		CellRangeAddress region = new CellRangeAddress(0, 0, (short) 0, (short) 1);
		sheet.addMergedRegion(region);
		HSSFRow row1 = sheet.createRow(1);
		HSSFCell row1_cell0 = row1.createCell(0);
		row1_cell0.setCellValue("单位名称：");
		row1_cell0.setCellStyle(style3);
		HSSFCell row1_cell1 = row1.createCell(1);
		row1_cell1.setCellValue(outData.getUnitName());
		row1_cell1.setCellStyle(style3);
		row1.setHeight((short) 600);
		HSSFRow row2 = sheet.createRow(2);
		HSSFCell row2_cell0 = row2.createCell(0);
		row2_cell0.setCellValue("单位地址：");
		row2_cell0.setCellStyle(style3);
		HSSFCell row2_cell1 = row2.createCell(1);
		row2_cell1.setCellValue(outData.getUnitAddress());
		row2_cell1.setCellStyle(style3);
		row2.setHeight((short) 600);
		HSSFRow row3 = sheet.createRow(3);
		HSSFCell row3_cell0 = row3.createCell(0);
		row3_cell0.setCellValue("报告月份：");
		row3_cell0.setCellStyle(style3);
		HSSFCell row3_cell1 = row3.createCell(1);
		row3_cell1.setCellValue(UtilConv.date2Str(new Date(), UtilConv.YEAR_MONTHS));
		row3_cell1.setCellStyle(style3);
		row3.setHeight((short) 600);
		HSSFRow row5 = sheet.createRow(5);
		HSSFCell row5_cell0 = row5.createCell(0);
		row5_cell0.setCellValue("说明：（内容待修改）");
		row5_cell0.setCellStyle(style2);
		HSSFCell row5_cell1 = row5.createCell(1);
		row5_cell1.setCellStyle(style2);
		row5.setHeight((short) 900);
		CellRangeAddress region1 = new CellRangeAddress(5, 5, (short) 0, (short) 1);
		sheet.addMergedRegion(region1);
		for (int i = 6; i <= 12; i++) {

			HSSFRow row6 = sheet.createRow(i);
			HSSFCell row6_cell0 = row6.createCell(0);
			HSSFCell row6_cell1 = row6.createCell(1);
			row6.setHeight((short) 600);
			if (i == 6) {
				row6_cell0.setCellValue(
						"1、 本 报 告 书 统 一 使 用 国 际 标 准 A 4 型 纸 ， 由 各 维 保 公 司 按 照 规 定的 式 样 制 作 ， 封 面 、 封 底 采 用 2 0 0 g 铜 版 纸 印 刷 ， 其 他 页 电 脑 打 印 ，    装 订 成 册 。 报 告 书 设 定 的 栏 目 应 逐 项 填 写 完 整 、 准 确 ； 不 需 填 写 的 ，    应 在 空 白 处 填 写 “ 无 ” 。\r\n"
								+ "2、 本 报 告 书 包 含 封 面 、 《 维 保 情 况 汇 总 表 》 、 《 维 保 情 况 详 情 表 》及 封 底 。 每 份 报 告 书 应 按 顺 序 编 写 页 码 ， 在 “ 共 页 第 页 ” 处 分 别\r\n"
								+ "填 写 总 页 数 、 页 码 ， 如 ： “ 共 6 页 第 2 页 ” 。\r\n"
								+ "3、 本 报 告 书 中 的 《 维 保 情 况 汇 总 表 》 的 内 容 应 与 维 保 现 场 填 写的 《 维 保 情 况 详 情 表 》 内 容 一 致 ， 由 维 保 人 员 、 维 保 公 司 审 核 确 认 ，    并 加 盖 维 保 公 司 印 章 、 骑 缝 章 。\r\n"
								+ "4、 报 告 书 中 的 “ □ ” ， 表 示 可 供 选 择 ， 在 选 中 内 容 前 的 “ □ ");

			}
			row6_cell0.setCellStyle(style3);
			row6_cell1.setCellStyle(style3);
		}
		CellRangeAddress region2 = new CellRangeAddress(6, 12, (short) 0, (short) 1);
		sheet.addMergedRegion(region2);
		HSSFRow row13 = sheet.createRow(13);
		HSSFCell row13_cell0 = row13.createCell(0);
		HSSFCell row13_cell1 = row13.createCell(1);
		HSSFCell row13_cell2 = row13.createCell(2);
		HSSFCell row13_cell3 = row13.createCell(3);
		HSSFCell row13_cell4 = row13.createCell(4);
		row13_cell0.setCellValue("维保情况汇总表");
		row13_cell0.setCellStyle(style2);
		row13_cell1.setCellStyle(style2);
		row13_cell2.setCellStyle(style2);
		row13_cell3.setCellStyle(style2);
		row13_cell4.setCellStyle(style2);
		row13.setHeight((short) 900);
		CellRangeAddress region3 = new CellRangeAddress(13, 13, (short) 0, (short) 4);
		sheet.addMergedRegion(region3);

		CellRangeAddress region6 = new CellRangeAddress(14, 14, (short) 2, (short) 4);
		sheet.addMergedRegion(region6);
		CellRangeAddress region7 = new CellRangeAddress(15, 15, (short) 2, (short) 4);
		sheet.addMergedRegion(region7);
		int userSize = 0;
		int siteSize = 0;
		if (Util.isNotEmpty(outData) && Util.isNotEmpty(outData.getUserData())) {
			userSize = outData.getUserData().size();
		}
		if (Util.isNotEmpty(outData) && Util.isNotEmpty(outData.getSiteData())) {
			siteSize = outData.getSiteData().size();
		}
		for (int i = 14; i < 18 + userSize; i++) {
			HSSFRow rowi = sheet.createRow(i);
			HSSFCell rowi_cell0 = rowi.createCell(0);
			HSSFCell rowi_cell1 = rowi.createCell(1);
			HSSFCell rowi_cell2 = rowi.createCell(2);
			HSSFCell rowi_cell3 = rowi.createCell(3);
			HSSFCell rowi_cell4 = rowi.createCell(4);
			if (i == 14) {
				rowi_cell0.setCellValue("项\r\n目\r\n概\r\n况");
				rowi_cell1.setCellValue("场所名称");
				rowi_cell2.setCellValue(outData.getUnitName());
				if(userSize!=0) {
					CellRangeAddress region4 = new CellRangeAddress(14, userSize + 17, (short) 0, (short) 0);
					sheet.addMergedRegion(region4);
				}else {
					CellRangeAddress region4 = new CellRangeAddress(14, userSize + 18, (short) 0, (short) 0);
					sheet.addMergedRegion(region4);
				}
			}
			if (i == 15) {
				rowi_cell1.setCellValue("场所地址");
				rowi_cell2.setCellValue(outData.getUnitAddress());
			}
			if (i == 16) {
				rowi_cell1.setCellValue("负责人");
				rowi_cell2.setCellValue(outData.getUnitChargePerson());
				rowi_cell3.setCellValue("联系电话");
				rowi_cell4.setCellValue(outData.getPersonPhone());
			}
			if (i == 17) {
				rowi_cell1.setCellValue("维保人数");
				rowi_cell2.setCellValue(outData.getMaintenancePersonCount());
				rowi_cell3.setCellValue("维保总次数：" + outData.getMaintenanceCount());
				CellRangeAddress region4 = new CellRangeAddress(17,17, (short) 3, (short) 4);
				sheet.addMergedRegion(region4);
			}
			if (i > 17 && i < 18 + userSize) {
				rowi_cell1.setCellValue("维保人员");
				rowi_cell2.setCellValue(outData.getUserData().get(i - 18).getUserName());
				rowi_cell3.setCellValue("维保次数：" + outData.getUserData().get(i - 18).getMaintenanceCount());
				CellRangeAddress region4 = new CellRangeAddress(i,i, (short) 3, (short) 4);
				sheet.addMergedRegion(region4);
			}
			rowi_cell0.setCellStyle(style4);
			rowi_cell1.setCellStyle(style3);
			rowi_cell2.setCellStyle(style3);
			rowi_cell3.setCellStyle(style3);
			rowi_cell4.setCellStyle(style3);
			rowi.setHeight((short) 600);
		}
		if(userSize != 0) {
			CellRangeAddress region8 = new CellRangeAddress(18, userSize + 17, (short) 1, (short) 1);
			sheet.addMergedRegion(region8);
		}
		// row14.setHeight((short)900);

		for (int i = 18 + userSize; i < 19 + userSize + siteSize; i++) {
			HSSFRow rowi = sheet.createRow(i);
			HSSFCell rowi_cell0 = rowi.createCell(0);
			HSSFCell rowi_cell1 = rowi.createCell(1);
			HSSFCell rowi_cell2 = rowi.createCell(2);
			HSSFCell rowi_cell3 = rowi.createCell(3);
			HSSFCell rowi_cell4 = rowi.createCell(4);
			if (i == 18 + userSize) {
				rowi_cell0.setCellValue("点\r\n位\r\n概\r\n况");
				rowi_cell1.setCellValue("点位名称");
				rowi_cell2.setCellValue("维保次数");
				rowi_cell3.setCellValue("维保人员");
				CellRangeAddress region4 = new CellRangeAddress(18 + userSize, 18 + userSize, (short) 3,
						(short) 4);
				sheet.addMergedRegion(region4);
				CellRangeAddress region5 = new CellRangeAddress(18 + userSize, 18 + userSize + siteSize, (short) 0,
						(short) 0);
				sheet.addMergedRegion(region5);
			}
			if (i > 18 + userSize && i < 19 + userSize + siteSize) {
				rowi_cell1.setCellValue(outData.getSiteData().get(i - 19 - userSize).getSiteName());
				rowi_cell2.setCellValue(outData.getSiteData().get(i - 19 - userSize).getMaintenanceCount());
				rowi_cell3.setCellValue(outData.getSiteData().get(i - 19 - userSize).getMaintenanceUserName());
				CellRangeAddress region4 = new CellRangeAddress(i,i, (short) 3, (short) 4);
				sheet.addMergedRegion(region4);
			}
			rowi_cell0.setCellStyle(style4);
			rowi_cell1.setCellStyle(style3);
			rowi_cell2.setCellStyle(style3);
			rowi_cell3.setCellStyle(style3);
			rowi_cell4.setCellStyle(style3);
			rowi.setHeight((short) 600);
		}
		for (int i = 19 + userSize + siteSize; i < 25 + userSize + siteSize; i++) {
			HSSFRow rowi = sheet.createRow(i);
			HSSFCell rowi_cell0 = rowi.createCell(0);
			HSSFCell rowi_cell1 = rowi.createCell(1);
			HSSFCell rowi_cell2 = rowi.createCell(2);
			HSSFCell rowi_cell3 = rowi.createCell(3);
			HSSFCell rowi_cell4 = rowi.createCell(4);
			rowi_cell0.setCellStyle(style4);
			rowi_cell1.setCellStyle(style3);
			rowi_cell2.setCellStyle(style3);
			rowi_cell3.setCellStyle(style3);
			rowi_cell4.setCellStyle(style3);
			if (i == 19 + userSize + siteSize) {
				rowi_cell0.setCellValue("意\r\n见\r\n总\r\n结");
				CellRangeAddress region9 = new CellRangeAddress(19 + userSize + siteSize, 24 + userSize + siteSize,
						(short) 0, (short) 0);
				sheet.addMergedRegion(region9);
				rowi_cell1.setCellValue("1、 本 报 告 书 统 一 使 用 国 际 标 准 A 4 型 纸 ， 由 各 维 保 公 司 按 照 规 定的 式 样 制 作 ， 封 面 、 封 底 采 用 2 0 0 g 铜 版 纸 印 刷 ， 其 他 页 电 脑 打 印 ，    装 订 成 册 。 报 告 书 设 定 的 栏 目 应 逐 项 填 写 完 整 、 准 确 ； 不 需 填 写 的 ，    应 在 空 白 处 填 写 “ 无 ” 。\r\n" + 
						"2、 本 报 告 书 包 含 封 面 、 《 维 保 情 况 汇 总 表 》 、 《 维 保 情 况 详 情 表 》及 封 底 。 每 份 报 告 书 应 按 顺 序 编 写 页 码 ， 在 “ 共 页 第 页 ” 处 分 别\r\n" + 
						"填 写 总 页 数 、 页 码 ， 如 ： “ 共 6 页 第 2 页 ” 。\r\n" + 
						"3、 本 报 告 书 中 的 《 维 保 情 况 汇 总 表 》 的 内 容 应 与 维 保 现 场 填 写的 《 维 保 情 况 详 情 表 》 内 容 一 致 ， 由 维 保 人 员 、 维 保 公 司 审 核 确 认 ，    并 加 盖 维 保 公 司 印 章 、 骑 缝 章 。");
			}
			rowi.setHeight((short) 600);
		}
		CellRangeAddress region10 = new CellRangeAddress(19 + userSize + siteSize, 24 + userSize + siteSize,
				(short) 1, (short) 4);
		sheet.addMergedRegion(region10);
		// 产生表格标题行
		HSSFRow sheetRow = sheet2.createRow(0);
		for (int i = 0; i < headers.length; i++) {
			HSSFCell sheetCell = sheetRow.createCell(i);
			sheetCell.setCellStyle(style);
			HSSFRichTextString text = new HSSFRichTextString(headers[i]);
			sheetCell.setCellValue(text);
		}

		// 遍历集合数据，产生数据行
		Iterator<T> it = dataset.iterator();
		int index = 0;

		HSSFFont sheetFont = workbook.createFont();
		sheetFont.setColor(HSSFColor.BLUE.index);
		while (it.hasNext()) {
			index++;
			row = sheet2.createRow(index);
			T t = (T) it.next();
			// 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
			Field[] fields = t.getClass().getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				HSSFCell celli = row.createCell(i);
				celli.setCellStyle(style2);
				Field field = fields[i];
				String fieldName = field.getName();
				String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
				Class tCls = t.getClass();
				Method getMethod = tCls.getMethod(getMethodName, new Class[] {});
				Object value = getMethod.invoke(t, new Object[] {});
				// 判断值的类型后进行强制类型转换
				String textValue = null;
				if (value instanceof Date) {
					Date date = (Date) value;
					textValue = UtilConv.date2Str(date, "yyyy-MM-dd");
				} else {
					// 其它数据类型都当作字符串简单处理
					if (value == null) {
						textValue = "";
					} else {
						textValue = HtmlUtils.htmlUnescape(value.toString());
					}

				}
				// 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
				if (textValue != null) {
					Pattern p = Pattern.compile("^//d+(//.//d+)?$");
					Matcher matcher = p.matcher(textValue);
					if (matcher.matches()) {
						// 是数字当作double处理
						celli.setCellValue(Double.parseDouble(textValue));
					} else {
						HSSFRichTextString richString = new HSSFRichTextString(textValue);
						// HSSFFont font3 = workbook.createFont();
						// font3.setColor(HSSFColor.BLUE.index);
						richString.applyFont(sheetFont);
						celli.setCellValue(richString);
					}
				}
			}

		}
		workbook.write(out);
	}
	
	/**
	 * 获取当前月份第一天和最后一天
	 * @param a （a=0,b=1  第一天）
	 * @param b	（a=1,b=0  最后一天）
	 * @return
	 */
	public String getMouthDay(int a,int b) {
		// 获取当月第一天和最后一天  
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");  
        String day;
        Calendar cale = Calendar.getInstance();
        // 获取前月的第一天  
        cale = Calendar.getInstance();  
        cale.add(Calendar.MONTH, a);  
        cale.set(Calendar.DAY_OF_MONTH, b);  
        day = format.format(cale.getTime());  
        return day;
	}

	public ByteArrayInputStream parse(final OutputStream out) throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		baos = (ByteArrayOutputStream) out;
		final ByteArrayInputStream swapStream = new ByteArrayInputStream(baos.toByteArray());
		return swapStream;
	}

}
