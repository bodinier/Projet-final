package com.excilys.librarymanager.service.impl;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.excilys.librarymanager.dao.IEmpruntDao;
import com.excilys.librarymanager.dao.IMembreDao;
import com.excilys.librarymanager.dao.impl.EmpruntDao;
import com.excilys.librarymanager.dao.impl.MembreDao;
import com.excilys.librarymanager.exception.ServiceException;
import com.excilys.librarymanager.model.Emprunt;
import com.excilys.librarymanager.model.Membre;
import com.excilys.librarymanager.service.IEmpruntService;

public class EmpruntService implements IEmpruntService {
	private static EmpruntService instance;
	private EmpruntService() { }	
	public static IEmpruntService getInstance() {
		if(instance == null) {
			instance = new EmpruntService();
		}
		return instance;
	}

	@Override
	public List<Emprunt> getList() throws ServiceException{
		List<Emprunt> emprunts = new ArrayList<>();
		IEmpruntDao IEmprunt = EmpruntDao.getInstance();
		try {
			emprunts = IEmprunt.getList();
		} catch (Exception e){
			throw new ServiceException("Erreur lors de l'appel à EmpruntGetList service", e);
		}
		return emprunts;
	}
	@Override
	public List<Emprunt> getListCurrent() throws ServiceException{
		List<Emprunt> emprunts = new ArrayList<>();
		IEmpruntDao IEmprunt = EmpruntDao.getInstance();
		try {
			emprunts = IEmprunt.getListCurrent();
		} catch (Exception e){
			throw new ServiceException("Erreur lors de l'appel à EmpruntGetListCurrent service", e);
		}
		return emprunts;
	}

	@Override
	public List<Emprunt> getListCurrentByMembre(int idMembre) throws ServiceException{
		List<Emprunt> emprunts = new ArrayList<>();
		IEmpruntDao IEmprunt = EmpruntDao.getInstance();
		try {
			emprunts = IEmprunt.getListCurrentByMembre(idMembre);
		} catch (Exception e){
			throw new ServiceException("Erreur lors de l'appel à EmpruntGetListByMembre service", e);
		}
		return emprunts;
	}

	@Override
	public List<Emprunt> getListCurrentByLivre(int idLivre) throws ServiceException{
		List<Emprunt> emprunts = new ArrayList<>();
		IEmpruntDao IEmprunt = EmpruntDao.getInstance();
		try {
			emprunts = IEmprunt.getListCurrentByLivre(idLivre);
		} catch (Exception e){
			throw new ServiceException("Erreur lors de l'appel à EmpruntGetListByLivre service", e);
		}
		return emprunts;
	}
	
	@Override
	public Emprunt getById(int id) throws ServiceException{
		Emprunt emprunt = new Emprunt();
		IEmpruntDao IEmprunt = EmpruntDao.getInstance();
		try {
			emprunt = IEmprunt.getById(id);
		} catch (Exception e){
			throw new ServiceException("Erreur lors de l'appel à EmpruntGetById service", e);
		}
		return emprunt;
	}

	@Override
	/**
	 * On vérifie ici que le membre a bien le droit d'emprunter et que le livre est dispo !
	 * Si ce n'est pas le cas, on renvoit une exception
	 */
	public void create(int idMembre, int idLivre, LocalDate dateEmprunt) throws Exception, ServiceException{
		IEmpruntDao IEmprunt = EmpruntDao.getInstance();
		IMembreDao IMembre = MembreDao.getInstance();
		Membre membre = IMembre.getById(idMembre);
		if (isEmpruntPossible(membre) && isLivreDispo(idLivre)){
			try {
				IEmprunt.create(idMembre, idLivre, dateEmprunt);
			} catch (Exception e){
				throw new ServiceException("Erreur lors de l'appel à EmpruntCreate service", e);
			}
		}
		else 
			throw new Exception("Ce membre ne peut plus emprunter de livres.");
	}

	@Override
	public void returnBook(int id) throws ServiceException{
		IEmpruntDao IEmprunt = EmpruntDao.getInstance();
		try {
			Emprunt emprunt = IEmprunt.getById(id);
			if (emprunt.getDateRetour() == null){
				emprunt.setDateRetour(LocalDate.now());
				IEmprunt.update(emprunt);
			}
		} catch (Exception e){
			throw new ServiceException("Erreur lors de l'appel à returnBook service", e);
		}
	}
	public int count() throws ServiceException{
		IEmpruntDao IEmprunt = EmpruntDao.getInstance();
		int count = -1;
		try {
			count = IEmprunt.count();
		} catch (Exception e){
			throw new ServiceException("Erreur lors de l'appel à empruntCount service", e);
		}
		return count;
	}

	public boolean isLivreDispo(int idLivre) throws ServiceException{
		IEmpruntDao IEmprunt = EmpruntDao.getInstance();
		boolean isDispo = false;
		// Un livre est dispo ssi il n'est pas actuellement emprunté. On considère que les livres sont en un seul exemplaire (id unique de toutes façons)
		try {
			if (IEmprunt.getListCurrentByLivre(idLivre).isEmpty()){
				System.out.println(IEmprunt.getListCurrentByLivre(idLivre));
				isDispo = true;
			}
		} catch (Exception e){
			throw new ServiceException("Erreur lors de l'appel a isLivreDispo service", e);
		}
		return isDispo;
	}

	@Override
	/**
	 * @return true si le membre peut emprunter un livre de plus en s'aidant de la méthode de Membre
	 */
	public boolean isEmpruntPossible(Membre membre) throws ServiceException {
		IEmpruntDao IEmprunt = EmpruntDao.getInstance();
		int nbEmpruntsEnCours=0;
		try {
			nbEmpruntsEnCours = IEmprunt.getListCurrentByMembre(membre.getId()).size();
		} catch (Exception e){
			throw new ServiceException("Erreur lors de l'appel a isEmpruntPossible service", e);
		}
		return membre.isEmpruntPossible(nbEmpruntsEnCours);
	}
}
