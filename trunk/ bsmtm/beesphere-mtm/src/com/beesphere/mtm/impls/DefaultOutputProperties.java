package com.beesphere.mtm.impls;

import com.beesphere.mtm.OutputProperties;

public class DefaultOutputProperties implements OutputProperties {

	private boolean omitXmlDeclaration;
	private boolean indent;
	private String method;
	private String version;
	private String encoding;
	private String mediaType;
	private String doctypePublic;
	private String doctypeSystem;
	private String cdataSectionElements;
	private boolean standAlone;
	
	
	public DefaultOutputProperties() {
	}
	public DefaultOutputProperties(boolean omitXmlDeclaration, boolean indent, String charset,
					String method, String version) {
		
		this.omitXmlDeclaration = omitXmlDeclaration;
		this.indent = indent;
		this.encoding = charset;
		this.method = method;
		this.version = version;
		
	}
	
	public boolean isOmitXmlDeclaration() {
		return omitXmlDeclaration;
	}
	public void setOmitXmlDeclaration(boolean omitXmlDeclaration) {
		this.omitXmlDeclaration = omitXmlDeclaration;
	}
	public boolean isIndent() {
		return indent;
	}
	public void setIndent(boolean indent) {
		this.indent = indent;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getEncoding() {
		return encoding;
	}
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
	public String getMediaType() {
		return mediaType;
	}
	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}
	public String getDoctypePublic() {
		return doctypePublic;
	}
	public void setDoctypePublic(String doctypePublic) {
		this.doctypePublic = doctypePublic;
	}
	public String getDoctypeSystem() {
		return doctypeSystem;
	}
	public void setDoctypeSystem(String doctypeSystem) {
		this.doctypeSystem = doctypeSystem;
	}
	public String getCdataSectionElements() {
		return cdataSectionElements;
	}
	public void setCdataSectionElements(String cdataSectionElements) {
		this.cdataSectionElements = cdataSectionElements;
	}
	public boolean isStandAlone() {
		return standAlone;
	}
	public void setStandAlone(boolean standAlone) {
		this.standAlone = standAlone;
	}
}
