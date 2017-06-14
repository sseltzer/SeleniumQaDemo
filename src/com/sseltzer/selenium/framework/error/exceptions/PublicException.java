package com.sseltzer.selenium.framework.error.exceptions;

public class PublicException extends FrameworkException {
	private static final long serialVersionUID = 4715251314652770489L;

	public PublicException() {
		super();
	}

	public PublicException(String message) {
		super(message);
	}

	public PublicException(String message, Throwable cause) {
		super(message, cause);
	}

	public PublicException(Throwable cause) {
		super(cause);
	}
}
