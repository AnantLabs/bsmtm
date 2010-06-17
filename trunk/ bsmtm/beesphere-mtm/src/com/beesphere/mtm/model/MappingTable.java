package com.beesphere.mtm.model;

import java.io.Serializable;
import java.util.Iterator;

import com.beesphere.mtm.InvalidMappingTableException;

public interface MappingTable extends Serializable {

	MappingLine find (String path, String attribute);
	
	MappingLine findIteration (MappingLine where);
	
	boolean isContributing (String path);

	boolean isExpressionContributing (String expName) throws InvalidMappingTableException;

	int getInParametersCount ();
	MappingParameter getInParameter (int index);

	Iterator<String> getExpressionsNames ();
	MappingExpression getExpression (String name) throws InvalidMappingTableException;

	String expression (MappingLine current) throws InvalidMappingTableException;

	MappingParameter getOutParameter (String name);

	String [] namespaces ();

	void reset ();
	
	int count ();
	
}