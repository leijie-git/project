package com.gw.equipment.data;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Data;

@Data
public class SdInterfaceOutData {
	//12位单位编号(notnull)
	@JSONField(name = "OwnerCode")
	 public String ownerCode;
	 //设备子号(notnull)
	@JSONField(name = "DeviceNo")
	 public Integer deviceNo;
	 //端口类型 1：模拟量 2：数字量(notnull)
	@JSONField(name = "IOType")
	 public Integer iOType;
	 //端口号，索引从1开始(notnull)
	@JSONField(name = "IOPort")
	 public Integer iOPort ;
	 //端口名称
	@JSONField(name = "IOName")
	 public String iOName;
	 //模拟量参数 k,b，使用英文逗号分隔两个小数 公式为y=k*x+b
	@JSONField(name = "AnalogPara")
	 public String analogPara;
	 //模拟量上限
	@JSONField(name = "AnalogUp")
	 public Double analogUp;
	 //模拟量下限
	@JSONField(name = "AnalogDown")
	 public Double analogDown ;
	 //模拟量单位
	@JSONField(name = "AnalogUnitID")
	 public String analogUnitID;
	 //正常电平 低电平：0 高电平：1
	@JSONField(name = "DigitalNormal")
	 public Integer digitalNormal ;
	 //正常名
	@JSONField(name = "GoodName")
	 public String goodName ;
	 //异常名
	@JSONField(name = "BadName")
	 public String badName;
	 
	 //系统类型 参见附录(notnull)
	@JSONField(name = "SysType")
	 public Integer sysType ;
	 //模拟量值类型 参见附录
	@JSONField(name = "Reserve")
	 public String reserve ;
	 //使用-1表示删除此接口(notnull)
	@JSONField(name = "Status")
	 public Integer status ;
	 
}
