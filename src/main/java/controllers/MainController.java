package controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


/**
 * Created by Cedric on 29/11/2016.
 */

public class MainController implements Initializable {

    @FXML
    private Button btnCreateNewDoc;
    private Stage stage;

    public void setupStage(Stage stage) {
        this.stage = stage;
    }

    public void initialize(URL location, ResourceBundle resources) {
       if (btnCreateNewDoc == null) System.out.println("fx:id=\"myButton\" was not injected: check your FXML file 'simple.fxml'.");

        btnCreateNewDoc.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/create_docs.fxml"));
                    Parent pageCreateDoc =  loader.load();
                    CreateDocController createDocController = loader.getController();
                    createDocController.setupStage(stage);
                    stage.setScene(new Scene(pageCreateDoc, 800, 500));
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }


}
