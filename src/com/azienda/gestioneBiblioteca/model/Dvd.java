package com.azienda.gestioneBiblioteca.model;

import com.azienda.gestioneBiblioteca.customExceptions.AlreadyBorrowedException;

public class Dvd extends AbstractItem implements InterfacciaBorrowable {
	private double durationMinutes;
	private boolean borrowed;

	public Dvd(String id, String title, double durationMinutes, boolean borrowed) {
		super(id, title);
		this.durationMinutes = durationMinutes;
		this.setBorrowed(borrowed);
	}

	public double getDurationMinutes() {
		return durationMinutes;
	}

	public void setDurationMinutes(Double durationMinutes) {
		this.durationMinutes = durationMinutes;
	}

	public boolean isBorrowed() {
		return borrowed;
	}

	public void setBorrowed(boolean borrowed) {
		this.borrowed = borrowed;
	}

	@Override
	public void borrow(String userId) throws AlreadyBorrowedException {
		if (borrowed) {
			throw new AlreadyBorrowedException("Impossibile prelevare il DVD",
					new Throwable("Il DVD è già in prestito"));
		}
		borrowed = true;
	}

	@Override
	public void giveBack() {
		borrowed = false;

	}

	@Override
	public String toString() {
		return "Dvd [durationMinutes=" + durationMinutes + ", borrowed=" + borrowed + "]" + " " + super.toString();
	}

}
