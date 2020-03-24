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
import java.time.LocalDate;


import java.util.ArrayList;
import java.util.List;
/**
 * Servlet implementation class EmpruntAddServlet
 */
@WebServlet("/EmpruntAddServlet")
public class EmpruntAddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		IMembreService membreService = MembreService.getInstance();
		List<Membre> membres = new ArrayList<Membre>();
		ILivreService livreService = LivreService.getInstance();
		List<Livre> livres = new ArrayList<Livre>();
		try {
			membres = membreService.getListMembreEmpruntPossible();
			livres = livreService.getListDispo();
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/emprunt_add.jsp");
		request.setAttribute("membres", membres);
		request.setAttribute("livres", livres);
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int idLivre = Integer.parseInt(request.getParameter("idLivre"));
        int idMembre = Integer.parseInt(request.getParameter("idMembre"));
        IEmpruntService empruntservice = EmpruntService.getInstance();
        try {
            empruntservice.create(idMembre, idLivre, LocalDate.now());
        } catch (ServiceException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

        response.sendRedirect("/library/emprunt_list");
	} 

}
