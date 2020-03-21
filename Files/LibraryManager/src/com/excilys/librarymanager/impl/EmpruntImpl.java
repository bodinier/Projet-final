package com.excilys.librarymanager.impl;

import java.time.LocalDate;
import java.util.List;
import com.excilys.librarymanager.dao.EmpruntDao;
import com.excilys.librarymanager.exception.DaoException;
import com.excilys.librarymanager.model.Emprunt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EmpruntImpl implements EmpruntDao {
	/**
	 * -------------------------- Attributs -------------------------- 
	 */
	private static EmpruntImpl instance;
	private EmpruntImpl() {}	
	public static EmpruntImpl getInstance() {
		if(instance == null) {
			instance = new EmpruntImpl();
		}
		return instance;
	}
	
	private static final String CREATE_QUERY = "INSERT INTO Film (titre, realisateur) VALUES (?, ?);";
	private static final String SELECT_ONE_QUERY = "SELECT * FROM Film WHERE id=?;";
	private static final String SELECT_ALL_QUERY = "SELECT * FROM Film;";
	private static final String UPDATE_QUERY = "UPDATE Film SET titre=?, realisateur=? WHERE id=?;";
	private static final String DELETE_QUERY = "DELETE FROM Film WHERE id=?;";

	@Override
	public List<Emprunt> getList() throws DaoException {
		List<Emprunt> emprunts = new ArrayList<Emprunt>();
		try (Connection connection = EstablishConnection.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_QUERY);
			 ResultSet res = preparedStatement.executeQuery();
				){
			while(res.next()) {
				Emprunt emprunt = new Emprunt(res.getInt("id"), res.getString("titre"), res.getString("realisateur"));
				emprunts.add(emprunt);
			}
			System.out.println("GET: " + emprunts);
		} catch (SQLException e) {
			throw new DaoException("Problème lors de la récupération de la liste des films", e);
		}
		return emprunts;
	}

	public List<Emprunt> getListCurrent() throws DaoException {

	}

	public List<Emprunt> getListCurrentByMembre(int idMembre) throws DaoException {

	}

	public List<Emprunt> getListCurrentByLivre(int idLivre) throws DaoException {

	}

	public Emprunt getById(int id) throws DaoException {

	}

	public void create(int idMembre, int idLivre, LocalDate dateEmprunt) throws DaoException {

	}

	public void update(Emprunt emprunt) throws DaoException {

	}

	public int count() throws DaoException {

	}
}
