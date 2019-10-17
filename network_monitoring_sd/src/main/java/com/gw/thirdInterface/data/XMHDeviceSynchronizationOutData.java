package com.gw.thirdInterface.data;

import lombok.Data;

/**
 * 新门海设备同步类 
 * @author zfg
 *
 */
@Data
public class XMHDeviceSynchronizationOutData {

	private Integer flag;//:1 新增 2  修改
	
	private String fsocial_uuid;//社会单位标识
	
	private String fdevice_uuid;//设备标识
	
	private String fdevicecnname;//设备名称
	
	private String fdeviceclass;//设备分类
	
	private String ftransmission_id;//监控点标识
	
	private String fcontrhost_code;//消控主机编号
	
	private String floop_num;//回路(部件区号)
	
	private String fdeviceaddress;//设备地址(部件位号)
	
	private String fposition;//安装位置
	
	private String fis_active;//状态
}
