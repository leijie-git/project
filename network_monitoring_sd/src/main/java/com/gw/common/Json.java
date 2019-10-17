package com.gw.common;

public class Json implements java.io.Serializable {

	private static final long serialVersionUID = 8722126593920041253L;

	private boolean success = false;

	private String msg = "";

	private Object obj = null;

//	private String code = "0";// 

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
