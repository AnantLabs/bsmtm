package com.beesphere.mtm.model;

import java.io.Serializable;

public interface MappingLineSelector extends Serializable {
	boolean select (MappingLine line);
}
