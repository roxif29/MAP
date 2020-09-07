import Controller.ControllerStudent;
import Domain.Student;
import Repository.CrudRepository;
import Repository.InFileStudentRepository;
import Validator.ValidatorStudent;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import services.ServiceStudent;

import java.io.IOException;


public class GUI extends Application {
    CrudRepository<String, Student> studentRepository;
    ServiceStudent serviceStudent;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        studentRepository = new InFileStudentRepository
                ("D:\\Lab7MAP\\data\\Students.txt");
        serviceStudent=new ServiceStudent(studentRepository,new ValidatorStudent());
        init1(primaryStage);
        primaryStage.setWidth(800);
        primaryStage.show();


    }

    private void init1(Stage primaryStage) throws IOException {

        FXMLLoader messageLoader = new FXMLLoader();
        messageLoader.setLocation(getClass().getResource("/views/StudentView.fxml"));
        AnchorPane messageTaskLayout = messageLoader.load();
        primaryStage.setScene(new Scene(messageTaskLayout));
        ControllerStudent controllerStudent = messageLoader.getController();
        controllerStudent.setServiceStudent(serviceStudent);


    }

}



