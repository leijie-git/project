package com.gw.front.index.data;

import java.util.List;

import lombok.Data;

@Data
public class FrontFireOutData {
	private Integer dealCount;//火警已处理数量
	private Integer noDealCount;//火警未处理数量
	private List<FrontFireInfoOutData> fireInfoList;
}
