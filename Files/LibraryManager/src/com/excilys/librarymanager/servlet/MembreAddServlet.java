package com.excilys.librarymanager.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.librarymanager.exception.ServiceException;
import com.excilys.librarymanager.model.Membre;
import com.excilys.librarymanager.service.IMembreService;
import com.excilys.librarymanager.service.impl.MembreService;

@WebServlet("/MembreAddServlet")
public class MembreAddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		IMembreService membreService = MembreService.getInstance();
		List<Membre> membres = new ArrayList<Membre>();
		try {
			membres = membreService.getList();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		request.setAttribute("membres", membres);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/membre_add.jsp");
		dispatcher.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		IMembreService membreService = MembreService.getInstance();
		int idMembre ;
		String nom = request.getParameter("nom");
		String prenom = request.getParameter("prenom");
		String adresse = request.getParameter("adresse");
		String email = request.getParameter("email");
		String telephone = request.getParameter("telephone");
		try {
			idMembre = membreService.create(nom, prenom, adresse, email, telephone);
		} catch (ServiceException e) {
			e.printStackTrace();
			throw new ServletException();
		}
		request.setAttribute("id", idMembre);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/membre_add.jsp");
		dispatcher.forward(request, response);
	}

}
