package exceptions;

import enums.ErrorType;

public class ApplicationException extends Exception {
	
	private ErrorType error;
	
	// Creating a new customer exception
	public ApplicationException(String message, ErrorType error){
	
		super(message);
		
		this.error=error;
	}
	
	// Wrapping a thrown exception in the system custom exception
	public ApplicationException(String message, ErrorType error, Exception e){
		
		super(message,e);
		
		this.error = error;
	}
	
	// Get error type
	public ErrorType getErrorType(){
		return error;
	}
}
