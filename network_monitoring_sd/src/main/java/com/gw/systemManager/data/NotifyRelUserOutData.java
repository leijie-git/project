package com.gw.systemManager.data;

import lombok.Data;

@Data
public class NotifyRelUserOutData {

	private String id;
	private String username;
	private String account;
	private boolean state = false;
}
