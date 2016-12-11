package models;


import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ahecht on 28/11/2016.
 */
public class Documents {

    private IntegerProperty id_document;
    private StringProperty nom,dateCreation,dateModif;

    public Documents(String nom){
        this.nom = new SimpleStringProperty(nom);
    }

    public Documents(int id_document, String nom) {
        this.id_document = new SimpleIntegerProperty(id_document);
        this.nom = new SimpleStringProperty(nom);
        dateCreation = new SimpleStringProperty(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
        dateModif = new SimpleStringProperty("");
    }

    public Documents(int id_document, String nom,String dateCreation, String dateModif) {
        this.id_document = new SimpleIntegerProperty(id_document);
        this.nom = new SimpleStringProperty(nom);
        this.dateCreation = new SimpleStringProperty(dateCreation);
        this.dateModif = new SimpleStringProperty(dateModif);
    }

    public Documents() {
        dateCreation = new SimpleStringProperty(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
        id_document = new SimpleIntegerProperty(0);
        nom = new SimpleStringProperty("");
        dateCreation = new SimpleStringProperty(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
        dateModif = new SimpleStringProperty("");
    }

    public int getId_document() {
        return id_document.get();
    }

    public void setId_document(int id_document) {
        this.id_document.set(id_document);
    }

    public String getNom() {
        return nom.get();
    }

    public StringProperty getNomController() {return nom;}
    public void setNom(String nom) {
        this.nom.set(nom);
    }

    public String getDateCreation() {
        return dateCreation.get();
    }
    public void setDateCreation(String dateCreation) {
        this.dateCreation.set(dateCreation);
    }

    public String getDateModif() {
        return dateModif.get();
    }
    public void setDateModif(String dateModif) {
        this.dateModif.set(dateModif);
    }

    public String toString() {
        return "id_document : "+id_document.get()+"\nnom : "+nom.get()+"\ndate de Création : "+dateCreation.get()+"\ndernière date de modification : "+dateModif.get();
    }
}
