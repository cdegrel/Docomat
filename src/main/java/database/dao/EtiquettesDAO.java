package database.dao;

import database.DatabaseHandler;
import models.Etiquettes;
import models.EtiquettesTextes;
import models.Textes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EtiquettesDAO implements DAO<Etiquettes> {

    private static final String SQL_INSERT = "INSERT INTO Etiquettes VALUES(null, ?)";
    private static final String SQL_DELETE = "DELETE FROM Etiquettes WHERE id_etiquette = ?";
    private static final String SQL_UPDATE = "UPDATE Etiquettes SET nom_etiquette = ? WHERE id_etiquette = ?";
    private static final String SQL_READ = "SELECT * FROM Etiquettes WHERE id_etiquette = ?";
    private static final String SQL_READALL = "SELECT * FROM Etiquettes";

    private static final String SQL_INSERT_Etiquettes_Textes = "INSERT INTO Etiquettes_Textes VALUES(null,?,?)";
    private static final String SQL_DELETE_Etiquettes_Textes = "DELETE FROM Etiquettes_Textes WHERE id_etiquette_texte = ?";
    private static final String SQL_READ_Etiquettes_Textes = "SELECT ET.id_etiquette,E.nom_Etiquette FROM Etiquettes E,Etiquetes_Textes ET WHERE  ET.id_texte = ? ";
    private static final String SQL_READALL_Etiquettes_By_Textes = "SELECT id_etiquette_texte,id_etiquette,id_texte FROM Etiquettes_Textes";


    private Connection conn;

    public EtiquettesDAO(){
        conn = DatabaseHandler.getInstance();
    }

    public void insert(Etiquettes obj) {
        PreparedStatement ps;

        try{
            ps = this.conn.prepareStatement(SQL_INSERT);
            ps.setString(1, obj.getNom_etiquette());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insert_Etiquetes_Textes(Etiquettes etiquettes, Textes textes) {
        PreparedStatement ps;

        try {
            ps = this.conn.prepareStatement(SQL_INSERT_Etiquettes_Textes);
            ps.setInt(1,etiquettes.getId_etiquette());
            ps.setInt(2,textes.getId_texte());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(Object key) {
        PreparedStatement ps;

        try{
            ps = this.conn.prepareStatement(SQL_DELETE);
            ps.setInt(1, Integer.parseInt(key.toString()));
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete_Etiquetes_Textes(Object key) {
        PreparedStatement ps;

        try {
            ps = conn.prepareStatement(SQL_DELETE_Etiquettes_Textes);
            ps.setInt(1,Integer.parseInt(key.toString()));
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Etiquettes obj) {
        PreparedStatement ps;

        try{
            ps = this.conn.prepareStatement(SQL_UPDATE);
            ps.setString(1, obj.getNom_etiquette());
            ps.setInt(2, obj.getId_etiquette());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Etiquettes read(Object key) {
        PreparedStatement ps;
        ResultSet res;
        Etiquettes etq = null;

        try{
            ps = this.conn.prepareStatement(SQL_READ);
            ps.setInt(1, Integer.parseInt(key.toString()));

            res = ps.executeQuery();
            while(res.next()) etq = new Etiquettes(res.getInt(1), res.getString(2));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return etq;
    }

    public Etiquettes read_Etiquettes_Textes(Object key) {
        PreparedStatement ps;
        ResultSet res;
        Etiquettes etq = null;

        try {
            ps = conn.prepareStatement(SQL_READ_Etiquettes_Textes);
            ps.setInt(1,Integer.parseInt(key.toString()));

            res = ps.executeQuery();
            while (res.next()) etq = new Etiquettes(res.getInt(1),res.getString(2));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return etq;
    }

    public List<Etiquettes> readAll() {
        PreparedStatement ps;
        ResultSet res;
        List<Etiquettes> etqs = new ArrayList<Etiquettes>();

        try{
            ps = this.conn.prepareStatement(SQL_READALL);

            res = ps.executeQuery();
            while(res.next()) etqs.add(new Etiquettes(res.getInt(1), res.getString(2)));

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return etqs;
    }

    public List<EtiquettesTextes> readAllEtiquettesByTextes() {
        PreparedStatement ps;
        ResultSet res;
        List<EtiquettesTextes> etiquettesTextesList = new ArrayList<EtiquettesTextes>();

        try {
            ps = conn.prepareStatement(SQL_READALL_Etiquettes_By_Textes);
            res = ps.executeQuery();
            while (res.next()) etiquettesTextesList.add(new EtiquettesTextes(res.getInt(1),res.getInt(2),res.getInt(3)));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return etiquettesTextesList;
    }
}
