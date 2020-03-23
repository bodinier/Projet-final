package com.excilys.librarymanager.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.excilys.librarymanager.model.Abonnement;
import com.excilys.librarymanager.model.Emprunt;
import com.excilys.librarymanager.model.Livre;
import com.excilys.librarymanager.model.Membre;
import com.excilys.librarymanager.dao.IEmpruntDao;
import com.excilys.librarymanager.exception.DaoException;
import com.excilys.librarymanager.persistence.ConnectionManager;


public class EmpruntDao implements IEmpruntDao {

	private static EmpruntDao instance;
	private EmpruntDao() { }	
	public static IEmpruntDao getInstance() {
		if(instance == null) {
			instance = new EmpruntDao();
		}
		return instance;
	}

	private static final String CREATE_QUERY = "INSERT INTO emprunt(idMembre, idLivre, dateEmprunt, dateRetour)" + 
			"VALUES (?, ?, ?, ?);";
	private static final String SELECT_ALL_QUERY = "SELECT e.id AS id, idMembre, nom, prenom, adresse, "
			+ "email, telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour "
			+ "FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre INNER JOIN livre "
			+ "ON livre.id = e.idLivre ORDER BY dateRetour DESC;";
	private static final String SELECT_CURRENT_QUERY = "SELECT e.id AS id, idMembre, nom, prenom, adresse, email, " + 
			"telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour " + 
			"FROM emprunt AS e " + 
			"INNER JOIN membre ON membre.id = e.idMembre " + 
			"INNER JOIN livre ON livre.id = e.idLivre " + 
			"WHERE dateRetour IS NULL;";
	private static final String SELECT_CURRENT_BY_MEMBRE_QUERY = "SELECT e.id AS id, idMembre, nom, prenom, adresse, email, " + 
			"telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour " + 
			"FROM emprunt AS e " + 
			"INNER JOIN membre ON membre.id = e.idMembre " + 
			"INNER JOIN livre ON livre.id = e.idLivre " + 
			"WHERE dateRetour IS NULL AND membre.id = ?;";
	private static final String SELECT_CURRENT_BY_LIVRE_QUERY = "SELECT e.id AS id, idMembre, nom, prenom, adresse, email, " + 
			"telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour " + 
			"FROM emprunt AS e " + 
			"INNER JOIN membre ON membre.id = e.idMembre " + 
			"INNER JOIN livre ON livre.id = e.idLivre " + 
			"WHERE dateRetour IS NULL AND livre.id = ?;" + 
			"";
	private static final String SELECT_BY_ID_QUERY = "SELECT e.id AS idEmprunt, idMembre, nom, prenom, adresse, email, " + 
			"telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour " + 
			"FROM emprunt AS e " + 
			"INNER JOIN membre ON membre.id = e.idMembre " + 
			"INNER JOIN livre ON livre.id = e.idLivre " + 
			"WHERE e.id = ?;";
	private static final String UPDATE_QUERY = "UPDATE emprunt " + 
			"SET idMembre = ?, idLivre = ?, dateEmprunt = ?, dateRetour = ? " + 
			"WHERE id = ?;";
	private static final String COUNT_QUERY = "SELECT COUNT(id) AS count FROM emprunt;";

	
	public List<Emprunt> getList() throws DaoException{
		List<Emprunt> emprunts = new ArrayList<>();
		
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_QUERY);
			 ResultSet res = preparedStatement.executeQuery();
				){
			while(res.next()) {
				Livre l = new Livre(res.getInt("idLivre"), res.getString("titre"), res.getString("auteur"), res.getString("isbn"));
				Membre m = new Membre(res.getInt("idMembre"), res.getString("nom"), res.getString("prenom"), res.getString("adresse"), res.getString("email"), res.getString("telephone"), Abonnement.valueOf(res.getString("abonnement")));
				LocalDate dateEmprunt = res.getDate("dateEmprunt").toLocalDate();
				LocalDate dateRetour = null;
				if (res.getDate("dateRetour") != null) {
					Date fin = res.getDate("dateRetour");
					dateRetour = fin.toLocalDate();
				}
				Emprunt f = new Emprunt(res.getInt("id"), l, m, dateEmprunt, dateRetour);
				emprunts.add(f);
			}
			//System.out.println("GET: " + emprunts);
		} catch (SQLException e) {
			throw new DaoException("Probleme lors de la recuperation de la liste des emprunts", e);
		}
		return emprunts;
	}
	
	public List<Emprunt> getListCurrent() throws DaoException{
		List<Emprunt> emprunts = new ArrayList<>();
		
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CURRENT_QUERY);
			 ResultSet res = preparedStatement.executeQuery();
				){
			while(res.next()) {
				Livre l = new Livre(res.getInt("idLivre"), res.getString("titre"), res.getString("auteur"), res.getString("isbn"));
				Membre m = new Membre(res.getInt("idMembre"), res.getString("nom"), res.getString("prenom"), res.getString("adresse"), res.getString("email"), res.getString("telephone"), Abonnement.valueOf(res.getString("abonnement")));
				Date deb = res.getDate("dateEmprunt");
				LocalDate dateEmprunt = deb.toLocalDate();
				LocalDate dateRetour = null;
				if (res.getDate("dateRetour") != null) {
					Date fin = res.getDate("dateRetour");
					dateRetour = fin.toLocalDate();
				}
				Emprunt f = new Emprunt(res.getInt("id"), l, m, dateEmprunt, dateRetour);
				emprunts.add(f);
			}
			//System.out.println("GET: " + emprunts);
		} catch (SQLException e) {
			throw new DaoException("Probleme lors de la recuperation de la liste des emprunts", e);
		}
		return emprunts;
	}
	
	public List<Emprunt> getListCurrentByMembre(int idMembre) throws DaoException{
		List<Emprunt> emprunts = new ArrayList<>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet res = null;
		
		try {
			connection = ConnectionManager.getConnection();
			preparedStatement = connection.prepareStatement(SELECT_CURRENT_BY_MEMBRE_QUERY);
			preparedStatement.setInt(1, idMembre);
			res = preparedStatement.executeQuery();
				
			while(res.next()) {
				Livre l = new Livre(res.getInt("idLivre"), res.getString("titre"), res.getString("auteur"), res.getString("isbn"));
				Membre m = new Membre(res.getInt("idMembre"), res.getString("nom"), res.getString("prenom"), res.getString("adresse"), res.getString("email"), res.getString("telephone"), Abonnement.valueOf(res.getString("abonnement")));
				Date deb = res.getDate("dateEmprunt");
				LocalDate dateEmprunt = deb.toLocalDate();
				LocalDate dateRetour = null;
				if (res.getDate("dateRetour") != null) {
					Date fin = res.getDate("dateRetour");
					dateRetour = fin.toLocalDate();
				}
				Emprunt f = new Emprunt(res.getInt("id"), l, m, dateEmprunt, dateRetour);
				emprunts.add(f);
			}
			//System.out.println("GET: " + emprunts);
		} catch (SQLException e) {
			throw new DaoException("Probleme lors de la recuperation de la liste des emprunts", e);
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
		return emprunts;
	}
	
	public List<Emprunt> getListCurrentByLivre(int idLivre) throws DaoException{
		List<Emprunt> emprunts = new ArrayList<>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet res = null;
		
		try {
			connection = ConnectionManager.getConnection();
			preparedStatement = connection.prepareStatement(SELECT_CURRENT_BY_LIVRE_QUERY);
			preparedStatement.setInt(1, idLivre);
			res = preparedStatement.executeQuery();
				
			while(res.next()) {
				Livre l = new Livre(res.getInt("idLivre"), res.getString("titre"), res.getString("auteur"), res.getString("isbn"));
				Membre m = new Membre(res.getInt("idMembre"), res.getString("nom"), res.getString("prenom"), res.getString("adresse"), res.getString("email"), res.getString("telephone"), Abonnement.valueOf(res.getString("abonnement")));
				Date deb = res.getDate("dateEmprunt");
				LocalDate dateEmprunt = deb.toLocalDate();
				LocalDate dateRetour = null;
				if (res.getDate("dateRetour") != null) {
					Date fin = res.getDate("dateRetour");
					dateRetour = fin.toLocalDate();
				}
				Emprunt f = new Emprunt(res.getInt("id"), l, m, dateEmprunt, dateRetour);
				emprunts.add(f);
			}
			//System.out.println("GET: " + emprunts);
		} catch (SQLException e) {
			throw new DaoException("Probleme lors de la recuperation de la liste des emprunts", e);
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
		return emprunts;
	}
	
	public Emprunt getById(int id) throws DaoException{
		Emprunt emprunt = new Emprunt();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet res = null;
		
		try {
			connection = ConnectionManager.getConnection();
			preparedStatement = connection.prepareStatement(SELECT_BY_ID_QUERY);
			preparedStatement.setInt(1, id);
			res = preparedStatement.executeQuery();
				
			if(res.next()) {
				Livre l = new Livre(res.getInt("idLivre"), res.getString("titre"), res.getString("auteur"), res.getString("isbn"));
				Membre m = new Membre(res.getInt("idMembre"), res.getString("nom"), res.getString("prenom"), res.getString("adresse"), res.getString("email"), res.getString("telephone"), Abonnement.valueOf(res.getString("abonnement")));
				Date deb = res.getDate("dateEmprunt");
				LocalDate dateEmprunt = deb.toLocalDate();
				LocalDate dateRetour = null;
				if (res.getDate("dateRetour") != null) {
					Date fin = res.getDate("dateRetour");
					dateRetour = fin.toLocalDate();
				}
				emprunt.setId(id);
				emprunt.setMembre(m);
				emprunt.setLivre(l);
				emprunt.setDateEmprunt(dateEmprunt);
				emprunt.setDateRetour(dateRetour);
			}
			//System.out.println("GET: " + emprunt);
		} catch (SQLException e) {
			throw new DaoException("Probleme lors de la recuperation de la liste des emprunts", e);
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
	
	public void create(int idMembre, int idLivre, LocalDate dateEmprunt) throws DaoException{
		ResultSet res = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		int id = -1;
		try {
			connection = ConnectionManager.getConnection();
			preparedStatement = connection.prepareStatement(CREATE_QUERY, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setInt(1, idMembre);
			preparedStatement.setInt(2, idLivre);
			preparedStatement.setString(3, dateEmprunt.toString());
			int daysToAdd = 1; //a modifier en fonction de l'abonnement je pense
			LocalDate dateRetour = dateEmprunt.plusDays(daysToAdd);
			preparedStatement.setString(4, dateRetour.toString());
			preparedStatement.executeUpdate();
			res = preparedStatement.getGeneratedKeys();
			if(res.next()){
				id = res.getInt(1);				
			}
			
			//System.out.println("CREATE emprunt:\n\tIdMembre: " + idMembre + "\n\tIdLivre: " + idLivre + "\n\tDate Emprunt: " + dateEmprunt + "\n\tDate Retour: " + dateRetour);
		} catch (SQLException e) {
			throw new DaoException("Probleme lors de la creation de l'emprunt", e);
		} finally {
			/*try {
				res.close();
			} catch (Exception e) {
				e.printStackTrace();
			}*/
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
			preparedStatement = connection.prepareStatement(UPDATE_QUERY);
			preparedStatement.setInt(1, emprunt.getMembre().getId());
			preparedStatement.setInt(2, emprunt.getLivre().getId());
			preparedStatement.setString(3, emprunt.getDateEmprunt().toString());
			preparedStatement.setString(4, emprunt.getDateRetour().toString());
			preparedStatement.setInt(5, emprunt.getId());
			preparedStatement.executeUpdate();

			//System.out.println("UPDATE: " + emprunt);
		} catch (SQLException e) {
			throw new DaoException("Probleme lors de la mise a jour de l'emprunt: " + emprunt, e);
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
		int count = 0;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connection = ConnectionManager.getConnection();
			preparedStatement = connection.prepareStatement(COUNT_QUERY);
			ResultSet res = preparedStatement.executeQuery();
			if(res.next()) {
				count = res.getInt("count");
			}
			//System.out.println("GET: " + count);
		} catch (SQLException e) {
			throw new DaoException("Probleme lors du comptage des emprunts", e);
		}
		return count;
	}
}
