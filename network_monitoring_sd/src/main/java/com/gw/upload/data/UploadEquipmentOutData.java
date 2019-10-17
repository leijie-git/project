package com.gw.upload.data;

import lombok.Data;

@Data
public class UploadEquipmentOutData {
	//EquipmentDetailGW
	private String id;
    private String name;
    private Integer iotype;//信号类型（1：模拟量 2：数字量3,输出）
    private Integer ioport;
    private String reserve;
    private String isuploada;
    private String isuploadb;
    //addressRel
    private String partcode;
    private String adress;
    private Integer isRTU;
}
