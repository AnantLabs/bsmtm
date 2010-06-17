package com.beesphere.mtm;

public interface OutputProperties {
	boolean isOmitXmlDeclaration ();
	boolean isIndent ();
	boolean isStandAlone ();
	String getVersion ();
	String getMethod ();
	String getEncoding ();
	String getMediaType ();
	String getDoctypePublic ();
	String getDoctypeSystem ();
	String getCdataSectionElements ();
}
