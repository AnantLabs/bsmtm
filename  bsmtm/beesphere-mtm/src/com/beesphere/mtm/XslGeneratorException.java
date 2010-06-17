package com.beesphere.mtm;

public class XslGeneratorException extends Exception {

	private static final long serialVersionUID = 3778652114497335944L;
	
	private int lineNumber;
	private String lineFragment;
	
	public XslGeneratorException (int lineNumber, String lineFragment) {
		this ();
		this.lineNumber = lineNumber;
		this.lineFragment = lineFragment;
	}

	public XslGeneratorException (int lineNumber) {
		this (lineNumber, null);
	}

	public XslGeneratorException () {
		super ();
	}

	public XslGeneratorException (String message, int lineNumber, String lineFragment) {
		this (message);
		this.lineNumber = lineNumber;
		this.lineFragment = lineFragment;
	}

	public XslGeneratorException (String message) {
		super (message);
	}

	public XslGeneratorException (Throwable throwable, String message) {
		super (message, throwable);
	}

	public XslGeneratorException (Throwable throwable, String message, int lineNumber, String lineFragment) {
		this (throwable, message);
		this.lineNumber = lineNumber;
		this.lineFragment = lineFragment;
	}

	public XslGeneratorException (Throwable throwable, int lineNumber, String lineFragment) {
		this (throwable);
		this.lineNumber = lineNumber;
		this.lineFragment = lineFragment;
	}

	public XslGeneratorException (Throwable throwable) {
		super (throwable);
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}

	public String getLineFragment() {
		return lineFragment;
	}

	public void setLineFragment(String lineFragment) {
		this.lineFragment = lineFragment;
	}

}
