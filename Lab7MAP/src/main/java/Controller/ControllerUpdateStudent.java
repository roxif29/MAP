package Controller;

import Domain.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.ServiceStudent;

public class ControllerUpdateStudent {
    @FXML
    private TextField textFieldId;
    @FXML
    private TextField textFieldNume;
    @FXML
    private TextField textFieldPrenume;
    @FXML
    private TextField textFieldGrupa;
    @FXML
    private TextField textFieldEmail;
    @FXML
    private TextField textFieldCadruDidactic;
    @FXML
    private TextArea textAreaMessage;


    private ServiceStudent service;
    Stage dialogStage;
    Student student;

    public void handleSave(ActionEvent actionEvent) {
        String id=textFieldId.getText();
        String nume=textFieldNume.getText();
        String prenume=textFieldPrenume.getText();
        String grupa=textFieldGrupa.getText();
        String email=textFieldEmail.getText();
        String cadruDidacticIndrumatorLab=textFieldCadruDidactic.getText();

        Student s=new Student(id,nume,prenume,grupa,email, cadruDidacticIndrumatorLab);
        if (null == this.student)
            saveStudent(s);
        else
            updateStudent(s);

    }

    public void handleCancel(ActionEvent actionEvent) {
        dialogStage.close();
    }


    @FXML
    private void initialize() {
    }


    public void setService(ServiceStudent service,  Stage stage, Student m) {
        this.service = service;
        this.dialogStage=stage;
        this.student=m;
        if (null != m) {
            setFields(m);
            textFieldId.setEditable(false);
        }
    }

    @FXML
    public void handleSave(){
        String id=textFieldId.getText();
        String nume=textFieldNume.getText();
        String prenume=textFieldPrenume.getText();
        String grupa=textFieldGrupa.getText();
        String email=textFieldEmail.getText();
        String cadruDidactic=textFieldCadruDidactic.getText();


        Student m=new Student(id,nume,prenume,grupa,email,cadruDidactic);
        if (null == this.student)
            saveStudent(m);
        else
            updateStudent(m);

    }

    private void updateStudent(Student s)
    {
        Student r= this.service.update(s);
        if (r==null)
            MessageAlert.showMessage(null, Alert.AlertType.INFORMATION,"Modificare student","Studentul a fost modificat");
        dialogStage.close();


    }


    private void saveStudent(Student s)
    {
        Student r= this.service.save(s);
        if (r==null)
            MessageAlert.showMessage(null, Alert.AlertType.INFORMATION,"Slavare student","Studentul a fost salvat");
        dialogStage.close();


    }

    private void clearFields() {
        textFieldId.setText("");
        textFieldNume.setText("");
        textFieldPrenume.setText("");
        textFieldGrupa.setText("");
        textAreaMessage.setText("");
    }
    private void setFields(Student s)
    {
        textFieldId.setText(s.getId());
        textFieldNume.setText(s.getNume());
        textFieldPrenume.setText(s.getPrenume());
        textFieldGrupa.setText(s.getGrupa());
        textFieldEmail.setText(s.getEmail());
        textFieldCadruDidactic.setText(s.getCadruDidacticIndrumatorLab());
    }

    @FXML
    public void handleCancel(){
        dialogStage.close();
    }
}
