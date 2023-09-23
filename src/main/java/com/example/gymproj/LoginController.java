package com.example.gymproj;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.sql.*;

public class LoginController {
    @FXML
    private TextField email;
    @FXML
    private PasswordField password;
    @FXML
    private Button registerButton;

    private HelloApplication mainApp; // Reference to the main application

    // This method will be called by the FXMLLoader after loading the FXML file
    public void initialize() {
        // Set up event handling for the registerButton
        registerButton.setOnAction(event -> showRegisterScene());
    }

    // Method to set a reference to the main app
    public void setMainApp(HelloApplication mainApp) {
        this.mainApp = mainApp;
    }


    @FXML
    public void login(ActionEvent event) throws SQLException {

        Window owner = registerButton.getScene().getWindow();

        if (email.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter your email id");
            return;
        }
        if (password.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter a password");
            return;
        }

        String emailId = email.getText();
        String pass = password.getText();

        DbConnect db = new DbConnect();
        boolean flag = db.validate(emailId, pass);


        if (!flag) {
            infoBox("Please enter correct Email and Password", null, "Failed");
        } else {
            infoBox("Login Successful!", null, "Failed");
            try {
                // Load the next FXML file
                FXMLLoader loader = new FXMLLoader(getClass().getResource("admin.fxml"));
                Parent root = loader.load();

                // Create a new scene
                Scene scene = new Scene(root);

                // Get the stage from the current scene
                Stage stage = (Stage) registerButton.getScene().getWindow();

                // Set the new scene on the stage
                stage.setScene(scene);
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
    public static void infoBox(String infoMessage, String headerText, String title) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(infoMessage);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.showAndWait();
    }
    private static void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }

    private void showRegisterScene() {
        if (mainApp != null) {
            mainApp.showRegisterScene();
        }
    }
    // Reference to the register scene
    private Scene registerScene;

    // Set up a method to set the register scene
    public void setRegisterScene(Scene registerScene) {
        this.registerScene = registerScene;
    }


}

