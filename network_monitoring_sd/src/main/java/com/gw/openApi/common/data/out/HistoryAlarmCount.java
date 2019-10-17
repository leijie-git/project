package com.gw.openApi.common.data.out;

import lombok.Data;

@Data
public class HistoryAlarmCount {
    private String unitName;
    private String unitId;
    private String unitCode;
    private int fireCount;
    private int faultCount;
    private int adminCount;
    private int startCount;
    private int feedCount;
    private int echo;

}
