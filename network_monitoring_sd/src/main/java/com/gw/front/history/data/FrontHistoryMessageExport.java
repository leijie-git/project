package com.gw.front.history.data;

import lombok.Data;

@Data
public class FrontHistoryMessageExport {

	private String unitCode;
	private String childStationNum;
    private String unitName;

    private String receiver;

    private String phone;
    
    private Integer sendCount;
}
