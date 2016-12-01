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
import models.Documents;


public class Main extends Application {

    private ObservableList<Documents> documentsList;
    private ControlGroup controlGroup;
    private DAOGroup daoGroup;

    @Override
    public void start(Stage primaryStage) throws Exception{
        documentsList = FXCollections.observableArrayList();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main_view.fxml"));
        Parent root = loader.load();
        daoGroup = new DAOGroup();
        Documents doc1 = new Documents(1,"doc1");
        doc1.setDateModif("31/11/2016");
        daoGroup.getDocumentsDAO().insert(doc1);
        Documents doc2 = new Documents(2,"doc2");
        doc2.setDateModif("30/11/2016");
        daoGroup.getDocumentsDAO().insert(doc2);
        Documents doc3 = new Documents(3,"doc3");
        daoGroup.getDocumentsDAO().insert(doc3);
        System.out.println(daoGroup.getDocumentsDAO().readAll());
        documentsList.addAll(daoGroup.getDocumentsDAO().readDocsByDateModif());
        controlGroup = new ControlGroup(loader,primaryStage);
        controlGroup.setStageMainController();
        controlGroup.getMainController().setMain(this);
        primaryStage.setTitle("Docomat");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, 800, 500));
        primaryStage.show();
    }

    public ObservableList<Documents> getDocumentsList() {
        return documentsList;
    }

    public ControlGroup getControlGroup() {
        return controlGroup;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
