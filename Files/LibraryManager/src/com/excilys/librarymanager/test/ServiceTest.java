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
import com.excilys.librarymanager.service.IEmpruntService;
import com.excilys.librarymanager.service.ILivreService;
import com.excilys.librarymanager.service.IMembreService;
import com.excilys.librarymanager.service.impl.EmpruntService;
import com.excilys.librarymanager.service.impl.LivreService;
import com.excilys.librarymanager.service.impl.MembreService;
import com.excilys.librarymanager.utils.FillDatabase;

public class ServiceTest {

	public static void testLivre() throws ServiceException {
		System.out.println("\n\nDémarrage du test de la table livre : ");
		ILivreService ILivre = LivreService.getInstance();
		List<Livre> livres = ILivre.getList();
		System.out.println("liste des livres : " + livres);

		System.out.println("\nTest : ajout d'un livre :");
		Livre lArracheCoeur = new Livre("l'Arrache-Coeur", "Boris Vian","978-2501124681");
		ILivre.create(lArracheCoeur.getTitre(), lArracheCoeur.getAuteur(), lArracheCoeur.getIsbn());
		livres = ILivre.getList();
        System.out.println("nouvelle liste des livres : " + livres);

        System.out.println("tentative de créer un livre sans titre :");
        Livre test = new Livre("", "NE DEVRAIT PAS MARCHER","978-2501124681");
		ILivre.create(test.getTitre(), test.getAuteur(), test.getIsbn());
		livres = ILivre.getList();
		System.out.println("nouvelle liste des livres : " + livres);

		System.out.println("\nTest : getById");
		test = ILivre.getById(5);
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
	public static void testMembre() throws ServiceException{
		System.out.println("\n\nDémarrage du test de la table membre : ");
		IMembreService IMembre = MembreService.getInstance();
		IEmpruntService IEmprunt = EmpruntService.getInstance();
		List<Membre> membres = IMembre.getList();
        System.out.println("liste des membres : " + membres);

		System.out.println("\nTest : ajout d'un membre :");
		Membre moi = new Membre("bodinier", "Alexandre", "317, Route de Belair", "alexandre.bodinier@ensta-paris.fr", "0000000000", Abonnement.VIP);
		IMembre.create("bodinier", "Alexandre", "317, Route de Belair", "alexandre.bodinier@ensta-paris.fr", "0000000000");
		membres = IMembre.getList();
        System.out.println("nouvelle liste des livres : " + membres);
        
        System.out.println("\n Test du getListEmpruntIsPossible");
        IEmprunt.create(13, 1, LocalDate.now());
        IEmprunt.create(13, 2, LocalDate.now());
        System.out.println("liste des livres empruntés par moi = " + IEmprunt.getListCurrentByMembre(13));
        System.out.println(IMembre.getListMembreEmpruntPossible());

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
	public static void testEmprunt() throws ServiceException{
		IEmpruntService IEmprunt = EmpruntService.getInstance();
		IMembreService IMembre = MembreService.getInstance();
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
		emprunts = IEmprunt.getListCurrentByMembre(5);
		System.out.println("liste des emprunts en cours = " + emprunts);
		
		System.out.println("\nTest du create : ");

        System.out.println("\nTest du returnBook : ");
        IEmprunt.returnBook(2);
        IEmprunt.returnBook(5);
        emprunts = IEmprunt.getList();
		System.out.println("liste des emprunts = " + emprunts);

        System.out.println("\n Test du isLivreDispo");
        System.out.println("devrait être dispo :" + IEmprunt.isLivreDispo(7));
        System.out.println("ne devrait pas être dispo :" + IEmprunt.isLivreDispo(5));

        System.out.println("\n Test du isEmpruntPossible");
        Membre moi = new Membre("BODINIER", "Alexandre", "317, Route de Belair", "alexandre.bodinier@ensta-paris.fr", "0000000000", Abonnement.BASIC);
        IEmprunt.create(11, 1, LocalDate.now());
        IEmprunt.create(11, 2, LocalDate.now());
        System.out.println("devrait impossible = " + IEmprunt.isEmpruntPossible(IMembre.getById(1)));
        System.out.println("devrait être impossible = " + IEmprunt.isEmpruntPossible(moi));

		System.out.println("Test du count : ");
		System.out.println("il y a " + IEmprunt.count() + "emprunts !");
	}

    // Ici, les fonctions test advanced ne vont tester que les nouvelles fonctionnalité de
        // service, celles de base ayant étées testées par DaoTest
    /**
     * Doit tester : 
     * getLivreDispo
     * les conventions de nommage avec update et create
     * @throws ServiceException
     */ 
    public static void testLivreAdvanced() throws ServiceException {
        IMembreService membreService = MembreService.getInstance();
        ILivreService livreService = LivreService.getInstance();
        IEmpruntService empruntService = EmpruntService.getInstance();
        System.out.println("\n TEST DE LA CLASSE LIVRESERVICE \n");
        System.out.println("\n test des livres dispos");
        System.out.println(livreService.getListDispo());

        System.out.println("tentative de création d'un livre sans titre :");
        livreService.create("", "LIVRE_SANS_TITRE", "2459");

    }

    /**
     * Doit tester :
     * getListMembreEmpruntPossible
     * les conventions de nommage avec update et create
     * @throws ServiceException
     */
	public static void testMembreAdvanced() throws ServiceException{
        IMembreService membreService = MembreService.getInstance();
        ILivreService livreService = LivreService.getInstance();
        IEmpruntService empruntService = EmpruntService.getInstance();
        System.out.println("\n TEST DE LA CLASSE MEMBRESERVICE\n");

        System.out.println("test de getListMembreEmpruntIsPossible");
        System.out.println(membreService.getListMembreEmpruntPossible());

        System.out.println("test de nommage sans nom : ");
        membreService.create("", "SANS_NOM", "test", "test", "test");

        System.out.println("test de nommage en minuscules : ");
        membreService.create("test_minuscule", "MINUSCULE", "test", "test", "test");
    }
    
    /**
     * Doit tester :
     * isLivreDispo
     * isEmpruntPossible
     * returnBook
     * @throws ServiceException
     */
    public static void testEmpruntAdavanced() throws ServiceException{
        IMembreService membreService = MembreService.getInstance();
        ILivreService livreService = LivreService.getInstance();
        IEmpruntService empruntService = EmpruntService.getInstance();
        livreService.create("le 11e livre", "la terreur du Débbug", "55_nobodyCares_55");
        System.out.println("le livre devrait être dispo et on affiche dispo ? = " + empruntService.isLivreDispo(11));
        System.out.println("le livre ne devrait pas être dispo et on affiche dispo ? = " + empruntService.isLivreDispo(3));

        System.out.println("test emprunt possible ?");
        membreService.create("MOI", "Moi", "jhk", "hihu", "098765432");
        empruntService.create(13, 1, LocalDate.now());
        empruntService.create(13, 2, LocalDate.now());

        System.out.println("devrait afficher ne peut pas emprunter et affiche emprunter ?="+empruntService.isEmpruntPossible(membreService.getById(13)));
        System.out.println("devrait afficher peut emprunter et affiche emprunter ?="+empruntService.isEmpruntPossible(membreService.getById(1)));

        Livre book = new Livre("not yet returned", "blabla", "123567890");
        livreService.create(book.getTitre(), book.getAuteur(), book.getIsbn());
        empruntService.create(2, 12, LocalDate.now());
        System.out.println(empruntService.getById(9));
        empruntService.returnBook(9);
        System.out.println(empruntService.getById(9));


    }
    
	public static void main(String args[]) throws Exception {
        System.out.println("Attention pour que les tests fonctionnent, il faut réinitialiser la database");
		//testLivre();
		//testEmprunt();
        //testMembre();
        testEmpruntAdavanced();
	}

}
