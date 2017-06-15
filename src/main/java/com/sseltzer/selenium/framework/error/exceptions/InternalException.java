package com.sseltzer.selenium.framework.error.exceptions;

public class InternalException extends FrameworkException {
	private static final long serialVersionUID = 4715251314652770489L;

	public InternalException() {
		super();
	}

	public InternalException(String message) {
		super(message);
	}

	public InternalException(String message, Throwable cause) {
		super(message, cause);
	}

	public InternalException(Throwable cause) {
		super(cause);
	}
}
