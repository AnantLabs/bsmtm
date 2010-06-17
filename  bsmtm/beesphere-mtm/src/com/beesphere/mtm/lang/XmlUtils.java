package com.beesphere.mtm.lang;

import java.io.IOException;
import java.io.Writer;

import com.beesphere.xsd.XmlStandard;

public class XmlUtils {
	
	static final String UNDERSCORE = "_";
	static final String AMP = "&";
	static final String APOS = "'";
	
	static final String ENT_AMP = "&amp;";
	static final String ENT_LT = "&lt;";
	static final String ENT_GT = "&gt;";
	static final String ENT_QUOT = "&quot;";
	static final String ENT_APOS = "&apos;";
	
	public static final String EMPTY = "";
	public static final String COLON = ":";
	public static final String QUOT = "\"";
	public static final String EQUALS = "=";
	public static final String SPACE = " ";

	public static final String NAME_ATTR = "name";
	public static final String VALUE_ATTR = "value";
	

	public static void writeStartTag (String tagName, boolean close, Writer buff) throws IOException {
		writeStartTag(null, tagName, close, buff);
	}
	
	public static void writeStartTag (String ns, String tagName, boolean close, Writer buff) throws IOException {
		buff.append (XmlStandard.LESS).append (tagName);
		if (close) {
			buff.append (XmlStandard.GREATER);
		} else {
			buff.append (XmlStandard.SPACE);
		}
	}
	
	public static void writeEndTag (String tagName, Writer buff) throws IOException {
		if (tagName != null) {
			buff.append (XmlStandard.LESS_END).append(tagName).append (XmlStandard.GREATER);
		} else {
			buff.append (XmlStandard.GREATER_START);
		}
	}
	
	public static void writeEndTag (String ns, String tagName, Writer buff) throws IOException {
		if (tagName != null) {
			buff.append (XmlStandard.LESS_END).append(tagName).append (XmlStandard.GREATER);
		} else {
			buff.append (XmlStandard.GREATER_START);
		}
	}
	
	public static void endTag (Writer buff) throws IOException {
		writeEndTag (null, buff);
	}
	
	public static void writeTag (String tagName, String value, Writer buff) throws IOException {
		writeTag (tagName, value, buff, true);
	}
	
	public static void writeTag (String tagName, String value, Writer buff, boolean filterValue) throws IOException {
		if (isNullOrEmpty (value)) {
			return;
		}
		writeStartTag (tagName, true, buff);
		if (filterValue) {
			buff.append (escapeXML (value));
		} else {
			buff.append (value);
		}
		writeEndTag (tagName, buff);
	}
	
	public static void writeNameAttribute (String ns, String value, boolean nullAsEmpty, boolean closeTag, Writer buff) throws IOException {
		if (value == null) {
			if (nullAsEmpty) {
				value = XmlStandard.EMPTY;
			} else {
				return;
			}
		}
		buff.append (NAME_ATTR).append (XmlStandard.EQUAL).append (XmlStandard.QUOT);
		if (ns != null) {
			buff.append (ns).append (XmlStandard.COLON);
		}
		buff.append (value).append (XmlStandard.QUOT);
		if (closeTag) {
			buff.append (XmlStandard.GREATER);
		} else {
			buff.append (XmlStandard.SPACE);
		}
	}
	
	public static void writeAttribute (String ns, String name, String value, boolean nullAsEmpty, boolean closeTag, Writer buff) throws IOException {
		if (value == null) {
			if (nullAsEmpty) {
				value = XmlStandard.EMPTY;
			} else {
				return;
			}
		}
		buff.append (name).append (XmlStandard.EQUAL).append (XmlStandard.QUOT).append (value).append (XmlStandard.QUOT);
		if (closeTag) {
			buff.append (XmlStandard.GREATER);
		} else {
			buff.append (XmlStandard.SPACE);
		}
	}
	
	public static void writeAttribute (String name, String value, boolean nullAsEmpty, boolean closeTag, Writer buff) throws IOException {
		if (value == null) {
			if (nullAsEmpty) {
				value = XmlStandard.EMPTY;
			} else {
				return;
			}
		}
		buff.append (name).append (XmlStandard.EQUAL).append (XmlStandard.QUOT).append (value).append (XmlStandard.QUOT);
		if (closeTag) {
			buff.append (XmlStandard.GREATER);
		} else {
			buff.append (XmlStandard.SPACE);
		}
	}
	
	public static boolean isNullOrEmpty(String var) {
		return (var == null || var.trim().equals (XmlStandard.EMPTY));
	}

	public static String escapeXML (String str) {
		return str.replaceAll(AMP, ENT_AMP)
					.replaceAll(XmlStandard.LESS, ENT_LT)
					.replaceAll(XmlStandard.GREATER, ENT_GT)
					.replaceAll(XmlStandard.QUOT, ENT_QUOT)
					.replaceAll(APOS, ENT_APOS);
	}

	public static String unescapeXml (String str) {
		if (isNullOrEmpty(str)) {
			return str;
		}
		return str.replace(ENT_AMP, AMP).replace(ENT_LT, XmlStandard.LESS).replace(ENT_QUOT, QUOT);
	}
	

}
