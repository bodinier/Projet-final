package com.excilys.librarymanager.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.excilys.librarymanager.dao.MembreDao;
import com.excilys.librarymanager.exception.DaoException;
import com.excilys.librarymanager.model.Abonnement;
import com.excilys.librarymanager.model.Membre;
import com.excilys.librarymanager.persistence.ConnectionManager;

public class MembreImpl implements MembreDao {
	private static MembreImpl instance;
	private MembreImpl() {}	//Constructeur en privé pour ne pas avoir plusieurs instances
	public static MembreImpl getInstance() {
		if(instance == null) {
			instance = new MembreImpl();
		}
		return instance;
	}
	private static final String INSERT_ROW = "INSERT INTO membre(nom, prenom, adresse, email, telephone, abonnement) VALUES (?, ?, ?, ?, ?, ?);";
	private static final String UPDATE = "UPDATE membre SET nom = ?, prenom = ?, adresse =?, email = ?, telephone = ?, abonnement = ? WHERE id = ?;";
	private static final String COUNT_ALL = "SELECT COUNT(id) AS count FROM membre;";
	private static final String SELECT_BY_ID = "Select id, nom, prenom, adresse, email, telephone, abonnement FROM membre WHERE id = ?;";
	private static final String SELECT_ALL = "SELECT id, nom, prenom, adresse, email, telephone, abonnement FROM membre ORDER BY nom, prenom;";
	private static final String DELETE = "DELETE FROM membre WHERE id = ?;";

	public List<Membre> getList() throws DaoException {
		List<Membre> membres = new ArrayList<>();
		
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL);
			 ResultSet res = preparedStatement.executeQuery();
				){
			while(res.next()) {
				String abonnement = res.getString("abonnement").toLowerCase();
				Abonnement abo=null;
				switch (abonnement){
					case "basic" :
						abo = Abonnement.BASIC;
						break;
					case "premium" :
						abo = Abonnement.PREMIUM;
						break;
					case "vip" :
						abo = Abonnement.VIP;
						break;
				}
				Membre membre = new Membre(res.getInt("idMembre"), res.getString("nom"), res.getString("prenom"), res.getString("adresse"), res.getString("email"), res.getString("telephone"), abo);
				membres.add(membre);
			}
			System.out.println("GET: " + membres);
		} catch (SQLException e) {
			throw new DaoException("Probleme lors de la recuperation des emprunts", e);
		}
		return membres;
	}

	public Membre getById(int id) throws DaoException {
		ResultSet res = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		Membre membre = new Membre();
		try {
			connection = ConnectionManager.getConnection();
			preparedStatement = connection.prepareStatement(SELECT_BY_ID);
			preparedStatement.setInt(1, id);
			res = preparedStatement.executeQuery();
			if(res.next()) {
				String abonnement = res.getString("abonnement").toLowerCase();
				Abonnement abo = Abonnement.aboFromString(abonnement);
				Membre membre1 = new Membre(res.getInt("id"), res.getString("nom"), res.getString("prenom"), res.getString("adresse"), res.getString("email"), res.getString("telephone"), abo);
				System.out.println("GET: " + membre1);
				membre = membre1;
			}
		} catch (SQLException e) {
			throw new DaoException("Probleme lors de la recupération du livre id = " + id, e);
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

	public int create(String nom, String prenom, String adresse, String email, String telephone) throws DaoException {
		ResultSet res = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		int id = -1;
		try {
			connection = ConnectionManager.getConnection();
			preparedStatement = connection.prepareStatement(INSERT_ROW, java.sql.Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, nom.toUpperCase());
			preparedStatement.setString(2, prenom);
			preparedStatement.setString(3, adresse);
			preparedStatement.setString(4, email);
			preparedStatement.setString(5, telephone);
			preparedStatement.setString(6, "basic");
			preparedStatement.executeUpdate();
			res = preparedStatement.getGeneratedKeys();
			if(res.next()){
				id = res.getInt(1);				
			}

			System.out.println("CREATE: id= " + nom + " " +prenom + " " +adresse + " " +email + " " +telephone+ " " +"basic");
		} catch (SQLException e) {
			throw new DaoException("Problème lors de la création du membre: " + nom + " " + prenom, e);
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

	public void update(Membre membre) throws DaoException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = ConnectionManager.getConnection();
			preparedStatement = connection.prepareStatement(UPDATE);
			preparedStatement.setString(1, membre.getNom());
			preparedStatement.setString(2, membre.getPrenom());
			preparedStatement.setString(3, membre.getAdresse());
			preparedStatement.setString(4, membre.getEmail());
			preparedStatement.setString(5, membre.getTelephone());
			preparedStatement.setString(6, membre.getAbonnement().toString());
			preparedStatement.setInt(7, membre.getId());
			preparedStatement.executeUpdate();

			System.out.println("UPDATE: " + membre);
		} catch (SQLException e) {
			throw new DaoException("Problème lors de la mise à jour du livre: " + membre, e);
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

	public void delete(int id) throws DaoException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = ConnectionManager.getConnection();
			preparedStatement = connection.prepareStatement(DELETE);
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();

			System.out.println("DELETE: " + id);
		} catch (SQLException e) {
			throw new DaoException("Problème lors de la suppression du livre: " + id, e);
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

	public int count() throws DaoException {
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
