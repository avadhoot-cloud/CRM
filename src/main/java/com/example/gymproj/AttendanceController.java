package com.example.gymproj;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AttendanceController {

        @FXML
        private DatePicker datePicker;

        @FXML
        private ComboBox<String> memberComboBox;

        @FXML
        private ToggleGroup presentyBtn;

        @FXML
        private RadioButton presentRadioButton;

        @FXML
        private RadioButton absentRadioButton;

        @FXML
        private Button recordAttendanceButton;

        @FXML
        private Label feedbackLabel;
        @FXML
        Button showAttendanceReportButton;

        Connection connection;

    @FXML
    public void initialize() throws SQLException {
        // Initialize the combo box with data from the member table
        DbConnect dbConnect = new DbConnect();
         connection = dbConnect.getConnection();
        populateMemberComboBox();
    }

    private void populateMemberComboBox() {
        String query = "SELECT id, fname FROM member";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            List<String> memberNames = new ArrayList<>();

            while (resultSet.next()) {
                int memberid = resultSet.getInt("id");
                String memberName = resultSet.getString("fname");
                memberNames.add(memberid + ": " + memberName);
            }

            memberComboBox.getItems().addAll(memberNames);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // testing
    @FXML
    private void showAttendanceReport(ActionEvent event) {
        // Load the AttendanceRecords.fxml view
        loadAttendanceRecordsStage();
    }


    // Other instance variables and methods...

        @FXML
        private void recordAttendance(ActionEvent event) {
            // Get the selected date, member, and attendance status
            LocalDate attend_date = datePicker.getValue();
            String selectedMember = memberComboBox.getValue();
            String status = "";
            int memberid = 0;
            // Split the selectedMember string into ID and Name
            String[] parts = selectedMember.split(":");
            if (parts.length == 2) {
                 memberid = Integer.parseInt(parts[0].trim()); // Extract and parse the member ID
                status = parts[1].trim(); // Extract the member name as the status

                if (presentRadioButton.isSelected()) {
                    status = "Present";
                } else if (absentRadioButton.isSelected()) {
                    status = "Absent";
                }
            }
            // Validate input
            if (attend_date != null && memberid != 0 && !status.isEmpty()) {
                // TODO: Insert the attendance record into your database or perform your desired action.
                // You will need to implement your database logic here.
                String insertQuery = "INSERT INTO attendance (memberid, attend_date, status) VALUES (?, ?, ?)";

                try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                    preparedStatement.setInt(1, memberid); // Replace memberId with the actual member's ID
                    preparedStatement.setDate(2, java.sql.Date.valueOf(attend_date)); // Replace attendDate with the actual date
                    preparedStatement.setString(3, status); // Replace status with the actual attendance status

                    int rowsAffected = preparedStatement.executeUpdate();

                    if (rowsAffected > 0) {
                        System.out.println("Attendance recorded successfully.");
                    } else {
                        System.out.println("Failed to record attendance.");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                // Provide feedback to the user
                feedbackLabel.setText("Attendance recorded successfully for " + memberid + " on " + attend_date);
            } else {
                // Handle the case where any of the required fields are not selected or filled in.
                feedbackLabel.setText("Please select all required fields.");
            }
        }
    private void loadAttendanceRecordsStage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AttendanceRecords.fxml"));
            Parent root = loader.load();

            // Access the controller of the new FXML file
            AttendanceRecordsController recordsController = loader.getController();

            // You can pass data to the new controller if needed
            // recordsController.setSomeData(someData);

            // Create a new stage for the AttendanceRecords.fxml view
            Stage stage = new Stage();
            stage.setScene(new Scene(root));

            // Show the new stage
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception as needed
        }
    }


}

