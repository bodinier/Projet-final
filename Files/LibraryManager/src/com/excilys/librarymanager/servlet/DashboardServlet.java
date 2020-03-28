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
import com.excilys.librarymanager.service.IEmpruntService;
import com.excilys.librarymanager.service.ILivreService;
import com.excilys.librarymanager.service.IMembreService;
import com.excilys.librarymanager.service.impl.EmpruntService;
import com.excilys.librarymanager.service.impl.LivreService;
import com.excilys.librarymanager.service.impl.MembreService;

@WebServlet("/")
public class DashboardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		IMembreService membreService = MembreService.getInstance();
		ILivreService livreService = LivreService.getInstance();
		IEmpruntService empruntService = EmpruntService.getInstance();
		int nbMembres = -1, nbLivres = -1, nbEmprunts = -1;
		List<Emprunt> emprunts = new ArrayList<Emprunt>();
		try {
			nbEmprunts = empruntService.count();
			nbMembres = membreService.count();
			nbLivres = livreService.count();
			emprunts = empruntService.getListCurrent();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		request.setAttribute("nbMembres", nbMembres);
		request.setAttribute("nbLivres", nbLivres);
		request.setAttribute("nbEmprunts", nbEmprunts);
		request.setAttribute("listeEmprunts", emprunts);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/dashboard.jsp");
		dispatcher.forward(request, response);
	}
	

}
