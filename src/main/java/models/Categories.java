package models;

public class Categories {

    private int id_categorie;
    private String libelle_categorie;
    private int id_cat_parent;

    public Categories(int id_categorie, String libelle_categorie, int id_cat_parent) {
        this.id_categorie = id_categorie;
        this.libelle_categorie = libelle_categorie;
        this.id_cat_parent = id_cat_parent;
    }

    public Categories(String libelle_categorie, int id_cat_parent) {
        this.libelle_categorie = libelle_categorie;
        this.id_cat_parent = id_cat_parent;
    }

    public int getId_categorie() {
        return id_categorie;
    }

    public void setId_categorie(int id_categorie) {
        this.id_categorie = id_categorie;
    }

    public String getLibelle_categorie() {
        return libelle_categorie;
    }

    public void setLibelle_categorie(String libelle_categorie) {
        this.libelle_categorie = libelle_categorie;
    }

    public int getId_cat_parent() {
        return id_cat_parent;
    }

    public void setId_cat_parent(int id_cat_parent) {
        this.id_cat_parent = id_cat_parent;
    }

    public String toString(){
        return "Catégorie n°"+id_categorie+":\n\tlibelle: "+libelle_categorie+"\n\tcategorie parent: "+id_cat_parent;
    }
}
