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
import com.excilys.librarymanager.model.Abonnement;
import com.excilys.librarymanager.model.Emprunt;
import com.excilys.librarymanager.model.Membre;
import com.excilys.librarymanager.service.IEmpruntService;
import com.excilys.librarymanager.service.IMembreService;
import com.excilys.librarymanager.service.impl.EmpruntService;
import com.excilys.librarymanager.service.impl.MembreService;

/**
 * Servlet implementation class MembreDetailsServlet
 */
@WebServlet("/MembreDetailsServlet")
public class MembreDetailsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
      
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		IMembreService membreService = MembreService.getInstance();
		IEmpruntService empruntService = EmpruntService.getInstance();
		int id = Integer.parseInt(request.getParameter("id"));
		List<Emprunt> emprunts = new ArrayList<Emprunt>();
		Membre membre = new Membre();
		try {
			membre = membreService.getById(id);
			emprunts = empruntService.getListCurrentByMembre(id);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		request.setAttribute("emprunts", emprunts);
		request.setAttribute("membre", membre);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/membre_details.jsp");
		dispatcher.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		IMembreService membreService = MembreService.getInstance();
		int id = Integer.parseInt(request.getParameter("id"));
		String nom = request.getParameter("nom");
		String prenom = request.getParameter("prenom");
		String adresse = request.getParameter("adresse");
		String email = request.getParameter("email");
		String telephone = request.getParameter("telephone");
		String abonnement = request.getParameter("abonnement");
		Membre membre = new Membre();
		try {
			membre = membreService.getById(id);
			if (!nom.equals("")) {membre.setNom(nom);}
			if (!prenom.equals("")) {membre.setPrenom(prenom);}
			if (!adresse.equals("")) {membre.setAdresse(adresse);}
			if (!email.equals("")) {membre.setEmail(email);}
			if (!telephone.equals("")) {membre.setTelephone(telephone);}
			if (!abonnement.equals("")) {membre.setAbonnement(Abonnement.valueOf(abonnement));}
			membreService.update(membre);
		} catch (ServiceException e) {
			e.printStackTrace();
			throw new ServletException();
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/membre_details.jsp");
		dispatcher.forward(request, response);
	}

}
