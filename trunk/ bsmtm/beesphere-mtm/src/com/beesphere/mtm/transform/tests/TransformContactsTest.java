package com.beesphere.mtm.transform.tests;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Date;

import com.beesphere.mtm.transform.TransformerException;
import com.beesphere.mtm.transform.impls.XslTransformer;

public class TransformContactsTest {

	public static void main(String[] args) throws FileNotFoundException, TransformerException {
		
		XslTransformer transformer = new XslTransformer (new FileInputStream ("tests/contacts.xsl"));
		transformer.addParameter("NOW", new Date ().toString());
		transformer.addParameter("com.soa.defaults.requestDate", new Date ().toString());
		transformer.transform(new FileInputStream ("tests/companies.xml"), 
					new FileOutputStream ("tests/contacts.xml"));
	}
	
}
