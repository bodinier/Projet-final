package com.excilys.librarymanager.model;

import java.time.LocalDate;

public class Emprunt {
    /**
     * --------------------------   Attributs d'un emprunt  --------------------------
     */
    private int id;
    private Livre livre;
    private Membre membre;
    private LocalDate dateEmprunt;
    private LocalDate dateRetour;

    /**
     * --------------------------   Constructeurs   --------------------------
     */

    /**
     * Constructeur d'instance Emprunt :
     * @param newId
     * @param newLivre
     * @param newMembre
     * @param newDateEmprunt
     * @param newDateRetour
     */
    public Emprunt(int newId, Livre newLivre, Membre newMembre, LocalDate newDateEmprunt, LocalDate newDateRetour){
        this.id = newId;
        this.livre = newLivre;
        this.membre = newMembre;
        this.dateEmprunt = newDateEmprunt;
        this.dateRetour = newDateRetour;
    }
    public Emprunt(Livre newLivre, Membre newMembre, LocalDate newDateEmprunt, LocalDate newDateRetour){
        this.livre = newLivre;
        this.membre = newMembre;
        this.dateEmprunt = newDateEmprunt;
        this.dateRetour = newDateRetour;
    }
    public Emprunt(){}
    /**
     * --------------------------   Getter  --------------------------
     */
    public int getId(){
        return this.id;
    }

    public Livre getLivre(){
        return this.livre;
    }

    public Membre getMembre(){
        return this.membre;
    }

    public LocalDate getDateEmprunt(){
        return this.dateEmprunt;
    }

    public LocalDate getDateRetour(){
        return this.dateRetour;
    }

    /**
     * --------------------------   Setter  --------------------------
     */
    public void setId(int newId){
        this.id = newId;
    }
    public void setLivre(Livre newLivre){
        this.livre = newLivre;
    }
    public void setMembre(Membre newMembre){
        this.membre = newMembre;
    }
    public void setDateEmprunt(LocalDate newDateEmprunt){
        this.dateEmprunt = newDateEmprunt;
    }
    public void setDateRetour(LocalDate newDateRetour){
        this.dateRetour = newDateRetour;
    }
    /**
     * --------------------------  Methode  --------------------------
     */
    @Override
    public String toString(){
        String str = "ID " + id + " : " + livre.getTitre() + " - " + livre.getAuteur() + " emprunté par : "
             + membre.getPrenom() + " " + membre.getNom()
                 + " du " + dateEmprunt + " au " + dateRetour + ".";
        return str;
    }
    
}