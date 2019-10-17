package com.gw.openApi.common.data.out;

import lombok.Data;

@Data
public class InterfaceRunningData {
    private String eqSystemName;//系统名称
    private String eqName;//设备名称
    private String detialName;//信号名称
    private String scope;//参考值范围
    private String analogUnit;//参数单位
    private String currentValue;//当前值
    private String installposition;//安装位置
    private String ioType;//数字量,模拟量
    private String digitalNormal;//正常值

}
