package com.gw.inspect.data;
import lombok.Data;
/**
 * 巡查点位管理输出类
 * @author zfg
 *
 */

@Data
public class InspectSiteOutData {

	private String ID;//点位id
	
	private String UnitID;//单位id

	private String SiteClassName;//分类名称
	
	private String SiteName;//位置名称
	
	private String SiteDesc;//位置描述
	
	private String SiteCode;//位置编号
	
	private String Remark;//备注
	
	private String InspectCycle;//巡查周期
	
	private String InspectCycleType;//周期类型
	
	private String InspectCount;//次数
	
	private String TaskStart;//巡查开始时间
	
	private String TaskEnd;//巡查结束时间
	
	private String NFCCode;//NFC编号
	
	private String Status;//是否生成任务
	
	private String planDetailID;//巡查计划详情ID
	
	private String TaskUserID;//巡查人id
	
	private String TaskUserName;//巡查人姓名

	private String buildID;//建筑

	private String buildAreaID;//区域ID

	private String executorID;//执行人ID

	private String executorName;//执行人

	private String qrCode;//QR码

	private String unitName;//单位名称

	private String userName;//人员名称

	//监督人ID
	private String supervisorID;
	//检查点个数
	private Integer siteCount;
	//已完成检查个数
	private String okCheckCount;
	//转单状态
	private String ReceiveStatus;
}
