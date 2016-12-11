package models;

/**
 * Created by Skygi on 26/11/2016.
 */
public class Textes {

    private int id_texte,id_categorie;
    private String nom,contenu;

    public Textes(int id_texte,String nom, int id_categorie,String contenu) {
        this.id_texte = id_texte;
        this.nom = nom;
        this.id_categorie = id_categorie;
        this.contenu = contenu;
    }

    public Textes(String nom,int id_categorie, String contenu) {
        this.id_categorie = id_categorie;
        this.nom = nom;
        this.contenu = contenu;
    }

    public int getId_texte() {
        return id_texte;
    }

    public void setId_texte(int id_texte) {
        this.id_texte = id_texte;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getId_categorie() {
        return id_categorie;
    }

    public void setId_categorie(int id_categorie) {
        this.id_categorie = id_categorie;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public String toString() {return "id_texte : "+id_texte+"\nid_catégorie : "+id_categorie+"\nnom : "+nom+"\ncontenu : "+contenu+"\n";}
}
