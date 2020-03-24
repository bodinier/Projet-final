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

import com.excilys.librarymanager.exception.*;

import com.excilys.librarymanager.model.*;

import com.excilys.librarymanager.service.*;
import com.excilys.librarymanager.service.impl.EmpruntService;
import com.excilys.librarymanager.service.impl.MembreService;

/**
 * Servlet implementation class MembreDetailsServlet
 */
@WebServlet("/MembreDetailsServlet")
public class MembreDetailsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		IMembreService membreService = MembreService.getInstance();
        int idMembre = Integer.parseInt(request.getParameter("id"));
        Membre membre = null;
        IEmpruntService empruntService = EmpruntService.getInstance();
		List<Emprunt> emprunts = new ArrayList<Emprunt>();
        try {
            membre = membreService.getById(idMembre);
            emprunts = empruntService.getListCurrentByMembre(idMembre);
        } catch (ServiceException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/membre_details.jsp");
        request.setAttribute("emprunts", emprunts);
        request.setAttribute("membre", membre);
        dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String adresse = request.getParameter("adresse");
        String email = request.getParameter("email");
        String telephone = request.getParameter("telephone");
        Abonnement abonnement = Abonnement.valueOf(request.getParameter("abonnement"));
        IMembreService membreService = MembreService.getInstance();
        try {
        	Membre membre = membreService.getById(id);
            membre.setNom(nom);
            membre.setPrenom(prenom);
            membre.setAdresse(adresse);
            membre.setEmail(email);
            membre.setTelephone(telephone);
            membre.setAbonnement(abonnement);
            membreService.update(membre);
        } catch (ServiceException e) {
            throw new ServletException(e.getLocalizedMessage());
        }
        
        String ID = String.valueOf(id);
        
        response.sendRedirect("/library/membre_details?id="+ID);
	}

}
