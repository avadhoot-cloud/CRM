package com.example.gymproj;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import com.example.gymproj.DbConnect;
import java.sql.Connection;
import java.sql.PreparedStatement;
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

    DbConnect db = new DbConnect();
    Connection connection;

    @FXML
    private void initialize() {
        genderToggleGroup = new ToggleGroup();
        maleRadioButton.setToggleGroup(genderToggleGroup);
        femaleRadioButton.setToggleGroup(genderToggleGroup);

        try {
            connection = DbConnect.getConnection();
            System.out.println("connection successful");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void addMember(ActionEvent actionEvent) {
        String insertQuery = "INSERT INTO member(fname, dob, gend, contact) VALUES (?, ?, ?, ?)";
        LocalDate selectedDate = dob.getValue();
        java.sql.Date sqlDate = java.sql.Date.valueOf(selectedDate);


        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setString(1, fname.getText());

            preparedStatement.setDate(2, sqlDate);
            RadioButton selectedRadioButton = (RadioButton) genderToggleGroup.getSelectedToggle();
            String gender = selectedRadioButton.getText();
            preparedStatement.setString(3, gender);

            preparedStatement.setString(4, mobile.getText());


            preparedStatement.executeUpdate();

            // Clear the input fields after insertion
            fname.clear();

            mobile.clear();
            genderToggleGroup.selectToggle(null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
