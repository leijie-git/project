package com.gw.inspect.data;
import lombok.Data;
/**
 * 巡查点位管理输入类
 * @author zfg
 *
 */
@Data
public class InspectSiteInData {

	
	private String ID;//点位id
	
	private String UnitID;//单位id
	
	private String SiteName;//位置名称
	
	private String SiteCode;//位置编号
	
	private String SiteDesc;//位置描述
	
	private String Remark;//备注
	
	private String InspectCycle;//巡查周期
	
	private String InspectCycleType;//周期类型
	
	private String InspectCount;//次数
	
	private String TaskStart;//巡查开始时间
	
	private String TaskEnd;//巡查结束时间
	
	private String NFCCode;//NFC编号
	
	private Integer pageNumber;//第几页
	
    private Integer pageSize;//每页条数
    
    private String planID;//计划ID

	private String buildID;//建筑

	private String buildAreaID;//区域ID
     //执行人
	private String executorName;

	private String executorID;//执行人ID

	private String qrCode;//QR码

}
