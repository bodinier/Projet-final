package com.excilys.librarymanager.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.librarymanager.exception.*;
import com.excilys.librarymanager.model.*;
import com.excilys.librarymanager.service.*;
import com.excilys.librarymanager.service.impl.MembreService;

/**
 * Servlet implementation class MembreDeleteServlet
 */
@WebServlet("/MembreDeleteServlet")
public class MembreDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		IMembreService membreService = MembreService.getInstance();
        int idMembre = Integer.parseInt(request.getParameter("id"));
        Membre membre = null;
        try {
            membre = membreService.getById(idMembre);
        } catch (ServiceException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/membre_delete.jsp");
        request.setAttribute("membre", membre);
        dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
        IMembreService membreService = MembreService.getInstance();
        try {
            membreService.delete(id);
        } catch (ServiceException e) {
            throw new ServletException(e.getLocalizedMessage());
        }
        response.sendRedirect("/library/membre_list");
	}

}
