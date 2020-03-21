package com.excilys.librarymanager.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.excilys.librarymanager.dao.LivreDao;
import com.excilys.librarymanager.exception.DaoException;
import com.excilys.librarymanager.model.Livre;
import com.excilys.librarymanager.persistence.ConnectionManager;

//TODO : il faut rajouter des appels à instance dans chaque fonction pour être sur d'avoir le droit

public class LivreImpl implements LivreDao {
	private static LivreImpl instance;
	private LivreImpl() {}	//Constructeur en privé pour ne pas avoir plusieurs instances
	public static LivreImpl getInstance() {
		if(instance == null) {
			instance = new LivreImpl();
		}
		return instance;
	}
	private static final String INSERT_ROW = "INSERT INTO livre(titre, auteur, isbn) VALUES (?; ?; ?);";
	private static final String UPDATE = "UPDATE livre SET titre = ?, auteur = ?, isbn = ? WHERE id = ?;";
	private static final String COUNT_ALL = "SELECT COUNT(id) AS count FROM livre;";
	private static final String SELECT_BY_ID = "Select id, titre, auteur, isbn FROM livre WHERE id = ?;";
	private static final String SELECT_ALL = "SELECT id, titre, auteur, isbn FROM livre;";
	private static final String DELETE = "DELETE FROM livre WHERE id = ?;";


	public List<Livre> getList() throws DaoException {
		List<Livre> livres = new ArrayList<>();
		
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL);
			 ResultSet res = preparedStatement.executeQuery();
				){
			while(res.next()) {
				Livre livre = new Livre(res.getInt("idLivre"), res.getString("titre"), res.getString("auteur"), res.getString("isbn"));
				livres.add(livre);
			}
			System.out.println("GET: " + livres);
		} catch (SQLException e) {
			throw new DaoException("Probleme lors de la recuperation des livres", e);
		}
		return livres;
	}

	public Livre getById(int id) throws DaoException {
		ResultSet res = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		Livre livre = new Livre();
		try {
			connection = ConnectionManager.getConnection();
			preparedStatement = connection.prepareStatement(SELECT_BY_ID);
			preparedStatement.setInt(1, id);
			res = preparedStatement.executeQuery();
			if(res.next()) {
				Livre livre1 = new Livre(res.getInt("idLivre"), res.getString("titre"), res.getString("auteur"), res.getString("isbn"));
				System.out.println("GET: " + livre1);
				livre = livre1;
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
		return livre;
	}

	@Override
	public int create(String titre, String auteur, String isbn) throws DaoException {
		ResultSet res = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		int id = -1;
		try {
			connection = ConnectionManager.getConnection();
			preparedStatement = connection.prepareStatement(INSERT_ROW, java.sql.Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, titre);
			preparedStatement.setString(2, auteur);
			preparedStatement.setString(3, isbn);
			preparedStatement.executeUpdate();
			res = preparedStatement.getGeneratedKeys();
			if(res.next()){
				id = res.getInt(1);				
			}

			System.out.println("CREATE: id= " + id + " " +titre +" "+ auteur + " " + isbn);
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
		return id;
	}

	public void update(Livre livre) throws DaoException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = ConnectionManager.getConnection();
			preparedStatement = connection.prepareStatement(UPDATE);
			preparedStatement.setString(1, livre.getTitre());
			preparedStatement.setString(2, livre.getAuteur());
			preparedStatement.setString(3, livre.getIsbn());
			preparedStatement.setInt(4, livre.getId());
			preparedStatement.executeUpdate();

			System.out.println("UPDATE: " + livre);
		} catch (SQLException e) {
			throw new DaoException("Problème lors de la mise à jour du livre: " + livre, e);
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
