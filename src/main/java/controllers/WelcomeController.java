package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.stage.Stage;
import models.Documents;
import views.Main2;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class WelcomeController implements Initializable {

    private Stage stage;
    private Main2 main;
    @FXML private ListView<String> listView;

    public void setupStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listView.setCellFactory(TextFieldListCell.forListView());
    }

    public void showNewTxt() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/v_texte/create_texte.fxml"));
        Parent pageCreateTxt = loader.load();
        if(main != null ){
            main.getControlGroup().setLoader(loader);
            main.getControlGroup().setStage(stage);
            main.getControlGroup().setStageCreateTxtController();
            main.getControlGroup().getCreateTxtController().setMain(main);
        }
        stage.setScene(new Scene(pageCreateTxt));
        stage.setMaximized(true);
        stage.show();
    }

    public void showNewDoc() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/v_document/new_document.fxml"));
        Parent pageCreateDoc = loader.load();
        if(main != null ){
            main.getControlGroup().setLoader(loader);
            main.getControlGroup().setStage(stage);
            main.getControlGroup().setStageCreateDocController();
            main.getControlGroup().getCreateDocController().setMain(main);
        }
        stage.setScene(new Scene(pageCreateDoc, 800, 500));
        stage.show();
    }

    public void showOpenDoc() {
        System.out.println("ouverture d'un document");
    }


    public void setMain(Main2 main) {
        ObservableList<String> noms;
        this.main = main;
        noms = FXCollections.observableArrayList();
        for(Documents doc : main.getDocumentsList()) noms.add(doc.getNom()+"\t\tcréé le : "+doc.getDateModif());
        listView.setItems(noms);
    }

}
