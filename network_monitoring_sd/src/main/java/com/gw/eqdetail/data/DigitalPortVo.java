package com.gw.eqdetail.data;

import lombok.Data;

@Data
public class DigitalPortVo {
    private String detailId;
    private Integer ioType = 1;
    private String ioPort;
    private String normalValue;
    private String goodName;
    private String badName;
    private String ioName;
    private String currentValue;
    private boolean isNormal;

}
