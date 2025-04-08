package org.views.orientationst_final;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class export extends Application {

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();


        VBox sidebar = new VBox(25);
        sidebar.getStyleClass().add("sidebar");
        ImageView logoView = new ImageView(new Image("/logo_usto(sans fond).png"));
        logoView.getStyleClass().add("logo");
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

        // ==================== Contenu principal ====================
        VBox content = new VBox(20);  // Réduction de l'espacement global
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(50, 30, 50, 30));
        content.setStyle("-fx-background-color: #F5F5F5;");

        ImageView imageView = new ImageView(new Image("/4212138-removebg-preview.png"));
        imageView.setFitWidth(320);
        imageView.setPreserveRatio(true);

        Label successLabel = new Label("Données traitées avec succès !");
        successLabel.getStyleClass().add("Mainlabel");

        Label exportLabel = new Label("Exporter le fichier en format");
        exportLabel.getStyleClass().add("normalLabel");
        VBox.setMargin(exportLabel, new Insets(-5, 0, 0, 0));
        Button excelBtn = createMainButton("Excel (.xlsx)");
        Button pdfBtn = createMainButton("PDF (.pdf)");

        HBox exportButtons = new HBox(12, excelBtn, pdfBtn);
        exportButtons.setAlignment(Pos.CENTER);

        content.getChildren().addAll(imageView, successLabel, exportLabel, exportButtons);

        // ==================== Mise en page ====================
        root.setLeft(sidebar);
        root.setCenter(content);

        Scene scene = new Scene(root, 900, 650);
        scene.getStylesheets().add((getClass().getResource("/style.css")).toExternalForm());

        primaryStage.setTitle("Interface Résultat");
        primaryStage.setScene(scene);
        primaryStage.show();
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

    private Button createMainButton(String text) {
        Button button = new Button(text);
        button.setPrefSize(175, 45);
        button.getStyleClass().add("MainButton");
        return button;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
