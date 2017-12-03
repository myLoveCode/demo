package com.example.core.exception;

public class InvalidRequestException extends RuntimeException {

	private static final long serialVersionUID = -8681706078261601113L;

	public InvalidRequestException() {
		super("输入参数异常");
	}

	public InvalidRequestException(Exception e) {
		super(e);
	}

	public InvalidRequestException(String message) {
		super(message);
	}

	public InvalidRequestException(String message, Throwable arg) {
		super(message, arg);
	}
}
