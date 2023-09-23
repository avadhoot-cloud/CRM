package com.example.gymproj;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import com.example.gymproj.MemberModel;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class ShowMemberController {

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

    private DbConnect db = new DbConnect();
    private Connection connection;

    @FXML
    private void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        fullNameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        dobColumn.setCellValueFactory(new PropertyValueFactory<>("dob"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
        mobileColumn.setCellValueFactory(new PropertyValueFactory<>("mobile"));

        // Create the "Edit" button column
        TableColumn<MemberModel, Void> editColumn = new TableColumn<>("Edit");
        editColumn.setPrefWidth(100);
        editColumn.setCellFactory(param -> new TableCell<MemberModel, Void>() {
            private final Button editButton = new Button("Edit");

            {
                editButton.setOnAction(event -> {
                    MemberModel member = getTableView().getItems().get(getIndex());
                    openEditMemberView(member); // Call the method to open the Edit Member view
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(editButton);
                }
            }
        });

        // Add the "Edit" button column to the TableView
        tableView.getColumns().add(editColumn);

        try {
            connection = DbConnect.getConnection();
            System.out.println("Connection successful");
            loadMemberData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Move this method outside of initialize
    private void openEditMemberView(MemberModel member) {
        try {
            // Load the Edit Member view (editMember.fxml)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("editMember.fxml"));
            Parent root = loader.load();

            // Get the controller for the Edit Member view
            EditMemberController editMemberController = loader.getController();

            // Pass the member data to the Edit Member controller
            editMemberController.initData(member, ShowMemberController.this);

            // Create a new stage (window) for the Edit Member view
            Stage editMemberStage = new Stage();
            editMemberStage.setScene(new Scene(root));
            editMemberStage.setTitle("Edit Member");

            // Show the Edit Member view
            editMemberStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void refreshTableView() {
        tableView.getItems().clear(); // Clear existing data
        loadMemberData(); // Load fresh data from the database
    }
    private void loadMemberData() {
        ArrayList<MemberModel> memberList = new ArrayList<>();

        // Query to retrieve data from the member table
        String selectQuery = "SELECT * FROM member";

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String fullName = resultSet.getString("fname");
                LocalDate dob = resultSet.getDate("dob").toLocalDate();
                String gender = resultSet.getString("gend");
                String mobile = resultSet.getString("contact");

                memberList.add(new MemberModel(id, fullName, dob, gender, mobile));
            }

            tableView.getItems().addAll(memberList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
