package com.gw.front.history.data;

import lombok.Data;

/**
 * 操作日志
 * 
 * @author Administrator
 *
 */
@Data
public class FrontHistoryOperationOutData {
	private String account;
	private String userName;
	private String unitName;
	private String unitCode;
	private String content;
	private String url;
	private String createDate;
	private String address;
	private String currentStatus;
}
