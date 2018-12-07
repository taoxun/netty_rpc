package com.viewscenes.netsupervisor.entity;

import java.io.Serializable;

public class Request  {
	
	private static final long serialVersionUID = 7929047349488932740L;
	
	private String id;
	private String className;// 类名
	private String methodName;// 函数名称
	private Class<?>[] parameterTypes;// 参数类型
	private Object[] parameters;// 参数列表
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public Class<?>[] getParameterTypes() {
		return parameterTypes;
	}
	public void setParameterTypes(Class<?>[] parameterTypes) {
		this.parameterTypes = parameterTypes;
	}
	public Object[] getParameters() {
		return parameters;
	}
	public void setParameters(Object[] parameters) {
		this.parameters = parameters;
	}
	
}
