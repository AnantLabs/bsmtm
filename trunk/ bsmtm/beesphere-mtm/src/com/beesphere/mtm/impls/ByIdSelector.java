package com.beesphere.mtm.impls;

import com.beesphere.mtm.model.OutputSelector;
import com.beesphere.mtm.model.Selectable;

public class ByIdSelector implements OutputSelector {
	
	private static final long serialVersionUID = 153529348448789210L;

	private String id;
	
	public ByIdSelector () {
	}
	
	public ByIdSelector (String id) {
		this.id = id;
	}
	
	@Override
	public boolean select (Selectable selectable) {
		if (selectable == null) {
			return false;
		}
		return selectable.getOutput () != null && selectable.getOutput ().equals (id);
	}
	
	public void setId (String id) {
		this.id = id;
	}
	
}
