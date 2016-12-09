package controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.Categories;
import views.Main2;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
        treeView.setEditable(true);
        treeView.setCellFactory(tree -> {
            TreeCell<String> cell = new TreeCell<String>() {
                @Override
                public void updateItem(String item,boolean empty) {
                    super.updateItem(item,empty);
                    if (empty) {
                        setText(null);
                    }
                    else {
                        setText(item);
                        setContextMenu(contextMenu(getTreeItem(),rootItem));
                    }
                }
            };
            return cell;
        });

        treeView.setRoot(rootItem);
    }

    public ContextMenu contextMenu(TreeItem<String> treeItem,TreeItem<String> root) {
        MenuItem createCat;
        String titleCat,contentCat;
        ContextMenu contextMenu = new ContextMenu();
        if (treeItem == root) {
            createCat = new MenuItem("Créer une catégorie");
            titleCat = "Création d'une nouvelle catégorie";
            contentCat = "Veuillez entrer le nom de la catégorie";
        }
        else {
            createCat = new MenuItem("Ajouter une sous-catégorie");
            titleCat = "Création d'une nouvelle sous-catégorie";
            contentCat = "Veuillez entrer le nom de la sous-catégorie";
        }
        MenuItem createTxt = new MenuItem("Créer un nouveau texte");
        MenuItem delCat = new MenuItem("Supprimer cette catégorie");
        contextMenu.getItems().addAll(createCat,createTxt,delCat);
        final String finalTitlecat = titleCat,finalContentcat=contentCat;
        createCat.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                TextInputDialog dialog = new TextInputDialog("Nouvelle catégorie");
                dialog.setTitle(finalTitlecat);
                dialog.setHeaderText(null);
                dialog.setContentText(contentCat);
                dialog.initModality(Modality.APPLICATION_MODAL);
                Optional<String> result = dialog.showAndWait();
                int catIdParent = 0;
                if (result.isPresent()) {
                    for (Categories cat : main.getCategoriesList()) {
                        if (cat.getLibelle_categorie().equals(treeItem.getValue())) {
                            catIdParent = cat.getId_categorie();
                        }
                    }
                    Categories categories = new Categories(result.get(),catIdParent);
                    main.getDaoGroup().getCategoriesDAO().insert(categories);
                    main.getCategoriesList().removeAll(main.getCategoriesList());
                    main.getCategoriesList().addAll(main.getDaoGroup().getCategoriesDAO().readAll());
                    treeItem.getChildren().add(new TreeItem<>(result.get()));
                }
            }
        });
        delCat.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Categories catDel = null;
                for (Categories cat : main.getCategoriesList()) {
                    if (cat.getLibelle_categorie().equals(treeItem.getValue())) {
                        catDel = cat;
                    }
                }
                if (catDel == null) return;
                main.getDaoGroup().getCategoriesDAO().delete(catDel.getId_categorie());
                main.getDaoGroup().getCategoriesDAO().deleteChildren(catDel.getId_categorie());
                main.getCategoriesList().remove(catDel);
                treeItem.getParent().getChildren().remove(treeItem);
            }
        });
        return contextMenu;
    }

    public void setMain(Main2 main) {
        this.main = main;
        if (main != null) for (Categories cat : main.getCategoriesList()) catList.add(cat);
        if (treeView != null)loadTree();

    }
}
