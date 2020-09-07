package Controller;

import Domain.Student;
import Observer.Observer;
import events.ChangeEventType;
import events.StudentChangeEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import services.ServiceStudent;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ControllerStudent implements Observer<StudentChangeEvent> {
    ServiceStudent service;
    ObservableList<Student> model = FXCollections.observableArrayList();
    @FXML
    TableView<Student> tableView;
    @FXML
    TableColumn<Student,String> tableColumnNume;
    @FXML
    TableColumn<Student,String> tableColumnPrenume;
    @FXML
    TableColumn<Student,String> tableColumnGrupa;
    @FXML
    TableColumn<Student,String> tableColumnEmail;
    @FXML
    TableColumn<Student,String> tableColumnCadruDidactic;

    private Stage primaryStage;
    private Scene studentScene;

    public void setServiceStudent(ServiceStudent serviceStudente) {
        service = serviceStudente;
        service.addObserver(this);
        initModel();
    }

    private void initModel() {
        Iterable<Student> messages = service.findAll();
        List<Student> StudentList = StreamSupport.stream(messages.spliterator(), false)
                .collect(Collectors.toList());
        model.setAll(StudentList);
    }
    public void update(StudentChangeEvent messageTaskChangeEvent) {

        initModel();
    }
    @FXML
    public void initialize() {
        tableColumnNume.setCellValueFactory(new PropertyValueFactory<Student, String>("nume"));
        tableColumnPrenume.setCellValueFactory(new PropertyValueFactory<Student, String>("prenume"));
        tableColumnGrupa.setCellValueFactory(new PropertyValueFactory<Student, String>("grupa"));
        tableColumnEmail.setCellValueFactory(new PropertyValueFactory<Student, String>("email"));
        tableColumnCadruDidactic.setCellValueFactory(new PropertyValueFactory<Student, String>("cadruDidacticIndrumatorLab"));
        tableView.setItems(model);
    }
    @FXML
    public void handleAddStudent(ActionEvent ev) {

        showStudentEditDialog(null);
//        try {
//
//            this.service.save(
//                    nameTextFieldFromStudentsTab.getText(),
//                    firstNameTextFieldFromStudentsTab.getText(),
//                    groupChoiceBoxFromStudentsTab.getValue(),
//                    emailTextFieldFromStudentsTab.getText(),
//                    teacherIdTextFieldFromStudentsTab.getText()
//            );
//        } catch (ValidationException e) {
//            MessageAlert.showErrorMessage(null,e.getMessage());
//        }


    }
    @FXML
    public void handleDeleteStudent(ActionEvent actionEvent) {
        Student selected = (Student) tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
                Student deleted = service.delete(selected.getID());
                if (null != deleted)
                    MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Delete", "Studentul a fost sters cu succes!");
           update(new StudentChangeEvent(ChangeEventType.DELETE,selected));
        } else MessageAlert.showErrorMessage(null, "Nu ati selectat nici un student!");
    }

    @FXML
    public void handleUpdateStudent(ActionEvent ev) throws IOException {
        Student selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            showStudentEditDialog(selected);
            update((new StudentChangeEvent(ChangeEventType.UPDATE,selected)));
        } else
//            "init1(primaryStage,);
//            primaryStage.setWidth(800);
//            primaryStage.show();"
            MessageAlert.showErrorMessage(null, "NU ati selectat nici un student");
    }
    public void showStudentEditDialog(Student student) {
        try {
            // create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/UpdateStudentView.fxml"));


            AnchorPane root = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Student");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            //dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);

            ControllerUpdateStudent controllerUpdateStudent = loader.getController();
            controllerUpdateStudent.setService(service, dialogStage, student);

            dialogStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }




//    private void init1(Stage primaryStage,Student s) throws IOException {
//        FXMLLoader messageLoader = new FXMLLoader();
//        messageLoader.setLocation(getClass().getResource("views/UpdateStudentView.fxml"));
//        AnchorPane messageTaskLayout = messageLoader.load();
//        primaryStage.setScene(new Scene(messageTaskLayout));
//        ControllerUpdateStudent controllerStudent = messageLoader.getController();
//        ControllerUpdateStudent.setService(serviceStudent,primaryStage,s);
//    }
}
