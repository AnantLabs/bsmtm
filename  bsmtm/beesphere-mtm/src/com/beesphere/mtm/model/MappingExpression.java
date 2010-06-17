package com.beesphere.mtm.model;

import java.io.Serializable;


/**
 * Represents an xsl variable model
 * 
 * <xsl:variable name="n" select="xsl expression" as="[xs:string][document-node()]...">
 * 		<content/>
 * </xsl:variable>
 * 
 * 
 */

public interface MappingExpression extends Serializable {
	
	enum VariableScope {
		GLOBAL,
		LOCAL
	}
	
	String 					getName ();
	boolean 				isVar ();
	VariableScope 			getScope ();
	String 					getContent (); // any subsequent node content 
	String 					getSelect (); // any subsequent node content 
}
