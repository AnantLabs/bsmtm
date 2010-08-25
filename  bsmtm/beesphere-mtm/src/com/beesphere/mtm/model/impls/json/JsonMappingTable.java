package com.beesphere.mtm.model.impls.json;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beesphere.mtm.InvalidMappingTableException;
import com.beesphere.mtm.impls.ByIdSelector;
import com.beesphere.mtm.lang.XmlUtils;
import com.beesphere.mtm.lang.XslStandard;
import com.beesphere.mtm.model.AbstractMappingTable;
import com.beesphere.mtm.model.MappingLine;
import com.beesphere.mtm.model.MappingParameter;
import com.beesphere.mtm.model.MappingExpression;
import com.beesphere.mtm.model.OutputSelector;
import com.beesphere.xsd.XmlStandard;
import com.qlogic.commons.utils.json.JsonArray;
import com.qlogic.commons.utils.json.JsonException;
import com.qlogic.commons.utils.json.JsonObject;

/**
 * 
 * 
	{
        in-params: [
		   'com.me.mycaracteristic'
		],
		expressions: {
		   e1: {text: "concat(/b/c, '-', /b/d)", asVar: true, scope: 'l'},
		   e2: {text: "concat(/data/rs/r/c_0, '', $com.me.mycaracteristic)", asVar: true, scope: 'g'},
		   e3: {text: '<xsl:template name="tpl1" match="/data/rs"></xsl:template>'},
		   e4: {text: '<xsl:call-template name="tpl1"/>', asVar: true, scope: 'l'}
		},
		lines: [
	          { output: 're6', target: '/data/hs/h_0', expression: '/po:CompletePO/po:Customer/po:Company'},
	          { output: 're6', target: '/data/hs/h_1', expression: "'Delivery Date'"},
	          { output: 're6', target: '/data/hs/h_2', expression: "'Number'"},
	          { output: 're6', iterateOn: '/po:CompletePO/po:LineItems/po:LineItem', vars: ['e1', 'e4']},
	          { output: 're6', target: '/data/rs/r', expression:'/po:CompletePO/po:LineItems/po:LineItem'},
	          { output: 're6', target: '/data/rs/r/c_0', expression: '/po:CompletePO/po:LineItems/po:LineItem/po:CustomerName'},
	          { output: 're6', target: '/data/rs/r/c_1', expression: '$e2'},
	          { output: 're6', target: '/data/rs/r/c_2', expression: '/po:CompletePO/po:LineItems/po:LineItem/po:Number'}
	          { output: 't2', iterateOn: '/po:CompletePO/po:LineItems/po:LineItem', vars: ['e1', 'e4']},
	          { output: 't2', target: '/data/rs/r', expression:'/po:CompletePO/po:LineItems/po:LineItem'},
	          { output: 't2', target: '/data/rs/r/c_0', expression: '$e3'},
	          { output: 't2', target: '/data/rs/r/c_1', expression: '/po:CompletePO/po:LineItems/po:LineItem/po:Article'},
	          { output: 't2', target: '/data/rs/r/c_2', expression: '$e1'}
		],
		namespaces: [
	          'xmlns:xls="http://www.beesphere.net/2010/xsds/xls"',
	          'xmlns:po="urn:Completepo"',
	          'xmlns:txt="http://www.beesphere.net/2010/xsds/txt"'
		],
		out-params: {
	          re6: {
				omit-xml-declaration: false,
				indent: true,
				version: '1.0',
				encoding: 'utf-8',
				standalone: true,
				doctype-public: '',
				doctype-system: '',
				method: 'xml',
				media-type: '',
				cdata-section-elements: '',
				namespace: 'xls'
	          },
	          t2: {
	            omit-xml-declaration: true,
	            method: 'xml'
	          },
	          t3: {
	            omit-xml-declarations: true,
	            method: 'xml'
	          }
	    }
	} 
 * 
 * 
 * 
 * 
 */

public class JsonMappingTable extends AbstractMappingTable {
	
	private static final long serialVersionUID = -7556773892656407694L;

	@SuppressWarnings("unused")
	private final static Logger logger = LoggerFactory.getLogger (JsonMappingTable.class);

	public static final String LINES 				= "lines";
	public static final String EXPRESSIONS 			= "expressions";
	public static final String IN_PARAMS 			= "in-params";
	public static final String OUT_PARAMS 			= "out-params";
	public static final String NAMESPACES 			= "namespaces";
	public static final String COUNT 				= "count";
	
	protected JsonArray lines;
	protected JsonObject expressions;

	protected JsonArray inParams;
	protected JsonObject outParams;
	
	protected JsonArray namespaces;
	
	protected JsonMappingLine line;
	protected JsonMappingLine nextLine;
	protected JsonMappingParameter parameter;
	protected JsonMappingExpression expression;
	
	protected Set<String> found;
	protected Set<String> foundVars;
	protected Map<String, String> doneIterations;
	
	protected OutputSelector selector;
	protected Map<String, String> names;
	
	public JsonMappingTable () {
	}
	
	public JsonMappingTable (String json) throws InvalidMappingTableException {
		this (json, null);
	}
	
	public JsonMappingTable (JsonObject json) throws InvalidMappingTableException {
		this (json, null);
	}
	
	public JsonMappingTable (String json, String output) throws InvalidMappingTableException {
		try {
			set (new JsonObject (json), output);
		} catch (JsonException je) {
			throw new InvalidMappingTableException (je);
		}
	}
	public JsonMappingTable (JsonObject json, String output) throws InvalidMappingTableException {
		set (json, output);
	}
	
	private void set (JsonObject json, String output) throws InvalidMappingTableException {
		line = new JsonMappingLine (this);
		nextLine = new JsonMappingLine (this);
		expression = new JsonMappingExpression (this);
		if (output != null) {
			selector = new ByIdSelector (output);
			parameter = new JsonMappingParameter (output);
		} else {
			selector = new ByIdSelector ();
			parameter = new JsonMappingParameter ();
		}
		if (json == null) {
			return;
		}
		if (json.get (name (LINES)) == null || ((JsonArray)json.get (name (LINES))).count() < 1) {
			throw new InvalidMappingTableException ("Mapping Table must contain mapping lines");
		}
		this.lines = (JsonArray)json.get (name (LINES));
		
		this.expressions = (JsonObject)json.get(name (EXPRESSIONS));
		
		this.inParams = (JsonArray)json.get (name (IN_PARAMS));

		this.namespaces = (JsonArray)json.get (name (NAMESPACES));
		
		this.outParams = (JsonObject)json.get (name (OUT_PARAMS));

		this.found = new HashSet<String> ();
		this.foundVars = new HashSet<String> ();
		this.doneIterations = new HashMap<String, String> ();
	}
	
	public void setOutput (String output) {
		((ByIdSelector)selector).setId (output);
		parameter.setOutput (output);
	}
	
	@Override
	public void reset () {
		parameter.reset ();
		line = new JsonMappingLine (this);
		nextLine = new JsonMappingLine (this);
		foundVars.clear();
		doneIterations.clear();
		found.clear ();
	}

	@Override
	public int count () {
		return this.lines.count ();
	}

	@Override
	protected boolean isValid (MappingLine line) {
		if (line == null) {
			return false;
		}
		if (!((JsonMappingLine)line).isValid ()) {
			return false;
		}
		return !XmlUtils.isNullOrEmpty (line.getExpression ()) || 
				!XmlUtils.isNullOrEmpty (line.getIterateOn ());
	}
	
	@Override
	public MappingLine find (String path, String attribute) {
		String target = path;
		if (attribute != null) {
			target = path + XslStandard.ATTRIBUTE_ACCESSOR + attribute;
		} 
		MappingLine foundIteration = null;
		for (int i = 0; i < lines.count (); i++) {
			line.set ((JsonObject)lines.get (i));
			if (line.getTarget () != null && line.getTarget ().equals (target) && selector.select (line) && isValid (line)) {
				if (line.isLoop()) {
					String expression = line.getExpression();
					if (expression == null || expression.trim().isEmpty()) {
						expression = line.getIterateOn();
					}
					doneIterations.put(String.valueOf(i), expression);
				}
				if (!found.contains (target)) {
					if (!line.isLoop()) {
						found.add (target);
						return line;
					} else {
						foundIteration = line;
					}
				} else if (line.isLoop()) {
					continue;
				}
				return line;
			}
		}
		if (foundIteration != null) {
			return foundIteration;
		}
		line.set (null);
		return null;
	}

	@Override
	public boolean isContributing (String path) {
		if (lines == null) {
			return false;
		}
		if (found.contains (path)) {
			return true;
		}
		for (int i = 0; i < lines.count (); i++) {
			line.set ((JsonObject)lines.get (i));
			if (!selector.select(line) || !isValid (line)) {
				continue;
			}
			if (line.getTarget () != null && line.getTarget ().startsWith (path + XmlStandard.SLASH)) {
				found.add (path);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean isExpressionContributing (String expName) throws InvalidMappingTableException {
		if (foundVars.contains (expName)) {
			return true;
		}
		
		MappingExpression variable = getExpression (expName);
		
		if (variable == null) {
			return false;
		}
		
		if (variable.isVar()) {
			foundVars.add (expName);
			return true;
		}
		for (int i = 0; i < lines.count (); i++) {
			line.set ((JsonObject)lines.get (i));
			String expression = line.getExpression ();
			
			if (expression != null && expression.contains (XslStandard.RUNTIME_VARIABLE_PREFIX + expName)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public MappingLine findIteration (MappingLine where) {
		
		//logger.debug("Searching iterations for :" + where);
		
		int size = lines.count ();
		int iterationsSize = doneIterations.size ();
		int index = 0;
		
		boolean found = false;
		while (index < size) {
			nextLine.set((JsonObject)lines.get (index));
			if (!selector.select (nextLine) || !nextLine.isLoop() || !isValid (nextLine)) {
				index ++;
				continue;
			}
			found = where!= null && !doneIterations.containsKey (String.valueOf(index));
			if (nextLine.getTarget() == null) {
				found |= (where == null && iterationsSize > 0);
			}
			if (found) {
				break;
			}
			index++;
		}
		if (!found || doneIterations.containsKey (String.valueOf (index))) {
			return null;
		}
		int lineOrder = -1;
		
		if (line != null) {
			lineOrder = Integer.parseInt (line.getProperty(JsonMappingLine.ORDER));
		}
		
		if (index >= lineOrder) {
			return null;
		}
		
		doneIterations.put(String.valueOf(index), nextLine.getIterateOn());
		return nextLine;
	}

	@Override
	public String expression (MappingLine current, boolean forIteration) throws InvalidMappingTableException {
		if (current == null) {
			return null;
		}

		String expression = current.getExpression();
		
		if (expression != null && expression.startsWith (XslStandard.RUNTIME_VARIABLE_PREFIX)) {
			String varName = expression.substring (XslStandard.RUNTIME_VARIABLE_PREFIX.length());
			MappingExpression variable = getExpression (varName);
			
			if (variable != null) {
				return variable.isVar() ? expression : variable.getContent();
			}
		}
		
		int order = 0;
		String exp = expression;
		if (current.isLoop() && current.getIterateOn() != null) {
			exp = current.getIterateOn();
		}
		
		String lastAncestor = null;
		int lineOrder = Integer.parseInt(current.getProperty(JsonMappingLine.ORDER));
		
		while (order <= lineOrder) {
			if (!doneIterations.containsKey (String.valueOf (order)) 
					|| doneIterations.get (String.valueOf (order)) == null) {
				order++;
				continue;
			}
			String ancestorCandidate = doneIterations.get (String.valueOf (order));
			if (exp.equals (ancestorCandidate)) {
				if (!current.isLoop() || !forIteration) {
					lastAncestor = ancestorCandidate;
					break;
				}
			}
			if (!exp.contains (ancestorCandidate + XmlStandard.SLASH)) {
				order++;
				continue;
			}
			lastAncestor = doneIterations.get (String.valueOf (order));
			order ++;
		}
		
		if (lastAncestor != null) {
			if ((!current.isLoop() || !forIteration) && exp.equals(lastAncestor)) {
				return XslStandard.DOT;
			} else if (exp.contains (lastAncestor + XmlStandard.SLASH)) {
				return exp.replace (lastAncestor + XmlStandard.SLASH, XmlUtils.EMPTY);
			}
		}
		
		if (current.getIterateOn () != null) {
			if (!XmlUtils.isNullOrEmpty (current.getExpression ())) {
				return current.getExpression ();
			}
			return current.getIterateOn ();
		}
		return current.getExpression();
	}

	String name (String name) {
		if (names != null && names.get(name) != null) {
			return names.get(name);
		}
		return name;
	}
	
	public Map<String, String> getNames () {
		return names;
	}
	public void setNames (Map<String, String> names) {
		this.names = names;
	}
	public void setSelector (ByIdSelector selector) {
		this.selector = selector;
	}

	@Override
	public MappingParameter getInParameter (int index) {
		if (inParams == null) {
			return null;
		}
		return parameter.set (String.valueOf (inParams.get (index)));
	}

	@Override
	public int getInParametersCount() {
		if (inParams == null) {
			return 0;
		}
		return inParams.count ();
	}

	@Override
	public MappingParameter getOutParameter (String name) {
		if (outParams == null) {
			return null;
		}
		
		if (!selector.select (parameter.set (name))) {
			return null;
		}
		
		JsonObject o = (JsonObject)outParams.get (parameter.getOutput ());
		
		if (o == null || o.isEmpty ()) {
			return null;
		}
		Object propValue = o.get (name);
		if (propValue == null) {
			return null;
		}
		return parameter.set (name, String.valueOf (propValue));
	}

	@Override
	public MappingExpression getExpression (String name) throws InvalidMappingTableException {
		if (expressions == null || expressions.get (name) == null) {
			return null;
		}
		try {
			return expression.set (name, expressions.getObject (name));
		} catch (JsonException e) {
			throw new InvalidMappingTableException (e, e.getMessage ());
		}
	}

	@Override
	public Iterator<String> getExpressionsNames () {
		if (expressions == null) {
			return null;
		}
		return expressions.keys ();
	}

	@Override
	public String [] namespaces () {
		if (namespaces == null) {
			return null;
		}
		String [] ns = new String [namespaces.count ()];
		for (int i = 0; i < ns.length; i++) {
			ns [i] = String.valueOf (namespaces.get (i));
		}
		return ns;
	}

	public OutputSelector getSelector() {
		return selector;
	}

}
