package com.sequencing.oauth.exception;

public class BasicAuthenticationFailedException extends Exception
{
	private static final long serialVersionUID = 4607326018356301058L;

	public BasicAuthenticationFailedException() {
	}

	public BasicAuthenticationFailedException(String message) {
		super(message);
	}

	public BasicAuthenticationFailedException(Throwable cause) {
		super(cause);
	}

	public BasicAuthenticationFailedException(String message, Throwable cause) {
		super(message, cause);
	}

	public BasicAuthenticationFailedException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
