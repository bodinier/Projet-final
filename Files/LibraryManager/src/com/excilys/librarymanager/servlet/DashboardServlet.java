package com.excilys.librarymanager.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;

import java.io.IOException;
import com.excilys.librarymanager.service.*;
import com.excilys.librarymanager.service.impl.EmpruntService;
import com.excilys.librarymanager.service.impl.LivreService;
import com.excilys.librarymanager.service.impl.MembreService;
import com.excilys.librarymanager.exception.*;
import com.excilys.librarymanager.model.*;

import java.util.ArrayList;
import java.util.List;




/**
 * Servlet implementation class DashboardServlet
 */
@WebServlet("/DashboardServlet")
public class DashboardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		IMembreService membreservice = MembreService.getInstance();
		ILivreService livreservice = LivreService.getInstance();
		IEmpruntService empruntservice = EmpruntService.getInstance();
		int nb_membres = -1;
		int nb_livres = -1;
		int nb_emprunts = -1;
		List<Emprunt> emprunts = new ArrayList<Emprunt>();
		try {
			nb_membres = membreservice.count();
			nb_livres = livreservice.count();
			nb_emprunts = empruntservice.count();
			emprunts = empruntservice.getListCurrent();
			
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		request.setAttribute("nb_membres", nb_membres);
		request.setAttribute("nb_livres", nb_livres);
		request.setAttribute("nb_emprunts", nb_emprunts);
		request.setAttribute("emprunts", emprunts);

		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/dashboard.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
