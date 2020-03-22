package com.excilys.librarymanager.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.excilys.librarymanager.dao.*;
import com.excilys.librarymanager.exception.DaoException;
import com.excilys.librarymanager.model.Abonnement;
import com.excilys.librarymanager.model.Emprunt;
import com.excilys.librarymanager.model.Livre;
import com.excilys.librarymanager.model.Membre;
import com.excilys.librarymanager.persistence.ConnectionManager;

//TODO : il faut rajouter des appels à instance dans chaque fonction pour être sur d'avoir le droit

public class EmpruntImpl implements EmpruntDao {

	private static EmpruntImpl instance;
	private EmpruntImpl() {}	//Constructeur en privé pour ne pas avoir plusieurs instances
	public static EmpruntImpl getInstance() {
		if(instance == null) {
			instance = new EmpruntImpl();
		}
		return instance;
	}
	private static final String INSERT_ROW = "INSERT INTO emprunt (idMembre, idLivre, dateEmprunt, dateRetour) VALUES (?, ?, ?, ?);";
	private static final String UPDATE = "UPDATE emprunt SET idMembre = ?, idLivre = ?, dateEmprunt = ?, dateRetour = ? WHERE id ?;";
	private static final String COUNT_ALL = "SELECT COUNT(id) as COUNT from emprunt;";
	private static final String SELECT_BY_ID = "SELECT e.id AS idEmprunt, idMembre, nom, prenom, adresse, email, telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre INNER JOIN livre ON livre.id = e.idLivre WHERE e.id = ?;";
	private static final String SELECT_ALL = "SELECT e.id AS id, idMembre, nom, prenom, adresse, email, telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre INNER JOIN livre ON livre.id = e.idLivre ORDER BY dateRetour DESC;";
	private static final String SELECT_ALL_CURRENT = "SELECT e.id AS id, idMembre, nom, prenom, adresse, email, telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre INNER JOIN livre ON livre.id = e.idLivre WHERE dateRetour IS NULL;";
	private static final String SELECT_ALL_CURRENT_BY_MEMBER = "SELECT e.id AS id, idMembre, nom, prenom, adresse, email, telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre INNER JOIN livre ON livre.id = e.idLivre WHERE dateRetour IS NULL AND membre.id = ?;";
	private static final String SELECT_ALL_CURRENT_BY_BOOK = "SELECT e.id AS id, idMembre, nom, prenom, adresse, email, telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre INNER JOIN livre ON livre.id = e.idLivre WHERE dateRetour IS NULL AND livre.id = ?;";

	@Override
	public List<Emprunt> getList() throws DaoException{
		List<Emprunt> emprunts = new ArrayList<>();
		
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL);
			 ResultSet res = preparedStatement.executeQuery();
				){
			while(res.next()) {
				Livre livre = new Livre(res.getInt("idLivre"), res.getString("titre"), res.getString("auteur"), res.getString("isbn"));

				String abonnement = res.getString("abonnement").toLowerCase();
				Abonnement abo = Abonnement.aboFromString(abonnement);
				Membre membre = new Membre(res.getInt("idMembre"), res.getString("nom"), res.getString("prenom"), res.getString("adresse"), res.getString("email"), res.getString("telephone"), abo);
				Emprunt e = new Emprunt(res.getInt("id"), livre, membre, res.getDate("dateEmprunt").toLocalDate(), res.getDate("dateRetour").toLocalDate());
				emprunts.add(e);
			}
			System.out.println("GET: " + emprunts);
		} catch (SQLException e) {
			throw new DaoException("Probleme lors de la recuperation des emprunts", e);
		}
		return emprunts;
	}
	
	@Override
	public List<Emprunt> getListCurrent() throws DaoException{
		List<Emprunt> emprunts = new ArrayList<>();
		
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_CURRENT);
			 ResultSet res = preparedStatement.executeQuery();
				){
			while(res.next()) {
				Livre livre = new Livre(res.getInt("idLivre"), res.getString("titre"), res.getString("auteur"), res.getString("isbn"));

				String abonnement = res.getString("abonnement").toLowerCase();
				Abonnement abo = Abonnement.aboFromString(abonnement);
				Membre membre = new Membre(res.getInt("idMembre"), res.getString("nom"), res.getString("prenom"), res.getString("adresse"), res.getString("email"), res.getString("telephone"), abo);
				Emprunt e = new Emprunt(res.getInt("id"), livre, membre, res.getDate("dateEmprunt").toLocalDate(), res.getDate("dateRetour").toLocalDate());
				emprunts.add(e);
			}
			System.out.println("GET: " + emprunts);
		} catch (SQLException e) {
			throw new DaoException("Probleme lors de la recuperation des emprunts", e);
		}
		return emprunts;
	}

	public List<Emprunt> getListCurrentByMembre(int idMembre) throws DaoException{
		List<Emprunt> emprunts = new ArrayList<>();

		ResultSet res = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = ConnectionManager.getConnection();
			preparedStatement = connection.prepareStatement(SELECT_ALL_CURRENT_BY_MEMBER);
			preparedStatement.setInt(1, idMembre);
			res = preparedStatement.executeQuery();

			while(res.next()) {
				Livre livre = new Livre(res.getInt("idLivre"), res.getString("titre"), res.getString("auteur"), res.getString("isbn"));

				String abonnement = res.getString("abonnement").toLowerCase();
				Abonnement abo = Abonnement.aboFromString(abonnement);
				Membre membre = new Membre(res.getInt("idMembre"), res.getString("nom"), res.getString("prenom"), res.getString("adresse"), res.getString("email"), res.getString("telephone"), abo);
				Emprunt e = new Emprunt(res.getInt("id"), livre, membre, res.getDate("dateEmprunt").toLocalDate(), res.getDate("dateRetour").toLocalDate());
				emprunts.add(e);
			}
			System.out.println("GET CURRENT BY idMembre: " + emprunts);
		} catch (SQLException e) {
			throw new DaoException("Problème lors de la lecture des emprunts: ", e);
		} finally {
			try {
				preparedStatement.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return emprunts;
	}

	public List<Emprunt> getListCurrentByLivre(int idLivre) throws DaoException{
		List<Emprunt> emprunts = new ArrayList<>();

		ResultSet res = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = ConnectionManager.getConnection();
			preparedStatement = connection.prepareStatement(SELECT_ALL_CURRENT_BY_BOOK);
			preparedStatement.setInt(1, idLivre);
			res = preparedStatement.executeQuery();

			while(res.next()) {
				Livre livre = new Livre(res.getInt("idLivre"), res.getString("titre"), res.getString("auteur"), res.getString("isbn"));

				String abonnement = res.getString("abonnement").toLowerCase();
				Abonnement abo = Abonnement.aboFromString(abonnement);
				Membre membre = new Membre(res.getInt("idMembre"), res.getString("nom"), res.getString("prenom"), res.getString("adresse"), res.getString("email"), res.getString("telephone"), abo);
				Emprunt e = new Emprunt(res.getInt("id"), livre, membre, res.getDate("dateEmprunt").toLocalDate(), res.getDate("dateRetour").toLocalDate());
				emprunts.add(e);
			}
			System.out.println("GET CURRENT BY idLivre: " + emprunts);
		} catch (SQLException e) {
			throw new DaoException("Problème lors de la lecture des emprunts: ", e);
		} finally {
			try {
				preparedStatement.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return emprunts;
	}

	public Emprunt getById(int id) throws DaoException{
		ResultSet res = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		Emprunt emprunt = new Emprunt();
		try {
			connection = ConnectionManager.getConnection();
			preparedStatement = connection.prepareStatement(SELECT_BY_ID);
			preparedStatement.setInt(1, id);
			res = preparedStatement.executeQuery();
			if(res.next()) {
				Livre livre = new Livre(res.getInt("idLivre"), res.getString("titre"), res.getString("auteur"), res.getString("isbn"));

				String abonnement = res.getString("abonnement").toLowerCase();
				Abonnement abo = Abonnement.aboFromString(abonnement);
				Membre membre = new Membre(res.getInt("idMembre"), res.getString("nom"), res.getString("prenom"), res.getString("adresse"), res.getString("email"), res.getString("telephone"), abo);
				Emprunt emp = new Emprunt(res.getInt("id"), livre, membre, res.getDate("dateEmprunt").toLocalDate(), res.getDate("dateRetour").toLocalDate());
				System.out.println("GET: " + emp);
				emprunt = emp;
			}
			
		} catch (SQLException e) {
			throw new DaoException("Probleme lors de la recupération de l'emprunt id = " + id, e);
		} finally {
			try {
				res.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				preparedStatement.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return emprunt;
	}

	@Override
	public void create(int idMembre, int idLivre, Date dateEmprunt, Date dateRetour) throws DaoException{
		ResultSet res = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		int id = -1;
		try {
			connection = ConnectionManager.getConnection();
			preparedStatement = connection.prepareStatement(INSERT_ROW, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setInt(1, idMembre);
			preparedStatement.setInt(2, idLivre);
			preparedStatement.setDate(3, dateEmprunt);
			preparedStatement.setDate(4, dateRetour);
			preparedStatement.executeUpdate();
			res = preparedStatement.getGeneratedKeys();
			if(res.next()){
				id = res.getInt(1);				
			}

			System.out.println("CREATE: id= " + id + " " +idMembre +" "+ idLivre + " " + dateEmprunt + " " + dateEmprunt);
		} catch (SQLException e) {
			throw new DaoException("Problème lors de la création de l'emprunt: " + id, e);
		} finally {
			try {
				res.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				preparedStatement.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void update(Emprunt emprunt) throws DaoException{
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = ConnectionManager.getConnection();
			preparedStatement = connection.prepareStatement(UPDATE);
			preparedStatement.setInt(1, emprunt.getMembre().getId());
			preparedStatement.setInt(2, emprunt.getLivre().getId());
			preparedStatement.setObject(3, emprunt.getDateEmprunt());
			preparedStatement.setObject(4, emprunt.getDateRetour());
			preparedStatement.setInt(5, emprunt.getId());
			preparedStatement.executeUpdate();

			System.out.println("UPDATE: " + emprunt);
		} catch (SQLException e) {
			throw new DaoException("Problème lors de la mise à jour de l'emprunt: " + emprunt, e);
		} finally {
			try {
				preparedStatement.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public int count() throws DaoException{
		int ctr=0;
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(COUNT_ALL);
			 ResultSet res = preparedStatement.executeQuery();
				){
			ctr = res.getInt("COUNT");
		} catch (SQLException e) {
			throw new DaoException("Problème lors du dénombrement des emprunts", e);
		}
		return ctr;
	}

} 