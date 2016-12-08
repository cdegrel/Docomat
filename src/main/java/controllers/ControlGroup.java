package controllers;

import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

/**
 * Created by ahecht on 30/11/2016.
 */
public class ControlGroup {


    private CreateDocController createDocController;
    private WelcomeController welcomeController;
    private CreateTxtController createTxtController;
    private FXMLLoader loader;
    private Stage stage;

    public ControlGroup(FXMLLoader loader,Stage stage) {
        this.loader = loader;
        this.stage = stage;
    }


    public void setStageCreateDocController(){
        createDocController = loader.getController();
        createDocController.setupStage(stage);
    }

    public void setStageWelcomeController() {
        welcomeController = loader.getController();
        welcomeController.setupStage(stage);
    }

    public void setStageCreateTxtController() {
        createTxtController = loader.getController();
        createTxtController.setupStage(stage);
    }


    public CreateDocController getCreateDocController() {
        return createDocController;
    }

    public WelcomeController getWelcomeController() {
        return welcomeController;
    }

    public CreateTxtController getCreateTxtController() {
        return createTxtController;
    }

    public void setLoader(FXMLLoader loader) {
        this.loader = loader;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
