package com.gw.front.index.data;


import lombok.Data;

@Data
public class FrontOperationLogInData {

    private String userId;

    private String content;

    private String url;

    private String currentStatus;
    
    private String recordType;
    
    private String address;

    private String remark;
    
    private String msgFrom;
    
}
