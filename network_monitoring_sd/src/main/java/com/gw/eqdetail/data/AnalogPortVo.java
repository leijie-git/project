package com.gw.eqdetail.data;

import lombok.Data;

@Data
public class AnalogPortVo {
    private Integer iotype = 2;
    private String detailId;
    private String detialName;
    private String ioPort;
    private String analogK;
    private String analogB;
    private String analogWarningUp;
    private String analogWarningDown;
    private String analogup;//最大值
    private String analogdown;//最小值
    private String currentValue;
    private String analogUnit;
    private boolean isNormal;

}
