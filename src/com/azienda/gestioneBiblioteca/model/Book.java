package com.azienda.gestioneBiblioteca.model;

import com.azienda.gestioneBiblioteca.customExceptions.AlreadyBorrowedException;

public class Book extends AbstractItem implements InterfacciaBorrowable{
	private String author;
	private boolean borrowed; // prestito
	
	public Book(String id, String title, String author, boolean borrowed) {
		super(id, title);
		this.author = author;
		this.borrowed = borrowed;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public boolean isBorrowed() {
		return borrowed;
	}

	public void setBorrowed(boolean borrowed) {
		this.borrowed = borrowed;
	}

	@Override
	public String toString() {
		return "Book, author: " + author + ", borrowed: " + borrowed + " " + super.toString();
	}

	@Override
	public void borrow(String userId) throws AlreadyBorrowedException {
		if (borrowed) {
			throw new AlreadyBorrowedException("Impossibile prelevare il libro", new Throwable("Il libro è già in prestito"));
		}
		borrowed = true;
	}

	@Override
	public void giveBack() {
		borrowed = false;
		
	}
	
	
}
