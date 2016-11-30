package models;

public class Etiquettes {

    private int id_etiquette;
    private String nom_etiquette;

    public Etiquettes(int id_etiquette, String nom_etiquette) {
        this.id_etiquette = id_etiquette;
        this.nom_etiquette = nom_etiquette;
    }

    public Etiquettes(String nom_etiquette) {
        this.nom_etiquette = nom_etiquette;
    }

    public void setNom_etiquette(String nom_etiquette) {
        this.nom_etiquette = nom_etiquette;
    }

    public int getId_etiquette() {
        return id_etiquette;
    }

    public void setId_etiquette(int id_etiquette) {
        this.id_etiquette = id_etiquette;
    }

    public String getNom_etiquette() {
        return nom_etiquette;
    }

    public String toString(){
        return "id_etiquettes : "+id_etiquette+"\nnom_etiquette"+nom_etiquette;
    }
}
