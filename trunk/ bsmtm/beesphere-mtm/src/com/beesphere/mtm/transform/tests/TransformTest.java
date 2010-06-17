package com.beesphere.mtm.transform.tests;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.beesphere.mtm.transform.TransformerException;
import com.beesphere.mtm.transform.impls.XslTransformer;

public class TransformTest {

	public static void main(String[] args) throws FileNotFoundException, TransformerException {
		
		XslTransformer transformer = new XslTransformer (new FileInputStream ("tests/gen-xsl.xsl"));
		transformer.transform(new FileInputStream ("tests/input.xml"), new FileOutputStream ("tests/output.xml"));
	}
	
}
