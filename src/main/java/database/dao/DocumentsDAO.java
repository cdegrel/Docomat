package database.dao;

import database.DatabaseHandler;
import models.Documents;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ahecht on 28/11/2016.
 */

public class DocumentsDAO implements DAO<Documents> {

    private static final String SQL_INSERT = "INSERT INTO Documents VALUES(null,?,?,?,?)";
    private static final String SQL_DELETE = "DELETE FROM Documents WHERE id_Document = ?";
    private static final String SQL_UPDATE = "UPDATE Documents SET nom_document = ?,sous_titre = ?,date_modif = ? WHERE id_Document = ?";
    private static final String SQL_READ = "SELECT * FROM Documents WHERE id_Document = ?";
    private static final String SQL_READALL = "SELECT * FROM Documents";

    private Connection conn;

    public DocumentsDAO() {
        this.conn = DatabaseHandler.getInstance();
    }

    public void insert(Documents obj) {
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement(SQL_INSERT);
            ps.setString(1,obj.getNom());
            ps.setString(2,obj.getSous_titre());
            ps.setString(3,obj.getDateCreation());
            ps.setString(4,obj.getDateModif());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(Object key) {
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement(SQL_DELETE);
            ps.setInt(1,Integer.parseInt(key.toString()));
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Documents obj) {
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement(SQL_UPDATE);
            ps.setString(1,obj.getNom());
            ps.setString(2,obj.getSous_titre());
            ps.setString(3,obj.getDateModif());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Documents read(Object key) {
        PreparedStatement ps = null;
        ResultSet res;
        Documents documents = null;

        try {
            ps = conn.prepareStatement(SQL_READ);
            ps.setInt(1,Integer.parseInt(key.toString()));

            res = ps.executeQuery();
            while (res.next()) documents = new Documents(res.getInt(1),res.getString(2));

            res.close();
            ps.close();
            conn.close();
            return documents;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return documents;
    }

    public List<Documents> readAll() {
        PreparedStatement ps = null;
        ResultSet res;
        List<Documents> documentsList = new ArrayList<Documents>();

        try {
            ps = conn.prepareStatement(SQL_READALL);
            res = ps.executeQuery();
            while (res.next()) documentsList.add(new Documents(res.getInt(1),res.getString(2)));

            ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return documentsList;
    }
}
