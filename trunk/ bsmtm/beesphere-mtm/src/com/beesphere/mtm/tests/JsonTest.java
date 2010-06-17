package com.beesphere.mtm.tests;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

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

public class JsonTest extends AbstractTest {
	
	public static void main (String [] args) throws XslGeneratorException, XsdTypeNotFoundException, XsdInvalidEntityException, XsdParserException, IOException, JsonException, InvalidMappingTableException {
		String json = BinaryStreamsUtils.toString (new FileInputStream ("tests/mappings.mtm"));
		JsonMappingTable model = new JsonMappingTable (new JsonObject(json));
		Map<String, String> names = new HashMap<String, String> ();
		names.put ("output", "reId");
		model.setNames (names);
		
		XslGenerator generator = new DefaultXslGenerator (new OutputStreamWriter (new FileOutputStream ("tests/gen-xsl.xsl")));
		model.setOutput (String.valueOf (62));
			generator.generate (
				createSchema (new FileInputStream ("tests/output.xsd")), 
				model
			);
			model.reset ();
			System.out.println();
			System.out.println("==========================================================");
			System.out.println();
	}
	
	public static boolean isNullOrEmpty(String var) {
		return (var == null || var.trim().isEmpty());
	}
	
}
