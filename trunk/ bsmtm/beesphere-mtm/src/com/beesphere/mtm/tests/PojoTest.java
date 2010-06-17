package com.beesphere.mtm.tests;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import com.beesphere.mtm.XslGenerator;
import com.beesphere.mtm.XslGeneratorException;
import com.beesphere.mtm.impls.DefaultXslGenerator;
import com.beesphere.mtm.model.impls.pojo.PojoMappingLine;
import com.beesphere.mtm.model.impls.pojo.PojoMappingTable;
import com.beesphere.xsd.XsdInvalidEntityException;
import com.beesphere.xsd.XsdParserException;
import com.beesphere.xsd.XsdTypeNotFoundException;

public class PojoTest extends AbstractTest {
	
	public static void main (String [] args) throws FileNotFoundException, XslGeneratorException, XsdTypeNotFoundException, XsdInvalidEntityException, XsdParserException {
		
		XslGenerator generator = new DefaultXslGenerator ();
		
		PojoMappingTable mappingTable = new PojoMappingTable ();
		PojoMappingLine line = new PojoMappingLine ();
		line.setTarget ("/data/rs/r");
		mappingTable.add (line);
		
		generator.generate (
			createSchema (new FileInputStream ("tests/sample.xsd")), 
			mappingTable
		);
		
	}
	
}
