package controllers;

import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

/**
 * Created by ahecht on 30/11/2016.
 */
public class ControlGroup {

    private MainController mainController;
    private CreateDocController createDocController;
    private FXMLLoader loader;
    private Stage stage;

    public ControlGroup(FXMLLoader loader,Stage stage) {
        this.loader = loader;
        this.stage = stage;
    }

    public void setStageMainController() {
        mainController = loader.getController();
        mainController.setupStage(stage);
    }

    public void setStageCreateDocController(){
        createDocController = loader.getController();
        createDocController.setupStage(stage);
    }

    public MainController getMainController() {
        return mainController;
    }

    public CreateDocController getCreateDocController() {
        return createDocController;
    }

    public void setLoader(FXMLLoader loader) {
        this.loader = loader;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
