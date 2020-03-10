package com.excilys.librarymanager.model;

public class Membre {
    /**
     * --------------------------   Attributs d'un membre   --------------------------
     */
    private int id;
    private String nom;
    private String prenom;
    private String adresse;
    private String email;
    private String telephone;
    private Abonnement abonnement;

    /**
     * --------------------------   Constructeur    --------------------------
     */
    public Membre(int id, String nom, String prenom, String adresse, String email, String telephone, Abonnement abonnement){
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.email = email;
        this.telephone = telephone;
        this.abonnement = abonnement;
    }
    public Membre(){}
    /**
     * --------------------------  Getter  --------------------------
     */
    public int getId(){
        return this.id;
    }
    public String getNom(){
        return this.nom;
    }
    public String getPrenom(){
        return this.prenom;
    }
    public String getAdresse(){
        return this.adresse;
    }
    public String getEmail(){
        return this.email;
    }
    public String getTelephone(){
        return this.telephone;
    }
    public Abonnement getAbonnement(){
        return this.abonnement;
    }
    /**
     * --------------------------   Setter  --------------------------
     */
    public void setId(int id1){
        this.id = id1;
    }
    public void setNom(String nom1){
        this.nom = nom1;
    }
    public void setPrenom(String prenom1){
        this.prenom = prenom1;
    }
    public void setAdresse(String adresse1){
        this.adresse = adresse1;
    }
    public void setEmail(String email1){
        this.email = email1;
    }
    public void setTelephone(String telephone1){
        this.telephone = telephone1;
    }
    public void setAbonnement(Abonnement abonnement1){
        this.abonnement = abonnement1;
    }
    /**
     * --------------------------  Methode  --------------------------
     */
    @Override
    public String toString(){
        String str = "ID " + id + " : " + prenom + "-" + nom + "Adresse : " + adresse + " "
                        + "email " + email + " telephone : " + telephone + " Abonnement = " + abonnement;
        return str;
    }
}