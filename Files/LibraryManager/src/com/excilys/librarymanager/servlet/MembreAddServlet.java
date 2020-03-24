package com.excilys.librarymanager.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.librarymanager.exception.ServiceException;

import com.excilys.librarymanager.service.*;
import com.excilys.librarymanager.service.impl.MembreService;

/**
 * Servlet implementation class MembreAddServlet
 */
@WebServlet("/MembreAddServlet")
public class MembreAddServlet extends HttpServlet {
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
		String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String adresse = request.getParameter("adresse");
        String email = request.getParameter("email");
        String telephone = request.getParameter("telephone");
        IMembreService membreService = MembreService.getInstance();
        int id = -1;
        try {
            id = membreService.create(nom, prenom, adresse, email, telephone);
        } catch (ServiceException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
        
        String ID = String.valueOf(id) ;

        response.sendRedirect("/library/membre_details?id="+ID);
	}

}
