package com.example.gymproj;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Window;

import javax.security.auth.callback.Callback;
import java.sql.SQLException;
import java.util.Optional;

public class RegisterController {
    @FXML
    private TextField username;
    @FXML
    private TextField email;
    @FXML
    private PasswordField password;
     @FXML
     private Button loginButton;
    @FXML
    private Button submitButton;
    private HelloApplication mainApp; // Reference to the main application

    // This method will be called by the FXMLLoader after loading the FXML file
    public void initialize() {
        // Set up event handling for the loginButton
        System.out.println("intialize get called ");
       //submitButton.setOnAction(event -> showRegisterScene());
    }

    // Method to set a reference to the main app
    public void setMainApp(HelloApplication mainApp) {
        this.mainApp = mainApp;
    }
    @FXML
    public void submit(ActionEvent event) throws SQLException {

        Window owner = submitButton.getScene().getWindow();


        if (username.getText().isEmpty()) {
            System.out.println("username should not be empty");
            return;
        }


        String fullName = username.getText();
        String emailId = email.getText();
        String pass = password.getText();

        DbConnect db = new DbConnect();
        try {
            if (db.insertRecord(fullName, emailId, pass)) {
                showRegistrationSuccessfulMessage(owner);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception here
        }
        // Now, navigate back to the login page
        mainApp.showLoginScene();

    }

    private void showRegistrationSuccessfulMessage(Window owner) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Registration Successful");
        alert.setHeaderText(null);
        alert.setContentText("Your registration was successful!");

        alert.initOwner(owner);

        // Use JavaFX's ButtonType instead of Callback
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            // User clicked OK, handle it if needed
        }

    }



    // Method to switch to the login scene
    private void showRegisterScene() {
        if (mainApp != null) {
            mainApp.showRegisterScene();
        }
    }

    @FXML
    public void saveMember(ActionEvent event) {
        // Your code to handle the action goes here
    }

}
