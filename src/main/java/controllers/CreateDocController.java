package controllers;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.Categories;
import models.Documents;
import views.Main2;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;


/**
 * Created by Cedric on 29/11/2016.
 */
public class CreateDocController implements Initializable {

    @FXML private TextField tf_name_doc;
    @FXML private TreeView<String> treeView;
    @FXML private Label lb_error;
    private List<Categories> catList;
    private Stage stage;
    private Main2 main=null;

    void setupStage(Stage stage) {
        this.stage = stage;
    }

    public void initialize(URL location, ResourceBundle resources) {
        catList = new ArrayList<>();
    }

    public void showCreateDoc() throws IOException {
        if (tf_name_doc.getText().isEmpty()) {
          lb_error.setText("Vous n'avez pas entré le nom du document à créer");
          lb_error.setVisible(true);
          return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/v_document/create_document.fxml"));
        Parent pageCreateDoc = loader.load();
        if(main != null){
            Documents documents = new Documents(tf_name_doc.getText());
            //main.getDaoGroup().getDocumentsDAO().insert(new Documents(tf_name_doc.getPromptText()));
            stage.setTitle("Docomat - " + documents.getNom());
            main.getControlGroup().setLoader(loader);
            main.getControlGroup().setStage(stage);
            main.getControlGroup().setStageCreateDocController();
            main.getControlGroup().getCreateDocController().setMain(main);
        }
        stage.setScene(new Scene(pageCreateDoc));
        stage.setMaximized(true);
        stage.show();
    }

    public void showWelcome() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/welcome.fxml"));
        Parent pageNameDoc = loader.load();
        if(main != null){
            main.getControlGroup().setLoader(loader);
            main.getControlGroup().setStage(stage);
            main.getControlGroup().setStageWelcomeController();
            main.getControlGroup().getWelcomeController().setMain(main);
        }
        stage.setScene(new Scene(pageNameDoc, 800, 500));
        stage.show();
    }

    public void cancel() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Quitter Docomat ");
        alert.setHeaderText("Vous êtes sur le point de quitter l'application Docomat");
        alert.setContentText("Voulez-vous quitter Docomat ?");
        Optional<ButtonType> reultat = alert.showAndWait();
        if (reultat.get() == ButtonType.OK) {
            System.exit(0);
        }
    }

    private void loadTree() {
        TreeItem<String> rootItem = new TreeItem<>("Catégories");
        TreeItem<String> nodeCat = null;
        rootItem.setExpanded(true);
        for (int i = 0; i < catList.size(); i++) {
            if (catList.get(i).getId_cat_parent() == 0) {
                nodeCat = new TreeItem<>(catList.get(i).getLibelle_categorie());
                nodeCat.setExpanded(true);
                rootItem.getChildren().add(nodeCat);
            }
            else {
                TreeItem<String> nodeSousCat = new TreeItem<>(catList.get(i).getLibelle_categorie());
                for (int j = 0; j < catList.size(); j++) {
                    if (catList.get(i).getId_cat_parent() == catList.get(j).getId_categorie()) {
                        for (int k = 0; k < rootItem.getChildren().size(); k++) {
                            if (rootItem.getChildren().get(k).getValue().equals(catList.get(j).getLibelle_categorie())) rootItem.getChildren().get(k).getChildren().add(nodeSousCat);
                        }
                    }
                }
            }
        }

        treeView.setRoot(rootItem);
    }

    public void setMain(Main2 main) {
        this.main = main;
        if (main != null) for (Categories cat : main.getCategoriesList()) catList.add(cat);
        if (treeView != null)loadTree();
    }
}
