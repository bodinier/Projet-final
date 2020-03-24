package com.excilys.librarymanager.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.librarymanager.exception.ServiceException;
import com.excilys.librarymanager.service.ILivreService;
import com.excilys.librarymanager.service.impl.LivreService;

/**
 * Servlet implementation class LivreAddServlet
 */
@WebServlet("/LivreAddServlet")
public class LivreAddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String titre = request.getParameter("titre");
        String auteur = request.getParameter("auteur");
        String isbn = request.getParameter("isbn");
        ILivreService livreservice = LivreService.getInstance();
        int id = -1;
        try {
            id = livreservice.create(titre, auteur, isbn);
        } catch (ServiceException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
        
        String ID = String.valueOf(id) ;

        response.sendRedirect("/library/livre_details?id="+ID);
	}

}
