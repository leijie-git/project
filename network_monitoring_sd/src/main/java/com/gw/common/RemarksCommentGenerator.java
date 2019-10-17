package com.gw.common;


import java.util.List;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.internal.DefaultCommentGenerator;
import org.mybatis.generator.internal.util.StringUtility;



/**
 * mybatis generator 自定义comment生成器. 基于MBG 1.3.2.
 *
 */
public class RemarksCommentGenerator extends DefaultCommentGenerator {
	
	public void addFieldComment(Field field, IntrospectedTable introspectedTable,
			IntrospectedColumn introspectedColumn) {
		String remark = introspectedColumn.getRemarks();
		String columnName = introspectedColumn.getActualColumnName();
		List<IntrospectedColumn> primaryKey = introspectedTable.getPrimaryKeyColumns();
		for (IntrospectedColumn pk : primaryKey) {
			if (columnName.equals(pk.getActualColumnName())) {
				remark += " (主健ID)";
				continue; // 主健属性上无需生明可选项跟必填项介绍
			}
			if (StringUtility.stringHasValue(remark)) {
				remark += introspectedColumn.isNullable() ? "(可选项)" : "(必填项)";
			}
		}
		String defaultValue = introspectedColumn.getDefaultValue();
		remark += null != defaultValue ? "  (默认值为: " + defaultValue + ")" : " (无默认值)";
		field.addJavaDocLine("/** " + remark + " */");
	}
}
