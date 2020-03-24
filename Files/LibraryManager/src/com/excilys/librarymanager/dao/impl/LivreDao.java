package com.excilys.librarymanager.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.excilys.librarymanager.model.Livre;
import com.excilys.librarymanager.dao.ILivreDao;
import com.excilys.librarymanager.exception.DaoException;
import com.excilys.librarymanager.persistence.ConnectionManager;

public class LivreDao implements ILivreDao {

	private static LivreDao instance;
	private LivreDao() { }	
	public static ILivreDao getInstance() {
		if(instance == null) {
			instance = new LivreDao();
		}
		return instance;
	}
	
	private static final String CREATE_QUERY = "INSERT INTO livre(titre, auteur, isbn) VALUES (?, ?, ?);";
	private static final String SELECT_ALL_QUERY = "SELECT id, titre, auteur, isbn FROM livre;";
	private static final String SELECT_BY_ID_QUERY = "SELECT id, titre, auteur, isbn FROM livre WHERE id = ?;";
	private static final String UPDATE_QUERY = "UPDATE livre SET titre = ?, auteur = ?, isbn = ? WHERE id = ?;";
	private static final String DELETE_QUERY = "DELETE FROM livre WHERE id = ?;";
	private static final String COUNT_QUERY = "SELECT COUNT(id) AS count FROM livre;";
	@Override
	public List<Livre> getList() throws DaoException{
		List<Livre> livres = new ArrayList<>();
		
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_QUERY);
			 ResultSet res = preparedStatement.executeQuery();
				){
			while(res.next()) {
				Livre l = new Livre(res.getInt("id"), res.getString("titre"), res.getString("auteur"), res.getString("isbn"));
				livres.add(l);
			}
			//System.out.println("GET: " + livres);
		} catch (SQLException e) {
			throw new DaoException("Probleme lors de la recuperation de la liste des livres", e);
		}
		return livres;
	}
	@Override
	public Livre getById(int id) throws DaoException{
		Livre livre = new Livre();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet res = null;
		
		try {
			connection = ConnectionManager.getConnection();
			preparedStatement = connection.prepareStatement(SELECT_BY_ID_QUERY);
			preparedStatement.setInt(1, id);
			res = preparedStatement.executeQuery();
				
			if(res.next()) {
				livre.setId(id);
				livre.setTitre(res.getString("titre"));
				livre.setAuteur(res.getString("auteur"));
				livre.setIsbn(res.getString("isbn"));
			}
			//System.out.println("GET: " + livre);
		} catch (SQLException e) {
			throw new DaoException("Probleme lors de la recuperation du livre", e);
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
	public int create(String titre, String auteur, String isbn) throws DaoException{
		ResultSet res = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		int id = -1;
		try {
			connection = ConnectionManager.getConnection();
			preparedStatement = connection.prepareStatement(CREATE_QUERY, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, titre);
			preparedStatement.setString(2, auteur);
			preparedStatement.setString(3, isbn);
			preparedStatement.executeUpdate();
			//pour recuperer l'id
			res = preparedStatement.getGeneratedKeys();
			if(res.next()){
				id = res.getInt(1);				
			}
			
			System.out.println("CREATE Livre:\n\tTitre: " + titre + "\n\tAuteur: " + auteur + "\n\tISBN: " + isbn+ "\n SUCCESS");
		} catch (SQLException e) {
			throw new DaoException("Probleme lors de la creation du livre", e);
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
	@Override
	public void update(Livre livre) throws DaoException{
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = ConnectionManager.getConnection();
			preparedStatement = connection.prepareStatement(UPDATE_QUERY);
			preparedStatement.setString(1, livre.getTitre());
			preparedStatement.setString(2, livre.getAuteur());
			preparedStatement.setString(3, livre.getIsbn());
			preparedStatement.setInt(4, livre.getId());
			preparedStatement.executeUpdate();

			System.out.println("UPDATE: " + livre + "\n SUCCESS");
		} catch (SQLException e) {
			throw new DaoException("Probleme lors de la mise a jour du livre: " + livre, e);
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
	@Override
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
			throw new DaoException("Probleme lors de la suppression du livre: " /*+ livre*/, e);
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
	@Override
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
			throw new DaoException("Probleme lors du comptage des livres", e);
		}
		return count;
	}

}
