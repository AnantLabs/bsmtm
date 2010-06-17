package com.beesphere.mtm.model.impls.pojo;

import com.beesphere.mtm.model.MappingParameter;

public class PojoMappingParameter implements MappingParameter {

	private static final long serialVersionUID = 8352562751436995882L;

	private String name;
	private String value;
	private MappingParameterKind kind;
	
	public PojoMappingParameter (String name, String value, MappingParameterKind kind) {
		this.name = name;
		this.value = value;
		this.kind = kind;
	}
	
	public PojoMappingParameter (String name, String value) {
		this (name, value, MappingParameterKind.USER);
	}
	
	public PojoMappingParameter (String name) {
		this (name, null);
	}
	
	@Override
	public String name() {
		return name;
	}

	@Override
	public String value () {
		return value;
	}

	@Override
	public MappingParameterKind kind () {
		return kind;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setKind(MappingParameterKind kind) {
		this.kind = kind;
	}

	@Override
	public String getOutput() {
		// TODO Auto-generated method stub
		return null;
	}

}
