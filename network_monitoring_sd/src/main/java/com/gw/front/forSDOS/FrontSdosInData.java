package com.gw.front.forSDOS;

import lombok.Data;

@Data
public class FrontSdosInData {
	private Integer pageIndex;
	private Integer pageSize;
	private String deviceCode;
	private String dtstart;
	private String dtend;
	private String token;
	private String privateKey;
}
