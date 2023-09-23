package com.example.gymproj;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    private Stage stage;
    private Scene registerScene;

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.stage = primaryStage;

        // Load the login scene
        FXMLLoader loginLoader = new FXMLLoader(HelloApplication.class.getResource("login.fxml"));
        Parent loginRoot = loginLoader.load();
        Scene loginScene = new Scene(loginRoot, 600, 440);

        // Set the login scene
        stage.setTitle("Hello!");
        stage.setScene(loginScene);
        stage.show();

        // Get a reference to the login controller and pass a reference to the main app
        LoginController loginController = loginLoader.getController();
        loginController.setMainApp(this);

        // Load the register scene (don't show it yet)
        FXMLLoader registerLoader = new FXMLLoader(HelloApplication.class.getResource("register.fxml"));
        Parent registerRoot = registerLoader.load();
       // Scene registerScene = new Scene(registerRoot);
        // Assign the class field, not a local variable
        registerScene = new Scene(registerRoot);
        // Get a reference to the register controller and pass a reference to the main app
        RegisterController registerController = registerLoader.getController();
        registerController.setMainApp(this);

        // Set up a method in the login controller to switch to the register scene
        loginController.setRegisterScene(registerScene);
    }

    // Method to change the scene content when the button is clicked
    public void showLoginScene() {
        try {
            // Reload the login scene (useful if you need to reset its state)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            // Set the login scene
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showRegisterScene() {
        try {
            // Show the register scene

            stage.setScene(registerScene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }






public static void main(String[] args) {
        launch();
    }
}
