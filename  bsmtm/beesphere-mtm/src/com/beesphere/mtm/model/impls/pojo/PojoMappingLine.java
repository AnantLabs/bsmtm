package com.beesphere.mtm.model.impls.pojo;

import com.beesphere.mtm.model.MappingLine;

public class PojoMappingLine implements MappingLine {
	
	private static final long serialVersionUID = 7779302209931760778L;

	private String target;
	private String expression;
	private String iterateOn;
	private boolean loop;
	private String[] expressionsNames;
	
	@Override
	public String getTarget () {
		return target;
	}
	public void setTarget (String target) {
		this.target = target;
	}
	@Override
	public String getExpression () {
		return expression;
	}
	public void setExpression (String expression) {
		this.expression = expression;
	}
	@Override
	public boolean isLoop () {
		return loop;
	}
	public void setLoop (boolean loop) {
		this.loop = loop;
	}
	@Override
	public String getIterateOn () {
		return iterateOn;
	}
	public void setIterateOn (String iterateOn) {
		this.iterateOn = iterateOn;
	}

	public void setExpressionsNames(String[] expressionsNames) {
		this.expressionsNames = expressionsNames;
	}
	@Override
	public String getOutput() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String[] getExpressionsNames() {
		// TODO Auto-generated method stub
		return expressionsNames;
	}

}
