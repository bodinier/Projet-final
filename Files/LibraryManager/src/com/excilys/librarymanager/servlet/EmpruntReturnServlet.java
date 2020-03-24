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



/**
 * Servlet implementation class EmpruntReturnServlet
 */
@WebServlet("/EmpruntReturnServlet")
public class EmpruntReturnServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		IEmpruntService empruntService = EmpruntService.getInstance();
		List<Emprunt> emprunts = new ArrayList<Emprunt>();
		
		try {
			emprunts = empruntService.getListCurrent();
			
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/emprunt_return.jsp");
		request.setAttribute("emprunts", emprunts);

		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int ID = Integer.parseInt(request.getParameter("id"));
        
        IEmpruntService empruntservice = EmpruntService.getInstance();
        try {
            empruntservice.returnBook(ID);
        } catch (ServiceException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

        response.sendRedirect("/library/emprunt_list");
	}

}
