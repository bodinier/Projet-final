package com.excilys.librarymanager.test;

import java.time.LocalDate;

import com.excilys.librarymanager.model.*;

public class ModelTest {
	
	public void modelTest(){
		String nom, prenom, adresse, email, telephone;
		nom = "Puech"; prenom = "Kevin"; adresse = "828 Bvd des Marechaux"; email = "kevin.puech@ensta.fr"; telephone = "0651251970";
		Abonnement abonnement = Abonnement.BASIC;
		Membre moi = new Membre(nom, prenom, adresse, email, telephone, abonnement);
		System.out.println(moi.toString());
		
		String titre, auteur, isbn;
		titre ="Harry Potter and the Philosopher's Stone"; auteur = "J. K. Rowling"; isbn = "0747532699";
		Livre monLivre = new Livre(titre, auteur, isbn);
		System.out.println(monLivre.toString());
		
		int year, month, day;
		year = 2020; month = 3; day = 13;
		LocalDate dateEmprunt = LocalDate.now();
		LocalDate dateRetour = LocalDate.of(year, month, day);
		Emprunt emprunt0 = new Emprunt(monLivre, moi, dateEmprunt, dateRetour);
		System.out.println(emprunt0.toString());
	}

	
}


