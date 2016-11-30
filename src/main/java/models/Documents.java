package models;


import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ahecht on 28/11/2016.
 */
public class Documents {

    private int id_document;
    private String nom,sous_titre,dateCreation,dateModif;

    public Documents(int id_document, String nom) {
        this.id_document = id_document;
        this.nom = nom;
        dateCreation = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
    }

    public Documents() {
        dateCreation = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
    }

    public int getId_document() {
        return id_document;
    }

    public void setId_document(int id_document) {
        this.id_document = id_document;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getSous_titre() {
        return sous_titre;
    }

    public String getDateCreation() {
        return dateCreation;
    }

    public String getDateModif() {
        return dateModif;
    }

    public String toString() {
        return "id_document : "+id_document+"\nnom : "+nom+"\nsous-titre : "+sous_titre+"\ndate de Création : "+dateCreation+"\ndernière date de modification : "+dateModif;
    }
}
