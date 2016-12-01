package database.dao;

/**
 * Created by ahecht on 01/12/2016.
 */
public class DAOGroup {

    private CategoriesDAO categoriesDAO;
    private DocumentsDAO documentsDAO;
    private EtiquettesDAO etiquettesDAO;
    private TextesDAO textesDAO;

    public DAOGroup() {
        categoriesDAO = DAOFactory.getCategoriesDAO();
        documentsDAO = DAOFactory.getDocumentsDAO();
        etiquettesDAO = DAOFactory.getEtiquettesDAO();
        textesDAO = DAOFactory.getTextesDAO();
    }

    public CategoriesDAO getCategoriesDAO() {
        return categoriesDAO;
    }

    public DocumentsDAO getDocumentsDAO() {
        return documentsDAO;
    }

    public EtiquettesDAO getEtiquettesDAO() {
        return etiquettesDAO;
    }

    public TextesDAO getTextesDAO() {
        return textesDAO;
    }
}
