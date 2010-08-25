package com.beesphere.mtm.impls;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beesphere.mtm.InvalidMappingTableException;
import com.beesphere.mtm.XslGenerator;
import com.beesphere.mtm.XslGeneratorException;
import com.beesphere.mtm.lang.XmlUtils;
import com.beesphere.mtm.lang.XslStandard;
import com.beesphere.mtm.model.MappingExpression;
import com.beesphere.mtm.model.MappingLine;
import com.beesphere.mtm.model.MappingParameter;
import com.beesphere.mtm.model.MappingTable;
import com.beesphere.xsd.XmlStandard;
import com.beesphere.xsd.model.XsdAttribute;
import com.beesphere.xsd.model.XsdComplexType;
import com.beesphere.xsd.model.XsdElement;
import com.beesphere.xsd.model.XsdManagedEntity;
import com.beesphere.xsd.model.XsdSchema;
import com.beesphere.xsd.model.XsdSet;
import com.beesphere.xsd.model.XsdSimpleType;

/**
 * @author A.BOUASSOULE
 *
 */
public class DefaultXslGenerator implements XslGenerator {

	private static final long serialVersionUID = -3890837229134445705L;

	@SuppressWarnings("unused")
	private final static Logger logger = LoggerFactory.getLogger(DefaultXslGenerator.class);
	
	private static Set<String> OUT_PARAMS = new HashSet<String> ();
	static {
		OUT_PARAMS.add (XslStandard.OUTPUT_OMIT_XML_DECLARATION);
		OUT_PARAMS.add (XslStandard.OUTPUT_INDENT);
		OUT_PARAMS.add (XslStandard.OUTPUT_VERSION);
		OUT_PARAMS.add (XslStandard.OUTPUT_ENCODING);
		OUT_PARAMS.add (XslStandard.OUTPUT_STANDALONE);
		OUT_PARAMS.add (XslStandard.OUTPUT_DOCTYPE_PUBLIC);
		OUT_PARAMS.add (XslStandard.OUTPUT_DOCTYPE_SYSTEM);
		OUT_PARAMS.add (XslStandard.OUTPUT_METHOD);
		OUT_PARAMS.add (XslStandard.OUTPUT_MEDIA_TYPE);
		OUT_PARAMS.add (XslStandard.OUTPUT_CDATA_SECTION_ELEMENTS);
	}

	private XsdSchema schema;
	private Writer writer;
	private MappingTable mappingTable;
	private String namespace;
	private String xmlns;

	public DefaultXslGenerator (Writer writer) {
		this.writer = writer;
	}

	public DefaultXslGenerator() {
		this (null);
	}

	@Override
	public void generate (XsdSchema schema, MappingTable mappingTable)
			throws XslGeneratorException {
		if (schema == null) {
			throw new XslGeneratorException("No Schema Found");
		}
		this.schema = schema;

		if (mappingTable == null) {
			throw new XslGeneratorException("No Mappings Found");
		}

		this.mappingTable = mappingTable;

		if (writer == null) {
			writer = new OutputStreamWriter(System.out);
		}

		String targetNameSpace = schema.getTargetNamespace ();
		MappingParameter ns = this.mappingTable.getOutParameter ("namespace");
		if (ns != null) {
			namespace = ns.value ();
		}
		if (targetNameSpace != null) {
			if (namespace == null) {
				namespace = "bs";
			}
			xmlns = "xmlns" + XmlUtils.COLON + namespace
					+ XmlUtils.EQUALS + XmlUtils.QUOT + targetNameSpace + XmlUtils.QUOT;
		}

		try {
			startDocument();
			XsdElement rootEl = this.schema.getRootElement();

			writeElement(rootEl, rootEl, XmlStandard.SLASH + rootEl.getName());
			endDocument();
			writer.flush();
		} catch (Throwable e) {
			throw new XslGeneratorException(e);
		}
		reset ();
	}
	
	/**
	 * First, searchs for a mapping line related to the xsd entity elementPath
	 * If no line is found, the entity is checked to verify its contribution in the xsl.
	 * Contribution means that a line exists with the exact path (elementPath), or which path contains the elementPath argument.
	 * If neither conditions are validated the entity is ignored.
	 * If the entity is a contributing element, the entity is handled depending on its xsd type.
	 * 
	 * 
	 * @param owner, the xsd entity containing this entity
	 * @param entity, the element we are searching for its mappings/iterations
	 * @param elementPath, this is xpath like 'identifier' among the xsd schema
	 * 
	 * @return 
	 * @throws IOException,
	 * @throws InvalidMappingTableException, when the wrong mapping lines are provided  
	 */
	
	private void writeElement(XsdManagedEntity owner, XsdElement entity,
			String elementPath) throws IOException, InvalidMappingTableException {

		if (owner.getRef() != null) {
			XsdManagedEntity refEntity = schema.get(owner.getRef());
			if (refEntity instanceof XsdElement) {
				writeElement(owner, (XsdElement) refEntity, elementPath);
			}
		}

		MappingLine line = mappingTable.find(elementPath, null);

		// if no valid line found for this path, and this path element is contributing just return
		// A path is not contributing if no elements (lines) are related to this path
		if (line == null && !mappingTable.isContributing(elementPath)) {
			return;
		}

		if (entity.getComplexType() != null) {
			writeComplexEntity(entity, entity.getComplexType(), elementPath,
					line);
		} else if (entity.getSimpleType() != null
				&& !XmlUtils.isNullOrEmpty(getEntityName(entity.getSimpleType()))) {
			writeSimpleEntity(entity, entity.getSimpleType(), elementPath, line);
		} else if (line != null) {
			writeElementTag (entity, line);
		}

		writer.flush();
	}

	/**
	 * @param owner
	 * @param entity
	 * @param elementPath
	 * @param line
	 * @throws IOException
	 * @throws InvalidMappingTableException
	 */
	private void writeSimpleEntity(XsdManagedEntity owner,
			XsdSimpleType entity, String elementPath, MappingLine line) throws IOException, InvalidMappingTableException {
		if (entity == null) {
			return;
		}
		boolean isLoop = line != null && line.isLoop();

		int countAncestors = 0;
		MappingLine nextLine = null;
		if (line != null) {
			nextLine = mappingTable.findIteration(line);
			while (nextLine != null && nextLine.isLoop()) {
				countAncestors++;
				XmlUtils.writeStartTag(XslStandard.FOR_EACH, false, writer);
				XmlUtils.writeAttribute(XslStandard.SELECT_ATTR, mappingTable.expression(nextLine, true), false, true, writer);
				writeVariables(nextLine.getExpressionsNames());
				nextLine = mappingTable.findIteration(nextLine);
			}
		}

		// open for-each
		if (isLoop) {
			XmlUtils.writeStartTag(XslStandard.FOR_EACH, false, writer);
			XmlUtils.writeAttribute(XslStandard.SELECT_ATTR, mappingTable.expression(line, true), false, true, writer);
		}

		writeElementTag(entity, null, false);
		
		//TODO add simple entity subelements handling

		XmlUtils.writeEndTag(XslStandard.ELEMENT, writer);
		// close for-each
		if (isLoop) {
			XmlUtils.writeEndTag(XslStandard.FOR_EACH, writer);
		}
		if (line != null) {
			for (int i = 0; i < countAncestors; i++) {
				XmlUtils.writeEndTag(XslStandard.FOR_EACH, writer);
			}
		}
	}

	/**
	 * @param owner
	 * @param entity
	 * @param elementPath
	 * @param line
	 * @throws IOException
	 * @throws InvalidMappingTableException
	 */
	private void writeComplexEntity(XsdManagedEntity owner,
			XsdComplexType entity, String elementPath, MappingLine line)
			throws IOException, InvalidMappingTableException {

		boolean isLoop = entity.getSet() != null && line != null
				&& line.isLoop();

		int count = 0;
		MappingLine nextLine = null;
		if (line != null) {
			nextLine = mappingTable.findIteration (line);
			while (nextLine != null && nextLine.isLoop()) {
				count++;
				XmlUtils.writeStartTag(XslStandard.FOR_EACH, false, writer);
				XmlUtils.writeAttribute(XslStandard.SELECT_ATTR, mappingTable.expression(nextLine, true), false, true, writer);
				writeVariables(nextLine.getExpressionsNames());
				nextLine = mappingTable.findIteration (nextLine);
			}
		}

		if (isLoop) {
			XmlUtils.writeStartTag(XslStandard.FOR_EACH, false, writer);
			XmlUtils.writeAttribute(XslStandard.SELECT_ATTR, mappingTable.expression(line, true), false, true, writer);
		}

		XmlUtils.writeStartTag(XslStandard.ELEMENT, false, writer);
		XmlUtils.writeNameAttribute(namespace, owner.getName(), false, true,
				writer);

		for (int i = 0; i < entity.countAttributes(); i++) {
			writeAttribute(owner, entity.getAttribute(i), elementPath);
		}

		/*int countNexts = 0;
		if (line != null) {
			writeVariables(line.getExpressionsNames());
			nextLine = mappingTable.findIteration(nextLine, elementPath);
			while (nextLine != null && nextLine.isLoop()) {
				countNexts++;
				XmlUtils.writeStartTag(XslStandard.FOR_EACH, false, writer);
				XmlUtils.writeAttribute(XslStandard.SELECT_ATTR, mappingTable
						.expression(nextLine), false, true, writer);
				writeVariables(nextLine.getExpressionsNames());
				nextLine = mappingTable.findIteration(nextLine, elementPath);
			}
		}*/
		writeSet(owner, entity.getSet(), elementPath);

		/*for (int i = 0; i < countNexts; i++) {
			XmlUtils.writeEndTag(XslStandard.FOR_EACH, writer);
		}*/

		XmlUtils.writeEndTag (XslStandard.ELEMENT, writer);

		if (isLoop) {
			XmlUtils.writeEndTag(XslStandard.FOR_EACH, writer);
		}
		if (line != null) {
			for (int i = 0; i < count; i++) {
				XmlUtils.writeEndTag(XslStandard.FOR_EACH, writer);
			}
		}
	}

	private void writeSet(XsdManagedEntity owner, XsdSet set, String elementPath)
			throws IOException, InvalidMappingTableException {
		Iterator<XsdManagedEntity> entities = set.entities();
		while (entities.hasNext()) {
			XsdManagedEntity entity = entities.next();
			if (entity instanceof XsdElement) {
				writeElement(owner, (XsdElement) entity, elementPath
						+ XmlStandard.SLASH + getEntityName(entity));
			} else if (entity instanceof XsdSet) {
				writeSet(owner, (XsdSet) entity, elementPath);
			}
		}
	}

	private void writeElementTag(XsdManagedEntity entity, MappingLine line) throws IOException, InvalidMappingTableException {
		if (entity == null) {
			return;
		}
		boolean isLoop = line != null && line.isLoop();

		int countAncestors = 0;
		MappingLine nextLine = null;
		if (line != null) {
			nextLine = mappingTable.findIteration(line);
			while (nextLine != null && nextLine.isLoop()) {
				countAncestors++;
				XmlUtils.writeStartTag(XslStandard.FOR_EACH, false, writer);
				XmlUtils.writeAttribute(XslStandard.SELECT_ATTR, mappingTable
						.expression(nextLine, true), false, true, writer);
				writeVariables(nextLine.getExpressionsNames());
				nextLine = mappingTable.findIteration(nextLine);
			}
		}

		String selectValue = mappingTable.expression(line, false);
		// open for-each
		if (isLoop) {
			XmlUtils.writeStartTag(XslStandard.FOR_EACH, false, writer);
			XmlUtils.writeAttribute(XslStandard.SELECT_ATTR, mappingTable.expression(line, true), false, true, writer);
		}

		XmlUtils.writeStartTag(XslStandard.ELEMENT, false, writer);
		XmlUtils.writeNameAttribute(namespace, getEntityName(entity), false,
				true, writer);
		if (selectValue != null) {
			writeSelectValueTag(selectValue);
		}
		XmlUtils.writeEndTag(XslStandard.ELEMENT, writer);
		// close for-each
		if (isLoop) {
			XmlUtils.writeEndTag(XslStandard.FOR_EACH, writer);
		}
		if (line != null) {
			for (int i = 0; i < countAncestors; i++) {
				XmlUtils.writeEndTag(XslStandard.FOR_EACH, writer);
			}
		}
	}

	private void writeElementTag(XsdManagedEntity entity, String selectValue,
			boolean endElement) throws IOException, InvalidMappingTableException {
		if (entity == null) {
			return;
		}

		XmlUtils.writeStartTag(XslStandard.ELEMENT, false, writer);
		XmlUtils.writeNameAttribute(namespace, getEntityName(entity), false,
				true, writer);
		if (selectValue != null) {
			writeSelectValueTag(selectValue);
		}
		if (endElement) {
			XmlUtils.writeEndTag(XslStandard.ELEMENT, writer);
		}
	}

	private void writeAttribute(XsdManagedEntity owner, XsdAttribute attribute,
			String elementPath) throws IOException, InvalidMappingTableException {
		MappingLine line = mappingTable.find(elementPath,
				getEntityName(attribute));
		if (line == null) {
			return;
		}
		XmlUtils.writeStartTag(XslStandard.ATTRIBUTE, false, writer);
		XmlUtils.writeNameAttribute(namespace, attribute.getName(), false,
				true, writer);
		XmlUtils.writeStartTag(XslStandard.VALUE_OF, false, writer);
		XmlUtils.writeAttribute(XslStandard.SELECT_ATTR, mappingTable
				.expression(line, false), false, false, writer);
		XmlUtils.endTag(writer);
		XmlUtils.writeEndTag(XslStandard.ATTRIBUTE, writer);
	}

	public void writeSelectValueTag(String value) throws IOException {
		XmlUtils.writeStartTag(XslStandard.VALUE_OF, false, writer);
		XmlUtils.writeAttribute(XslStandard.SELECT_ATTR, value, false, false,
				writer);
		XmlUtils.endTag(writer);
	}

	public void startDocument() throws IOException, InvalidMappingTableException {
		writer.append(XmlStandard.XML_HEADER);
		
		writer.append (XslStandard.XSL_STYLESHEET_DECLARATION_START);
		if (xmlns != null) {
			writer.append (XmlUtils.SPACE).append (xmlns).append (XmlUtils.SPACE);
		}
		
		String [] namespaces = mappingTable.namespaces();
		Set<String> added = null;
		if (namespaces != null && namespaces.length > 0) {
			added = new HashSet<String> ();
			added.add (xmlns);
			for (int i = 0; i < namespaces.length; i++) {
				String ns = namespaces[i];
				if (ns != null && !added.contains (ns)) {
					writer.append (XmlUtils.SPACE).append (ns).append (XmlUtils.SPACE);
					added.add (ns);
				}
			}
		}
		if (added != null) {
			added.clear ();
			added = null;
		}
		writer.append (XslStandard.XSL_STYLESHEET_DECLARATION_END);

		writeOutputProperties ();
		writeParameters();
		writeGlobalVariables();
		XmlUtils.writeStartTag(XslStandard.TEMPLATE, false, writer);
		XmlUtils.writeAttribute(XslStandard.MATCH_ATTR, XmlStandard.SLASH,
				false, true, writer);
		writer.flush ();
	}

	private void writeOutputProperties() throws IOException {
		Iterator<String> outNames = OUT_PARAMS.iterator ();
		if (outNames == null) {
			return;
		}
		XmlUtils.writeStartTag (XslStandard.OUTPUT, false, writer);
		while (outNames.hasNext ()) {
			String name = outNames.next ();
			String value = parameterValue (mappingTable.getOutParameter (name));
			if (value != null) {
				XmlUtils.writeAttribute (name, value, false, false, writer);
			}
		}
		XmlUtils.endTag(writer);
	}
	
	private String parameterValue (MappingParameter param) {
		if (param == null) {
			return null;
		}
		return param.value();
	}
	
	private void writeGlobalVariables() throws IOException, InvalidMappingTableException {
		writeVariables(true);
	}

	private void writeVariables(String[] lineVariables) throws IOException, InvalidMappingTableException {
		if (lineVariables == null || lineVariables.length == 0) {
			return;
		}
		
		for (String name : lineVariables) {
			writeVariable(mappingTable.getExpression(name));
		}
	}

	private void writeVariables(boolean filterGlobal) throws IOException, InvalidMappingTableException {
		Iterator<String> expNames = mappingTable.getExpressionsNames();
		if (expNames == null) {
			return;
		}
		
		while (expNames.hasNext()) {
			MappingExpression exp = mappingTable.getExpression(expNames.next());
			if (!filterGlobal
					|| exp.getScope() == null
					|| exp.getScope().equals(
							MappingExpression.VariableScope.GLOBAL)) {
				writeVariable(exp);
			}
		}
	}

	private void writeVariable(MappingExpression variable) throws IOException, InvalidMappingTableException {
		if (variable == null
				|| !mappingTable.isExpressionContributing(variable.getName())) {
			return;
		}
		if (!variable.isVar()) {
			writer.write(variable.getContent());
			return;
		}
		XmlUtils.writeStartTag(XslStandard.VARIABLE, false, writer);
		XmlUtils.writeAttribute(XslStandard.NAME, variable.getName(), false,
				false, writer);
		String vVal = variable.getSelect ();
		if (vVal == null) {
			vVal = variable.getContent();
		}
		XmlUtils.writeAttribute(XslStandard.SELECT_ATTR, vVal, false,
					false, writer);
		writer.write(XmlStandard.GREATER_START);
	}

	public void endDocument() throws IOException {
		XmlUtils.writeEndTag(XslStandard.TEMPLATE, writer);
		XmlUtils.writeEndTag(XslStandard.STYLESHEET, writer);
	}

	private void writeParameters() throws IOException {
		for (int i = 0; i < mappingTable.getInParametersCount (); i++) {
			MappingParameter param = mappingTable.getInParameter(i);
			if (param == null) {
				continue;
			}
			XmlUtils.writeStartTag(XslStandard.PARAM, false, writer);
			XmlUtils.writeAttribute(XslStandard.NAME, param.name(), false,
					false, writer);
			XmlUtils.writeAttribute(XmlUtils.VALUE_ATTR, param.value(), false,
					false, writer);
			writer.append(XmlStandard.GREATER_START);
		}
	}

	private String getEntityName(XsdManagedEntity entity) {
		return entity.getName() != null ? entity.getName() : entity.getRef();
	}

	@Override
	public void reset () {
		schema = null;
		if (writer != null) {
			try {
				writer.close ();
			} catch (IOException ioex) {
				// IGNORE
			}
		}
		writer = null;
		namespace = null;
		xmlns = null;
		mappingTable.reset ();
	}

	public XsdSchema getSchema() {
		return schema;
	}

	public void setSchema(XsdSchema schema) {
		this.schema = schema;
	}

	public Writer getWriter() {
		return writer;
	}

	public void setWriter(Writer writer) {
		this.writer = writer;
	}

}
