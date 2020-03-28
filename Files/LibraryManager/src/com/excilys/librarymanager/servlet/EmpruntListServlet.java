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
import com.excilys.librarymanager.model.Emprunt;
import com.excilys.librarymanager.model.Livre;
import com.excilys.librarymanager.model.Membre;
import com.excilys.librarymanager.service.IEmpruntService;
import com.excilys.librarymanager.service.ILivreService;
import com.excilys.librarymanager.service.IMembreService;
import com.excilys.librarymanager.service.impl.EmpruntService;
import com.excilys.librarymanager.service.impl.LivreService;
import com.excilys.librarymanager.service.impl.MembreService;

@WebServlet("/EmpruntListServlet")
public class EmpruntListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String show = request.getParameter("show");
		IEmpruntService empruntService = EmpruntService.getInstance();
		List<Emprunt> emprunts = new ArrayList<Emprunt>();
		try {
			if (show != null) {
				if (show.equals("all")) {
				emprunts = empruntService.getList();}
			}
			else {
				emprunts = empruntService.getListCurrent();
			}
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		request.setAttribute("emprunts", emprunts);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/emprunt_list.jsp");
		dispatcher.forward(request, response);
	}

}
