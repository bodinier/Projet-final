package com.excilys.librarymanager.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.excilys.librarymanager.dao.IEmpruntDao;
import com.excilys.librarymanager.dao.IMembreDao;
import com.excilys.librarymanager.dao.impl.EmpruntDao;
import com.excilys.librarymanager.dao.impl.MembreDao;
import com.excilys.librarymanager.exception.DaoException;
import com.excilys.librarymanager.exception.ServiceException;
import com.excilys.librarymanager.model.Membre;
import com.excilys.librarymanager.service.IMembreService;

public class MembreService implements IMembreService {

	private static MembreService instance;
	private MembreService() { }	
	public static IMembreService getInstance() {
		if(instance == null) {
			instance = new MembreService();
		}
		return instance;
	}
	
	@Override
	public List<Membre> getList() throws ServiceException{
		IMembreDao IMembre = MembreDao.getInstance();
		List<Membre> membres = new ArrayList<>();
		try {
			membres = IMembre.getList();
		} catch	(DaoException e) {
			throw new ServiceException("Erreur lors de l'appel à membreGetList service",e);
		}
		return membres;
	}

	@Override
	public List<Membre> getListMembreEmpruntPossible() throws ServiceException{
		IMembreDao IMembre = MembreDao.getInstance();
		IEmpruntDao IEmprunt = EmpruntDao.getInstance();
		List<Membre> membres = new ArrayList<>();
		List<Membre> membresEmpruntPossibles = new ArrayList<>();
		try {
			membres = IMembre.getList();
			int nbLivresEmpruntes;
			Membre membre = new Membre();
			if (membres != null){
				for (int i=0; i<membres.size(); i++){
					membre = membres.get(i);
					nbLivresEmpruntes = IEmprunt.getListCurrentByMembre(membre.getId()).size();
					if (membre.isEmpruntPossible(nbLivresEmpruntes)){
						membresEmpruntPossibles.add(membre);
					}
				}
			}
		} catch	(DaoException e) {
			throw new ServiceException("Erreur lors de l'appel à membreGetList service",e);
		}
		return membresEmpruntPossibles;
	}

	@Override
	public Membre getById(int id) throws ServiceException{
		IMembreDao IMembre = MembreDao.getInstance();
		Membre membre = new Membre();
		try {
			membre = IMembre.getById(id);
		} catch	(DaoException e) {
			throw new ServiceException("Erreur lors de l'appel à membreGetById service",e);
		}
		return membre;
	}
	
	@Override
	public int create(String nom, String prenom, String adresse, String email, String telephone) throws ServiceException{
		IMembreDao IMembre = MembreDao.getInstance();
		if (nom != ""){
			int id =0;
			try {
				id = IMembre.create(nom.toUpperCase(), prenom, adresse, email, telephone);
			} catch	(DaoException e) {
				throw new ServiceException("Erreur lors de l'appel à membreCreate service",e);
			}
			return id;
		}
		else {
			throw new ServiceException("Le nom est vide");
		}
	}

	@Override
	public void update(Membre membre) throws ServiceException{
		IMembreDao IMembre = MembreDao.getInstance();
		if (membre.getNom() != ""){
			membre.setNom(membre.getNom().toUpperCase());
			try {
				IMembre.update(membre);
			} catch	(DaoException e) {
				throw new ServiceException("Erreur lors de l'appel à membreUpdate service",e);
			}
		}
		else {
			throw new ServiceException("Le nom est vide");
		}
	}
	@Override
	public void delete(int id) throws ServiceException{
		IMembreDao IMembre = MembreDao.getInstance();
		try {
			IMembre.delete(id);
		} catch	(DaoException e) {
			throw new ServiceException("Erreur lors de l'appel à membreDelete service",e);
		}
	}

	@Override
	public int count() throws ServiceException{
		IMembreDao IMembre = MembreDao.getInstance();
		int ctr=-1;
		try {
			ctr = IMembre.count();
		} catch	(DaoException e) {
			throw new ServiceException("Erreur lors de l'appel à membreCount service",e);
		}
		return ctr;
	}

}
