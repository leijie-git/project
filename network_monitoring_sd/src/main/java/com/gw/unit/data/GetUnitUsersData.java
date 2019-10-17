package com.gw.unit.data;

import java.util.List;

import lombok.Data;

@Data
public class GetUnitUsersData {
	private String id;
	private String userName;
	private List<GetUnitUsersData> hasUsers;
	private List<GetUnitUsersData> noUsers;
}
