package com.gw.equipment.data;

import com.alibaba.fastjson.annotation.JSONField;

public class SdDevicesOutData {
	//12位设备编号
	@JSONField(name = "OwnerCode")
	 public String ownerCode;
	 //设备索引 1：报警主机 2：传输装置 3：RTU
	@JSONField(name = "DeviceIndex")
	 public String deviceIndex;
	 //设备子号 1-255
	@JSONField(name = "DeviceNo")
	 public String deviceNo ;
	 //设备名称
	@JSONField(name = "DeviceName")
	 public String deviceName ;
	//使用-1表示删除此设备
	@JSONField(name = "DeviceStatus")
	 public String deviceStatus ;
	//部位号解析方式
	@JSONField(name = "NodeParse")
	 public String nodeParse ;
	//是否是独立上线设备，区别于跟随传输装置上来的设备
	@JSONField(name = "IsIndependent")
	 public String isIndependent ;

}
