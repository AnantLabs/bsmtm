package com.beesphere.mtm.model;


public interface MappingLine extends Selectable {
	String getOutput ();
	String getTarget ();
	String getExpression ();
	String getIterateOn ();
	boolean isLoop ();
	String[] getExpressionsNames ();
}
