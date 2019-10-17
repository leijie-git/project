package com.gw.front.history.data;

import lombok.Data;

/**
 * 操作日志
 * 
 * @author Administrator
 *
 */
@Data
public class FrontHistoryOperationExport {
	private String account;
	private String userName;
	private String unitName;
	private String content;
	private String currentStatus;
	private String createDate;
	private String address;
}
