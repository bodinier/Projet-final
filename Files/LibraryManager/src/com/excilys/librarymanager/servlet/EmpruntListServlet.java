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
import com.excilys.librarymanager.exception.*;
import com.excilys.librarymanager.model.*;



import java.util.ArrayList;
import java.util.List;
/**
/**
 * Servlet implementation class EmpruntListServlet
 */
@WebServlet("/EmpruntListServlet")
public class EmpruntListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
      
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 
	        IEmpruntService empruntservice = EmpruntService.getInstance();
	        List<Emprunt> emprunts = new ArrayList<Emprunt>();
	        try {
	            if (request.getParameter("show").compareTo("all") == 0){
	                emprunts = empruntservice.getList();
	            }
	            else {
	                emprunts = empruntservice.getListCurrent();
	            }
	        } catch (ServiceException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
	        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/emprunt_list.jsp");
	        request.setAttribute("emprunts", emprunts);
	        dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
