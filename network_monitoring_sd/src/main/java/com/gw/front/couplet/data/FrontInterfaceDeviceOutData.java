package com.gw.front.couplet.data;

import java.util.List;

import lombok.Data;

@Data
public class FrontInterfaceDeviceOutData {
	private String id;
	private String eqName;
	private String classCode;
	private String buildareaname;//区域
	private String buildImgbg;
	private String pointVideoId;
	private List<FrontInterFaceStatusOutData> interfaceList;
}
