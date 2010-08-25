package com.beesphere.mtm.model.impls.json;

import com.beesphere.mtm.model.MappingLine;
import com.qlogic.commons.utils.json.JsonArray;
import com.qlogic.commons.utils.json.JsonObject;

public class JsonMappingLine implements MappingLine {
	
	private static final long serialVersionUID = 7779302209931760778L;
	
	public static final String OUTPUT		= "output";
	public static final String ORDER		= "order";
	public static final String TARGET		= "target";
	public static final String ITERATE_ON 	= "iterateOn";
	public static final String EXPRESSION 	= "expression";
	public static final String VALID 		= "valid";
	public static final String VARIABLES 	= "vars";
	
	protected JsonMappingTable mappingTable;
	
	protected JsonObject line;
	
	public JsonMappingLine (JsonMappingTable mappingTable) {
		this.mappingTable = mappingTable;
	}
	
	public JsonMappingLine (JsonObject line) {
		set (line);
	}
	
	protected void set (JsonObject line) {
		this.line = line;
	}
	
	protected boolean isValid () {
		return line.isNull(mappingTable.name (VALID)) || line.getBoolean (mappingTable.name (VALID));
	}
	
	@Override
	public String getTarget () {
		return getProperty (TARGET);
	}
	@Override
	public String getExpression () {
		return getProperty (EXPRESSION);
	}
	
	@Override
	public boolean isLoop () {
		return getIterateOn () != null;
	}
	
	@Override
	public String getIterateOn () {
		return getProperty (ITERATE_ON);
	}

	@Override
	public String getProperty (String name) {
		if (line == null) {
			return null;
		}
		return (String)line.get (mappingTable.name (name));
	}

	@SuppressWarnings("unchecked")
	@Override
	public String[] getExpressionsNames() {
		if (line == null) {
			return null;
		}
		Object oVars = line.get (mappingTable.name (VARIABLES));
		if (oVars == null) {
			return null;
		}
		
		JsonArray vars = (JsonArray)oVars;
		if (vars.count () == 0) {
			return null;
		}
		String [] variables = new String [vars.count ()];
		for (int i = 0; i < vars.count(); i++) {
			variables [i] = (String)vars.get(i);
		}
		return variables;
	}

	@Override
	public String getOutput() {
		return getProperty (mappingTable.name (OUTPUT));
	}
	
}
