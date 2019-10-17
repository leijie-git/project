package com.gw.front.analysis.data;

import lombok.Data;

@Data
public class FrontAnaLysisLookupExport {
	private String unitCode;
	private String childstationnum;
	private String unitName;
	private String lookupCount;//查岗次数
	private String answerCount;//应答次数
	private String online;//在岗率
}
