package org.views.orientationst_final;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class MainController {
    private static MainController instance;
    private Stage primaryStage;

    private MainController(Stage stage) {
        this.primaryStage = stage;
    }

    public static MainController getInstance(Stage stage) {
        if (instance == null) {
            instance = new MainController(stage);
        }
        return instance;
    }

    public void showLoginView() {
        loadView("ConnexionVue.fxml");
    }

    public void showImportFilesView() {
        loadView("ImportFilesVue.fxml");
    }

    public void showQuotaManagerView() {
        loadView("QuotaManagerVue.fxml");
    }

    public void showResultView() {
        loadView("ResultatVue.fxml");
    }

    private void loadView(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();
            primaryStage.setScene(new Scene(root, 900, 600));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


