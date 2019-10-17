package com.gw.front.analysis.data;

import lombok.Data;

@Data
public class FrontAnaLysisLookupOutData {

	private String unitName;
	private String unitId;
	private String childstationnum;
	private String unitCode;
	private String lookupCount;//查岗次数
	private String answerCount;//应答次数
}
