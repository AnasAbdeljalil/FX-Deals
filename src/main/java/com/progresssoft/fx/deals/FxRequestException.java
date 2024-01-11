package com.progresssoft.fx.deals;

public class FxRequestException extends RuntimeException {

	private static final long serialVersionUID = -432528857361517859L;

	public FxRequestException() {
		super();
	}

	public FxRequestException(final String message) {
		super(message);
	}

	public FxRequestException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public FxRequestException(final String message, final Throwable cause, final boolean enableSuppression,
			final boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public FxRequestException(final Throwable cause) {
		super(cause);
	}

}
