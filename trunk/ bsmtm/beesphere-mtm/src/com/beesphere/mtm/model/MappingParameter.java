package com.beesphere.mtm.model;

public interface MappingParameter extends Selectable {
	
	enum MappingParameterKind {
		SYSTEM,
		USER
	}
	
	String 					name ();
	String 					value ();
	MappingParameterKind 	kind ();
	
}
