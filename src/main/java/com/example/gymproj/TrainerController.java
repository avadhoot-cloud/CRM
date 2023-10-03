package com.example.gymproj;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class TrainerController {
    @FXML
    private TextField traineridField;
    @FXML
    private ComboBox<Integer> memberIdComboBox;
    @FXML
    private ComboBox<String> genderComboBox;
    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField contactField;

    @FXML
    private DatePicker joiningDateDatePicker;

    @FXML
    private TextField salaryField;

    @FXML
    private TextField shiftField;
    @FXML
    private Button addButton;
    @FXML
            private Button clearButton;
    @FXML
            private Button updateButton;
    @FXML
            private Button deleteButton;

    Connection connection;

    @FXML
    private TableView<TrainerModel> trainerTableView;

    @FXML
    private TableColumn<TrainerModel, Integer> trainerIdColumn;

    @FXML
    private TableColumn<TrainerModel, String> firstNameColumn;

    @FXML
    private TableColumn<TrainerModel, String> lastNameColumn;

    @FXML
    private TableColumn<TrainerModel, String> genderColumn;

    @FXML
    private TableColumn<TrainerModel, String> contactColumn;

    @FXML
    private TableColumn<TrainerModel, Date> joiningDateColumn;

    @FXML
    private TableColumn<TrainerModel, Double> salaryColumn;

    @FXML
    private TableColumn<TrainerModel, String> shiftColumn;

    private ObservableList<TrainerModel> trainerList = FXCollections.observableArrayList();


    @FXML
    private void initialize() {
        // Add the new "trainer" column before the "frame" column
      //  trainerTableView.getColumns().add(1, trainerIdColumn);
        // Populate the gender ComboBox
        genderComboBox.getItems().addAll("Male", "Female");
        // Initialize the member ID ComboBox
        populateMemberIdComboBox();
        // Add a listener to detect row selection changes
        trainerTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            populateFormFields(newValue);
        });
        // Call the method to populate trainer records in the TableView
        populateTrainerRecords();
        // Initialize the TableView and bind columns to Trainer properties
        trainerTableView.setItems(trainerList);
        trainerIdColumn.setCellValueFactory(new PropertyValueFactory<>("trainerId"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
        contactColumn.setCellValueFactory(new PropertyValueFactory<>("contact"));
        joiningDateColumn.setCellValueFactory(new PropertyValueFactory<>("joiningDate"));
        salaryColumn.setCellValueFactory(new PropertyValueFactory<>("salary"));
        shiftColumn.setCellValueFactory(new PropertyValueFactory<>("shift"));

        try {
            connection = DbConnect.getConnection();
            System.out.println("connection successful");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void populateMemberIdComboBox() {
        // Retrieve existing member IDs from the members table and populate the ComboBox
        // You can use a SQL query to fetch the member IDs
        List<Integer> memberIds = getExistingMemberIds(); // Replace with your SQL query
        memberIdComboBox.getItems().addAll(memberIds);
    }
    private void populateTrainerRecords() {
        try {
            // Clear the existing records in the ObservableList
            trainerList.clear();

            // Fetch trainer records from the database
            connection = DbConnect.getConnection();
            String sql = "SELECT trainer_id,fname,lname,gend,contact,joining_date,salary,shift FROM trainer"; // Adjust your SQL query accordingly
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                // Create a TrainerModel object for each record and add it to the ObservableList
                TrainerModel trainer = new TrainerModel();
                trainer.setTrainerId(resultSet.getInt("trainer_id"));
               // trainer.setMemberId(resultSet.getInt("memb_id"));
                trainer.setFirstName(resultSet.getString("fname"));
                trainer.setLastName(resultSet.getString("lname"));
                trainer.setGender(resultSet.getString("gend"));
                trainer.setContact(resultSet.getString("contact"));
                trainer.setJoiningDate(resultSet.getDate("joining_date").toLocalDate());
                trainer.setSalary(resultSet.getDouble("salary"));
                trainer.setShift(resultSet.getString("shift"));

                trainerList.add(trainer);
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle any SQL errors here
        }
    }
    private void populateFormFields(TrainerModel selectedTrainer) {
        if (selectedTrainer != null) {
            traineridField.setText(String.valueOf(selectedTrainer.getTrainerId()));

            memberIdComboBox.setValue(selectedTrainer.getMemberId());
            genderComboBox.setValue(selectedTrainer.getGender());
            firstNameField.setText(selectedTrainer.getFirstName());
            lastNameField.setText(selectedTrainer.getLastName());
            contactField.setText(selectedTrainer.getContact());
            joiningDateDatePicker.setValue(selectedTrainer.getJoiningDate());
            salaryField.setText(String.valueOf(selectedTrainer.getSalary()));
            shiftField.setText(selectedTrainer.getShift());
        }
    }


    @FXML
    public void addTrainer(ActionEvent actionEvent) {
        // Retrieve values from the form
        int trainerid = Integer.parseInt(traineridField.getText());
        int memberId = memberIdComboBox.getValue(); // Selected member ID
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String gender = genderComboBox.getValue();
        String contact = contactField.getText();
       // java.sql.Date joiningDate = java.sql.Date.valueOf(joiningDateDatePicker.getValue());
        LocalDate joiningDate = joiningDateDatePicker.getValue(); // Get LocalDate

        // Convert LocalDate to java.sql.Date
        java.sql.Date sqljoiningDate = java.sql.Date.valueOf(joiningDate);

        double salary = Double.parseDouble(salaryField.getText());
        String shift = shiftField.getText();

        try {
            // Insert the data into the database
            connection = DbConnect.getConnection();
            String sql = "INSERT INTO trainer (trainer_id,memb_id, fname, lname, gend, contact, joining_date, salary, shift) " +
                    "VALUES (?,?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,trainerid);
            preparedStatement.setInt(2, memberId);
            preparedStatement.setString(3, firstName);
            preparedStatement.setString(4, lastName);
            preparedStatement.setString(5, gender);
            preparedStatement.setString(6, contact);
            preparedStatement.setDate(7, sqljoiningDate);
            preparedStatement.setDouble(8, salary);
            preparedStatement.setString(9, shift);

            preparedStatement.executeUpdate();

            // Close resources
            preparedStatement.close();
            connection.close();
            // Create a new Trainer object
            TrainerModel newTrainer = new TrainerModel();
            newTrainer.setTrainerId(trainerid);
             newTrainer.setMemberId(memberId);
            newTrainer.setFirstName(firstName);
            newTrainer.setLastName(lastName);
            newTrainer.setGender(gender);
            newTrainer.setContact(contact);
            newTrainer.setJoiningDate(joiningDate);
            newTrainer.setSalary(salary);
            newTrainer.setShift(shift);

            // Add the Trainer to the ObservableList
            trainerList.add(newTrainer);

            // Clear the form after successful insertion
           // clearFormFields();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle any SQL errors here
        }

    }

    // Method to retrieve existing member IDs from the members table
    private List<Integer> getExistingMemberIds() {
        List<Integer> memberIds = new ArrayList<>();
        try{
            connection = DbConnect.getConnection();
            String sql = "SELECT id FROM member"; // Adjust your SQL query accordingly
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                memberIds.add(resultSet.getInt("id"));
            }
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle any SQL errors here
        }
        return memberIds;
    }
    @FXML
    public void updateTrainer(ActionEvent actionEvent) {
        // Get the selected TrainerModel from the TableView
        TrainerModel selectedTrainer = trainerTableView.getSelectionModel().getSelectedItem();

        if (selectedTrainer != null) {
            // Retrieve the updated values from the form
            int memberId = memberIdComboBox.getValue(); // Selected member ID
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String gender = genderComboBox.getValue();
            String contact = contactField.getText();
            LocalDate joiningDate = joiningDateDatePicker.getValue();
            java.sql.Date sqlJoiningDate = java.sql.Date.valueOf(joiningDate);
            double salary = Double.parseDouble(salaryField.getText());
            String shift = shiftField.getText();

            try {
                // Update the data in the database
                connection = DbConnect.getConnection();
                String sql = "UPDATE trainer SET  memb_id=?, fname=?, lname=?, gend=?, contact=?, joining_date=?, salary=?, shift=? WHERE trainer_id=?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, memberId);
                preparedStatement.setString(2, firstName);
                preparedStatement.setString(3, lastName);
                preparedStatement.setString(4, gender);
                preparedStatement.setString(5, contact);
                preparedStatement.setDate(6, sqlJoiningDate);
                preparedStatement.setDouble(7, salary);
                preparedStatement.setString(8, shift);
                preparedStatement.setInt(9, selectedTrainer.getTrainerId()); // Use the ID of the selected Trainer

                preparedStatement.executeUpdate();

                // Update the TrainerModel in the ObservableList
                for (TrainerModel trainer : trainerList) {
                    if (trainer.getTrainerId() == selectedTrainer.getTrainerId()) {
                        selectedTrainer.setMemberId(memberId);
                        selectedTrainer.setFirstName(firstName);
                        selectedTrainer.setLastName(lastName);
                        selectedTrainer.setGender(gender);
                        selectedTrainer.setContact(contact);
                        selectedTrainer.setJoiningDate(joiningDate);
                        selectedTrainer.setSalary(salary);
                        selectedTrainer.setShift(shift);
                        break;
                    } }
                trainerTableView.refresh();
                // Clear the form after successful update
                  clearFormFields();

            } catch (SQLException e) {
                e.printStackTrace();
                // Handle any SQL errors here
            }
        } else {
            // Handle the case where no trainer is selected for update
            // You can show a message or provide feedback to the user
        }
    }
    @FXML
    public void deleteTrainer(ActionEvent actionEvent) {
        TrainerModel selectedTrainer = trainerTableView.getSelectionModel().getSelectedItem();

        if (selectedTrainer != null) {
            // Prompt the user for confirmation (optional)
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Deletion");
            alert.setHeaderText("Delete Trainer");
            alert.setContentText("Are you sure you want to delete this trainer?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Delete the trainer from the database

                 selectedTrainer.deleteFromDatabase();
                // Remove the trainer from the ObservableList
                trainerList.remove(selectedTrainer);

                // Clear the form fields after successful deletion (optional)
                clearFormFields();
            }
        } else {
            // Handle the case where no trainer is selected for deletion
            // You can show a message or provide feedback to the user
        }
    }

    @FXML
    public void clearFormFields() {
        // Clear the form fields
        traineridField.clear();
        memberIdComboBox.getSelectionModel().clearSelection();
        genderComboBox.getSelectionModel().clearSelection();
        firstNameField.clear();
        lastNameField.clear();
        contactField.clear();
        joiningDateDatePicker.getEditor().clear(); // Clear the date picker's text field
        salaryField.clear();
        shiftField.clear();
    }

}