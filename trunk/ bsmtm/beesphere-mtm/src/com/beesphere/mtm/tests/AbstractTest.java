package com.beesphere.mtm.tests;

import java.io.InputStream;

import com.beesphere.xsd.XsdInvalidEntityException;
import com.beesphere.xsd.XsdParser;
import com.beesphere.xsd.XsdParserException;
import com.beesphere.xsd.XsdTypeNotFoundException;
import com.beesphere.xsd.model.XsdSchema;
import com.beesphere.xsd.util.XsdUtils;

public class AbstractTest {
	
	protected static XsdSchema createSchema (InputStream xsd) throws XsdTypeNotFoundException, XsdInvalidEntityException, XsdParserException {
		XsdParser parser = new XsdParser ();
		parser.parse (xsd);
		XsdSchema schema = parser.getSchema ();
		XsdUtils.organize (schema, false);
		return schema;	
	}
	
}
