package jp.kouto.fuyuki.akiba.todo_application.exceptions;

public class RyzaDBException extends Exception{

	public RyzaDBException(String message) {
		super(message);
	}
	
	public RyzaDBException(String message, Throwable cause){
		super(message, cause);
	}
}
