package com.excilys.librarymanager.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.excilys.librarymanager.dao.ILivreDao;
import com.excilys.librarymanager.dao.impl.LivreDao;
import com.excilys.librarymanager.exception.ServiceException;
import com.excilys.librarymanager.model.Livre;
import com.excilys.librarymanager.service.IEmpruntService;
import com.excilys.librarymanager.service.ILivreService;

public class LivreService implements ILivreService {
	private static LivreService instance;
	private LivreService() { }	
	public static ILivreService getInstance() {
		if(instance == null) {
			instance = new LivreService();
		}
		return instance;
	}

	@Override
	public List<Livre> getList() throws ServiceException{
		List<Livre> livres = new ArrayList<>();
		ILivreDao ILivre = LivreDao.getInstance();
		try {
			livres = ILivre.getList();
		} catch (Exception e) {
			throw new ServiceException("Erreur lors de l'appel à LivreGetList service", e);
		}
		return livres;
	}

	@Override
	public List<Livre> getListDispo() throws ServiceException{
		// En 1) on trouve la liste des livres 2) on ajoute à la liste des dispo ssi ils sont dispo
		List<Livre> livres = new ArrayList<>();
		List<Livre> livresDispo = new ArrayList<>();
		ILivreDao ILivre = LivreDao.getInstance();
		IEmpruntService livreService = EmpruntService.getInstance();
		try {
			Livre livre = new Livre();
			livres = ILivre.getList();
			if (livres != null){ // On vérifie que la liste n'est pas nulle
				for (int i=0; i <livres.size(); i++){
					livre = livres.get(i);
					if (livreService.isLivreDispo(livre.getId()))
						livresDispo.add(livre);
				}
			}
		} catch (Exception e) {
			throw new ServiceException("Erreur lors de l'appel à LivreGetListDispo service", e);
		}
		return livresDispo;
	}

	@Override
	public Livre getById(int id) throws ServiceException{
		Livre livre = new Livre();
		ILivreDao ILivre = LivreDao.getInstance();
		try {
			livre = ILivre.getById(id);
		} catch (Exception e){
			throw new ServiceException(" Erreur lors de l'appel à LivreGetById service", e);
		}
		return livre;
	}

	@Override
	public int create(String titre, String auteur, String isbn) throws ServiceException{
		ILivreDao ILivre = LivreDao.getInstance();
		int id = -1;
		if (titre != ""){
			try {
				id = ILivre.create(titre, auteur, isbn);
			} catch (Exception e){
				throw new ServiceException("Erreur lors de la creation d'un livre dans service", e);
			}
		} else {
			throw new ServiceException("Le titre du livre est vide !");
		}
		return id;
	}

	@Override
	public void update(Livre livre) throws ServiceException{
		ILivreDao ILivre = LivreDao.getInstance();
		if (livre.getTitre() != ""){
			try {
				ILivre.update(livre);
			} catch (Exception e){
				throw new ServiceException("Erreur lors de la mise à jour d'un livre dans service", e);
			}
		} else {
			throw new ServiceException("Le titre du livre est vide !");
		}
	}

	@Override
	public void delete(int id) throws ServiceException{
		ILivreDao ILivre = LivreDao.getInstance();
		try {
			ILivre.delete(id);
		} catch (Exception e){
			throw new ServiceException("Erreur lors de la suppression d'un livre dans service");
		}
	}

	@Override
	public int count() throws ServiceException{
		ILivreDao ILivre = LivreDao.getInstance();
		int count =-1;
		try {
			count = ILivre.count();
		} catch (Exception e){
			throw new ServiceException("Erreur lors du comptage des livres dans service");
		}
		return count;
	}
}
