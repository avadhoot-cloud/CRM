package com.example.gymproj;



import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttendanceRecordsController {

    @FXML
    private TableView<AttendanceRecord> attendanceTableView;

    @FXML
    private TableColumn<AttendanceRecord, LocalDate> dateColumn;

    @FXML
    private TableColumn<AttendanceRecord, String> statusColumn;

    @FXML
    private TextField textid;

    @FXML
    private ComboBox<String> monthComboBox;

    @FXML
    private Button searchButton;
    Connection connection;

    public void initialize() throws SQLException {
        // Initialize the TableView and columns
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Initialize the combo box with data from the member table
        DbConnect dbConnect = new DbConnect();
        connection = dbConnect.getConnection();

        // Populate the Month ComboBox
        List<String> months = Arrays.asList(
                "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"
        );
        monthComboBox.getItems().addAll(months);
    }

    @FXML
    private void handleSearchButtonClick() {
        // Handle the search functionality here based on Member ID and Month selection
        String memberId = textid.getText();
        String selectedMonth = monthComboBox.getValue();

        // Placeholder logic: Display dummy data for demonstration purposes
        attendanceTableView.getItems().clear();

        // Create a mapping of month names to numeric values
        Map<String, Integer> monthMap = new HashMap<>();
        monthMap.put("January", 1);
        monthMap.put("February", 2);
        monthMap.put("March", 3);
        monthMap.put("April", 4);
        monthMap.put("May", 5);
        monthMap.put("June", 6);
        monthMap.put("July", 7);
        monthMap.put("August", 8);
        monthMap.put("September", 9);
        monthMap.put("October", 10);
        monthMap.put("November", 11);
        monthMap.put("December", 12);
        // ... Add mappings for all months

        // Get the numeric value of the selected month
        Integer monthValue = monthMap.get(selectedMonth);

        String query = "SELECT attend_date, status FROM attendance WHERE memberid = ? AND MONTH(attend_date) = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, memberId);
            preparedStatement.setInt(2, monthValue);

            // Execute the query and process the results
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                LocalDate attendDate = resultSet.getDate("attend_date").toLocalDate();
                String status = resultSet.getString("status");

                // Create an AttendanceRecord object and add it to your TableView
                AttendanceRecord record = new AttendanceRecord(attendDate, status);
                attendanceTableView.getItems().add(record);
                // Debug print to check retrieved data
                System.out.println("Date: " + attendDate + ", Status: " + status);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception as needed
        }

// Debug print statements
        System.out.println("Member ID: " + memberId);
        System.out.println("Selected Month: " + selectedMonth);

    }

    // Add methods for loading and displaying attendance records as explained in previous responses
}
