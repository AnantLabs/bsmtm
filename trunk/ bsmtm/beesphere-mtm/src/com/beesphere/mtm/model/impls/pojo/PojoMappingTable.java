package com.beesphere.mtm.model.impls.pojo;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import com.beesphere.mtm.InvalidMappingTableException;
import com.beesphere.mtm.lang.XslStandard;
import com.beesphere.mtm.model.AbstractMappingTable;
import com.beesphere.mtm.model.MappingExpression;
import com.beesphere.mtm.model.MappingLine;
import com.beesphere.mtm.model.MappingParameter;
import com.beesphere.xsd.XmlStandard;

public class PojoMappingTable extends AbstractMappingTable {
	
	private static final long serialVersionUID = -5272941932061337317L;
	
	private Map<String, MappingLine> lines;
	private Set<String> found;
	
	public void add (MappingLine line) {
		if (lines == null) {
			lines = new LinkedHashMap<String, MappingLine> ();
			found = new HashSet<String> ();
		}
		
		lines.put (line.getTarget (), line);
	}
	
	@Override
	public void reset () {
		
	}

	@Override
	public int count () {
		if (lines == null) {
			return 0;
		}
		return lines.size ();
	}

	@Override
	public MappingLine find (String path, String attribute) {
		if (lines == null) {
			return null;
		}
		MappingLine line;
		if (attribute != null) {
			line = lines.get (path + XslStandard.ATTRIBUTE_ACCESSOR + attribute);
		} else {
			line = lines.get (path);
		}
		if (!isValid (line)) {
			line = null;
		}
		return line;
	}

	@Override
	public boolean isContributing (String path) {
		if (lines == null) {
			return false;
		}
		if (found.contains (path)) {
			return true;
		}
		// search in map keys
		Iterator<String> keys = lines.keySet ().iterator ();
		while (keys.hasNext ()) {
			if (keys.next ().startsWith (path + XmlStandard.SLASH)) {
				found.add (path);
				return true;
			}
		}
		return false;
	}


	@Override
	public MappingLine findIteration (MappingLine where) {
		return lines.get(found.size() + 1);
	}

	@Override
	public String expression(MappingLine current) {
		if (current == null) {
			return null;
		}
		if (current.getIterateOn() != null) {
			return current.getIterateOn();
		}
		return current.getExpression();
	}

	@Override
	public boolean isExpressionContributing(String varName) {
		return true;
	}

	@Override
	public String[] namespaces() {
		return null;
	}

	@Override
	public MappingExpression getExpression(String name)
			throws InvalidMappingTableException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterator<String> getExpressionsNames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MappingParameter getInParameter(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getInParametersCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public MappingParameter getOutParameter(String name) {
		// TODO Auto-generated method stub
		return null;
	}

}
