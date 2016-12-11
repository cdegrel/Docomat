package launch;

import controllers.ControlGroup;
import controllers.DocomatController;
import database.dao.DAOGroup;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import models.*;

import java.io.IOException;

public class Docomat extends Application {

    private ObservableList<Documents> documentsList;
    private ObservableList<Categories> categoriesList;
    private ObservableList<Textes> textesList;
    private ObservableList<Etiquettes> etiquettesList;
    private ObservableList<EtiquettesTextes> etiquettesTextesList;
    private ControlGroup controlGroup;
    private DAOGroup daoGroup;

    @Override
    public void start(Stage primaryStage) throws Exception {
        documentsList = FXCollections.observableArrayList();
        categoriesList = FXCollections.observableArrayList();
        textesList = FXCollections.observableArrayList();
        etiquettesList = FXCollections.observableArrayList();
        etiquettesTextesList = FXCollections.observableArrayList();
        daoGroup = new DAOGroup();

        documentsList.addAll(daoGroup.getDocumentsDAO().readDocsByDateModif());
        categoriesList.addAll(daoGroup.getCategoriesDAO().readAll());
        textesList.addAll(daoGroup.getTextesDAO().readAll());
        etiquettesList.addAll(daoGroup.getEtiquettesDAO().readAll());
        etiquettesTextesList.addAll(daoGroup.getEtiquettesDAO().readAllEtiquettesByTextes());

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/welcome.fxml"));
        Parent root = loader.load();
        controlGroup = new ControlGroup(loader, primaryStage);
        controlGroup.setStageWelcomeController();
        controlGroup.getWelcomeController().setMain(this);
        primaryStage.getIcons().add(new Image("/img/logo-app.png"));
        primaryStage.setTitle("Welcome to Docomat");
        primaryStage.setResizable(true);
        primaryStage.setScene(new Scene(root, 800, 400));
        primaryStage.show();
    }


    public ObservableList<Documents> getDocumentsList(){ return documentsList; }

    public ObservableList<Categories> getCategoriesList() {
        return categoriesList;
    }

    public ObservableList<Textes> getTextesList() {
        return textesList;
    }

    public ObservableList<Etiquettes> getEtiquettesList() {
        return etiquettesList;
    }

    public ObservableList<EtiquettesTextes> getEtiquettesTextesList() {
        return etiquettesTextesList;
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
