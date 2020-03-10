package com.excilys.librarymanager.model;

public class Livre {
    /**
     * --------------------------   Attributs d'un livre    --------------------------
     */
    private int id;
    private String titre;
    private String auteur;
    private String isbn;

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
    public Livre(int newId, String newTitre, String newAuteur, String newIsbn){
        this.id = newId;
        this.titre = newTitre;
        this.auteur = newAuteur;
        this.isbn = newIsbn;
    }
    public Livre(){}
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
    public void setTitre(String newTitre){
        this.titre = newTitre;
    }
    public void setAuteur(String newAuteur){
        this.auteur = newAuteur;
    }
    public void setIsbn(String newIsbn){
        this.isbn = newIsbn;
    }
    /**
     * --------------------------  Methode  --------------------------
     */
    @Override
    public String toString(){
        String str = "ID " + id + " : " + titre + "-" + auteur + "(ISBN : " + isbn + ")";
        return str;
    }
}