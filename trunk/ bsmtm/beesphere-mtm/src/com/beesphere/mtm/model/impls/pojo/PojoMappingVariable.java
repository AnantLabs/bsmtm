package com.beesphere.mtm.model.impls.pojo;

import com.beesphere.mtm.model.MappingExpression;

public class PojoMappingVariable implements MappingExpression {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6967515183152483363L;
	
	private boolean var;
	private String as;
	private String name;
	private String select;
	private String content;
	private MappingExpression.VariableScope scope;
	
	public PojoMappingVariable (String name, String select, String content) {
		this.name = name;
		this.select = select;
		this.content = content;
	}

	public PojoMappingVariable (String name, String select, String content, VariableScope scope, boolean asVar) {
		this.name = name;
		this.var = asVar;
		this.scope = scope;
		if (asVar) {
			this.select = select;
		}
		if (!asVar) {
			this.select = null;
			this.content = content;
		} else if (select != null) {
			this.select = select;
		} else {
			this.content = content;
		}
	}

	public boolean isVar() {
		return var;
	}

	public void setVar(boolean asVar) {
		this.var = asVar;
	}

	public String getAs() {
		return as;
	}

	public void setAs(String as) {
		this.as = as;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSelect() {
		return select;
	}

	public void setSelect(String select) {
		this.select = select;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public MappingExpression.VariableScope getScope() {
		return scope;
	}

	public void setScope(MappingExpression.VariableScope scope) {
		this.scope = scope;
	}


}
