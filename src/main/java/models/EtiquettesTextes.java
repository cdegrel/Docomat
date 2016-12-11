package models;

/**
 * Created by ahecht on 10/12/2016.
 */
public class EtiquettesTextes {

    private int idEtiquetteTexte,idEtiquette,idTexte;

    public EtiquettesTextes(int idEtiquetteTexte, int idEtiquette, int idTexte) {
        this.idEtiquetteTexte = idEtiquetteTexte;
        this.idEtiquette = idEtiquette;
        this.idTexte = idTexte;
    }

    public EtiquettesTextes(int idEtiquette, int idTexte) {
        this.idEtiquette = idEtiquette;
        this.idTexte = idTexte;
    }

    public int getIdEtiquette() {
        return idEtiquette;
    }

    public int getIdTexte() {
        return idTexte;
    }

    public String toString() {
        return "idEtiquette : "+idEtiquette+"\tidTexte : "+idTexte+"\n";
    }
}
