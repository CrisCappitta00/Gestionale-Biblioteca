package com.azienda.gestioneBiblioteca.customExceptions;

public class LimitExceededException extends Exception {
	public LimitExceededException(String message, Throwable cause) {
		super(message, cause);
	}
}
