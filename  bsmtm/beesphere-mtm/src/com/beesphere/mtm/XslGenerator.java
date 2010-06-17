package com.beesphere.mtm;

import java.io.Serializable;

import com.beesphere.mtm.model.MappingTable;
import com.beesphere.xsd.model.XsdSchema;

public interface XslGenerator extends Serializable {
	void generate (XsdSchema schema, MappingTable mappingTable) throws XslGeneratorException;
	void reset ();
}
