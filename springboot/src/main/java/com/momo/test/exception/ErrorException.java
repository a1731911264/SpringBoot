package com.momo.test.exception;

public class ErrorException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private String errorCode;
	
	private String errorMessage;

	
	
	public ErrorException(String errorCode, String errorMessage) {
		super();
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public ErrorException(String errorMessage) {
		super();
		this.errorMessage = errorMessage;
	}
	
	/**
	 * @return the errorCode
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * @param errorCode the errorCode to set
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * @param errorMessage the errorMessage to set
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	
}
