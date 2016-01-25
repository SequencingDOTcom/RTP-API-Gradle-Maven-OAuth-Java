package com.sequencing.oauth.exception;

public class NonAuthorizedException extends Exception
{
	private static final long serialVersionUID = -7927469831608112282L;

	public NonAuthorizedException() {
	}

	public NonAuthorizedException(String message) {
		super(message);
	}

	public NonAuthorizedException(Throwable cause) {
		super(cause);
	}

	public NonAuthorizedException(String message, Throwable cause) {
		super(message, cause);
	}

	public NonAuthorizedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
