package controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.GridPane;
import javafx.scene.web.HTMLEditor;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import launch.Docomat;
import models.*;
import org.controlsfx.control.Notifications;
import utils.AutoCompleteTextField;
import utils.File;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class DocomatController implements Initializable {
    @FXML
    private HTMLEditor html_doc, html_text;
    @FXML
    private TextArea ta_doc, ta_text;

    private Stage stage;
    private Docomat main=null;
    @FXML private TreeView<String> treeView;
    @FXML TabPane tabPane;
    @FXML Tab tab;
    @FXML Button saveBtn;
    @FXML ToolBar toolbar;
    private List<Categories> catList;
    private List<Textes> textesList;
    private List<Etiquettes> etiquettesList;
    private List<EtiquettesTextes> etiquettesTextesList;
    private AutoCompleteTextField textField = null;
    private Button search = null,clearTreeview = null;


    void setMain(Docomat main) {
        this.main = main;
        if (main != null){
            setCatList();
            setTextesList();
            setEtiquettesList();
            setEtiquettesTextesList();
        }
        if (treeView != null) loadTree();
        if (main.getControlGroup().getWelcomeController().getPath() != null) ta_doc.setText(File.readFile(main.getControlGroup().getWelcomeController().getPath()));
        setAutoComplete();
    }


    void setupStage(Stage stage) {
        this.stage = stage;
    }

    void setSaveBtn() {
        saveBtn.getScene().getAccelerators().put(new KeyCodeCombination(KeyCode.S, KeyCombination.SHORTCUT_DOWN), () -> saveBtn.fire());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        catList = new ArrayList<>();
        textesList = new ArrayList<>();
        etiquettesList = new ArrayList<>();
        etiquettesTextesList = new ArrayList<>();
        Platform.runLater(this::setSaveBtn);
    }

    public TreeView loadTree() {
        TreeItem<String> rootItem = new TreeItem<>("Catégories");
        TreeItem<String> nodeCat = null;
        TreeItem<String> nodeSousCat = null;
        TreeItem<String> nodeTexte = null;
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
            for (int j = 0; j < textesList.size(); j++) {
                if (textesList.get(j).getId_categorie() == catList.get(i).getId_categorie()) {
                    nodeTexte = new TreeItem<>(textesList.get(j).getNom());
                    if (nodeCat!=null && nodeCat.getValue().equals(catList.get(i).getLibelle_categorie())) nodeCat.getChildren().add(nodeTexte);
                    else if (nodeSousCat.getValue().equals(catList.get(i).getLibelle_categorie())) nodeSousCat.getChildren().add(nodeTexte);
                }
                for (int k = 0; k < etiquettesTextesList.size(); k++) {
                    if (etiquettesTextesList.get(k).getIdTexte() == textesList.get(j).getId_texte()) {
                        for (int l = 0; l < etiquettesList.size(); l++) {
                            if (etiquettesTextesList.get(k).getIdEtiquette() == etiquettesList.get(l).getId_etiquette()) {
                                TreeItem<String> nodeEtq = new TreeItem<>(etiquettesList.get(l).getNom_etiquette());
                                nodeEtq.setExpanded(true);
                                if (nodeTexte != null && nodeTexte.getValue().equals(textesList.get(j).getNom())) nodeTexte.getChildren().add(nodeEtq);
                            }
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
                        getTreeItem().setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/img/folder.png"))));
                        setContextMenu(contextMenu(getTreeItem(),rootItem));
                        for (Textes textes : textesList) {
                            if (getTreeItem().getValue().equals(textes.getNom())) {
                                getTreeItem().setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/img/etoile.png"))));
                                setOnMousePressed(event -> {
                                    if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                                        if (tabPane.isDisable()){
                                            tabPane.setDisable(false);
                                            tab.setText(textes.getNom());
                                            ta_text.setText(textes.getContenu());
                                            tabPane.getSelectionModel().select(tab);
                                        }
                                        else {
                                            Tab newtab = new Tab(textes.getNom());
                                            newtab.setId("tab");
                                            TextArea textArea = new TextArea();
                                            textArea.setText(textes.getContenu());
                                            newtab.setContent(textArea);
                                            /*HTMLEditor htmlEditor = new HTMLEditor();
                                            htmlEditor.setHtmlText(textes.getContenu());
                                            newtab.setContent(htmlEditor);*/
                                            tabPane.getTabs().add(newtab);
                                            tabPane.getSelectionModel().select(newtab);
                                        }
                                    }
                                });
                            }
                        }
                        for (Etiquettes etq : etiquettesList) {
                            if (etq.getNom_etiquette().equals(getTreeItem().getValue())) {
                                getTreeItem().setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/img/logo-welcome.png"))));
                            }
                        }
                        if ( tabPane.getSelectionModel().getSelectedItem() != null) tabPane.getSelectionModel().getSelectedItem().setOnCloseRequest(event -> saveTxt());
                    }
                }
            };
            return cell;
        });
        treeView.setRoot(rootItem);
        return treeView;
    }

    private ContextMenu contextMenu(TreeItem<String> treeItem, TreeItem<String> root) {
        MenuItem createCat;
        String titleCat,contentCat;
        ContextMenu contextMenu = new ContextMenu();
        MenuItem delCat = new MenuItem("Supprimer cette catégorie");
        MenuItem modifCat = new MenuItem("Renommer cette catégorie");
        MenuItem createTxt = new MenuItem("Créer un nouveau texte");
        MenuItem modifTxt = new MenuItem("Renommer ce texte");
        MenuItem delTxt = new MenuItem("Supprimer ce texte");
        MenuItem importTxt = new MenuItem("Importer ce texte dans le document");
        MenuItem createEtq = new MenuItem("Ajouter un mot-clé");
        MenuItem modifEtq = new MenuItem("Modifier un mot-clé");
        MenuItem delEtq = new MenuItem("Supprimer un mot-clé");
        if (treeItem == root) {
            createCat = new MenuItem("Créer une catégorie");
            titleCat = "Création d'une nouvelle catégorie";
            contentCat = "Veuillez entrer le nom de la catégorie";
            modifCat.setDisable(true);
            delCat.setDisable(true);
            createTxt.setDisable(true);
            modifTxt.setDisable(true);
            delTxt.setDisable(true);
            importTxt.setDisable(true);
            createEtq.setDisable(true);
            modifEtq.setDisable(true);
            delEtq.setDisable(true);
        }
        else {
            createCat = new MenuItem("Ajouter une sous-catégorie");
            titleCat = "Création d'une nouvelle sous-catégorie";
            contentCat = "Veuillez entrer le nom de la sous-catégorie";
            modifTxt.setDisable(true);
            delTxt.setDisable(true);
            importTxt.setDisable(true);
            createEtq.setDisable(true);
            modifEtq.setDisable(true);
            delEtq.setDisable(true);
        }
        for (Textes textes : textesList) {
            if (treeItem.getValue().equals(textes.getNom())) {
                createCat.setDisable(true);
                modifCat.setDisable(true);
                delCat.setDisable(true);
                createTxt.setDisable(true);
                modifTxt.setDisable(false);
                delTxt.setDisable(false);
                importTxt.setDisable(false);
                createEtq.setDisable(false);
                modifEtq.setDisable(true);
                delEtq.setDisable(true);
            }
        }
        for (Etiquettes etiquettes : etiquettesList) {
            if (treeItem.getValue().equals(etiquettes.getNom_etiquette())) {
                createCat.setDisable(true);
                modifCat.setDisable(true);
                delCat.setDisable(true);
                createTxt.setDisable(true);
                modifTxt.setDisable(true);
                delTxt.setDisable(true);
                importTxt.setDisable(true);
                createEtq.setDisable(true);
                modifEtq.setDisable(false);
                delEtq.setDisable(false);
            }
        }
        contextMenu.getItems().addAll(createCat,modifCat,delCat,createTxt,modifTxt,delTxt,importTxt,createEtq,modifEtq,delEtq);
        createCat.setOnAction(event -> {
            TextInputDialog dialog = createDialog("Nouvelle catégorie",titleCat,null,contentCat);
            Optional<String> result = dialog.showAndWait();
            int catIdParent = 0;
            if (result.isPresent()) {
                for (Categories cat : main.getCategoriesList()) {
                    while(result.get().equals(cat.getLibelle_categorie())) {
                        Alert alert = createAlert("Ce nom existe déjà ", Alert.AlertType.CONFIRMATION,null);
                        Optional<ButtonType> button = alert.showAndWait();
                        if (button.get() == ButtonType.CANCEL) return;
                        dialog = createDialog(result.get(),titleCat,null,contentCat);
                        result = dialog.showAndWait();
                        if (!result.isPresent()) return;
                    }
                    if (cat.getLibelle_categorie().equals(treeItem.getValue())) {
                        catIdParent = cat.getId_categorie();
                    }
                }
                Categories categories = new Categories(result.get(),catIdParent);
                main.getDaoGroup().getCategoriesDAO().insert(categories);
                main.getCategoriesList().clear();
                main.getCategoriesList().addAll(main.getDaoGroup().getCategoriesDAO().readAll());
                setCatList();
                setAutoComplete();
                treeItem.getChildren().add(new TreeItem<>(categories.getLibelle_categorie()));
                treeItem.setExpanded(true);
            }
        });
        modifCat.setOnAction(event -> {
            Categories categories = null;
            TextInputDialog dialog = createDialog(treeItem.getValue(),"Renommer cette catégorie",null,contentCat);
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                for (Categories cat : main.getCategoriesList()) {
                    while(result.get().equals(cat.getLibelle_categorie())) {
                        Alert alert = createAlert("Ce nom existe déjà ", Alert.AlertType.CONFIRMATION,null);
                        Optional<ButtonType> button = alert.showAndWait();
                        if (button.get() == ButtonType.CANCEL) return;
                        dialog = createDialog(result.get(),titleCat,null,contentCat);
                        result = dialog.showAndWait();
                        if (!result.isPresent()) return;
                    }
                    if (cat.getLibelle_categorie().equals(treeItem.getValue())) categories = cat;
                }
                if (categories == null) return;
                categories.setLibelle_categorie(result.get());
                main.getDaoGroup().getCategoriesDAO().update(categories);
                main.getCategoriesList().clear();
                main.getCategoriesList().addAll(main.getDaoGroup().getCategoriesDAO().readAll());
                setCatList();
                setAutoComplete();
                treeItem.setValue(categories.getLibelle_categorie());
            }
        });
        delCat.setOnAction(event -> {
            Categories catDel = null;
            for (Categories cat : main.getCategoriesList()) {
                if (cat.getLibelle_categorie().equals(treeItem.getValue())) {
                    catDel = cat;
                }
            }
            if (catDel == null) return;
            Alert alert = createAlert("Vous êtes sur le point de supprimer définitivement cette catégorie, " +
                    "voulez vous continuez ? ", Alert.AlertType.WARNING,null);
            Optional<ButtonType> buttonType = alert.showAndWait();
            if (buttonType.isPresent() && buttonType.get() == ButtonType.OK) {
                main.getDaoGroup().getCategoriesDAO().delete(catDel.getId_categorie());
                main.getDaoGroup().getCategoriesDAO().deleteChildren(catDel.getId_categorie());
                main.getCategoriesList().remove(catDel);
                setCatList();
                setAutoComplete();
                treeItem.getParent().getChildren().remove(treeItem);
            }
        });
        createTxt.setOnAction(event -> {
            TextInputDialog dialog = createDialog("Nouveau texte","Création d'un nouveau texte",null,"Veuiller entrer le nom du texte : ");
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                for (Textes textes : textesList){
                    while(result.get().equals(textes.getNom())) {
                        Alert alert = createAlert("Ce nom existe déjà ", Alert.AlertType.CONFIRMATION,null);
                        Optional<ButtonType> button = alert.showAndWait();
                        if (button.get() == ButtonType.CANCEL) return;
                        dialog = createDialog(result.get(),"Création d'un nouveau texte",null,"Veuiller entrer le nom du texte : ");
                        result = dialog.showAndWait();
                        if (!result.isPresent()) return;
                    }
                }
                for (Categories cat : main.getCategoriesList()) {
                    if (cat.getLibelle_categorie().equals(treeItem.getValue())) {
                        Textes textes = new Textes(result.get(),cat.getId_categorie(),"");
                        main.getDaoGroup().getTextesDAO().insert(textes);
                        main.getTextesList().clear();
                        main.getTextesList().addAll(main.getDaoGroup().getTextesDAO().readAll());
                        setTextesList();
                        treeItem.getChildren().add(new TreeItem<>(textes.getNom()));
                        treeItem.setExpanded(true);
                        if (tabPane.isDisable()){
                            tabPane.setDisable(false);
                            tab.setText(textes.getNom());
                            ta_text.setText(textes.getContenu());
                            tabPane.getSelectionModel().select(tab);
                        }
                        else {
                            Tab newtab = new Tab(textes.getNom());
                            newtab.setId("tab");
                            TextArea textArea = new TextArea();
                            textArea.setText(textes.getContenu());
                            newtab.setContent(textArea);
                            /*HTMLEditor htmlEditor = new HTMLEditor();
                            htmlEditor.setHtmlText(textes.getContenu());
                            newtab.setContent(htmlEditor);*/
                            tabPane.getTabs().add(newtab);
                            tabPane.getSelectionModel().select(newtab);
                        }
                    }
                }
            }

        });
        modifTxt.setOnAction(event -> {
            Textes texte = null;
            TextInputDialog dialog = createDialog(treeItem.getValue(),"Renommer ce Texte",null,"Veuiller entrer le nom du texte : ");
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                for (Textes textes : textesList){
                    while (result.get().equals(textes.getNom())) {
                        Alert alert = createAlert("Ce nom existe déjà ", Alert.AlertType.CONFIRMATION,null);
                        Optional<ButtonType> button = alert.showAndWait();
                        if (button.get() == ButtonType.CANCEL) return;
                        dialog = createDialog(result.get(),"Création d'un nouveau texte",null,"Veuiller entrer le nom du texte : ");
                        result = dialog.showAndWait();
                        if (!result.isPresent()) return;
                    }
                    if (textes.getNom().equals(treeItem.getValue())) texte = textes;
                }
                if (texte == null) return;
                texte.setNom(result.get());
                main.getDaoGroup().getTextesDAO().update(texte);
                main.getTextesList().clear();
                main.getTextesList().addAll(main.getDaoGroup().getTextesDAO().readAll());
                setTextesList();
                treeItem.setValue(texte.getNom());
                tab.setText(result.get());
            }
        });
        delTxt.setOnAction(event -> {
            Textes textes = null;
            for (Textes txt : main.getTextesList()) {
                if (txt.getNom().equals(treeItem.getValue())) {
                    textes = txt;
                }
            }
            if (textes == null) return;
            Alert alert = createAlert("Vous êtes sur le point de supprimer définitivement ce texte, " +
                    "voulez vous continuez ? ", Alert.AlertType.WARNING,null);
            Optional<ButtonType> buttonType = alert.showAndWait();
            if (buttonType.isPresent() && buttonType.get() == ButtonType.OK) {
                Tab tabRem = null;
                for (Tab tab : tabPane.getTabs()) {
                    if (tab.getText().equals(textes.getNom())) {
                        tabRem = tab;
                    }
                }
                if (tabRem != null){
                  tabPane.getTabs().remove(tabRem);
                }
                main.getDaoGroup().getTextesDAO().delete(textes.getId_texte());
                main.getTextesList().remove(textes);
                setTextesList();
                setAutoComplete();
                treeItem.getParent().getChildren().remove(treeItem);
            }
        });
        importTxt.setOnAction(event -> {
            for (Textes textes : textesList) {
                if (treeItem.getValue().equals(textes.getNom())) {
                    ta_doc.setText(ta_doc.getText()+textes.getContenu());
                    return;
                }
            }
        });
        createEtq.setOnAction((ActionEvent event) -> {
            TextInputDialog dialog= createDialogEtq(treeItem,"");
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                String[] motsCles = result.get().split(",");
                for (Etiquettes etiquettes : etiquettesList) {
                    if (motsCles.length == 1) {
                        while (result.get().equals(etiquettes.getNom_etiquette())){
                            Alert alert = createAlert("Ce nom existe déjà ", Alert.AlertType.WARNING, null);
                            Optional<ButtonType> button = alert.showAndWait();
                            if (button.get() == ButtonType.CANCEL) return;
                            dialog = createDialogEtq(treeItem, result.get());
                            result = dialog.showAndWait();
                            if (!result.isPresent()) return;
                        }
                    }
                    else {
                        for (String motCle : motsCles) {
                            while (motCle.equals(etiquettes.getNom_etiquette())) {
                                Alert alert = createAlert(motCle+" existe déjà", Alert.AlertType.WARNING,null);
                                Optional<ButtonType> buttonType = alert.showAndWait();
                                if (buttonType.get() == ButtonType.CANCEL) return;
                                dialog = createDialogEtq(treeItem,result.get());
                                result = dialog.showAndWait();
                                if (!result.isPresent()) return;
                            }
                        }
                    }
                }
                for (Textes textes : textesList) {
                    if (textes.getNom().equals(treeItem.getValue())) {
                        if (motsCles.length == 1) {
                            Etiquettes etiquette = new Etiquettes(result.get());
                            main.getDaoGroup().getEtiquettesDAO().insert(etiquette);
                            main.getEtiquettesList().clear();
                            main.getEtiquettesList().addAll(main.getDaoGroup().getEtiquettesDAO().readAll());
                            setEtiquettesList();
                            for (Etiquettes etiquettes : etiquettesList) {
                                if (etiquette.getNom_etiquette().equals(etiquette.getNom_etiquette())) {
                                    etiquette.setId_etiquette(etiquettes.getId_etiquette());
                                }
                            }
                            main.getDaoGroup().getEtiquettesDAO().insert_Etiquetes_Textes(etiquette,textes);
                            main.getEtiquettesTextesList().clear();
                            main.getEtiquettesTextesList().addAll(main.getDaoGroup().getEtiquettesDAO().readAllEtiquettesByTextes());
                            setEtiquettesTextesList();
                            treeItem.getChildren().add(new TreeItem<>(etiquette.getNom_etiquette()));
                            treeItem.setExpanded(true);
                        }
                        else {
                            for (String motCle : motsCles) {
                                Etiquettes etiquette = new Etiquettes(motCle);
                                main.getDaoGroup().getEtiquettesDAO().insert(etiquette);
                                main.getEtiquettesList().clear();
                                main.getEtiquettesList().addAll(main.getDaoGroup().getEtiquettesDAO().readAll());
                                setEtiquettesList();
                                for (Etiquettes etiquettes : etiquettesList) {
                                    if (etiquette.getNom_etiquette().equals(etiquette.getNom_etiquette())) {
                                        etiquette.setId_etiquette(etiquettes.getId_etiquette());
                                    }
                                }
                                main.getDaoGroup().getEtiquettesDAO().insert_Etiquetes_Textes(etiquette, textes);
                                main.getEtiquettesTextesList().clear();
                                main.getEtiquettesTextesList().addAll(main.getDaoGroup().getEtiquettesDAO().readAllEtiquettesByTextes());
                                setEtiquettesTextesList();
                                treeItem.getChildren().add(new TreeItem<>(motCle));
                                treeItem.setExpanded(true);
                            }
                        }
                    }
                }
                setAutoComplete();
            }

        });
        modifEtq.setOnAction(event -> {
            Etiquettes etiquette = null;
            TextInputDialog dialog = createDialogEtq(treeItem,treeItem.getValue());
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                for (Etiquettes etiquettes : etiquettesList) {
                    while (result.get().equals(etiquettes.getNom_etiquette())) {
                        Alert alert = createAlert("Ce nom existe déjà", Alert.AlertType.WARNING,null);
                        Optional<ButtonType> buttonType = alert.showAndWait();
                        if (buttonType.get() == ButtonType.CANCEL) return;
                        dialog = createDialogEtq(treeItem,result.get());
                        result = dialog.showAndWait();
                        if (!result.isPresent()) return;
                    }
                    if (etiquettes.getNom_etiquette().equals(treeItem.getValue())) etiquette = etiquettes;
                }
                if (etiquette == null) return;
                etiquette.setNom_etiquette(result.get());
                main.getDaoGroup().getEtiquettesDAO().update(etiquette);
                main.getEtiquettesList().clear();
                main.getEtiquettesList().addAll(main.getDaoGroup().getEtiquettesDAO().readAll());
                treeItem.setValue(etiquette.getNom_etiquette());
            }
        });
        delEtq.setOnAction(event -> {
            Etiquettes etiqDel = null;
            EtiquettesTextes etiqTxtDel = null;

            for (Etiquettes etq : etiquettesList) {
                if (etq.getNom_etiquette().equals(treeItem.getValue())) {
                    etiqDel = etq;
                }
            }
            if (etiqDel == null) return;
            for (EtiquettesTextes etqTxt : etiquettesTextesList) {
                if (etqTxt.getIdEtiquette() == etiqTxtDel.getIdEtiquette()) {
                    etiqTxtDel = etqTxt;
                }
            }
            Alert alert = createAlert("Vous êtes sur le point de supprimer définitivement cette étiquette,"+
                    "voulez-vous continuez ?", Alert.AlertType.WARNING,null);
            Optional<ButtonType> buttonType = alert.showAndWait();
            if (buttonType.isPresent() && buttonType.get() == ButtonType.OK) {
                main.getDaoGroup().getEtiquettesDAO().delete(etiqDel);
                main.getDaoGroup().getEtiquettesDAO().delete_Etiquetes_Textes(etiqTxtDel);
                main.getEtiquettesList().remove(etiqDel);
                main.getEtiquettesTextesList().remove(etiqTxtDel);
                setEtiquettesList();
                setEtiquettesTextesList();
            }
        });
        return contextMenu;
    }

    /**
     * Créer un nouveau document
     */
    public void newDoc(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation nouveau document");
        alert.setHeaderText("Vous êtes sur le point de supprimer le document en cours d'édition");
        alert.setContentText("Voulez-vous supprimer ce document ?");
        Optional<ButtonType> response = alert.showAndWait();
        if(response.get() == ButtonType.OK) ta_doc.setText("");
    }

    public void importDoc() {
        if(ta_doc.getText().length() != 0){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation nouveau document");
            alert.setHeaderText("Vous êtes sur le point de supprimer le document en cours d'édition");
            alert.setContentText("Voulez-vous supprimer ce document ?");
            Optional<ButtonType> response = alert.showAndWait();
            if(response.get() != ButtonType.OK) return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisissez un document à importer");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));

        String path = new java.io.File(String.valueOf(fileChooser.showOpenDialog(stage))).getPath();
        ta_doc.setText(File.readFile(path));
    }

    public void saveTxt() {
        TextArea textArea = (TextArea)  tabPane.getSelectionModel().getSelectedItem().getContent();
        String contenu = textArea.getText();
        /*HTMLEditor htmlEditor = (HTMLEditor) tabPane.getSelectionModel().getSelectedItem().getContent();
        String contenu = htmlEditor.getHtmlText();*/
        Textes txtModif = null;
        for (Textes textes : textesList) {
            if (tabPane.getSelectionModel().getSelectedItem().getText().equals(textes.getNom())) {
                txtModif = textes;
            }
        }
        if (txtModif == null) return;
        txtModif.setContenu(contenu);
        main.getDaoGroup().getTextesDAO().update(txtModif);
        main.getTextesList().clear();
        main.getTextesList().addAll(main.getDaoGroup().getTextesDAO().readAll());
        setTextesList();
        Notifications.create()
                .title("Sauvegarde du texte")
                .text("Votre texte à bien été sauvegardé")
                .owner(stage)
                .showConfirm();
    }

    public void exportDoc() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisissez où ranger votre document");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));

        java.io.File file = fileChooser.showSaveDialog(stage);
        if(file != null) {
            File.saveFile(ta_doc.getText(), file);
            for (Documents documents : main.getDocumentsList()) {
                if (documents.getNom().equals(file.getName())) {
                    main.getDaoGroup().getDocumentsDAO().update(new Documents(file.getName()));
                    return;
                }
            }
        }

    }

    private void setAutoComplete() {
        List<String> nomsEtq = new ArrayList<>();
        for (Etiquettes etiquettes : etiquettesList) {
            nomsEtq.add(etiquettes.getNom_etiquette());
        }
        if (toolbar != null) {
           if (textField != null) {
               textField.getEntries().addAll(nomsEtq);
           }
           else {
               search = new Button("rechercher");
               clearTreeview = new Button("Effacer recherche");
               textField = new AutoCompleteTextField();
               textField.setPromptText("Recherche par Mot-clé");
               textField.getEntries().addAll(nomsEtq);
               toolbar.getItems().add(textField);
               toolbar.getItems().add(search);
               toolbar.getItems().add(clearTreeview);
           }
        }
        if (clearTreeview != null) {
          clearTreeview.setOnAction(event -> {
              treeView.getRoot().getChildren().clear();
              loadTree();
          });
        }
        if (search != null) {
            search.setOnAction(event -> {
                treeView.getRoot().getChildren().clear();
                TreeItem<String> root = new TreeItem<>("Résultats");
                HashSet<String> txts = new HashSet<String>();
                Etiquettes etiquette = null;
                for (Etiquettes etiquettes : etiquettesList) {
                    if (etiquettes.getNom_etiquette().equals(textField.getText())){
                       etiquette = etiquettes;
                    }
                }
                if (etiquette == null) return;
                for (Textes textes : textesList) {
                    for (EtiquettesTextes etiquettesTextes : etiquettesTextesList) {
                        if (textes.getId_texte() == etiquettesTextes.getIdTexte() && etiquette.getId_etiquette() == etiquettesTextes.getIdEtiquette()) {
                            txts.add(textes.getNom());
                        }
                    }
                }
                for (String string : txts) {
                    TreeItem<String> treeItem = new TreeItem<>(string);
                    root.getChildren().add(treeItem);
                }
                root.setExpanded(true);
                treeView.setRoot(root);
            });
        }

    }

    private void setCatList() {
        catList.clear();
        for (Categories cat : main.getCategoriesList()) catList.add(cat);
    }


    private void setTextesList() {
        textesList.clear();
        for (Textes textes : main.getTextesList()) textesList.add(textes);
    }

    private void setEtiquettesList() {
        etiquettesList.clear();
        for (Etiquettes etiquettes : main.getEtiquettesList()) etiquettesList.add(etiquettes);
    }
    private void setEtiquettesTextesList() {
        etiquettesTextesList.clear();
        for (EtiquettesTextes etiquettesTextes : main.getEtiquettesTextesList()) etiquettesTextesList.add(etiquettesTextes);
    }

    private TextInputDialog createDialog(String defaultValue, String title, String header, String content){
        TextInputDialog dialog = new TextInputDialog(defaultValue);
        dialog.setTitle(title);
        dialog.setHeaderText(header);
        dialog.setContentText(content);
        dialog.initModality(Modality.APPLICATION_MODAL);
        return dialog;
    }

    private TextInputDialog createDialogEtq(TreeItem<String> treeItem,String content) {
        TextInputDialog dialog = new TextInputDialog(content);
        dialog.setTitle("Insérer des mots clés");
        dialog.setHeaderText("Ajouter de nouveaux mots clés a " + treeItem.getValue());
        dialog.setContentText("Liste des mots clés séparés par ,");
        return dialog;
    }

    private Alert createAlert(String label, Alert.AlertType alertType,String header) {
        GridPane grid = new GridPane();
        grid.add(new Label(label),1,1);
        Alert alert = new Alert(alertType);
        alert.setHeaderText(header);
        alert.getDialogPane().setContent(grid);
        return alert;

    }
}
