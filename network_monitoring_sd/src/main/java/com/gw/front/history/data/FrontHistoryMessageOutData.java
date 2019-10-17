package com.gw.front.history.data;

import lombok.Data;

@Data
public class FrontHistoryMessageOutData {

	private String unitCode;
	private String childStationNum;
    private String unitName;

    private String sender;

    private String receiver;

    private String phone;

    private String sendDate;

    private String status;

    private String messageType;

    private String content;
    
    private Integer sendCount;
    
    private Integer failCount;
    
    private Integer succCount;
}
