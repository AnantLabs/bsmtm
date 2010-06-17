package com.beesphere.mtm;

public class InvalidMappingTableException extends Exception {

	private static final long serialVersionUID = 556067938830708523L;

	public InvalidMappingTableException (String message) {
		super (message);
	}

	public InvalidMappingTableException (Throwable throwable, String message) {
		super (message, throwable);
	}

	public InvalidMappingTableException (Throwable throwable) {
		super (throwable);
	}

}
