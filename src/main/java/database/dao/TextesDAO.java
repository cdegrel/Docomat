package database.dao;

import database.DatabaseHandler;
import models.Documents;
import models.Textes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class TextesDAO implements DAO<Textes> {

    private static final String SQL_INSERT = "INSERT INTO Textes VALUES(null,?,?,?)";
    private static final String SQL_DELETE = "DELETE FROM Textes WHERE id_texte = ?";
    private static final String SQL_UPDATE = "UPDATE Textes SET nom_texte = ?, id_categorie = ?,contenu = ? WHERE id_texte = ?";
    private static final String SQL_READ = "SELECT * FROM Textes WHERE id_texte = ?";
    private static final String SQL_READALL = "SELECT * FROM Textes";

    private static final String SQL_INSERT_Document_Textes = "INSERT INTO Document_Textes VALUES(?,?)";
    private static final String SQL_DELETE_Document_Textes = "DELETE FROM Document_Textes WHERE id_document = ? AND id_texte = ?";
    private static final String SQL_READ_Document_Textes = "SELECT T.id_texte,T.nomTexte,T.id_categorie,T.contenu FROM Textes T,Document_Textes DT WHERE Dt.id_document = ? AND DT.id_texte = ? AND DT.id_texte = T.id_texte";
    private static final String SQL_READALL_Textes_By_Document = "SELECT T.id_texte,T.nomTexte,T.id_categorie,T.contenu FROM Textes T,Document_Textes DT WHERE T.id_texte = DT.id_texte AND DT.id_document = ?";


    private Connection conn;

    public TextesDAO() {
        this.conn = DatabaseHandler.getInstance();
    }

    public void insert(Textes obj) {
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement(SQL_INSERT);
            ps.setString(1,obj.getNom());
            ps.setInt(2,obj.getId_categorie());
            ps.setString(3,obj.getContenu());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertDocumentTextes(Documents documents, Textes textes) {
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement(SQL_INSERT_Document_Textes);
            ps.setInt(1,documents.getId_document());
            ps.setInt(2,textes.getId_texte());
            ps.executeUpdate();
            ps.close();
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
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteDocumentTextes(Documents documents, Textes textes) {
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement(SQL_DELETE_Document_Textes);
            ps.setInt(1,documents.getId_document());
            ps.setInt(2,textes.getId_texte());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Textes obj) {
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement(SQL_UPDATE);
            ps.setString(1,obj.getNom());
            ps.setInt(2,obj.getId_categorie());
            ps.setString(3,obj.getContenu());
            ps.setInt(4,obj.getId_texte());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Textes read(Object key) {

        PreparedStatement ps = null;
        ResultSet res;
        Textes texte = null;

        try {
            ps = conn.prepareStatement(SQL_READ);
            ps.setInt(1,Integer.parseInt(key.toString()));

            res = ps.executeQuery();
            while (res.next()) texte = new Textes(res.getInt(1),res.getString(2),res.getInt(3),res.getString(4));

            res.close();
            ps.close();
            conn.close();
            return texte;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return texte;
    }

    public List<Textes> readAll() {
        PreparedStatement ps = null;
        ResultSet res;
        List<Textes> textes = new ArrayList<Textes>();

        try {
            ps = conn.prepareStatement(SQL_READALL);
            res = ps.executeQuery();
            while (res.next()) textes.add(new Textes(res.getInt(1),res.getString(2),res.getInt(3),res.getString(4)));

            ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return textes;
    }
}
