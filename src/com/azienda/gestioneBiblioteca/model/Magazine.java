package com.azienda.gestioneBiblioteca.model;

public class Magazine extends AbstractItem{
	private int issueNumber; // num uscita

	public Magazine(String id, String title, int issueNumber) {
		super(id, title);
		this.issueNumber = issueNumber;
	}

	public int getIssueNumber() {
		return issueNumber;
	}

	public void setIssueNumber(int issueNumber) {
		this.issueNumber = issueNumber;
	}

	@Override
	public String toString() {
		return "Magazine, issueNumber: " + issueNumber + " " + super.toString();
	}
	
}
