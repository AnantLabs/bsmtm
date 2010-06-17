package com.beesphere.mtm.transform.tests;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.beesphere.mtm.transform.TransformerException;
import com.beesphere.mtm.transform.impls.XslTransformer;

public class XslBasicTransformer {
	public static void main(String[] args) throws FileNotFoundException {
		
		if (args == null || args.length < 3) {
			System.out.print(">> You must provide the 3 mandatory arguments : xsl file [Arg1], xml input file [Arg3], xml output file [Arg3]");
			System.exit(0);
		}
		
		FileInputStream inXsl = new FileInputStream (args[0]);
		FileInputStream inXml = new FileInputStream (args[1]);
		FileOutputStream outXml = new FileOutputStream (args[2]);
		
		XslTransformer transformer = new XslTransformer (inXsl);
		try {
			transformer.transform(inXml, outXml);
		} catch (TransformerException e) {
			e.printStackTrace();
			System.exit(0);
		} finally {
			try {
				if (inXsl != null) {
					inXsl.close();
				}
				if (outXml != null) {
					outXml.close();
				}
				if (inXml != null) {
					inXml.close();
				}
			} catch (IOException ioex) {
			}
		}
		System.out.print(">> Transformation processedd successfuly");
	}
	

}
