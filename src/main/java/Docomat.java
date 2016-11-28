import database.DatabaseHandler;
import database.dao.DAO;
import database.dao.DAOFactory;
import models.Categories;

import java.util.List;

public class Docomat {

    public static void main(String[] args) {

        System.out.println("Bienvenue sur Docomat !");
        DatabaseHandler.getInstance();

        DAO<Categories> cat = DAOFactory.getCategoriesDAO();
        cat.insert(new Categories("Test", 0));
        cat.insert(new Categories("Voiture", 0));

        List<Categories> list = cat.readAll();
        for (Categories cats : list){
            System.out.println(cats.toString());
        }

        cat.update(new Categories(1, "Télévision", 0));
        list = cat.readAll();

        for (Categories cats : list){
            System.out.println(cats.toString());
        }
    }
}
