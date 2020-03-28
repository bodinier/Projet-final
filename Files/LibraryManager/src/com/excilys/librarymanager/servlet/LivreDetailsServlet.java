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
import com.excilys.librarymanager.model.Emprunt;
import com.excilys.librarymanager.model.Livre;
import com.excilys.librarymanager.model.Membre;
import com.excilys.librarymanager.service.IEmpruntService;
import com.excilys.librarymanager.service.ILivreService;
import com.excilys.librarymanager.service.IMembreService;
import com.excilys.librarymanager.service.impl.EmpruntService;
import com.excilys.librarymanager.service.impl.LivreService;
import com.excilys.librarymanager.service.impl.MembreService;

/**
 * Servlet implementation class LivreDetailsServlet
 */
@WebServlet("/LivreDetailsServlet")
public class LivreDetailsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		IMembreService membreService = MembreService.getInstance();
		ILivreService livreService = LivreService.getInstance();
		IEmpruntService empruntService = EmpruntService.getInstance();
		int id = Integer.parseInt(request.getParameter("id"));
		List<Emprunt> emprunts = new ArrayList<Emprunt>();
		Livre livre = new Livre();
		try {
			livre = livreService.getById(id);
			emprunts = empruntService.getListCurrentByLivre(id);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		request.setAttribute("emprunts", emprunts);
		request.setAttribute("livre", livre);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/livre_details.jsp");
		dispatcher.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ILivreService livreService = LivreService.getInstance();
		int id = Integer.parseInt(request.getParameter("id"));
		String titre = request.getParameter("titre");
		String auteur = request.getParameter("auteur");
		String isbn = request.getParameter("isbn");
		Livre livre = new Livre();
		try {
			livre = livreService.getById(id);
			if (!auteur.equals("")) {livre.setAuteur(auteur);}
			if (!isbn.equals("")) {livre.setIsbn(isbn);}
			if (!titre.equals("")) {livre.setTitre(titre);}
			livreService.update(livre);
		} catch (ServiceException e) {
			e.printStackTrace();
			throw new ServletException();
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/livre_details.jsp");
		dispatcher.forward(request, response);
	}

}
