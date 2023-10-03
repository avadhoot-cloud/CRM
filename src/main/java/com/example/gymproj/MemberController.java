package com.example.gymproj;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import com.example.gymproj.DbConnect;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class MemberController {
   @FXML
    TextField fname;
   @FXML
    Button buttonSubmit;
   @FXML
   DatePicker dob;
   @FXML
   TextField mobile;
    @FXML
    private RadioButton maleRadioButton;
    @FXML
    private RadioButton femaleRadioButton;
    @FXML
    private ToggleGroup genderToggleGroup;
    @FXML
    private ComboBox<String> memberComboBox;
    @FXML
            private Hyperlink feesLink;

    DbConnect db = new DbConnect();
    Connection connection;


    @FXML
    private void initialize() throws IOException {
        genderToggleGroup = new ToggleGroup();
        maleRadioButton.setToggleGroup(genderToggleGroup);
        femaleRadioButton.setToggleGroup(genderToggleGroup);
        // Populate the ComboBox with batch timings from the database
        // Initialize BatchController to create batchComboBox



          memberComboBox.setItems(populateBatchComboBox());
        // Add an event handler to the ComboBox for selection changes
       // batchComboBox.setOnAction(this::handleComboBoxSelection);

        try {
            connection = DbConnect.getConnection();
            System.out.println("connection successful");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public ObservableList<String> populateBatchComboBox() {
        ObservableList<String> batchTimings = FXCollections.observableArrayList();
        try {
            // Establish a database connection (use your own connection logic)
            connection = db.getConnection();

            // Query to retrieve batch timings from the 'batch' column
            String selectQuery = "SELECT DISTINCT batch FROM member";

            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    String batchTiming = resultSet.getString("Batch");
                    batchTimings.add(batchTiming);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return batchTimings;
    }

    @FXML
    public void addMember(ActionEvent actionEvent) {
        String insertQuery = "INSERT INTO member(fname, dob, gend, contact,batch) VALUES (?, ?, ?, ?, ?)";
        LocalDate selectedDate = dob.getValue();
        java.sql.Date sqlDate = java.sql.Date.valueOf(selectedDate);


        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setString(1, fname.getText());

            preparedStatement.setDate(2, sqlDate);
            RadioButton selectedRadioButton = (RadioButton) genderToggleGroup.getSelectedToggle();
            String gender = selectedRadioButton.getText();
            preparedStatement.setString(3, gender);

            preparedStatement.setString(4, mobile.getText());
            // Get the selected batch from the ComboBox
            String selectedBatch = memberComboBox.getValue();
            preparedStatement.setString(5, selectedBatch);


            preparedStatement.executeUpdate();

            // Clear the input fields after insertion
            fname.clear();

            mobile.clear();
            genderToggleGroup.selectToggle(null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void navigateToFees() {
        try {
            // Load the fees.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fees.fxml"));
            Parent root = loader.load();

            // Create a new scene
            Scene scene = new Scene(root);

            // Get the stage and set the new scene
            Stage stage = (Stage) feesLink.getScene().getWindow(); // Assuming hyperlink is the name of your Hyperlink control
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
            // Handle any exceptions that may occur during navigation
        }
    }
}
