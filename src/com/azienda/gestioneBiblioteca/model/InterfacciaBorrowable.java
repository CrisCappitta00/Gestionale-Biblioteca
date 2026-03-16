package com.azienda.gestioneBiblioteca.model;

import com.azienda.gestioneBiblioteca.customExceptions.AlreadyBorrowedException;

public interface InterfacciaBorrowable {
	public void borrow(String userId) throws AlreadyBorrowedException;
	public void giveBack();
	public boolean isBorrowed();
}
