package com.gw.util;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

@Data
public class HttpJson {
	private boolean success = false;

	private String msg = "";

	private JSONObject obj;

}
