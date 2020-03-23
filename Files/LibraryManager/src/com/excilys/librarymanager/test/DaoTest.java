package com.excilys.librarymanager.test;

import java.time.LocalDate;
import java.util.List;

import com.excilys.librarymanager.dao.*;
import com.excilys.librarymanager.dao.impl.*;
import com.excilys.librarymanager.exception.*;
import com.excilys.librarymanager.model.Abonnement;
import com.excilys.librarymanager.model.Emprunt;
import com.excilys.librarymanager.model.Livre;
import com.excilys.librarymanager.model.Membre;


public class DaoTest {

	public static void testLivre() throws DaoException{
		System.out.println("\n\nDémarrage du test de la table livre : ");
		ILivreDao ILivre = LivreDao.getInstance();
		List<Livre> livres = ILivre.getList();
		System.out.println("liste des livres : " + livres);

		System.out.println("\nTest : ajout d'un livre :");
		Livre lArracheCoeur = new Livre("l'Arrache-Coeur", "Boris Vian","978-2501124681");
		ILivre.create(lArracheCoeur.getTitre(), lArracheCoeur.getAuteur(), lArracheCoeur.getIsbn());
		livres = ILivre.getList();
		System.out.println("nouvelle liste des livres : " + livres);

		System.out.println("\nTest : getById");
		Livre test = ILivre.getById(5);
		System.out.println("livre[5] = " + test);

		System.out.println("\nTest : update");
		test = ILivre.getById(3);
		System.out.println("livre[3] = " + test);
		test.setAuteur("UPDATED");
		ILivre.update(test);

		System.out.println("\nTest : count");
		System.out.println("il y a : " + ILivre.count() + " livres !");

		System.out.println("\nTest : delete");
		System.out.println("il y a : " + ILivre.count() + " livres !");
		System.out.println("Livre à supprimer = " + ILivre.getById(11).toString());
		ILivre.delete(11);
		System.out.println("il y a : " + ILivre.count() + " livres !");
		livres = ILivre.getList();
		System.out.println("nouvelle liste des livres : " + livres);

	}
	public static void testMembre() throws DaoException{
		System.out.println("\n\nDémarrage du test de la table membre : ");
		IMembreDao IMembre = MembreDao.getInstance();
		List<Membre> membres = IMembre.getList();
		System.out.println("liste des membres : " + membres);

		System.out.println("\nTest : ajout d'un membre :");
		Membre moi = new Membre("BODINIER", "Alexandre", "317, Route de Belair", "alexandre.bodinier@ensta-paris.fr", "0000000000", Abonnement.VIP);
		IMembre.create("BODINIER", "Alexandre", "317, Route de Belair", "alexandre.bodinier@ensta-paris.fr", "0000000000");
		membres = IMembre.getList();
		System.out.println("nouvelle liste des livres : " + membres);

		System.out.println("\nTest : getById");
		Membre test = IMembre.getById(5);
		System.out.println("membre[5] = " + test);

		System.out.println("\nTest : update");
		test = IMembre.getById(3);
		System.out.println("membre[3] = " + test);
		test.setNom("UPDATED");
		IMembre.update(test);

		System.out.println("\nTest : count");
		System.out.println("il y a : " + IMembre.count() + " membres !");

		System.out.println("\nTest : delete");
		System.out.println("il y a : " + IMembre.count() + " membres !");
		System.out.println("membre à supprimer = " + IMembre.getById(13).toString());
		IMembre.delete(13);
		System.out.println("il y a : " + IMembre.count() + " membres !");
		membres = IMembre.getList();
		System.out.println("nouvelle liste des membres : " + membres);
	}
	public static void testEmprunt() throws DaoException{
		IEmpruntDao IEmprunt = EmpruntDao.getInstance();
		System.out.println("\n\n Début des tests de la classe emprunts : ");
		List<Emprunt> emprunts = IEmprunt.getList();
		System.out.println("\nTest du getList : ");
		System.out.println("liste des emprunts = " + emprunts);

		System.out.println("\nTest du getListCurrent : ");
		emprunts = IEmprunt.getListCurrent();
		System.out.println("liste des emprunts en cours = " + emprunts);

		System.out.println("\nTest du getListCurrentByLivre : ");
		emprunts = IEmprunt.getListCurrentByLivre(3);
		System.out.println("liste des emprunts en cours = " + emprunts);

		System.out.println("\nTest du getListCurrentByMember : ");
		emprunts = IEmprunt.getListCurrentByMembre(6);
		System.out.println("liste des emprunts en cours = " + emprunts);
		
		System.out.println("\nTest du create : ");
		emprunts = IEmprunt.getListCurrentByMembre(5);
		System.out.println("liste des emprunts en cours par M. n°5= " + emprunts);
		IEmprunt.create(3, 5, LocalDate.now());
		emprunts = IEmprunt.getListCurrentByMembre(5);
		System.out.println("liste des emprunts en cours par M. n°5= " + emprunts);

		System.out.println("\nTest du update : ");
		Emprunt test = IEmprunt.getById(7);
		System.out.println("avant : " + test);
		test.setDateEmprunt(LocalDate.now().plusDays(14));
		IEmprunt.update(test);
		test = IEmprunt.getById(7);
		System.out.println("maintenant : " + test);

		System.out.println("Test du count : ");
		System.out.println("il y a " + IEmprunt.count() + "emprunts !");
	}

	public static void main(String args[]) throws DaoException {
		testLivre();
		testEmprunt();
		testMembre();
	}

}
