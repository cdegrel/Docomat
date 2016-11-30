package controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


/**
 * Created by Cedric on 29/11/2016.
 */
public class CreateDocController implements Initializable {

    @FXML
    private Button btn_MenuPrincpal;
    private Stage stage;

    public void setupStage(Stage stage) {
        this.stage = stage;
    }


    public void initialize(URL location, ResourceBundle resources) {
        assert btn_MenuPrincpal != null : "fx:id=\"myButton\" was not injected: check your FXML file 'main_view.fxml'.";
        btn_MenuPrincpal.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/main_view.fxml"));
                    Parent pageCreateDoc = loader.load();
                    MainController mainController = loader.getController();
                    mainController.setupStage(stage);
                    stage.setScene(new Scene(pageCreateDoc, 800, 500));
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
