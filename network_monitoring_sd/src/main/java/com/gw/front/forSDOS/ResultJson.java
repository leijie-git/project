package com.gw.front.forSDOS;

import lombok.Data;

@Data
public class ResultJson implements java.io.Serializable {

	private static final long serialVersionUID = 8722126593920041253L;

	private boolean IsSuccess = false;

	private String Message = "";

	private Object data = null;

}
