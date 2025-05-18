package jp.kouto.fuyuki.akiba.todo_application.exceptions;

public class EmailSenderException extends Exception{

	public EmailSenderException(String message) {
		super(message);
	}
	
	public EmailSenderException(String message, Throwable cause){
		super(message, cause);
	}
}
