package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.ContextMenuEvent;
import javafx.stage.Stage;
import models.Categories;
import views.Main2;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CreateTxtController implements Initializable {

    private Stage stage;
    private Main2 main=null;
    @FXML private TreeView<String> treeView;
    private List<Categories> catList;

    void setupStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        catList = new ArrayList<>();
    }



    private void loadTree() {
        TreeItem<String> rootItem = new TreeItem<>("Catégories");
        TreeItem<String> nodeCat = null;
        TreeItem<String> nodeSousCat = null;
        rootItem.setExpanded(true);
        for (int i = 0; i < catList.size(); i++) {
            if (catList.get(i).getId_cat_parent() == 0) {
                nodeCat = new TreeItem<>(catList.get(i).getLibelle_categorie());
                nodeCat.setExpanded(true);
                rootItem.getChildren().add(nodeCat);
            }
            else {
               nodeSousCat = new TreeItem<>(catList.get(i).getLibelle_categorie());
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
        ContextMenu contextMenu = contextMenu();
        if (nodeCat != null) {

        }
    }

    public ContextMenu contextMenu() {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem createCat = new MenuItem("Créer une catégorie");
        MenuItem createTxt = new MenuItem("Créer un nouveau texte");
        MenuItem delCat = new MenuItem("Supprimer cette catégorie");
        contextMenu.getItems().addAll(createCat,createTxt,delCat);
        return contextMenu;
    }

    public void setMain(Main2 main) {
        this.main = main;
        if (main != null) for (Categories cat : main.getCategoriesList()) catList.add(cat);
        if (treeView != null)loadTree();

    }
}
