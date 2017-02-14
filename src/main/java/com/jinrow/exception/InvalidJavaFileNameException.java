package com.jinrow.exception;

public class InvalidJavaFileNameException extends Exception {


	private static final long serialVersionUID = -3834142514846997048L;

	
	public InvalidJavaFileNameException() {
		super("Filename is does not have a java extension");
	}
	
	public InvalidJavaFileNameException(String message) {
		super(message);
	}
}
