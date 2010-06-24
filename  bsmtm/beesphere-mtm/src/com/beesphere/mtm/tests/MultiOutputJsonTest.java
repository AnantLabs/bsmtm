package com.beesphere.mtm.tests;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

import com.beesphere.mtm.InvalidMappingTableException;
import com.beesphere.mtm.XslGeneratorException;
import com.beesphere.mtm.impls.DefaultXslGenerator;
import com.beesphere.mtm.model.impls.json.JsonMappingTable;
import com.beesphere.xsd.XsdInvalidEntityException;
import com.beesphere.xsd.XsdParserException;
import com.beesphere.xsd.XsdTypeNotFoundException;
import com.qlogic.commons.utils.io.BinaryStreamsUtils;
import com.qlogic.commons.utils.json.JsonException;
import com.qlogic.commons.utils.json.JsonObject;

public class MultiOutputJsonTest extends AbstractTest {
	
	public static void main (String [] args) 
		throws XslGeneratorException, XsdTypeNotFoundException, XsdInvalidEntityException, 
		XsdParserException, IOException, JsonException, InvalidMappingTableException {
		
		String json = BinaryStreamsUtils.toString (new FileInputStream ("tests/two_mappings.mtm"));
		JsonMappingTable model = new JsonMappingTable (new JsonObject(json));
		
		DefaultXslGenerator generator = new DefaultXslGenerator (new FileWriter(new File ("tests/gen-out1.xsl")));
		model.setOutput ("out1");
		generator.generate (
			createSchema (new FileInputStream ("tests/output1.xsd")), 
			model
		);
		
		model.reset ();
		generator.reset ();
		generator.setWriter(new FileWriter(new File ("tests/gen-out2.xsl")));
		model.setOutput ("out2");
		generator.generate (
			createSchema (new FileInputStream ("tests/output2.xsd")), 
			model
		);
		model.reset ();
		generator.reset ();
	}
}
