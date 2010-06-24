package com.beesphere.mtm.transform;

import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.transform.stream.StreamResult;

import org.xml.sax.InputSource;

public interface Transformer {

	public void transform (InputStream source, OutputStream result) throws TransformerException;
	public void transform (InputSource source, StreamResult result) throws TransformerException;
	public void addParameter (String name, String value);
}
