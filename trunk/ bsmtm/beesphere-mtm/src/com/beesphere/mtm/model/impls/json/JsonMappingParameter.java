package com.beesphere.mtm.model.impls.json;

import com.beesphere.mtm.model.MappingParameter;

public class JsonMappingParameter implements MappingParameter {

	private static final long serialVersionUID = 8352562751436995882L;

	private String output;
	private String name;
	private String value;
		
	public JsonMappingParameter () {
		this (null);
	}
	
	public JsonMappingParameter (String output) {
		this (output, null);
	}
	
	public JsonMappingParameter (String output, String name) {
		this (output, name, null);
	}
	
	public JsonMappingParameter (String output, String name, String value) {
		this.output = output;
		this.name = name;
		this.value = value;
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
		return MappingParameterKind.USER;
	}

	public MappingParameter set (String name) {
		return this.set (name, null);
	}
	
	public MappingParameter set (String name, String value) {
		this.name = name;
		this.value = value;
		return this;
	}
	
	public MappingParameter reset () {
		return this.set (null, null);
	}

	@Override
	public String getOutput () {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}
}
