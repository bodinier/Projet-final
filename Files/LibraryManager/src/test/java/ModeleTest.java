package test.java;

import java.time.LocalDate;

import com.excilys.librarymanager.model.*;

public class ModeleTest{
    public static void main(String args[]){
        Membre toto = new Membre(1, "tata", "toto", "ensta", "toto@ensta.fr", "08976565", Abonnement.VIP);
        Livre livre = new Livre(1, "le bossu de notre dame", "victor hugo", "786745");
        Emprunt emprunt = new Emprunt(1, livre, toto, LocalDate.parse("2020-03-10"), LocalDate.parse("2020-04-10"));
        System.out.println(toto.toString());
    }

}

