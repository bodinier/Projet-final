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
import com.excilys.librarymanager.model.Emprunt;
import com.excilys.librarymanager.model.Livre;
import com.excilys.librarymanager.model.Membre;
import com.excilys.librarymanager.service.IEmpruntService;
import com.excilys.librarymanager.service.ILivreService;
import com.excilys.librarymanager.service.IMembreService;
import com.excilys.librarymanager.service.impl.EmpruntService;
import com.excilys.librarymanager.service.impl.LivreService;
import com.excilys.librarymanager.service.impl.MembreService;


@WebServlet("/LivreDeleteServlet")
public class LivreDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		IMembreService membreService = MembreService.getInstance();
		ILivreService livreService = LivreService.getInstance();
		IEmpruntService empruntService = EmpruntService.getInstance();
		String id = request.getParameter("id");
		Livre livre = new Livre();
		List<Emprunt> emprunts = new ArrayList<Emprunt>();
		List<Livre> livresDispo = new ArrayList<Livre>();
		try {
			livre = livreService.getById(Integer.parseInt(id));
			emprunts = empruntService.getListCurrent();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		request.setAttribute("emprunts", emprunts);
		request.setAttribute("livre", livre);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/livre_delete.jsp");
		dispatcher.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ILivreService livreService = LivreService.getInstance();
		int idLivre = Integer.parseInt(request.getParameter("id"));
		try {
			livreService.delete(idLivre);
		} catch (ServiceException e) {
			e.printStackTrace();
			throw new ServletException();
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/livre_delete.jsp");
		dispatcher.forward(request, response);
	}

}
