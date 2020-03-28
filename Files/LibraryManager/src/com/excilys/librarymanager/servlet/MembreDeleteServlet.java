package com.excilys.librarymanager.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.librarymanager.exception.ServiceException;
import com.excilys.librarymanager.model.Membre;
import com.excilys.librarymanager.service.IMembreService;
import com.excilys.librarymanager.service.impl.MembreService;

/**
 * Servlet implementation class MembreDeleteServlet
 */
@WebServlet("/MembreDeleteServlet")
public class MembreDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		IMembreService membreService = MembreService.getInstance();
		String id = request.getParameter("id");
		Membre membre = new Membre();
		try {
			membre = membreService.getById(Integer.parseInt(id));
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		request.setAttribute("membre", membre);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/membre_delete.jsp");
		dispatcher.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		IMembreService membreService = MembreService.getInstance();
		int idMembre = Integer.parseInt(request.getParameter("id"));
		try {
			membreService.delete(idMembre);
		} catch (ServiceException e) {
			e.printStackTrace();
			throw new ServletException();
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/membre_delete.jsp");
		dispatcher.forward(request, response);
	}

}
