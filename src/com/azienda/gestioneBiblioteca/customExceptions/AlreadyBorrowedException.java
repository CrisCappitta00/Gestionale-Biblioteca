package com.azienda.gestioneBiblioteca.customExceptions;

public class AlreadyBorrowedException extends Exception{
	public AlreadyBorrowedException(String message, Throwable cause) {
		super(message, cause);
	}
}
