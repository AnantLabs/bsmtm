package com.beesphere.mtm.model;

import com.beesphere.mtm.lang.XmlUtils;

public abstract class AbstractMappingTable implements MappingTable {
	
	private static final long serialVersionUID = -5272941932061337317L;

	protected boolean isValid (MappingLine line) {
		if (line == null) {
			return false;
		}
		return !XmlUtils.isNullOrEmpty (line.getExpression ()) || 
				!XmlUtils.isNullOrEmpty (line.getIterateOn ());
	}

}
