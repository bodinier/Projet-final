package com.excilys.librarymanager.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.librarymanager.exception.ServiceException;
import com.excilys.librarymanager.service.*;
import com.excilys.librarymanager.service.impl.LivreService;
import com.excilys.librarymanager.model.*;


/**
 * Servlet implementation class LivreDeleteServlet
 */
@WebServlet("/LivreDeleteServlet")
public class LivreDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
        ILivreService livreService = LivreService.getInstance();
        int idLivre = Integer.parseInt(request.getParameter("id"));
        Livre livre = null;
        try {
            livre = livreService.getById(idLivre);
        } catch (ServiceException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/livre_delete.jsp");
        request.setAttribute("livre", livre);
        dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
        ILivreService livreService = LivreService.getInstance();
        try {
            livreService.delete(id);
        } catch (ServiceException e) {
            throw new ServletException(e.getLocalizedMessage());
        }
        response.sendRedirect("/library/livre_list");
	}

}
