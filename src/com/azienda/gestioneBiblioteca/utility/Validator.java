package com.azienda.gestioneBiblioteca.utility;

import com.azienda.gestioneBiblioteca.customExceptions.LimitExceededException;

public class Validator {
	// costanti
	public static final String LIBRARY_NAME = "LIBRARY";
	public static final Integer MAX_BORROW_PER_USER = 10;
	//aggiungo un metodo che verifica se un utente supera il limite dei prestii
	public static void checkBorrowLimit(int currentBorrowed) throws LimitExceededException{
		if (currentBorrowed >= MAX_BORROW_PER_USER) {
	        throw new LimitExceededException("Limite di " + MAX_BORROW_PER_USER + " prestiti raggiunto.", new Throwable("Troppi oggetti"));
	    }
	}
}
