package controllers;

import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

/**
 * Created by ahecht on 30/11/2016.
 */
public class ControlGroup {

    private WelcomeController welcomeController;
    private DocomatController docomatController;
    private FXMLLoader loader;
    private Stage stage;

    public ControlGroup(FXMLLoader loader,Stage stage) {
        this.loader = loader;
        this.stage = stage;
    }

    public void setStageWelcomeController() {
        welcomeController = loader.getController();
        welcomeController.setupStage(stage);
    }

    public void setDocomatController(){
        docomatController = loader.getController();
        docomatController.setupStage(stage);
    }

    public WelcomeController getWelcomeController() {
        return welcomeController;
    }

    public DocomatController getDocomatController() {
        return docomatController;
    }

    public void setLoader(FXMLLoader loader) {
        this.loader = loader;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
