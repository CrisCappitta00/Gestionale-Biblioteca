package com.azienda.gestioneBiblioteca.customExceptions;

public class ItemNotFoundException extends Exception {
	public ItemNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
