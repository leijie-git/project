package com.gw.front.history.data;

import com.gw.openApi.common.data.CheckAcountBaseData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class FrontHistoryInData extends CheckAcountBaseData {
	@ApiModelProperty("当前页码")
	private Integer pageNumber;
	@ApiModelProperty("页面大小")
	private Integer pageSize;
	@ApiModelProperty("dateType")
	private String dateType;
	@ApiModelProperty("开始时间")
	private String startDate;
	@ApiModelProperty("结束时间")
	private String endDate;
	@ApiModelProperty("关键字查询")
	private String keyword;
	@ApiModelProperty("单位ID")
	private String unitId;
	@ApiModelProperty("status")
	private String status;
	@ApiModelProperty("dealResult")
	private String dealResult;
	@ApiModelProperty("记录类型")
	private String type;//记录类型
	@ApiModelProperty("数据库来源")
	private String database;
	@ApiModelProperty("upStatus")
	private String upStatus;
	@ApiModelProperty("softVersion")
	private String softVersion;
	@ApiModelProperty("eqSystem")
	private String eqSystem;
	@ApiModelProperty("id")
	private String id;
	@ApiModelProperty("姓名")
	private String userName;
	@ApiModelProperty("手机")
	private String phone;
	@ApiModelProperty("年")
	private Integer year;
	@ApiModelProperty("月")
	private Integer month;
	@ApiModelProperty("建筑ID")
	private String buildId;
	@ApiModelProperty("statusList")
	private List<String> statusList;
	@ApiModelProperty("是否处理")
	private String isDeal;
	@ApiModelProperty("是否可以点名")
	private String isNaming;//是否可以点名
	@ApiModelProperty("设备ID")
	private String netDeviceId;//
	@ApiModelProperty("单位名称")
	private String unitName;//
	@ApiModelProperty("sql查找类型")
	private String selectType;//

	private String sqlType;//
	private String ioType;//1：模拟量 2：数字量
}
