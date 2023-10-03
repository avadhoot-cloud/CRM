package com.example.gymproj;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class FeesController {

    @FXML
    private ComboBox<MemberData> memberComboBox; // Assuming this ComboBox contains member names or IDs
    @FXML
    private TextField feesTypeField;
    @FXML
    private TextField amountField;
    @FXML
    private DatePicker paymentDateField;
    @FXML
    private TextField paymentTypeField;
    @FXML
    private TextField statusField;
    @FXML
    private Button backButton;
    @FXML
    private Button saveButton;

    // MemberData class to represent both member name and member_id
    public static class MemberData {
        private final String memberName;
        private final int memberId;

        public MemberData(String memberName, int memberId) {
            this.memberName = memberName;
            this.memberId = memberId;
        }

        public String getMemberName() {
            return memberName;
        }

        public int getMemberId() {
            return memberId;
        }

        @Override
        public String toString() {
            return memberName; // Display member names in the ComboBox
        }
    }

    DbConnect db = new DbConnect();

    // This method is called when the controller is initialized.
    @FXML
    private void initialize() {
        // Populate the memberComboBox with member names or IDs from the database
        populateMemberComboBox();
    }

    private void populateMemberComboBox() {
        try {
            // Fetch member names or IDs from the database (replace with your database query)
            List<MemberData> memberList = db.getMemberNamesAndIDs(); // You need to implement this method in your Dbconnect class

            // Populate the ComboBox with the retrieved member data
           // memberComboBox.getItems().addAll(memberList);
            memberComboBox.getItems().addAll(memberList);
            // Optionally, set a default value or prompt text
            memberComboBox.setPromptText("Select a Member");
        } catch (Exception e) {
            e.printStackTrace();
            // Handle any exceptions that may occur during data retrieval
        } }

    @FXML
      private void saveFeeDetails() {
        try {
            // Retrieve user input
            String feesType = feesTypeField.getText();
            double amount = Double.parseDouble(amountField.getText());
            String paymentDate = paymentDateField.getValue().toString();
            String paymentType = paymentTypeField.getText();
            String status = statusField.getText();

            // Retrieve the selected MemberData object from the ComboBox
            MemberData selectedMember = memberComboBox.getValue();
            int memberId = selectedMember.getMemberId();

            // TODO: Construct and execute an SQL query to insert fee details into the fees table
            String insertQuery = "INSERT INTO fees (member_id, fees_type, amount, payment_date, payment_type, status) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";

            // Create a PreparedStatement and set the parameters
            try (Connection connection = DbConnect.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

                preparedStatement.setInt(1, memberId);
                preparedStatement.setString(2, feesType);
                preparedStatement.setDouble(3, amount);
                preparedStatement.setString(4, paymentDate);
                preparedStatement.setString(5, paymentType);
                preparedStatement.setString(6, status);

                // Execute the query
                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    // Fee details were successfully saved, you can display a success message or take other actions
                    // Optionally, clear the input fields
                    clearFields();
                } else {
                    // Handle the case where no rows were affected (insertion failed)
                    // Display an error message or log the issue
                }
            } catch (SQLException e) {
                e.printStackTrace();
                // Handle database-related exceptions here (e.g., connection issues, SQL errors)
                // Display an error message or log the exception
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            // Handle the case where the amount field contains an invalid number format
            // Display an error message to the user
        } catch (Exception e) {
            e.printStackTrace();
            // Handle any other exceptions that may occur during fee saving
        }
    }

    private void clearFields() {
        // Clear the input fields
        feesTypeField.clear();
        amountField.clear();
        paymentDateField.setValue(null); // Clear the date picker
        paymentTypeField.clear();
        statusField.clear();

        // Optionally, reset the ComboBox selection
        memberComboBox.getSelectionModel().clearSelection();
    }

    @FXML
    private void navigateToMemberRegistration() {
        try {
            // Load the memberRegister.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MemRegister.fxml"));
            Parent root = loader.load();

            // Create a new scene
            Scene scene = new Scene(root);

            // Get the stage and set the new scene
            Stage stage = (Stage) backButton.getScene().getWindow(); // Assuming backButton is the name of your "Back" button
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
            // Handle any exceptions that may occur during navigation
        }
    }
}
