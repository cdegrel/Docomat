package database.dao;

import database.DatabaseHandler;
import models.Categories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoriesDAO implements DAO<Categories> {

    private static final String SQL_INSERT = "INSERT INTO Categories VALUES(null, ?, ?)";
    private static final String SQL_DELETE = "DELETE FROM Categories WHERE id_categorie = ?";
    private static final String SQL_UPDATE = "UPDATE Categories SET libelle_categorie = ?, id_cat_parent = ? WHERE id_categorie = ?";
    private static final String SQL_READ = "SELECT * FROM Categories WHERE id_categorie = ?";
    private static final String SQL_READALL = "SELECT * FROM Categories";

    private Connection conn;

    public CategoriesDAO(){
        conn = DatabaseHandler.getInstance();
    }

    public void insert(Categories obj) {
        PreparedStatement ps = null;

        try{
            ps = conn.prepareStatement(SQL_INSERT);
            ps.setString(1, obj.getLibelle_categorie());
            ps.setInt(2, obj.getId_cat_parent());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }/*finally {
            try {
                if(ps != null) ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try{
                if(!conn.isClosed()) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }*/
    }

    public void delete(Object key) {
        PreparedStatement ps = null;

        try{
            ps = conn.prepareStatement(SQL_DELETE);
            ps.setInt(1, Integer.parseInt(key.toString()));
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }/*finally {
            try {
                if(ps != null) ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try{
                if(!conn.isClosed()) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }*/
    }

    public void update(Categories obj) {
        PreparedStatement ps = null;

        try{
            ps = conn.prepareStatement(SQL_UPDATE);
            ps.setString(1, obj.getLibelle_categorie());
            ps.setInt(2, obj.getId_cat_parent());
            ps.setInt(3, obj.getId_categorie());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }/*finally {
            try {
                if(ps != null) ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try{
                if(!conn.isClosed()) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }*/
    }

    public Categories read(Object key) {
        PreparedStatement ps = null;
        ResultSet res;
        Categories cat = null;

        try{
            ps = conn.prepareStatement(SQL_READ);
            ps.setInt(1, Integer.parseInt(key.toString()));

            res = ps.executeQuery();
            while(res.next()) cat = new Categories(res.getInt(1), res.getString(2), res.getInt(3));

            res.close();
            ps.close();
            conn.close();
            return cat;
        } catch (SQLException e) {
            e.printStackTrace();
        }/*finally {
            try {
                if(ps != null) ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try{
                if(!conn.isClosed()) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }*/

        return cat;
    }

    public List<Categories> readAll() {
        PreparedStatement ps = null;
        ResultSet res;
        List<Categories> cats = new ArrayList<Categories>();

        try{
            ps = conn.prepareStatement(SQL_READALL);
            res = ps.executeQuery();
            while(res.next()) cats.add(new Categories(res.getInt(1), res.getString(2), res.getInt(3)));
            ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }/*finally {
            try {
                if(ps != null) ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try{
                if(!conn.isClosed()) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }*/

        return cats;
    }
}
