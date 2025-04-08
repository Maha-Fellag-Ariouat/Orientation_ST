package org.views.orientationst_final;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Quota extends Application {

    // This list holds each row that represents a specialty
    private final ArrayList<HBox> specialtyRows = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) {
        // BorderPane layout: left sidebar, center content
        BorderPane borderPane = new BorderPane();

        // --- LEFT SIDEBAR ---
        VBox sidebar = new VBox(10); // vertical box with spacing 10
        sidebar.setPadding(new Insets(20)); // margin around the sidebar
        sidebar.setStyle("-fx-background-color: #90AED5;"); // background color
        sidebar.setPrefWidth(200); // fixed width for sidebar

        // Fake logo placeholder
        Label logo = new Label("LOGO.png");


        // Sidebar buttons
        Button importButton = new Button("Import des Fichiers");
        importButton.setPrefWidth(300);
        importButton.setPrefHeight(50);

        importButton.setStyle("-fx-background-color:white;-fx-font-size: 12px; -fx-text-fill:#163376;");
        Button manageQuotaButton = new Button("Gestion des Quotas");
        manageQuotaButton.setPrefWidth(300);
        manageQuotaButton.setPrefHeight(50);
        // Highlight the active button in dark blue
        manageQuotaButton.setStyle("-fx-background-color: #163376;-fx-font-size: 12px; -fx-text-fill: white;");
        Button exportButton = new Button("Export des Fichiers");
        exportButton.setPrefWidth(300);
        exportButton.setPrefHeight(50);
        exportButton.setStyle("-fx-background-color:white;-fx-font-size: 12px; -fx-text-fill:#163376;");

        VBox.setMargin(importButton, new Insets(0, 0, 15, 0));
        VBox.setMargin(manageQuotaButton, new Insets(0, 0, 15, 0));
        VBox.setMargin(exportButton, new Insets(0, 0, 15, 0));
        // Spacer pushes the logout button to the bottom
        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        // Logout button positioned at the bottom-left
        Button logoutButton = new Button("Déconnexion");
        logoutButton.setStyle("-fx-background-color:#90AED5;-fx-font-size: 12px; -fx-text-fill:white;");
        logoutButton.setId("logoutButton");
        logoutButton.setOnAction(e -> {
            System.out.println("Déconnecté avec succès.");
            primaryStage.close(); // closes the app
        });


        // Add a spacer to push the buttons lower
        Region topSpacer = new Region();
        VBox.setVgrow(topSpacer, Priority.ALWAYS);

// Add all sidebar elements in order: logo, spacer, buttons, bottom spacer, logout button
        sidebar.getChildren().addAll(logo, topSpacer, importButton, manageQuotaButton, exportButton, spacer, logoutButton);


        // --- LISTE DES SPÉCIALITÉS ---
        VBox specialtyList = new VBox(10); // box that will hold all specialties
        specialtyList.setPadding(new Insets(20)); // margin around it
        Label listTitle = new Label("Liste des spécialités");
        listTitle.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #163376;");

        // title for the list
        specialtyList.getChildren().add(listTitle);

        // --- ADD SPECIALTY FORM ---
        VBox addSpecialty = new VBox(10);
        addSpecialty.setPadding(new Insets(50));
        Label addTitle = new Label("Ajouter une spécialité");
        addTitle.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #163376;");
// form title

        // TextFields for input: name and quota
        Label specialtyNameLabel = new Label("Nom de la spécialité");
        specialtyNameLabel.setStyle(" -fx-font-size: 20px;-fx-text-fill:#163376;");
        TextField specialtyNameField = new TextField();
        specialtyNameField.setPromptText("Entrez le nom de spécialité");
        Label quotaLabel = new Label("Quota disponible");
        quotaLabel.setStyle(" -fx-font-size: 20px;-fx-text-fill:#163376;");
        TextField quotaField = new TextField();
        quotaField.setPromptText("Entrez le quota disponible");

        // Save button
        Button saveButton = new Button("Enregistre");
        saveButton.setStyle("-fx-background-color: #163376;-fx-font-size:12px ;-fx-font-weight: bold; -fx-text-fill: white;");
        saveButton.setPrefWidth(100);
        saveButton.setPrefHeight(30);




        // Function to handle save logic
        Runnable saveSpecialty = () -> {
            String name = specialtyNameField.getText();
            String quota = quotaField.getText();

            if (!name.isEmpty() && !quota.isEmpty()) {
                try {
                    int quotaValue = Integer.parseInt(quota);
                    if (quotaValue < 0) throw new NumberFormatException();

                    // Create row for specialty and add to the list
                    HBox row = createSpecialtyRow(name, quota, specialtyList);
                    specialtyRows.add(row);
                    specialtyList.getChildren().add(row);

                    // Clear fields for next input
                    specialtyNameField.clear();
                    quotaField.clear();
                    specialtyNameField.requestFocus();
                    System.out.println("Spécialité ajoutée: " + name + " avec quota: " + quota);
                } catch (NumberFormatException ex) {
                    // Error dialog if quota is invalid
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setHeaderText("Quota invalide");
                    errorAlert.setContentText("Le quota doit être un nombre entier positif.");
                    errorAlert.showAndWait();
                }
            } else {
                System.out.println("Veuillez remplir tous les champs.");
            }
        };

        // Pressing enter on specialtyNameField will focus on quotaField
        specialtyNameField.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) quotaField.requestFocus();
        });

        // Pressing enter on quotaField will save
        quotaField.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) saveSpecialty.run();
        });

        // Save button click
        saveButton.setOnAction(e -> saveSpecialty.run());

        // Add form elements
        addSpecialty.getChildren().addAll(addTitle, specialtyNameLabel, specialtyNameField, quotaLabel, quotaField, saveButton);


        // Horizontal layout: left (list), right (add form)
        HBox mainContent = new HBox(20);
        mainContent.getChildren().addAll(specialtyList, addSpecialty);

        // Bottom-right button: "Lancer Affectation"
        Button assignButton = new Button("Lancer Affectation");
        assignButton.setStyle("-fx-background-color: #163376; -fx-text-fill: white;-fx-font-size:15px ;-fx-font-weight: bold; -fx-padding: 20 30 20 30;");
        assignButton.setOnAction(e -> {
            // Popup alert when launched
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Affectation");
            alert.setHeaderText("Lancement de l'affectation");
            alert.setContentText("L'affectation des spécialités est lancée avec succès !");
            alert.showAndWait();
        });

        // Using StackPane to position button at bottom-right over main content
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(mainContent, assignButton);
        StackPane.setAlignment(assignButton, Pos.BOTTOM_RIGHT);
        StackPane.setMargin(assignButton, new Insets(20)); // margin from edges

        // Add sidebar and main stack pane to borderPane
        borderPane.setLeft(sidebar);
        borderPane.setCenter(stackPane);
// Scene setup
        Scene scene = new Scene(borderPane, 1000, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Gestion des Quotas");
        primaryStage.show();
    }

    // Method to create each row representing a specialty
    private HBox createSpecialtyRow(String name, String quota, VBox parentList) {
        HBox row = new HBox(50);
        row.setPadding(new Insets(0));

        // Labels for name and quota
        Label nameLabel = new Label(name);
        Label quotaLabel = new Label(quota);
        nameLabel.setStyle("-fx-font-size: 20px;-fx-text-fill:#163376;");
        quotaLabel.setStyle("-fx-font-size: 20px;-fx-text-fill:#163376;");
        Region spacer = new Region();
        row.setHgrow(spacer, Priority.ALWAYS);

        // Double click to edit / right-click to delete
        row.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && event.getButton() == MouseButton.PRIMARY) {
                // Inline edit with TextFields
                TextField nameField = new TextField(nameLabel.getText());
                TextField quotaField = new TextField(quotaLabel.getText());

                row.getChildren().clear();
                row.getChildren().addAll(nameField, quotaField);

                // Save changes on Enter or focus loss
                nameField.setOnAction(e -> saveChanges(row, nameField, quotaField, nameLabel, quotaLabel));
                quotaField.setOnAction(e -> saveChanges(row, nameField, quotaField, nameLabel, quotaLabel));

                nameField.focusedProperty().addListener((obs, oldVal, newVal) -> {
                    if (!newVal) saveChanges(row, nameField, quotaField, nameLabel, quotaLabel);
                });
                quotaField.focusedProperty().addListener((obs, oldVal, newVal) -> {
                    if (!newVal) saveChanges(row, nameField, quotaField, nameLabel, quotaLabel);
                });

            } else if (event.getButton() == MouseButton.SECONDARY) {
                // Right-click for delete confirmation
                Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                confirm.setTitle("Confirmation");
                confirm.setHeaderText("Supprimer la spécialité ?");
                confirm.setContentText("Voulez-vous vraiment supprimer " + nameLabel.getText() + " ?");
                confirm.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        parentList.getChildren().remove(row);
                        specialtyRows.remove(row);
                        System.out.println("Spécialité supprimée: " + nameLabel.getText());
                    }
                });
            }
        });

        // Add both labels to row
        row.getChildren().addAll(nameLabel, spacer, quotaLabel);
        return row;
    }


    // Method to save changes after editing specialty
    private void saveChanges(HBox row, TextField nameField, TextField quotaField, Label nameLabel, Label quotaLabel) {
        String newName = nameField.getText();
        String newQuota = quotaField.getText();
        Region spacer = new Region();
        row.setHgrow(spacer, Priority.ALWAYS);

        try {
            int quotaValue = Integer.parseInt(newQuota);
            if (quotaValue < 0) throw new NumberFormatException();

            nameLabel.setText(newName);
            quotaLabel.setText(newQuota);

            // Replace fields back with labels
            row.getChildren().clear();
            row.getChildren().addAll(nameLabel,spacer, quotaLabel);
            System.out.println("Spécialité modifiée: " + newName + " avec quota: " + newQuota);
        } catch (NumberFormatException ex) {
            // Invalid quota fallback
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Quota invalide");
            errorAlert.setContentText("Le quota doit être un nombre entier positif.");
            errorAlert.showAndWait();
            row.getChildren().clear();
            row.getChildren().addAll(nameLabel,spacer, quotaLabel);
        }
    }

    // Entry point
    public static void main(String[] args) {
        launch(args);
    }
}