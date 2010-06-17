package com.beesphere.mtm.lang;

public interface XslStandard {

	String NAME = "name";
	String EMPTY_STRING = "''";
	
	String NO = "no";
	String YES = "yes";
	
	String OUTPUT_OMIT_XML_DECLARATION = "omit-xml-declaration";
	String OUTPUT_INDENT = "indent";
	String OUTPUT_VERSION = "version";
	String OUTPUT_ENCODING = "encoding";
	String OUTPUT_STANDALONE = "standalone";
	String OUTPUT_DOCTYPE_PUBLIC = "doctype-public";
	String OUTPUT_DOCTYPE_SYSTEM = "doctype-system";
	String OUTPUT_METHOD = "method";
	String OUTPUT_MEDIA_TYPE = "media-type";
	String OUTPUT_CDATA_SECTION_ELEMENTS = "cdata-section-elements";
		
	String RUNTIME_VARIABLE_PREFIX = "$";
	
	String ATTRIBUTE_ACCESSOR = "/@";
	String PATH_SEPARATOR = "/";

	/**
	 * Applies a template rule from an imported style sheet
	 */
	String APPLY_IMPORTS ="xsl:apply-imports";
	
	/**
	 * Applies a template rule to the current 
	 * element or to the current element's child nodes
	 */
	String APPLY_TEMPLATES = "xsl:apply-templates";
	
	/**
	 * Adds an attribute
	 */
	String ATTRIBUTE = "xsl:attribute";
	
	/**
	 * Defines a named set of attributes
	 * */
	String ATTRIBUTE_SET = "xsl:attribute-set";
	
	/**Calls a named template*/
	String CALL_TEMPLATE = "xsl:call-template";
	
	/**Used in conjunction with 
	 * 	<when> and <otherwise> 
	 * to express multiple conditional tests
	 */
	String CHOOSE = "xsl:choose";
	
	/**
	 * Creates a comment node in the result tree
	 */
	String COMMENT = "xsl:comment";
	
	/**
	 * Creates a copy of the current 
	 * node (without child nodes and attributes)
	 */
	String COPY = "xsl:copy";
	
	/**
	 * Creates a copy of the current node (with child nodes and attributes)
	 */
	String COPY_OF = "xsl:copy-of";
	
	/**
	 * Defines the characters and symbols to be used when 
	 * converting numbers into strings, 
	 * with the format-number() function
	 */
	String DECIMAL_FORMAT = "xsl:decimal-format";
	
	/**Creates an element node in the output document*/
	String ELEMENT = "xsl:element";
	
	/**
	 * Specifies an alternate code to run if 
	 * the processor does not support an XSLT element
	 */
	String FALLBACK = "xsl:fallback";
	
	/**
	 * Loops through each node in a specified node set
	 */
	String FOR_EACH = "xsl:for-each";
	
	/**
	 * Contains a template that will be applied only if a specified condition is true
	 */
	String IF = "xsl:if";
	
	/**
	 * Imports the contents of one style sheet into another.
	 * Note: An imported style sheet has lower precedence than the importing style sheet
	 */
	String IMPORT = "xsl:import";
	
	/**
	 * Includes the contents of one style sheet into another.
	 * Note: An included style sheet has the same precedence as the including style sheet
	 */
	String INCLUDE = "xsl:include";
	
	/**
	 * Declares a named key that can be used in the style sheet with the key() function
	 */
	String KEY = "xsl:key";
	
	/**
	 * Writes a message to the output (used to report errors)
	 */
	String MESSAGE = "xsl:message";
	
	/**
	 * Replaces a namespace in the style sheet to a different namespace in the output
	 */
	String NAMESPACE_ALIAS = "xsl:namespace-alias";
	
	/**
	 * Determines the integer position of the current node and formats a number
	 */
	String NUMBER = "xsl:number";
	
	/**
	 * Specifies a default action for the <choose> element
	 */
	String OTHERWISE = "xsl:otherwise";
	
	/**
	 * Defines the format of the output document
	 */
	String OUTPUT = "xsl:output";

	/**
	 * Declares a local or global parameter
	 */
	String PARAM = "xsl:param";

	/**
	 * Defines the elements for which white space should be preserved
	 */
	String PRESERVE_SPACE = "xsl:preserve-space";

	/**
	 * Writes a processing instruction to the output
	 */
	String PROCESSING_INSTRUCTION = "xsl:processing-instruction";

	/**
	 * Sorts the output
	 */
	String SORT = "xsl:sort";
	
	/**
	 * Defines the elements for which white space should be removed
	 */
	String STRIP_SPACE = "xsl:strip-space";

	/**
	 * Defines the root element of a style sheet
	 */
	String STYLESHEET = "xsl:stylesheet";

	/**
	 * Rules to apply when a specified node is matched
	 */
	String TEMPLATE = "xsl:template";

	/**
	 * Writes literal text to the output
	 */
	String TEXT = "xsl:text";

	/**
	 * Defines the root element of a style sheet
	 */
	String TRANSFORM = "xsl:transform";

	/**
	 * Extracts the value of a selected node
	 */
	String VALUE_OF = "xsl:value-of";

	/**
	 * Declares a local or global variable
	 */
	String VARIABLE = "xsl:variable";

	/**
	 * Specifies an action for the choose element
	 */
	String WHEN = "xsl:when";

	/**
	 * Defines the value of a parameter to be passed into a template
	 */
	String WITH_PARAM = "xsl:with-param";

	String MATCH_ATTR = "match";

	String SELECT_ATTR = "select";
	
	String AS_ATTR = "as";
	
	String AT = "@";

	String XSL_STYLESHEET_DECLARATION = "<xsl:stylesheet version=\"2.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\">";
	String XSL_STYLESHEET_DECLARATION_START = "<xsl:stylesheet version=\"2.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\" ";
	String XSL_STYLESHEET_DECLARATION_END = ">";

	String XSL_TRANSFORM_DECLARATION = "<xsl:transform version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\">";

}
