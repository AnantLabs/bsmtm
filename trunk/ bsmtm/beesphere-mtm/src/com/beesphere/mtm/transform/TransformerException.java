package com.beesphere.mtm.transform;

public class TransformerException extends Exception {


	/**
	 * 
	 */
	private static final long serialVersionUID = 5075672114573857877L;

	public TransformerException (String message) {
		super (message);
	}

	public TransformerException (Throwable throwable, String message) {
		super (message, throwable);
	}

	public TransformerException (Throwable throwable) {
		super (throwable);
	}


}
