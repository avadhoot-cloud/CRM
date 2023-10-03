package com.example.gymproj;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
public class AdminController implements Initializable{

    @FXML
    Button memberButton;
    @FXML
    Button buttonShow;
    @FXML
    Button batchButton;
    @FXML
    Button trainerButton;

    @FXML
    Button attendanceButton;
    @FXML
    Button attendReportButton;

    //my bad - the freaking mouse event
    @FXML
    private void handleButtonClicks(javafx.event.ActionEvent mouseEvent) {
        if (mouseEvent.getSource() == memberButton) {
            loadStage("MemRegister.fxml");}
        else if(mouseEvent.getSource()== buttonShow) {
            loadStage("showMember.fxml");
            }
        else if(mouseEvent.getSource()== batchButton)
            loadStage("batch.fxml");
        else if(mouseEvent.getSource()== trainerButton)
            loadStage("trainer.fxml");
        else if(mouseEvent.getSource()==attendanceButton)
            loadStage("attendance.fxml");
        else if(mouseEvent.getSource()==attendReportButton)
            loadStage("AttendanceRecords.fxml");
        }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    private void loadStage(String fxml) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxml));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));

          //  stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}



