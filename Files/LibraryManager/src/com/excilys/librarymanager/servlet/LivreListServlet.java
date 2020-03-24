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
import com.excilys.librarymanager.service.impl.LivreService;

/**
 * Servlet implementation class LivreListServlet
 */
@WebServlet("/LivreListServlet")
public class LivreListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ILivreService livreservice = LivreService.getInstance();
		
		
		List<Livre> livres = new ArrayList<Livre>();
		try {
			
			livres = livreservice.getList();
			
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		request.setAttribute("livres", livres);

		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/livre_list.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
