package gui;

import config.ApplicationContext;
import domain.SemesterStructure1;
import domain.SemesterStructure2;
import domain.UnivYearStructure;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import repository.*;
import service.GradeService;
import service.HomeworkService;
import service.StudentService;
import service.TeacherService;
import validator.GradeValidator;
import validator.HomeworkValidator;
import validator.StudentValidator;
import validator.TeacherValidator;

public class LogApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {


        SemesterStructure1 semesterStructure1 = new SemesterStructure1();
        SemesterStructure2 semesterStructure2 = new SemesterStructure2();
        UnivYearStructure univYearStructure = new UnivYearStructure(semesterStructure1, semesterStructure2);

        StudentValidator studentValidator = new StudentValidator();
        StudentFileRepository studentFileRepository = new StudentFileRepository("D:\\lab7-9\\data\\students.txt");
        StudentService studentService = new StudentService(studentFileRepository, studentValidator);

        HomeworkValidator homeworkValidator = new HomeworkValidator();
        HomeworkFileRepository homeworkFileRepository = new HomeworkFileRepository("D:\\lab7-9\\data\\homework.txt");
        HomeworkService homeworkService = new HomeworkService(homeworkFileRepository, homeworkValidator, univYearStructure);

        TeacherValidator teacherValidator = new TeacherValidator();
        //TeacherRepository teacherRepository = new TeacherRepository();
        TeacherFileRepository teacherFileRepository = new TeacherFileRepository("D:\\lab7-9\\data\\teachers.txt");
        TeacherService teacherService = new TeacherService(teacherFileRepository, teacherValidator);

        GradeValidator gradeValidator = new GradeValidator();
        GradeFileRepository gradeFileRepository = new GradeFileRepository("D:\\lab7-9\\data\\grade.txt");
        GradeService gradeService = new GradeService(gradeFileRepository, gradeValidator, univYearStructure, studentService, homeworkService, teacherService);


        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/gui.fxml"));

        AnchorPane root=loader.load();
        Controller ctrl=loader.getController();


        ctrl.setUnivYear(univYearStructure);
        ctrl.setServices(studentService, teacherService, homeworkService, gradeService);
        ctrl.updateModels();
        primaryStage.setScene(new Scene(root, 1181, 874));
        primaryStage.setTitle("Hello World");
        primaryStage.setMinHeight(874);
        primaryStage.setMinWidth(1184);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}