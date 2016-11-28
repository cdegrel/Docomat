package database.dao;

import database.DatabaseHandler;
import models.Etiquettes;

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

    public void delete(Object key) {
        PreparedStatement ps;

        try{
            ps = this.conn.prepareStatement(SQL_DELETE);
            ps.setInt(1, Integer.parseInt(key.toString()));
            ps.executeUpdate();
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
            return etq;
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
}
