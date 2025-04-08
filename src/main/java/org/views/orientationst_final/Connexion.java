package org.views.orientationst_final;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

public class Connexion extends Application {
    @Override
    public void start(Stage primaryStage) {
        MainController mainController = MainController.getInstance(primaryStage);

        primaryStage.setTitle("Connexion au Portail Administrateur");

        // Logo
        ImageView logoView = new ImageView(new Image("logo_usto(sans fond).png"));
        logoView.setFitWidth(110);
        logoView.setPreserveRatio(true);

        // Titre
        Label titreConnexion = new Label("Connexion au Portail Administrateur");
        titreConnexion.setFont(Font.font("Sans Serif Collection", FontWeight.BOLD, 22));
        titreConnexion.setTextFill(Color.rgb(22, 51, 118));

        // Instruction
        Label instructionLabel = new Label("Veuillez remplir le formulaire ci-dessous afin de vous connecter.");
        instructionLabel.setFont(Font.font("Sans Serif Collection", FontWeight.LIGHT, 12));
        instructionLabel.setTextFill(Color.GRAY);
        VBox.setMargin(instructionLabel, new Insets(-22, 0, 0, 0)); // 🔥 Rapprochement avec le titre 🔥

        // Formulaire centré
        VBox formContainer = new VBox(12);
        formContainer.setAlignment(Pos.CENTER);

        GridPane formBox = new GridPane();
        formBox.setVgap(6);
        formBox.setPadding(new Insets(2, 0, 2, 0));

        // Labels et champs de saisie
        Label userLabel = new Label("Nom d’utilisateur");
        userLabel.getStyleClass().add("normalLabel");

        userLabel.setTextFill(Color.BLACK);

        TextField userField = new TextField();
        userField.setPrefWidth(280);
        userField.setPrefHeight(38);
        userField.setPromptText("Entrez votre identifiant");
        userField.getStyleClass().add("fields");
       // userField.setStyle("-fx-background-color: #EAEAEA; -fx-border-radius: 5px; -fx-border-color: lightgray; -fx-padding: 8px;");

        Label passLabel = new Label("Mot de Passe");
        passLabel.getStyleClass().add("normalLabel");
      //  passLabel.setFont(Font.font("Sans Serif Collection", FontWeight.BOLD, 12));
        passLabel.setTextFill(Color.BLACK);

        // **Champs pour le mot de passe (2 champs, un caché, un visible)**
        PasswordField passField = new PasswordField();
        passField.setPrefWidth(280);
        passField.setPrefHeight(38);
        passField.setPromptText("Entrez votre mot de passe");
        passField.getStyleClass().add("fields");
        //passField.setStyle("-fx-background-color: #EAEAEA; -fx-border-radius: 5px; -fx-border-color: lightgray; -fx-padding: 8px;");

        TextField passFieldVisible = new TextField();
        passFieldVisible.setPrefWidth(280);
        passFieldVisible.setPrefHeight(38);
        passFieldVisible.setPromptText("Entrez votre mot de passe");
        passFieldVisible.getStyleClass().add("fields");
        //passFieldVisible.setStyle("-fx-background-color: #EAEAEA; -fx-border-radius: 5px; -fx-border-color: lightgray; -fx-padding: 8px;");
        passFieldVisible.setManaged(false);
        passFieldVisible.setVisible(false);

        // Case à cocher pour afficher le mot de passe
        CheckBox showPasswordCheckBox = new CheckBox("Afficher mot de passe");
        showPasswordCheckBox.setFont(Font.font("Arial", 11));
        showPasswordCheckBox.setTextFill(Color.DARKBLUE);
        VBox.setMargin(showPasswordCheckBox, new Insets(3, 0, 0, 0)); // 🔥 Rapprochement 🔥

        // **Événement pour afficher/masquer le mot de passe**
        showPasswordCheckBox.setOnAction(e -> {
            if (showPasswordCheckBox.isSelected()) {
                passFieldVisible.setText(passField.getText());
                passFieldVisible.setManaged(true);
                passFieldVisible.setVisible(true);
                passField.setManaged(false);
                passField.setVisible(false);
            } else {
                passField.setText(passFieldVisible.getText());
                passField.setManaged(true);
                passField.setVisible(true);
                passFieldVisible.setManaged(false);
                passFieldVisible.setVisible(false);
            }
        });

        // Organisation des champs
        VBox userBox = new VBox(0, userLabel, userField); // 🔥 Espacement réduit 🔥
        VBox passBox = new VBox(0, passLabel, passField, passFieldVisible, showPasswordCheckBox); // 🔥 Garde le bon style 🔥

        formBox.add(userBox, 0, 0);
        formBox.add(passBox, 0, 1);
        formBox.setAlignment(Pos.CENTER);

        // Bouton de connexion
        Button loginButton = getButton(userField, passField,mainController);
        loginButton.getStyleClass().add("MainButton");
      //  loginButton.setStyle("-fx-background-color: #112D66; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: BOLD; -fx-pref-width: 250px; -fx-pref-height: 50px; -fx-border-radius: 8px;");
        VBox.setMargin(loginButton, new Insets(15, 0, 0, 0));

        // Formulaire
        formContainer.getChildren().addAll(formBox, loginButton);

        // Conteneur principal
        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(10));
        root.getChildren().addAll(logoView, titreConnexion, instructionLabel, formContainer);
      //  root.setStyle("-fx-background-color: white;");
        root.setStyle("-fx-background-color: #F5F5F5;");
        // Création de la scène
        Scene scene = new Scene(root, 900, 650);
        scene.getStylesheets().add((getClass().getResource("/style.css")).toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @NotNull
    private static Button getButton(TextField userField, TextField passField,MainController mainController) {
        Button loginButton = new Button("Se Connecter");
        loginButton.setOnAction(e -> {
            String username = userField.getText();
            String password = passField.getText();

            if (username.isEmpty() || password.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur de Connexion");
                alert.setContentText("Veuillez remplir vos informations");
                alert.showAndWait();
            } else {
                mainController.showImportFilesView();
            }
        });
        return loginButton;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
