package com.excilys.librarymanager.model;

public class Livre {
    /**
     * --------------------------   Attributs d'un livre    --------------------------
     */
    int id;
    String titre;
    String auteur;
    String isbn;

    /**
     * --------------------------   Constructeurs   --------------------------
     */
    /**
     * Constructeur d'instance Livre
     * @param newId
     * @param newTitre
     * @param newAuteur
     * @param newIsbn
     */
    Livre(int newId, String newTitre, String newAuteur, String newIsbn){
        this.id = newId;
        this.titre = newTitre;
        this.auteur = newAuteur;
        this.isbn = newIsbn;
    }

    /**
     * --------------------------  Getter  --------------------------
     */
    public int getId(){
        return this.id;
    }

    public String getTitre(){
        return this.titre;
    }

    public String getAuteur(){
        return this.auteur;
    }

    public String getIsbn(){
        return this.isbn;
    }
    
    /**
     * --------------------------  Setter  --------------------------
     */
    public void setId(int newId){
        this.id = newId;
    }
}