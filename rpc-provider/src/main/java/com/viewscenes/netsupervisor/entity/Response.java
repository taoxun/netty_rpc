package com.viewscenes.netsupervisor.entity;

import java.io.Serializable;

public class Response  {

	private static final long serialVersionUID = -1023480952777229650L;
	
	private String requestId;
	private int code;
	private String error_msg;
	private Object data;
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getError_msg() {
		return error_msg;
	}
	public void setError_msg(String error_msg) {
		this.error_msg = error_msg;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
}
