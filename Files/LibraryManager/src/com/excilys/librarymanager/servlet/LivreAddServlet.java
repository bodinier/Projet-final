package com.excilys.librarymanager.servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.librarymanager.exception.ServiceException;
import com.excilys.librarymanager.model.Livre;
import com.excilys.librarymanager.model.Membre;
import com.excilys.librarymanager.service.IEmpruntService;
import com.excilys.librarymanager.service.ILivreService;
import com.excilys.librarymanager.service.IMembreService;
import com.excilys.librarymanager.service.impl.EmpruntService;
import com.excilys.librarymanager.service.impl.LivreService;
import com.excilys.librarymanager.service.impl.MembreService;

/**
 * Servlet implementation class LivreAddServlet
 */
@WebServlet("/LivreAddServlet")
public class LivreAddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		IMembreService membreService = MembreService.getInstance();
		ILivreService livreService = LivreService.getInstance();
		IEmpruntService empruntService = EmpruntService.getInstance();
		List<Membre> membres = new ArrayList<Membre>();
		List<Livre> livresDispo = new ArrayList<Livre>();
		try {
			livresDispo = livreService.getListDispo();
			membres = membreService.getListMembreEmpruntPossible();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		request.setAttribute("livresDispo", livresDispo);
		request.setAttribute("membres", membres);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/livre_add.jsp");
		dispatcher.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ILivreService livreService = LivreService.getInstance();
		int idLivre ;
		String titre = request.getParameter("titre");
		String auteur = request.getParameter("auteur");
		String isbn = request.getParameter("isbn");
		try {
			idLivre = livreService.create(titre, auteur, isbn);
		} catch (ServiceException e) {
			e.printStackTrace();
			throw new ServletException();
		}
		request.setAttribute("id", idLivre);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/livre_add.jsp");
		dispatcher.forward(request, response);
	}

}
