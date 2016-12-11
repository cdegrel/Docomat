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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import launch.Docomat;
import models.Documents;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class WelcomeController implements Initializable {

    private String path;
    private Stage stage;
    private Docomat main;
    @FXML private ListView<String> listView;

    public void setupStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listView.setCellFactory(TextFieldListCell.forListView());
    }

    public void showOpenDoc() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisissez où ranger votre document");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        path = new java.io.File(String.valueOf(fileChooser.showOpenDialog(stage))).getPath();
        showDocomat();
    }

    public void showDocomat() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/docomat2.fxml"));
        Parent pageCreateTxt = loader.load();
        if(main != null ){
            main.getControlGroup().setLoader(loader);
            main.getControlGroup().setStage(stage);
            main.getControlGroup().setDocomatController();
            main.getControlGroup().getDocomatController().setMain(main);
        }
        stage.setScene(new Scene(pageCreateTxt));
        stage.setMaximized(true);
        stage.show();
    }

    public void setMain(Docomat main) {
        ObservableList<String> noms;
        this.main = main;
        noms = FXCollections.observableArrayList();
        for(Documents doc : main.getDocumentsList()) noms.add(doc.getNom()+"\t\tcréé le : "+doc.getDateModif());
        listView.setItems(noms);
    }

    public String getPath(){ return path; }
}
