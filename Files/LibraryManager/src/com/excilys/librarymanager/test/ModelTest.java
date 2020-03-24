package com.excilys.librarymanager.test;

import java.time.LocalDate;

import com.excilys.librarymanager.model.*;

public class ModelTest {
	
	public void modelTest(){
		String nom, prenom, adresse, email, telephone;
		nom = "BODINIER"; prenom = "Alexandre"; adresse = "828 Bvd des Marechaux"; email = "alexandre.bodinier@ensta-paris.fr"; telephone = "0000000000";
		Abonnement abonnement = Abonnement.BASIC;
		Membre moi = new Membre(nom, prenom, adresse, email, telephone, abonnement);
		System.out.println(moi.toString());
		
		String titre, auteur, isbn;
		titre ="l'Arrache-Coeur"; auteur = "Boris Vian"; isbn = "123-567898756";
		Livre monLivre = new Livre(titre, auteur, isbn);
		System.out.println(monLivre.toString());
		
		int year, month, day;
		year = 2020; month = 3; day = 24;
		LocalDate dateEmprunt = LocalDate.now();
		LocalDate dateRetour = LocalDate.of(year, month, day);
		Emprunt emprunt0 = new Emprunt(monLivre, moi, dateEmprunt, dateRetour);
		System.out.println(emprunt0.toString());
	}

	
}


