package com.example.gymproj;



import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
    public class EditMemberController {
        private ShowMemberController showMemberController;
        private  MemberModel member;

        @FXML
        private TextField fname;
        @FXML
        private DatePicker dob;
        @FXML
        private TextField mobile;
        @FXML
        private RadioButton maleRadioButton;
        @FXML
        private RadioButton femaleRadioButton;


        public void initData(MemberModel memberData, ShowMemberController showMemberController) {
            // Populate the fields with member data
            this.showMemberController = showMemberController;
            this.member = memberData;
            fname.setText(member.getFullName());

            // Set date of birth (assuming member.getDob() returns a LocalDate)
            dob.setValue(member.getDob());

            mobile.setText(member.getMobile());

            // Set the gender radio button
            if ("Male".equals(member.getGender())) {
                maleRadioButton.setSelected(true);
            } else if ("Female".equals(member.getGender())) {
                femaleRadioButton.setSelected(true);
            }
        }

        // Add a method for saving edits when the user clicks the "SAVE" button
        @FXML
        private void saveMember(ActionEvent event) {
            // Implement saving logic here
            // Get the edited information from the form
            String editedFullName = fname.getText();
            LocalDate editedDob = dob.getValue();
            String editedMobile = mobile.getText();
            String editedGender = maleRadioButton.isSelected() ? "Male" : "Female";

            // Update the member data
            member.setFullName(editedFullName);
            member.setDob(editedDob);
            member.setMobile(editedMobile);
            member.setGender(editedGender);

            // Update the member data in the database
            updateMemberInDatabase(member);
            //call tableview refresh
            showMemberController.refreshTableView();
            // Close the Edit Member window (you can get the window and close it)
            fname.getScene().getWindow().hide();
        }


        private void updateMemberInDatabase(MemberModel member) {
            // Update the member data in the database using a SQL UPDATE statement
            String updateQuery = "UPDATE member SET fname = ?, dob = ?, gend = ?, contact = ? WHERE id = ?";

            try (Connection connection = DbConnect.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                preparedStatement.setString(1, member.getFullName());
                preparedStatement.setDate(2, java.sql.Date.valueOf(member.getDob()));
                preparedStatement.setString(3, member.getGender());
                preparedStatement.setString(4, member.getMobile());
                preparedStatement.setInt(5, member.getId());

                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }



