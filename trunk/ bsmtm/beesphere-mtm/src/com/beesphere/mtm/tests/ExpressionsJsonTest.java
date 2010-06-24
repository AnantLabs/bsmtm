package com.beesphere.mtm.tests;

import java.io.FileInputStream;
import java.io.IOException;

import com.beesphere.mtm.InvalidMappingTableException;
import com.beesphere.mtm.XslGenerator;
import com.beesphere.mtm.XslGeneratorException;
import com.beesphere.mtm.impls.DefaultXslGenerator;
import com.beesphere.mtm.model.impls.json.JsonMappingTable;
import com.beesphere.xsd.XsdInvalidEntityException;
import com.beesphere.xsd.XsdParserException;
import com.beesphere.xsd.XsdTypeNotFoundException;
import com.qlogic.commons.utils.io.BinaryStreamsUtils;
import com.qlogic.commons.utils.json.JsonException;
import com.qlogic.commons.utils.json.JsonObject;

public class ExpressionsJsonTest extends AbstractTest {
	
	public static void main (String [] args) 
		throws XslGeneratorException, XsdTypeNotFoundException, XsdInvalidEntityException, 
		XsdParserException, IOException, JsonException, InvalidMappingTableException {
		
		String json = BinaryStreamsUtils.toString (new FileInputStream ("tests/expressions.mtm"));
		JsonMappingTable model = new JsonMappingTable (new JsonObject(json));
		
		XslGenerator generator = new DefaultXslGenerator ();
		model.setOutput ("exps");
		generator.generate (
			createSchema (new FileInputStream ("tests/output.xsd")), 
			model
		);
		model.reset ();
	}
}
