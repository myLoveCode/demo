package com.example.core.exception;

public class SystemException extends RuntimeException {

	private static final long serialVersionUID = 8989828988277681296L;

	public SystemException() {
		super("系统异常");
	}

	public SystemException(Exception e) {
		super(e);
	}

	public SystemException(String message) {
		super(message);
	}

	public SystemException(String message, Throwable arg) {
		super(message, arg);
	}
}
