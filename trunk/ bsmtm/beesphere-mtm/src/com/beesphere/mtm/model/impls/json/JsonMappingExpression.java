package com.beesphere.mtm.model.impls.json;

import com.beesphere.mtm.lang.XmlUtils;
import com.beesphere.mtm.model.MappingExpression;
import com.qlogic.commons.utils.json.JsonObject;

public class JsonMappingExpression implements MappingExpression {

	private static final long serialVersionUID = 7880406935220124967L;
	
	public static final String CONTENT	= "text";
	public static final String ASVAR	= "asVar";
	public static final String SCOPE = "scope";
	public static final String AS = "as";
	public static final String SELECT = "select";
	public static final String NAME = "name";
	public static final String GLOBAL_SCOPE = "g";

	private JsonObject variable;
	private String name;
	
	protected JsonMappingTable mappingTable;
	
	public JsonMappingExpression (JsonMappingTable mappingTable) {
		this.mappingTable = mappingTable;
	}
	
	public JsonMappingExpression (JsonMappingTable mappingTable, String name, JsonObject variable) {
		this.mappingTable = mappingTable;
		set (name, variable);
	}
	
	public MappingExpression set (String name, JsonObject variable) {
		this.name = name;
		this.variable = variable;
		return this;
	}

	@Override
	public String getSelect() {
		return XmlUtils.unescapeXml((String)variable.get(mappingTable.name (SELECT)));
	}
	
	@Override
	public String getContent() {
		return XmlUtils.unescapeXml((String)variable.get(mappingTable.name (CONTENT)));
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public VariableScope getScope() {
		String scope = (String)variable.get(mappingTable.name (SCOPE));
		return (scope == null || scope.equals(mappingTable.name (GLOBAL_SCOPE))) ? VariableScope.GLOBAL : VariableScope.LOCAL;
	}

	@Override
	public boolean isVar() {
		if (variable.get(mappingTable.name (ASVAR)) == null) {
			return false;
		}
		return (Boolean)variable.get(mappingTable.name (ASVAR));
	}

}
