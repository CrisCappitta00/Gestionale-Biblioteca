package com.azienda.gestioneBiblioteca.ui;

import java.util.Scanner;

import com.azienda.gestioneBiblioteca.logic.LibraryManager;
import com.azienda.gestioneBiblioteca.model.Book;
import com.azienda.gestioneBiblioteca.model.Dvd;
import com.azienda.gestioneBiblioteca.model.Magazine;

public class MainBiblioteca {

	public static void main(String[] args) {
		// Usiamo il try-with-resources per lo scanner
		try (Scanner sc = new Scanner(System.in)) {

			LibraryManager lm = new LibraryManager();
			boolean running = true;

			System.out.println("===Benvenuto nel sistema di gestione Bibliotecario===");

			while (running) {
				menu();
				int scelta = sc.nextInt();
				sc.nextLine(); // pulizia buffer

				switch (scelta) {
				case 1:
					addItem(lm, sc);
					break;
				case 2:
					borrowItem(lm, sc);
					break;
				case 3:
					returnItem(lm, sc);
					break;
				case 4:
					lm.listBorrowedItem(); // uso il metodo del library manager
					break;
				case 5:
					running = false;
					System.out.println("Chiusura programma in corso...");
					break;
				case 6:
					lm.listAllItem(); // visualizzo il catalogo della biblioteca
					break;
				default:
					System.out.println("Scelta non valida!.");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Errore fatale: " + e.getMessage());
		}
		System.out.println("Programma Terminato.");
	}

	public static void menu() {
		System.out.println("===== MENU BIBLIOTECA =====");
		System.out.println("1. Aggiungi oggetto");
		System.out.println("2. Prendi in prestito un oggetto");
		System.out.println("3. Restituisci un oggetto");
		System.out.println("4. Visualizza oggetti presi in prestito dall'utente");
		System.out.println("5. Esci");
		System.out.println("6. Visualizza il catalogo della biblioteca");
		System.out.print("Scelta: ");
	}

	// function add item
	public static void addItem(LibraryManager manager, Scanner sc) {
		System.out.println("Inserisci ID oggetto");
		String id = sc.nextLine();
		System.out.println("Aggiungi Titolo");
		String title = sc.nextLine();
		recognizeItem(manager, id, title, sc);
		System.out.println("Oggetto aggiunto con successo, ecco il catalogo aggiornato: ");
		manager.listAllItem();
	}

	public static void borrowItem(LibraryManager manager, Scanner sc) {
		System.out.println("ID Oggetto da prendere in presto: ");
		String itemId = sc.nextLine();
		System.out.println("ID Utente: ");
		String userId = sc.nextLine();

		try {
			manager.borrowItem(itemId, userId);
			System.out.println("Prestito effettuato con successo!");

		} catch (Exception e) {
			// Qui catturo le eccezione AlreadyBorrowed e itemNotFound
			System.out.println("ERRORE: " + e.getMessage() + " - " + e.getCause());
		}
	}

	public static void returnItem(LibraryManager manager, Scanner sc) {
		System.out.println("ID Oggetto da restituire: ");
		String itemId = sc.nextLine();
		try {
			manager.returnItem(itemId);
			System.out.println("Restituzione effettuata con successo!");

		} catch (Exception e) {
			// Qui catturo le eccezione AlreadyBorrowed e itemNotFound
			System.out.println("ERRORE durante la restituzione: " + e.getMessage() + " - " + e.getCause());
		}
	}

	public static void recognizeItem(LibraryManager manager, String id, String title, Scanner sc) {
		System.out.println("Cosa vuoi aggiungere? (Libro / DVD / Rivista(Magazine) )");
		String type = sc.nextLine();
		// creo degli if else concatenati per controllare se l'oggetto da aggiungere
		// corrisponde a una delle tre scelte
		if (type.equalsIgnoreCase("Libro")) {
			System.out.println("Autore: ");
			String autore = sc.nextLine();
			manager.addItem(new Book(id, title, autore, false));
		} else if (type.equalsIgnoreCase("DVD")) {
			System.out.println("Durata (minuti): ");
			double durata = sc.nextDouble();
			sc.nextLine();
			manager.addItem(new Dvd(id, title, durata, false));
		} else if (type.equalsIgnoreCase("Rivista") || type.equalsIgnoreCase("Magazine")) {
			System.out.println("Numero uscita: ");
			int uscita = sc.nextInt();
			sc.nextLine();
			manager.addItem(new Magazine(id, title, uscita));
		} else {
			System.out.println("Tipo non riconosciuto. Operazione annullata.");
		}
	}
}
