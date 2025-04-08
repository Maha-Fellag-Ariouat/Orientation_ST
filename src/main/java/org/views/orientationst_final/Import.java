package org.views.orientationst_final;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import java.io.File;

public class Import extends Application {
    private ListView<String> importedFilesList;
    private ProgressBar progressBar;
    private Label progressLabel;

    @Override
    public void start(Stage primaryStage) {
        MainController mainController = MainController.getInstance(primaryStage);
        primaryStage.setTitle("Gestion des Fichiers");

        // 📌 Barre latérale stylisée
        VBox sidebar = new VBox(25);
        sidebar.getStyleClass().add("sidebar");
        ImageView logoView = new ImageView(new Image("/logo_usto(sans fond).png"));
        logoView.setFitWidth(100);
        logoView.setPreserveRatio(true);

        Button importBtn = createSidebarButton("Import des Fichiers");
        Button gestionBtn = createSidebarButton("Gestion des Quotas");
        Button exportBtn = createSidebarButton("Export des Fichiers", true);

        Button logoutButton = new Button("Déconnexion");
        logoutButton.setOnAction(e -> {
            System.out.println("Déconnecté avec succès.");
            primaryStage.close();
        });
        logoutButton.getStyleClass().add("logoutLabel");

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        sidebar.getChildren().addAll(logoView, importBtn, gestionBtn, exportBtn, spacer, logoutButton);

        //  Contenu principal
        VBox mainContent = new VBox(10);
        mainContent.setAlignment(Pos.CENTER);
        mainContent.setStyle("-fx-padding: 5;");
        mainContent.setPrefWidth(700);

        Label title = new Label("Importation des fichiers");
        title.setAlignment(Pos.TOP_LEFT);
        title.getStyleClass().add("Mainlabel");
        title.setAlignment(Pos.TOP_CENTER);


        VBox dropZone = createDropZone();

        Label importingLabel = new Label("Fichiers importés");

        importingLabel.getStyleClass().add("normalLabel");
        importedFilesList = new ListView<>();
        importedFilesList.getStyleClass().add("importedFilesList");
        importedFilesList.setPrefHeight(100);

        Label importingStateLabel = new Label("Fichiers en cours d'importation");
        importingStateLabel.getStyleClass().add("normalLabel");
        progressBar = new ProgressBar(0);
        progressBar.setPrefWidth(500);
        progressBar.setStyle("-fx-accent: #335c7a;");
        progressBar.setVisible(false);

        progressLabel = new Label("Importation en cours...");
        progressLabel.setVisible(false);

        HBox downloadContainer = new HBox();
        downloadContainer.setAlignment(Pos.BOTTOM_RIGHT);
        Button downloadButton = createStyledButton("Télécharger");
        downloadButton.setOnAction(e -> {
            mainController.showQuotaManagerView();
        });

        downloadContainer.getChildren().add(downloadButton);

        mainContent.getChildren().addAll(title, dropZone, importingStateLabel, progressBar, progressLabel,importingLabel, importedFilesList, downloadContainer);

        HBox root = new HBox(sidebar, mainContent);
        Scene scene = new Scene(root, 900, 600);
        scene.getStylesheets().add((getClass().getResource("/style.css")).toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Button createStyledButton(String text) {
        Button button = new Button(text);
        button.getStyleClass().add("MainButton");
        return button;
    }

    private VBox createDropZone() {
        VBox dropZone = new VBox(10);
        dropZone.setAlignment(Pos.CENTER);
        dropZone.setPadding(new Insets(40, 50, 40, 50));
        dropZone.getStyleClass().add("dropZone");

        ImageView cloudIcon = new ImageView(new Image("/attachment_4303996.png"));
        cloudIcon.setFitWidth(50);
        cloudIcon.setFitHeight(50);

        Label dropLabel = new Label("Glisser un fichier ici");
        dropLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #333;");

        Button selectFileButton = createStyledButton("Sélectionner un fichier");
        selectFileButton.setOnAction(e -> openFileChooser());

        dropZone.getChildren().addAll(cloudIcon, dropLabel, selectFileButton);
        dropZone.setOnDragOver(event -> {
            if (event.getGestureSource() != dropZone && event.getDragboard().hasFiles()) {
                event.acceptTransferModes(TransferMode.COPY);
            }
            event.consume();
        });
        dropZone.setOnDragDropped(this::handleFileDrop);
        return dropZone;
    }

    private void openFileChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files", "*.xls", "*.xlsx"));
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            startFileUpload(file.getName());
        }
    }

    private void handleFileDrop(DragEvent event) {
        Dragboard db = event.getDragboard();
        if (db.hasFiles()) {
            File file = db.getFiles().get(0);
            if (file.getName().endsWith(".xls") || file.getName().endsWith(".xlsx")) {
                startFileUpload(file.getName());
            } else {
                showAlert("Seuls les fichiers Excel sont acceptés !");
            }
        }
        event.setDropCompleted(true);
        event.consume();
    }

    private void startFileUpload(String fileName) {
        Platform.runLater(() -> {
            progressBar.setProgress(0);
            progressBar.setVisible(true);
            progressLabel.setVisible(true);
        });

        new Thread(() -> {
            try {
                for (int i = 0; i <= 100; i += 5) {
                    Thread.sleep(100);
                    final double progress = i / 100.0;
                    Platform.runLater(() -> progressBar.setProgress(progress));
                }
                Platform.runLater(() -> {
                    importedFilesList.getItems().add(fileName);
                    progressBar.setVisible(false);
                    progressLabel.setVisible(false);
                    showAlert("Le fichier a été importé avec succès !");
                });
            } catch (InterruptedException ignored) {
            }
        }).start();
    }

    private void showAlert(String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }
    private Button createSidebarButton(String text) {
        return createSidebarButton(text, false);
    }

    private Button createSidebarButton(String text, boolean isPrimary) {
        Button button = new Button(text);
        button.setPrefWidth(200);
        button.setPrefHeight(40);
        if (isPrimary) {
            button.getStyleClass().add("sidebar-button-primary");
        } else {
            button.getStyleClass().add("sidebar-button");
        }
        return button;
    }
    public static void main(String[] args) {
        launch(args);
    }
}