package com.sseltzer.selenium.framework.error.exceptions;

public class FrameworkException extends RuntimeException {
	private static final long serialVersionUID = 4715251314652770489L;

	public FrameworkException() {
		super();
	}

	public FrameworkException(String message) {
		super(message);
	}

	public FrameworkException(String message, Throwable cause) {
		super(message, cause);
	}

	public FrameworkException(Throwable cause) {
		super(cause);
	}
}
