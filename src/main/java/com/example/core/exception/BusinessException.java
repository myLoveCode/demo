package com.example.core.exception;

public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = -4942849136285353014L;

	public BusinessException() {
		super("操作异常");
	}

	public BusinessException(Exception e) {
		super(e);
	}

	public BusinessException(String message) {
		super(message);
	}

	public BusinessException(String message, Throwable arg) {
		super(message, arg);
	}
}
