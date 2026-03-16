package com.azienda.gestioneBiblioteca.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.azienda.gestioneBiblioteca.customExceptions.AlreadyBorrowedException;
import com.azienda.gestioneBiblioteca.customExceptions.ItemNotFoundException;
import com.azienda.gestioneBiblioteca.customExceptions.LimitExceededException;
import com.azienda.gestioneBiblioteca.model.AbstractItem;
import com.azienda.gestioneBiblioteca.model.InterfacciaBorrowable;
import com.azienda.gestioneBiblioteca.utility.Validator;

public class LibraryManager {
	// Lista degli oggetti presenti in biblioteca (libri, riviste, DVD ecc.)
	private ArrayList<AbstractItem> items;
	/*
	 * Mappa che tiene traccia dei prestiti: - Chiave: ID dell’utente (String) -
	 * Valore: lista degli oggetti che ha preso in prestito
	 * 
	 * La lista contiene solo oggetti che implementano InterfacciaBorrowable.
	 */
	private HashMap<String, ArrayList<InterfacciaBorrowable>> borrowedByUser; // Tenere traccia di quali oggetti sono
																				// presi in prestito dall' utente

	public LibraryManager() {
		super();
		items = new ArrayList<>();
		borrowedByUser = new HashMap<>();
	}

	// aggiungo un oggetto al catalogo generale della biblioteca
	public void addItem(AbstractItem item) {
		items.add(item);
	}

	/**
	 * Cerca un oggetto in base al suo ID. Se lo trova, lo restituisce. Se non lo
	 * trova, lancia una ItemNotFoundException.
	 */
	public AbstractItem getItemById(String id) throws ItemNotFoundException {
		// Scandisce tutti gli oggetti nel catalogo
		for (AbstractItem abstractItem : items) {
			// Controlla se l'ID coincide
			if (abstractItem.getId().equals(id)) {
				return abstractItem;
			}
		}
		// Se il ciclo finisce senza trovare nulla → eccezione
		throw new ItemNotFoundException("Oggetto con ID: " + id + " non trovato.", new Throwable("ID Inesistente"));
	}

	/**
	 * Gestisce la procedura di prestito di un oggetto. 
	 * - Verifica se esiste 
	 * - Controlla se è un oggetto prestabile (implementa InterfacciaBorrowable)
	 * - Tenta il prestito (che può lanciare AlreadyBorrowedException) 
	 * - Registra il prestito nella mappa borrowedByUser
	 * 
	 * @throws LimitExceededException
	 */
	public void borrowItem(String itemId, String userId)
			throws AlreadyBorrowedException, ItemNotFoundException, LimitExceededException {
		// Recupero dell’oggetto (o eccezione se non esiste)
		AbstractItem item = getItemById(itemId);// può lanciare ItemNotFoundException
		// Verifico che l’oggetto possa essere preso in prestito e sia borrowable
		if (!(item instanceof InterfacciaBorrowable)) {
			throw new IllegalArgumentException("Questo oggetto non può essere preso in prestito.");
		}
		// Cast all’interfaccia, così da poter usare borrow() e giveBack()
		InterfacciaBorrowable bItem = (InterfacciaBorrowable) item;

		// 1. Recupero la lista attuale dell'utente
		ArrayList<InterfacciaBorrowable> userBorrowed = borrowedByUser.get(userId);
		// 2. Se l'utente non esiste ancora nella mappa, creo la sua lista
		if (userBorrowed == null) {
			userBorrowed = new ArrayList<>();
			borrowedByUser.put(userId, userBorrowed);
		}
		// 3. Controllo se può prendere altro (ora userBorrowed non è più null)
		Validator.checkBorrowLimit(userBorrowed.size());

		// Tenta di effettuare il prestito (potrebbe generare AlreadyBorrowedException)
		bItem.borrow(userId);

		userBorrowed.add(bItem);
	}

	/**
	 * Gestisce la restituzione di un oggetto. 
	 * - Verifica che esista 
	 * - Verifica che sia effettivamente un oggetto prestabile 
	 * - Rimuove l'oggetto da tutte le liste utente in cui può trovarsi 
	 * - Segna l’oggetto come “non in prestito”
	 */
	public void returnItem(String itemId) throws ItemNotFoundException {
		// Recupero dell'oggetto dal catalogo
		AbstractItem item = getItemById(itemId);

		// Solo gli oggetti Borrowable possono essere restituiti
		if (!(item instanceof InterfacciaBorrowable)) {
			throw new IllegalArgumentException("Questo oggetto non è di tipo prestabile.");
		}
		// cast
		InterfacciaBorrowable bItem = (InterfacciaBorrowable) item;

		// Provo a rimuoverlo dalle liste degli utenti
		boolean found = false;
		for (ArrayList<InterfacciaBorrowable> lista : borrowedByUser.values()) {
			if (lista.remove(bItem)) {
				found = true;
				break; // trovato e rimosso, non serve cercare oltre
			}
		}
		if (!found) {
			System.out.println("Avviso: L'oggetto non risultava assegnato a nessun utente.");
		}
		// Segna l'oggetto come restituito
		bItem.giveBack();

		// Rimuovo eventuali utenti che ora hanno la lista vuota
		borrowedByUser.entrySet().removeIf(entry -> entry.getValue().isEmpty());
	}

	public void listAllItem() {
		System.out.println("=== CATALOGO BIBLIOTECA ===");
		if (!items.isEmpty()) {
			for (AbstractItem abstractItem : items) {
				System.out.println(abstractItem.toString());
			}
		} else {
			System.out.println("Catalogo attualmente vuoto!");
		}

	}

	public void listBorrowedItem() {
		System.out.println("=== RIEPILOGO PRESTITI PER UTENTE ===");

		if (borrowedByUser.isEmpty()) {
			System.out.println("Non ci sono prestiti attivi al momento.");
			return;
		}

		// scorro tutte le chiavi (gli id utente) della mappa
		for (String userId : borrowedByUser.keySet()) {
			ArrayList<InterfacciaBorrowable> listObjects = borrowedByUser.get(userId);

			// se l'utente ha degli oggetti in prestito, li stampo
			if (!listObjects.isEmpty()) {
				System.out.println("Utente ID: " + userId);
				for (InterfacciaBorrowable bItem : listObjects) {
					// poiché bItem è un'interfaccia, la stampo come oggetto
					System.out.println(" - " + bItem.toString());
				}
			}
		}
	}
}
