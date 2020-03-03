package com.excilys.librarymanager.model;


public class Emprunt {
    /**
     * --------------------------   Attributs d'un emprunt  --------------------------
     */
    int id;
    Livre livre;
    Membre membre;
    java.time.LocalDate dateEmprunt;
    java.time.LocalDate dateRetour;

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
    Emprunt(int newId, Livre newLivre, Membre newMembre, java.time.LocalDate newDateEmprunt, java.time.LocalDate newDateRetour){
        this.id = newId;
        this.livre = newLivre;
        this.membre = newMembre;
        this.dateEmprunt = newDateEmprunt;
        this.dateRetour = newDateRetour;
    }

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

    public java.time.LocalDate getDateEmprunt(){
        return this.dateEmprunt;
    }

    public java.time.LocalDate getDateRetour(){
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
    public void setDateEmprunt(java.time.LocalDate newDateEmprunt){
        this.dateEmprunt = newDateEmprunt;
    }
    public void setDateRetour(java.time.LocalDate newDateRetour){
        this.dateRetour = newDateRetour;
    }
}