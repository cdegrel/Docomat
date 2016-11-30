package database.dao;

public class DAOFactory {

    public static CategoriesDAO getCategoriesDAO(){
        return new CategoriesDAO();
    }

    public static EtiquettesDAO getEtiquettesDAO(){
        return new EtiquettesDAO();
    }

    public static TextesDAO getTextesDAO(){ return new TextesDAO(); }

    public static DocumentsDAO getDocumentsDAO(){ return new DocumentsDAO(); }
}
