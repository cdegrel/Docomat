package views;

import controllers.ControlGroup;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.Documents;

public class Main extends Application {

    private ObservableList<Documents> documentsList;
    private ControlGroup controlGroup;

    @Override
    public void start(Stage primaryStage) throws Exception{
        documentsList = FXCollections.observableArrayList();
        documentsList.add(new Documents(1,"document 1"));
        documentsList.add(new Documents(2,"document 2"));
        documentsList.add(new Documents(3,"document 3"));
        documentsList.add(new Documents(4,"document 4"));
        documentsList.add(new Documents(5,"document 5"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main_view.fxml"));
        Parent root = loader.load();
        controlGroup = new ControlGroup(loader,primaryStage);
        controlGroup.setStageMainController();
        controlGroup.getMainController().setMain(this);
        primaryStage.setTitle("Docomat");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, 800, 500));
        primaryStage.show();
    }

    public ObservableList<Documents> getDocumentsList() {
        return documentsList;
    }

    public ControlGroup getControlGroup() {
        return controlGroup;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
