package gui;

import domain.Grade;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import service.GradeService;

import java.net.URL;
import java.util.ResourceBundle;

public class EditGradeController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label studentNameLabel;

    @FXML
    private Label homeworkLabel;

    @FXML
    private Label teacherNameLabel;

    @FXML
    private Label valueLabel;

    @FXML
    private Label feedbackLabel;

    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;

    private GradeService gradeService;

    private Controller controller;

    private Stage dialogStage;

    private Grade grade;



    //----------------------Methods

    public void setService(GradeService gradeService,  Stage stage, Grade grade, Controller controller) {
        this.gradeService = gradeService;
        this.dialogStage=stage;
        this.grade = grade;
        this.controller = controller;
        if (null != grade) {
            setFields(grade);

        }
    }

    private void setFields(Grade grade) {
        studentNameLabel.setText(grade.getStudentName());
        homeworkLabel.setText(grade.getHomeworkDescription());
        teacherNameLabel.setText(grade.getTeacherName());
        valueLabel.setText(String.valueOf(grade.getValue()));
        feedbackLabel.setText(grade.getFeedback());

    }

    @FXML
    void handleCancelButton(ActionEvent event) {
        dialogStage.close();
    }

    @FXML
    void handleSaveButton(ActionEvent event) {
        this.gradeService.save(grade);
        //controller.updateGradesModel();
        dialogStage.close();
        MessageAlert.showMessage(null, Alert.AlertType.CONFIRMATION,"Grade added","The grade has been added!");
    }

    @FXML
    void initialize() {

    }
}