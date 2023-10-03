package com.example.gymproj;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BatchController {
    @FXML
    private TableView<MemberModel> tableView;
    @FXML
    private TableColumn<MemberModel, Integer> idColumn;
    @FXML
    private TableColumn<MemberModel, String> fullNameColumn;
    @FXML
    private TableColumn<MemberModel, LocalDate> dobColumn;
    @FXML
    private TableColumn<MemberModel, String> genderColumn;
    @FXML
    private TableColumn<MemberModel, String> mobileColumn;

    @FXML
    private  ComboBox<String> batchComboBox; // Make sure to specify the appropriate data type if your ComboBox holds different data
    private  DbConnect db = new DbConnect();
    private static Connection connection;

        // Other methods and fields...


    @FXML
    private void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        fullNameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        dobColumn.setCellValueFactory(new PropertyValueFactory<>("dob"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
        mobileColumn.setCellValueFactory(new PropertyValueFactory<>("mobile"));
        // Populate the ComboBox with batch timings from the database
        populateBatchComboBox();
        // Add an event handler to the ComboBox for selection changes
        batchComboBox.setOnAction(this::handleComboBoxSelection);

    }
    public void  populateBatchComboBox() {
        ObservableList<String> batchTimings = FXCollections.observableArrayList();
        try {
            // Establish a database connection (use your own connection logic)
             connection = db.getConnection();

            // Query to retrieve batch timings from the 'batch' column
            String selectQuery = "SELECT DISTINCT batch FROM member";

            // Create a list to store batch timings
           // List<String> batchTimings = new ArrayList<>();

            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    String batchTiming = resultSet.getString("Batch");
                    batchTimings.add(batchTiming);
                }
            }

            // Populate the ComboBox with the retrieved batch timings
            batchComboBox.getItems().addAll(batchTimings);

        } catch (SQLException e) {
            e.printStackTrace();
        }
       // return batchTimings; // Add this return statement
    }
    @FXML
    public void handleComboBoxSelection(ActionEvent event) {
        // This method will be called when a selection is made in the ComboBox
        String selectedBatch = batchComboBox.getValue(); // Get the selected batch value
        // You can perform actions based on the selected batch here
       // System.out.println("Selected Batch: " + selectedBatch);
        // You can now query the database to retrieve members for the selected batch
        // and update the TableView accordingly. Here, I assume you have a method to
        // retrieve members based on the selected batch.
        ObservableList<MemberModel> members = getMembersByBatch(selectedBatch);

        // Set the TableView items to the new list of members
        tableView.setItems(members);
    }
    // You need to define a method to retrieve members based on the selected batch.
    // Replace Member with your actual member class.
    private ObservableList<MemberModel> getMembersByBatch(String batch) {
        // Query the database to retrieve members for the selected batch
        // and return the results as an ObservableList.
        // Implement this method according to your database schema and structure.
        // Example:
        ObservableList<MemberModel> members = FXCollections.observableArrayList();
        try {
            String selectQuery = "SELECT id,fname,dob,gend,contact FROM member WHERE batch = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setString(1, batch);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                // Create Member objects and add them to the list
                int id = resultSet.getInt("id");
                String fullName = resultSet.getString("fname");
                LocalDate dob = resultSet.getDate("dob").toLocalDate();
                String gender = resultSet.getString("gend");
                String mobile = resultSet.getString("contact");
                // Add more fields as needed

                MemberModel member = new MemberModel(id,fullName,dob,gender,mobile,batch);
                members.add(member);
            }

            preparedStatement.close();
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return members;
    }
}
