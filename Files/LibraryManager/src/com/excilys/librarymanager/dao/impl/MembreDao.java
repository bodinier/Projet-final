package com.excilys.librarymanager.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.excilys.librarymanager.dao.IMembreDao;
import com.excilys.librarymanager.exception.DaoException;
import com.excilys.librarymanager.model.Abonnement;
import com.excilys.librarymanager.model.Membre;
import com.excilys.librarymanager.persistence.ConnectionManager;

public class MembreDao implements IMembreDao {

	private static MembreDao instance;
	private MembreDao() { }	
	public static IMembreDao getInstance() {
		if(instance == null) {
			instance = new MembreDao();
		}
		return instance;
	}
	
	private static final String CREATE_QUERY = "INSERT INTO membre(nom, prenom, adresse, email, telephone, abonnement)" +  
			"VALUES (?, ?, ?, ?, ?, ?);";
	private static final String SELECT_ALL_QUERY = "SELECT id, nom, prenom, adresse, email, telephone, abonnement " + 
			"FROM membre " + 
			"ORDER BY nom, prenom;";
	private static final String SELECT_BY_ID_QUERY = "SELECT id, nom, prenom, adresse, email, telephone, abonnement " + 
			"FROM membre " + 
			"WHERE id = ?;";
	private static final String UPDATE_QUERY = "UPDATE membre " + 
			"SET nom = ?, prenom = ?, adresse = ?, email = ?, telephone = ?, " + 
			"abonnement = ? " + 
			"WHERE id = ?;";
	private static final String DELETE_QUERY = "DELETE FROM membre WHERE id = ?;";
	private static final String COUNT_QUERY = "SELECT COUNT(id) AS count FROM membre;";
	
	public List<Membre> getList() throws DaoException{
		List<Membre> membres = new ArrayList<>();
		
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_QUERY);
			 ResultSet res = preparedStatement.executeQuery();
				){
			while(res.next()) {
				Membre m = new Membre(res.getInt("id"), res.getString("nom"), res.getString("prenom"), res.getString("adresse"), res.getString("email"), res.getString("telephone"), Abonnement.valueOf(res.getString("abonnement")));
				membres.add(m);
			}
		} catch (SQLException e) {
			throw new DaoException("Probleme lors de la recuperation de la liste des membres", e);
		}
		return membres;
	}
	
	public Membre getById(int id) throws DaoException{
		Membre membre = new Membre();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet res = null;
		
		try {
			connection = ConnectionManager.getConnection();
			preparedStatement = connection.prepareStatement(SELECT_BY_ID_QUERY);
			preparedStatement.setInt(1, id);
			res = preparedStatement.executeQuery();
				
			if(res.next()) {
				membre.setId(id);
				membre.setNom(res.getString("nom"));
				membre.setPrenom(res.getString("prenom"));
				membre.setAdresse(res.getString("adresse"));
				membre.setEmail(res.getString("email"));
				membre.setTelephone(res.getString("telephone"));
				membre.setAbonnement(Abonnement.valueOf(res.getString("abonnement")));
			}
		} catch (SQLException e) {
			throw new DaoException("Probleme lors de la recuperation du membre", e);
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
		return membre;
	}
	
	public int create(String nom, String prenom, String adresse, String email, String telephone) throws DaoException{
		ResultSet res = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		int id = -1;
		try {
			connection = ConnectionManager.getConnection();
			preparedStatement = connection.prepareStatement(CREATE_QUERY, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, nom);
			preparedStatement.setString(2, prenom);
			preparedStatement.setString(3, adresse);
			preparedStatement.setString(4, email);
			preparedStatement.setString(5, telephone);
			preparedStatement.setString(6, "BASIC");
			preparedStatement.executeUpdate();
			res = preparedStatement.getGeneratedKeys();
			if(res.next()){
				id = res.getInt(1);				
			}
			
			System.out.println("CREATE Membre:\n\n nom: " + nom + "\n prenom: " + prenom + "\n adresse: " + adresse+ "\n email: " + email+ "\n telephone: " + telephone+ "\n SUCCESS");
		} catch (SQLException e) {
			throw new DaoException("Probleme lors de la creation du membre", e);
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
		return id;
	}
	
	public void update(Membre membre) throws DaoException{
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = ConnectionManager.getConnection();
			preparedStatement = connection.prepareStatement(UPDATE_QUERY);
			preparedStatement.setString(1, membre.getNom());
			preparedStatement.setString(2, membre.getPrenom());
			preparedStatement.setString(3, membre.getAdresse());
			preparedStatement.setString(4, membre.getEmail());
			preparedStatement.setString(5, membre.getTelephone());
			preparedStatement.setString(6, membre.getAbonnement().toString().toUpperCase());
			preparedStatement.setInt(7, membre.getId());
			preparedStatement.executeUpdate();

			System.out.println("UPDATE: " + membre + "\n SUCCESS");
		} catch (SQLException e) {
			throw new DaoException("Probleme lors de la mise a jour du membre: " + membre, e);
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
	
	public void delete(int id) throws DaoException{
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = ConnectionManager.getConnection();
			preparedStatement = connection.prepareStatement(DELETE_QUERY);
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();
			preparedStatement.close();
			connection.close();
			System.out.println("DELETE: SUCCESSFUL");
		} catch (SQLException e) {
			throw new DaoException("Probleme lors de la suppression du livre: ", e);
		}  finally {
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
		} catch (SQLException e) {
			throw new DaoException("Probleme lors du comptage des membres", e);
		}
		return count;
	}

}
