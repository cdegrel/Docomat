package database.dao;

import models.Categories;
import models.Etiquettes;

public class DAOFactory {

    public static DAO<Categories> getCategoriesDAO(){
        return new CategoriesDAO();
    }

    public static DAO<Etiquettes> getEtiquettesDAO(){
        return new EtiquettesDAO();
    }
}
