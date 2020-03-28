package com.excilys.librarymanager.servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.librarymanager.exception.ServiceException;
import com.excilys.librarymanager.model.Emprunt;
import com.excilys.librarymanager.service.IEmpruntService;
import com.excilys.librarymanager.service.impl.EmpruntService;

/**
 * Servlet implementation class EmpruntReturnServlet
 */
@WebServlet("/EmpruntReturnServlet")
public class EmpruntReturnServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		IEmpruntService empruntService = EmpruntService.getInstance();
		List<Emprunt> emprunts = new ArrayList<Emprunt>();
		try {
			emprunts = empruntService.getListCurrent();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		request.setAttribute("emprunts", emprunts);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/emprunt_return.jsp");
		dispatcher.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int idLivre = Integer.parseInt(request.getParameter("id"));
		IEmpruntService empruntService = EmpruntService.getInstance();
		try {
			empruntService.returnBook(idLivre);
		} catch (ServiceException e) {
			e.printStackTrace();
			throw new ServletException();
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/emprunt_return.jsp");
		dispatcher.forward(request, response);
	}

}
