package com.gw.unit.data;

import lombok.Data;

@Data
public class UserExperienceOutData {
	private String id;
	private String userId;
	private String companyName;
	private String companyAddr;
	private String job;
	private String entryDate;
	private String quitDate;
	private String remark;
    private String card;

}
