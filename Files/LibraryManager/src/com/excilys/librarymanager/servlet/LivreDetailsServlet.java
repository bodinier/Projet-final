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
import com.excilys.librarymanager.model.*;
import com.excilys.librarymanager.service.*;
import com.excilys.librarymanager.service.impl.EmpruntService;
import com.excilys.librarymanager.service.impl.LivreService;


/**
 * Servlet implementation class LivreDetailsServlet
 */
@WebServlet("/LivreDetailsServlet")
public class LivreDetailsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ILivreService livreService = LivreService.getInstance();
        int idLivre = Integer.parseInt(request.getParameter("id"));
        Livre livre = null;
        IEmpruntService empruntService = EmpruntService.getInstance();
		List<Emprunt> emprunts = new ArrayList<Emprunt>();
        try {
            livre = livreService.getById(idLivre);
            emprunts = empruntService.getListCurrentByLivre(idLivre);
        } catch (ServiceException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/livre_details.jsp");
        request.setAttribute("emprunts", emprunts);
        request.setAttribute("livre", livre);
        dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
        String titre = request.getParameter("titre");
        String auteur = request.getParameter("auteur");
        String isbn = request.getParameter("isbn");
        ILivreService livreService = LivreService.getInstance();
        try {
            Livre livre = livreService.getById(id);
            livre.setTitre(titre);
            livre.setAuteur(auteur);
            livre.setIsbn(isbn);
            livreService.update(livre);;
        } catch (ServiceException e) {
            throw new ServletException(e.getLocalizedMessage());
        }
        
        String ID = String.valueOf(id);
        
        response.sendRedirect("/library/livre_details?id="+ID);
	}

}
