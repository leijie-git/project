package com.gw.inspect.data;

import lombok.Data;

/**
 * 巡查计划输入实体
 * @author zfg
 *
 */
@Data
public class InspectPlanInData {

	private String ID;//计划ID
	
	private String UnitID;//单位ID
	
	private String PlanName;//计划名称
	
	private String PlanStart;//开始时间
	
	private String PlanEnd;//结束时间
	
	private String DefaultUserID;//默认人（UT_Base_User）ID
	
	private String DefaultUserName;//默认人
	
	private String CreateUser;//创建人
	
	private String CreateDate;//创建时间

	private String Remark;//备注
	
	private String Status;//状态
	
	private String UserID;//当前登录用户id
	
	private Integer pageNumber;//第几页
	
    private Integer pageSize;//每页条数
   //监督人（计划责任人）id
	private String supervisorID;
//	监督人（计划责任人
	private String supervisorName;

}
