package views;

import controllers.ControlGroup;
import database.dao.DAOGroup;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.Categories;
import models.Documents;

public class Main2 extends Application {

    private ObservableList<Documents> documentsList;
    private ObservableList<Categories> categoriesList;
    private ControlGroup controlGroup;
    private DAOGroup daoGroup;

    @Override
    public void start(Stage primaryStage) throws Exception {
        documentsList = FXCollections.observableArrayList();
        categoriesList = FXCollections.observableArrayList();
        daoGroup = new DAOGroup();

        Documents doc1 = new Documents(1,"doc1");
        doc1.setDateModif("31/11/2016");
        daoGroup.getDocumentsDAO().insert(doc1);
        Documents doc2 = new Documents(2,"doc2");
        doc2.setDateModif("30/11/2016");
        daoGroup.getDocumentsDAO().insert(doc2);
        Documents doc3 = new Documents(3,"doc3");
        daoGroup.getDocumentsDAO().insert(doc3);
        documentsList.addAll(daoGroup.getDocumentsDAO().readDocsByDateModif());
        daoGroup.getCategoriesDAO().insert(new Categories("voitures",0));
        daoGroup.getCategoriesDAO().insert(new Categories("avions",0));
        daoGroup.getCategoriesDAO().insert(new Categories("peugeot",1));
        daoGroup.getCategoriesDAO().insert(new Categories("renault",1));
        daoGroup.getCategoriesDAO().insert(new Categories("airbus",2));
        categoriesList.addAll(daoGroup.getCategoriesDAO().readAll());


        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/welcome.fxml"));
        Parent root = loader.load();
        controlGroup = new ControlGroup(loader, primaryStage);
        controlGroup.setStageWelcomeController();
        controlGroup.getWelcomeController().setMain(this);
        primaryStage.setTitle("Welcome to Docomat");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, 800, 400));
        primaryStage.show();
    }

    public ObservableList<Documents> getDocumentsList(){ return documentsList; }

    public ObservableList<Categories> getCategoriesList() {
        return categoriesList;
    }

    public DAOGroup getDaoGroup() {
        return daoGroup;
    }

    public ControlGroup getControlGroup() {
        return controlGroup;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
