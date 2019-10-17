package com.gw.inspect.data;

import lombok.Data;

/**
 * 巡查点导入
 *
 * @author zfg
 */
@Data
public class InspectInData {
    private String siteCode;//位置编号
    private String siteName;//位置名称
    private String siteDesc;//位置描述
    private String inspectCycleType;//周期类型
    private String inspectCount;//次数
    private String nFCCode;//NFC编号
    private String inspectId;//巡查点分类名称
    private String checkInfo1;//检查内容1
    private String checkInfo2;//检查内容2
    private String checkInfo3;//检查内容3
    private String checkInfo4;//检查内容4
    private String checkInfo5;//检查内容5
    private String checkInfo6;//检查内容6
    private String checkInfo7;//检查内容7
    private String checkInfo8;//检查内容8
}