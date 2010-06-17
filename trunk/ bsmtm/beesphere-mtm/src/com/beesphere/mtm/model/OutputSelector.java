package com.beesphere.mtm.model;

import java.io.Serializable;

public interface OutputSelector extends Serializable {
	boolean select (Selectable selectable);
}
