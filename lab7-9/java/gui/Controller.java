package gui;

import domain.*;
import events.GradeChangeEvent;
import events.HomeworkChangeEvent;
import events.StudentChangeEvent;
import events.TeacherChangeEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import observer.Observer;
import service.GradeService;
import service.HomeworkService;
import service.StudentService;
import service.TeacherService;
import validator.ValidationException;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.*;


public class Controller  {

    //to make Controller to implements more than one interfaces - DONE!


    public Observer<GradeChangeEvent> gradeChangeEventObserver(){
        return new Observer<GradeChangeEvent>() {
            @Override
            public void update(GradeChangeEvent gradeChangeEvent) {
                initGradeModel();
            }
        };
    }

    public Observer<TeacherChangeEvent> teacherChangeEventObserver(){
        return new Observer<TeacherChangeEvent>() {
            @Override
            public void update(TeacherChangeEvent teacherChangeEvent) {
                initTeacherModel();
            }
        };
    }

    public Observer<StudentChangeEvent> studentChangeEventObserver(){
        return new Observer<StudentChangeEvent>() {
            @Override
            public void update(StudentChangeEvent studentChangeEvent) {
                initStudentModel();
            }
        };
    }

    public Observer<HomeworkChangeEvent> homeworkChangeEventObserver (){
        return new Observer<HomeworkChangeEvent>() {
            @Override
            public void update(HomeworkChangeEvent homeworkChangeEvent) {
                initHomeworkModel();
            }
        };
    }

    private ObservableList<Student> studentsModel = FXCollections.observableArrayList();
    private ObservableList<Teacher> teachersModel = FXCollections.observableArrayList();
    private ObservableList<Grade> gradesModel = FXCollections.observableArrayList();
    private ObservableList<Homework> homeworksModel = FXCollections.observableArrayList();
    private ObservableList<String> groupChoiceBoxModelFromStudentsTab= FXCollections.observableArrayList();
    private ObservableList<Integer> valueChoiceBoxModelFromGradesTab= FXCollections.observableArrayList();
    private ObservableList<String> latePublicationOfGradesComboBoxModelFromGradesTab = FXCollections.observableArrayList();
    private ObservableList<String> reportsChoiceBoxModelFromGradesTab= FXCollections.observableArrayList();

    private ObservableList<Integer> startWeekChoiceBoxModelFromHomeworksTab= FXCollections.observableArrayList();
    private ObservableList<Integer> deadlineWeekChoiceBoxModelFromHomeworksTab= FXCollections.observableArrayList();
    private ObservableList<String> homeworkIdComboBoxModelFromGradesTab= FXCollections.observableArrayList();
    private ObservableList<String> studentIdComboBoxModelFromGradesTab= FXCollections.observableArrayList();

    private StudentService studentService;
    private TeacherService teacherService;
    private HomeworkService homeworkService;
    private GradeService gradeService;
    private UnivYearStructure univYearStructure;

    @FXML
    private TextArea feedbackTextAreaFromGradesTab;

    @FXML
    private ComboBox latePublicationOfGradesComboBoxFromGradesTab;

    @FXML
    private ComboBox studentIdComboBoxFromGradesTab;

    @FXML
    private TextField valueTextFieldFromGradesTab;

    @FXML
    private Label latePublicationOfGradesLabelFromGradesTab;

    @FXML
    private ComboBox homeworkIdComboBoxFromGradesTab;

    @FXML
    private TextArea reportTextAreaForFourthReport;

    @FXML
    private TextField feedbackTextFieldFromGradesTab;

    @FXML
    private ChoiceBox<Integer> latePublicationOfGradesChoiceBoxFromGradesTab;

    @FXML
    private Button saveButtonFromStudentsTab;

    @FXML
    private Button viewButtonFromStudentsTab;

    @FXML
    private ChoiceBox<String> reportChoiceBoxFromStudentsTab;

    @FXML
    private Button clearFilterFieldsFromButtonStudentsTab;

    @FXML
    private Button clearFieldsButtonFromStudetsTab;

    @FXML
    private Button saveButtonFromHomeworksTab;

    @FXML
    private Button viewButtonFromHomeworksTab;

    @FXML
    private ChoiceBox<String> reportChoiceBoxFromHomeworksTab;

    @FXML
    private Button clearFilterFieldsButtonFromHomeworksTab;

    @FXML
    private Button clearFieldsButtonFromHomeworksTab;

    @FXML
    private Button saveButtonFromTeachersTab;

    @FXML
    private Button viewButtonFromTeachersTab;

    @FXML
    private ChoiceBox<String> reportChoiceBoxFromTeachesTab;

    @FXML
    private Button clearFilterFieldsButtonFromTeachersTab;

    @FXML
    private Button clearFilterFieldsButtonFromGradesTab;

    @FXML
    private Button saveButtonFromGradesTab;

    @FXML
    private Button viewButtonFromGradesTab;

    @FXML
    private ChoiceBox<String> reportChoiceBoxFromGradesTab;

    @FXML
    private Button clearFieldsButtonFromGradesTab;

    @FXML
    private CheckBox latePublicationOfGradesCheckBoxFromGradesTab;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private MenuBar menuBar;

    @FXML
    private MenuItem closeMenuItem;

    @FXML
    private RadioMenuItem theme1;

    @FXML
    private RadioMenuItem theme2;

    @FXML
    private MenuItem aboutMenuItem;


    //------------------------TEACHER TAB ATRIBUTES
    @FXML
    private TableView<Teacher> tableFromTeachersTab;

    @FXML
    private TableColumn<Teacher, String> tableFromTeachersTabColumnId;

    @FXML
    private TableColumn<Teacher, String> tableFromTeachersTabColumnName;

    @FXML
    private TableColumn<Teacher, String> tableFromTeachersTabColumnFirstName;

    @FXML
    private TableColumn<Teacher, String> tableFromTeachersTabColumnEmail;


    @FXML
    private TextField nameTextFieldFromTeachersTab;

    @FXML
    private TextField firstNameTextFieldFromTeachersTab;

    @FXML
    private TextField emailTextFieldFromTeachersTab;

    @FXML
    private Button addButtonFromTeachersTab;

    @FXML
    private Button deleteButtonFromTeachersTab;

    @FXML
    private Button updateButtonFromTeachersTab;

    @FXML
    private TextField idFilterTextFieldFromTeachersTab;

    @FXML
    private TextField nameFilterTextFieldFromTeachersTab;

    @FXML
    private TextField firstNameFilterTextFieldFromTeachersTab;

    @FXML
    private TextField emailFilterTextFieldFromTeachersTab;


    //---------------------------HOMEWORK TAB ATRIBUTES
    @FXML
    private TableView<Homework> tableFromHomeworksTab;

    @FXML
    private TableColumn<Homework, String> tableFromHomeworksTabColumnId;

    @FXML
    private TableColumn<Homework, String> tableFromHomeworksTabColumnDescription;

    @FXML
    private TableColumn<Homework, String> tableFromHomeworksTabColumnStartWeek;

    @FXML
    private TableColumn<Homework, String> tableFromHomeworksTabColumnDeadlineWeek;


    @FXML
    private TextField descriptionTextFieldFromHomeworksTab;

    @FXML
    private Button addButtonFromHomeworksTab;

    @FXML
    private Button deleteButtonFromHomeworksTab;

    @FXML
    private Button updateButtonFromHomeworksTab;

    @FXML
    private ChoiceBox<Integer> startWeekChoiceBoxFromHomeworksTab;

    @FXML
    private ChoiceBox<Integer> deadlineWeekChoiceBoxFromHomeworksTab;

    @FXML
    private TextField idFilterTextFieldFromHomeworksTab;

    @FXML
    private TextField descriptionFilterTextFieldFromHomeworksTab;

    @FXML
    private TextField startWeekFilterTextFieldFromHomeworksTab;

    @FXML
    private TextField deadlineWeekFilterTextFieldFromHomeworksTab;


    //----------------------------------STUDENT TAB ATRIBUTION
    @FXML
    private TableView<Student> tableFromStudentsTab;

    @FXML
    private TableColumn<Student, String> tableFromStudentsTabColumnId;

    @FXML
    private TableColumn<Student, String> tableFromStudentsTabColumnName;

    @FXML
    private TableColumn<Student, String> tableFromStudentsTabColumnFirstName;

    @FXML
    private TableColumn<Student, String> tableFromStudentsTabColumnGroup;

    @FXML
    private TableColumn<Student, String> tableFromStudentsTabColumnEmail;

    @FXML
    private TableColumn<Student, String> tableFromStudentsTabColumnTeacherTrainingLab;


    @FXML
    private TextField nameTextFieldFromStudentsTab;

    @FXML
    private TextField firstNameTextFieldFromStudentsTab;

    @FXML
    private Button addButtonFromStudentsTab;

    @FXML
    private Button deleteButtonFromStudentsTab;

    @FXML
    private Button updateButtonFromStudentsTab;

    @FXML
    private ChoiceBox<String> groupChoiceBoxFromStudentsTab;

    @FXML
    private TextField emailTextFieldFromStudentsTab;

    @FXML
    private TextField teacherIdTextFieldFromStudentsTab;

    @FXML
    private TextField idFilterTextFieldFromStudentsTab;

    @FXML
    private TextField nameFilterTextFieldFromStudentsTab;

    @FXML
    private TextField firstNameFilterTextFieldFromStudentsTab;

    @FXML
    private TextField groupFilterTextFieldFromStudentsTab;

    @FXML
    private TextField emailFilterTextFieldFromStudentsTab;

    @FXML
    private TextField teacherIdFilterTextFieldFromStudentsTab;


    //------------------------------GRADES TAB ATRIBUTION
    @FXML
    private TableView<Grade> tableFromGradesTab;

    @FXML
    private TableColumn<Grade, String> tableFromGradesTabColumnStudentName;

    @FXML
    private TableColumn<Grade, String> tableFromGradesTabColumnHomeworkId;

    @FXML
    private TableColumn<Grade, String> tableFromGradesTabColumnValue;

    @FXML
    private TableColumn<Grade, String> tableFromGradesTabColumnTeacherId;


    @FXML
    private TextField studentIdTextFieldFromGradesTab;

    @FXML
    private TextField homeworkIdTextFieldFromGradesTab;

    @FXML
    private Button addButtonFromGradesTab;

    @FXML
    private Button deleteButtonFromGradesTab;

    @FXML
    private Button updateButtonFromGradesTab;

    @FXML
    private ChoiceBox<Integer> valueChoiceBoxFromGradesTab;

    @FXML
    private TextField teacherIdTextFieldFromGradesTab;

    @FXML
    private CheckBox motivationCheckBoxFromGradesTab;

    @FXML
    private TextField studentIdFilterTextFieldFromGradesTab;

    @FXML
    private TextField homeworkIdFilterTextFieldFromGradesTab;

    @FXML
    private TextField valueFilterTextFieldFromGradesTab;

    @FXML
    private TextField teacherIdFilterTextFieldFromGradesTab;


    //-----------METHOD


    @FXML
    void handleAddButtonFromTeachersTab(ActionEvent event) {
        try {
            this.teacherService.save(
                    nameTextFieldFromTeachersTab.getText(),
                    firstNameTextFieldFromTeachersTab.getText(),
                    emailTextFieldFromTeachersTab.getText()
            );
            MessageAlert.showMessage(null, Alert.AlertType.CONFIRMATION, "Teacher added", "The teacher has been added succesfully!");
        } catch (ValidationException e) {
            MessageAlert.showErrorMessage(null, e.getMessage());
        }
        this.clearFieldsFromTeachersTab();
        //this.updateTeachersModel();
        //a new window and a correct message
    }

    @FXML
    void handleDeleteButtonFromTeachersTab(ActionEvent event) {
        Teacher teacher = tableFromTeachersTab.getSelectionModel().getSelectedItem();
        if(this.teacherService.delete(teacher.getId()) != null){
            MessageAlert.showMessage(null,Alert.AlertType.CONFIRMATION, "Delete Teacher", "The teacher information has been deleted!");
            this.updateTeachersModel();
        }else{
            MessageAlert.showErrorMessage(null, "You haven't selected any teacher or the teacher doesn't exist!");
        }
    }

    @FXML
    void handleEmailFilterTextFieldFromTeachersTab(KeyEvent event) {
        this.handleIdFilterTextFieldFromTeachersTab(event);
    }

    @FXML
    void handleFirstNameFilterTextFieldFromTeachersTab(KeyEvent event) {
        this.handleIdFilterTextFieldFromTeachersTab(event);
    }


    @FXML
    void handleNameFilterTextFieldFromTeachersTab(KeyEvent event) {
        this.handleIdFilterTextFieldFromTeachersTab(event);
    }


    @FXML
    void handleIdFilterTextFieldFromTeachersTab(KeyEvent event) {
        event.consume();
        List<Teacher> initList = new ArrayList<Teacher>();

        for(Teacher teacher:
                this.teacherService.findAll()) {
            initList.add(teacher);
        }


        List<Teacher> teacherList = this.teacherService.findAllById(
                this.idFilterTextFieldFromTeachersTab.getText(),
                this.teacherService.findAllByName(
                        this.nameFilterTextFieldFromTeachersTab.getText(),
                        this.teacherService.findAllByFirstName(
                                this.firstNameFilterTextFieldFromTeachersTab.getText(),
                                this.teacherService.findAllByEmail(
                                        this.emailFilterTextFieldFromTeachersTab.getText(),
                                        initList
                                )
                        )
                )
        );
        teachersModel.setAll(teacherList);
        tableFromTeachersTab.setItems(teachersModel);
    }


    @FXML
    void handleUpdateButtonFromTeachersTab(ActionEvent event) {  // !!! try except for the case when we don't have selected items in table
        if (tableFromTeachersTab.getSelectionModel().getSelectedItem() != null){
            this.teacherService.update(
                    tableFromTeachersTab.getSelectionModel().getSelectedItem().getId(),
                    nameTextFieldFromTeachersTab.getText(),
                    firstNameTextFieldFromTeachersTab.getText(),
                    emailTextFieldFromTeachersTab.getText()
            );
            this.updateTeachersModel();
            MessageAlert.showMessage(null, Alert.AlertType.CONFIRMATION, "Update Teacher", "The teacher information has been update!");
        }
        else {
            MessageAlert.showErrorMessage(null, "You didn's selected any teacher!\n Select a teacher you want to delete \n and try again!");
        }
    }

    @FXML
    public void handleNameFilterTextFieldFromStudentsTab(KeyEvent keyEvent) {
        keyEvent.consume();
        List<Student> initList = new ArrayList<Student>();

        for(Student student:
                this.studentService.findAll()) {
            initList.add(student);
        }


        List<Student> studentList = this.studentService.findAllById(
                idFilterTextFieldFromStudentsTab.getText(),
                this.studentService.findAllByName(
                        nameFilterTextFieldFromStudentsTab.getText(),
                        this.studentService.findAllByFirstName(
                                firstNameFilterTextFieldFromStudentsTab.getText(),
                                this.studentService.findAllByGroup(
                                        groupFilterTextFieldFromStudentsTab.getText(),
                                        this.studentService.findAllByEmail(
                                                emailFilterTextFieldFromStudentsTab.getText(),
                                                this.studentService.findAllByTeacherTrainingLab(
                                                        teacherIdFilterTextFieldFromStudentsTab.getText(),
                                                        initList
                                                )
                                        )
                                )
                        )
                )

        );
        studentsModel.setAll(studentList);
        tableFromStudentsTab.setItems(studentsModel);
    }

    @FXML
    public void handleFirstNameFilterTextFieldFromStudentsTab(KeyEvent keyEvent) {
        handleNameFilterTextFieldFromStudentsTab(keyEvent);
    }

    @FXML
    public void handleTeacherIdFilterTextFieldFromStudentsTab1(KeyEvent keyEvent) {
        handleNameFilterTextFieldFromStudentsTab(keyEvent);
    }

    @FXML
    public void handleStudentIdFilterTextFieldFromGradesTab(KeyEvent keyEvent) {
        keyEvent.consume();
        List<Grade> initList = new ArrayList<Grade>();

        for (Grade grade :
                this.gradeService.findAll()) {
            initList.add(grade);
        }

        int value = 0;
        if( ! valueFilterTextFieldFromGradesTab.getText().equals("")) {
            value = Integer.parseInt(valueFilterTextFieldFromGradesTab.getText());
            List<Grade> gradeList = this.gradeService.findAllByHomeworkId(
                    homeworkIdFilterTextFieldFromGradesTab.getText(),
                    this.gradeService.findALlByStudentId(
                            studentIdFilterTextFieldFromGradesTab.getText(),
                            this.gradeService.findAllByTeacherId(
                                    teacherIdFilterTextFieldFromGradesTab.getText(),
                                    this.gradeService.findAllByValue(
                                            value,
                                            initList
                                    )
                            )
                    )
            );
            gradesModel.setAll(gradeList);
            tableFromGradesTab.setItems(gradesModel);
        }
        else {
            List<Grade> gradeList = this.gradeService.findAllByHomeworkId(
                    homeworkIdFilterTextFieldFromGradesTab.getText(),
                    this.gradeService.findALlByStudentId(
                            studentIdFilterTextFieldFromGradesTab.getText(),
                            this.gradeService.findAllByTeacherId(
                                    teacherIdFilterTextFieldFromGradesTab.getText(),
                                    initList
                            )
                    )
            );
            gradesModel.setAll(gradeList);
            tableFromGradesTab.setItems(gradesModel);
        }

    }

    @FXML
    public void handleValueFilterTextFieldFromGradesTab(KeyEvent keyEvent) {
        handleStudentIdFilterTextFieldFromGradesTab(keyEvent);
    }

    @FXML
    public void handleHomeworkIdFilterTextFieldFromGradesTab(KeyEvent keyEvent) {
        handleStudentIdFilterTextFieldFromGradesTab(keyEvent);
    }

    @FXML
    public void handleMotivationCheckBoxFromGradesTab(ActionEvent actionEvent) {
        if (motivationCheckBoxFromGradesTab.isSelected())
            this.valueTextFieldFromGradesTab.setText(String.valueOf(Integer.parseInt(this.valueTextFieldFromGradesTab.getText()) + 1));
        else
            this.valueTextFieldFromGradesTab.setText(String.valueOf(Integer.parseInt(this.valueTextFieldFromGradesTab.getText()) - 1));
    }

    @FXML
    public void handleUpdateButtonFromGradesTab(ActionEvent actionEvent) {
        if(tableFromGradesTab.getSelectionModel().getSelectedItem() != null){
            Grade grade = tableFromGradesTab.getSelectionModel().getSelectedItem();
            int value = 1;
            try{
                value = Integer.parseInt(valueTextFieldFromGradesTab.getText());
            }catch (Exception e){
                MessageAlert.showErrorMessage(null, "Invalid value!");
            }

            String automateFeedback = "";
            if (this.homeworkService.findOne(grade.getHomeworkId()).getDeadlineWeek() < this.univYearStructure.getCurrentWeek())
                automateFeedback += "The grade has been reduced with "
                        +
                        String.valueOf(10- Integer.parseInt(valueTextFieldFromGradesTab.getText()))
                        +
                        "points due to delays. ";

            grade.setFeedback(automateFeedback.concat(feedbackTextAreaFromGradesTab.getText()));

            this.gradeService.update(
                    grade.getStudentId(),
                    grade.getHomeworkId(),
                    String.valueOf(value),
                    grade.getTeacherId(),
                    automateFeedback.concat(feedbackTextAreaFromGradesTab.getText())
            );
        }
        else{
            MessageAlert.showErrorMessage(null, "You didn't select any grade!\n Select a grade and then try again!");
        }
        // updateGradesModel();
    }

    @FXML
    public void handleDeleteButtonFromGradesTab(ActionEvent actionEvent) {
        if (tableFromGradesTab.getSelectionModel().getSelectedItem() != null) {
            this.gradeService.delete(tableFromGradesTab.getSelectionModel().getSelectedItem().getStudentId() + " " + tableFromGradesTab.getSelectionModel().getSelectedItem().getHomeworkId());
            MessageAlert.showMessage(null, Alert.AlertType.CONFIRMATION, "Grade deleted", "The grade has been deleted!");
        }
        else{
            MessageAlert.showErrorMessage(null, "You didn't select any grade!\n Select the grade you want to delete\n and then try again!");
        }
        // updateGradesModel();
    }

    @FXML
    public void handleAddButtonFromGradesTab(ActionEvent actionEvent) {
        try {

            Student student = this.studentService.findOneByNameAndFirstName((String) studentIdComboBoxFromGradesTab.getValue());

            Homework homework = this.homeworkService.findOneByDescription((String) homeworkIdComboBoxFromGradesTab.getValue());

            Teacher teacher = this.teacherService.findOneByNameAndFirstName(teacherIdTextFieldFromGradesTab.getText());

            if (student == null)
                throw new ValidationException("The student doesn't exist");
            if (homework == null)
                throw new ValidationException("The homework doesn't exist");

            Grade grade = new Grade(student.getId(), homework.getId(), Integer.parseInt(valueTextFieldFromGradesTab.getText()),
                    this.univYearStructure.getCurrentDateTime(), teacher.getId());
            String automateFeedback = "";
            if (homework.getDeadlineWeek() < this.univYearStructure.getCurrentWeek())
                automateFeedback += "The grade has been reduced with "
                        +
                        String.valueOf(10- Integer.parseInt(valueTextFieldFromGradesTab.getText()))
                        +
                        "points due to delays. ";

            grade.setFeedback(automateFeedback.concat(feedbackTextAreaFromGradesTab.getText()));
            grade.setHomeworkDescription(homework.getDescription());
            grade.setStudentName(student.getName().concat(" " + student.getFirstName()));
            grade.setTeacherName(teacher.getName().concat(" " + teacher.getFirstName()));

            showMEditGradeDialog(grade);

            this.gradeService.saveToTxtFile(student.getName() + "_" + student.getFirstName(),
                    homework.getId(),
                    String.valueOf(grade.getValue()),
                    this.univYearStructure.getCurrentWeekFromLocalDateTime(LocalDateTime.now()),
                    homework.getDeadlineWeek(),
                    grade.getFeedback()
            );
            //MessageAlert.showMessage(null, Alert.AlertType.CONFIRMATION, "Grade added","The grade has been added!");
            //updateGradesModel();
        } catch (ValidationException e) {
            MessageAlert.showErrorMessage(null, e.getMessage());
        }

    }

    @FXML
    public void handleTeacherIdFilterTextFieldFromStudentsTab(KeyEvent keyEvent) {
        handleNameFilterTextFieldFromStudentsTab(keyEvent);
    }

    @FXML
    public void handleEmailFilterTextFieldFromStudentsTab(KeyEvent keyEvent) {
        handleNameFilterTextFieldFromStudentsTab(keyEvent);
    }

    @FXML
    public void handleGroupFilterTextFieldFromStudentsTab(KeyEvent keyEvent) {
        handleNameFilterTextFieldFromStudentsTab(keyEvent);
    }

    @FXML
    public void handleIdFilterTextFieldFromStudentsTab(KeyEvent keyEvent) {
        handleNameFilterTextFieldFromStudentsTab(keyEvent);
    }

    @FXML
    public void handleUpdateButtonFromStudentsTab(ActionEvent actionEvent) {
        if (tableFromStudentsTab.getSelectionModel().getSelectedItem() != null){
            this.studentService.update(tableFromStudentsTab.getSelectionModel().getSelectedItem().getId(),
                    nameTextFieldFromStudentsTab.getText(),
                    firstNameTextFieldFromStudentsTab.getText(),
                    groupChoiceBoxFromStudentsTab.getValue(),
                    emailTextFieldFromStudentsTab.getText(),
                    teacherIdTextFieldFromStudentsTab.getText());
        }
        else {
            MessageAlert.showErrorMessage(null, "You didn't select any student! \n Select the student you want to update\n and try again!");
        }
    }

    @FXML
    public void handleDeleteButtonFromStudentsTab(ActionEvent actionEvent) {
        if (tableFromStudentsTab.getSelectionModel().getSelectedItem() != null){
            Student student = tableFromStudentsTab.getSelectionModel().getSelectedItem();
            this.studentService.delete(student.getId());
            MessageAlert.showMessage(null, Alert.AlertType.CONFIRMATION, "The student has been deleted!","The student with the id ".concat(student.getId()).concat( "  has been deleted!"));
            this.gradeService.deleteAllFromOneId(student.getId());
            //updateGradesModel();
            // updateStudentsModel();
        }
        else{
            MessageAlert.showErrorMessage(null, "You didn't selected any student! \nSelect the student you want to delete \nand try again!");
        }
    }

    @FXML
    public void handleAddButtonFromStudentsTab(ActionEvent actionEvent) {
        try {
            this.studentService.save(
                    nameTextFieldFromStudentsTab.getText(),
                    firstNameTextFieldFromStudentsTab.getText(),
                    groupChoiceBoxFromStudentsTab.getValue(),
                    emailTextFieldFromStudentsTab.getText(),
                    teacherIdTextFieldFromStudentsTab.getText()
            );
        } catch (ValidationException e) {
            MessageAlert.showErrorMessage(null,e.getMessage());
        }

        //updateStudentsModel();
    }

    @FXML
    public void handleDeadlineWeekFilterTextFieldFromHomeworksTab(KeyEvent keyEvent) {
        handleIdFilterTextFieldFromHomeworksTab(keyEvent);
    }

    @FXML
    public void handleStartWeekFilterTextFieldFromHomeworksTab(KeyEvent keyEvent) {
        handleIdFilterTextFieldFromHomeworksTab(keyEvent);
    }

    @FXML
    public void handleDescriptionFilterTextFieldFromHomeworksTab(KeyEvent keyEvent) {
        handleIdFilterTextFieldFromHomeworksTab(keyEvent);
    }

    @FXML
    public void handleIdFilterTextFieldFromHomeworksTab(KeyEvent keyEvent) {
        keyEvent.consume();
        List<Homework> initList = new ArrayList<Homework>();
        List<Homework> homeworkList = null;
        for (Homework homework:
                this.homeworkService.findAll()
        ) {
            initList.add(homework);
        }
        if (startWeekFilterTextFieldFromHomeworksTab.getText().equals("")){
            if (deadlineWeekFilterTextFieldFromHomeworksTab.getText().equals("")){
                homeworkList = this.homeworkService.findAllById(
                        idFilterTextFieldFromHomeworksTab.getText(),
                        this.homeworkService.findAllByDescription(
                                descriptionFilterTextFieldFromHomeworksTab.getText(),
                                initList
                        )
                );
                homeworksModel.setAll(homeworkList);
                tableFromHomeworksTab.setItems(homeworksModel);
            }
            else{
                int deadlineWeek = Integer.parseInt(deadlineWeekFilterTextFieldFromHomeworksTab.getText());
                homeworkList = this.homeworkService.findAllByDeadlineWeek(
                        deadlineWeek,
                        this.homeworkService.findAllById(
                                idFilterTextFieldFromHomeworksTab.getText(),
                                this.homeworkService.findAllByDescription(
                                        descriptionFilterTextFieldFromHomeworksTab.getText(),
                                        initList
                                )
                        )
                );

                homeworksModel.setAll(homeworkList);
                tableFromHomeworksTab.setItems(homeworksModel);

            }
        }

        if (deadlineWeekFilterTextFieldFromHomeworksTab.equals("")){
            if(startWeekFilterTextFieldFromHomeworksTab.getText().equals("")){
                homeworkList = this.homeworkService.findAllById(
                        idFilterTextFieldFromHomeworksTab.getText(),
                        this.homeworkService.findAllByDescription(
                                descriptionFilterTextFieldFromHomeworksTab.getText(),
                                initList
                        )
                );
                homeworksModel.setAll(homeworkList);
                tableFromHomeworksTab.setItems(homeworksModel);
            }
            else{
                int startWeek = Integer.parseInt(startWeekFilterTextFieldFromHomeworksTab.getText());
                homeworkList = this.homeworkService.findAllByStartWeek(
                        startWeek,
                        this.homeworkService.findAllById(
                                idFilterTextFieldFromHomeworksTab.getText(),
                                this.homeworkService.findAllByDescription(
                                        descriptionFilterTextFieldFromHomeworksTab.getText(),
                                        initList
                                ))
                );
                homeworksModel.setAll(homeworkList);
                tableFromHomeworksTab.setItems(homeworksModel);
            }
        }
        if ((! startWeekFilterTextFieldFromHomeworksTab.getText().equals(""))
                && (! deadlineWeekFilterTextFieldFromHomeworksTab.getText().equals("")) ){
            int deadlineWeek = Integer.parseInt(startWeekFilterTextFieldFromHomeworksTab.getText());
            int startWeek = Integer.parseInt(startWeekFilterTextFieldFromHomeworksTab.getText());
            homeworkList = this.homeworkService.findAllByDeadlineWeek(
                    deadlineWeek,
                    this.homeworkService.findAllByStartWeek(
                            startWeek,
                            this.homeworkService.findAllById(
                                    idFilterTextFieldFromHomeworksTab.getText(),
                                    this.homeworkService.findAllByDescription(
                                            descriptionFilterTextFieldFromHomeworksTab.getText(),
                                            initList
                                    ))
                    )
            );

        }
        homeworksModel.setAll(homeworkList);
        tableFromHomeworksTab.setItems(homeworksModel);


    }

    @FXML
    public void handleAddButtonFromHomeworksTab(ActionEvent actionEvent) {
        try {
            this.homeworkService.save(descriptionTextFieldFromHomeworksTab.getText(),
                    String.valueOf(deadlineWeekChoiceBoxFromHomeworksTab.getValue())
            );
            MessageAlert.showMessage(null, Alert.AlertType.CONFIRMATION, "Homework added!","The homework has been added!");
        } catch (ValidationException e) {
            MessageAlert.showErrorMessage(null,e.getMessage());
        }
        //updateHomeworksModel();
    }

    @FXML
    public void handleDeleteButtonFromHomeworksTab(ActionEvent actionEvent) {
        if(tableFromHomeworksTab.getSelectionModel().getSelectedItem() != null){
            this.homeworkService.delete(tableFromHomeworksTab.getSelectionModel().getSelectedItem().getId());
            MessageAlert.showMessage(null, Alert.AlertType.CONFIRMATION, "Homework deleted!","You deleted the homework with id ".concat(tableFromHomeworksTab.getSelectionModel().getSelectedItem().getId()));
        }
        else{
            MessageAlert.showErrorMessage(null, "You didn't selected any homework!\n Select a homework and \n try again!");
        }
        //updateHomeworksModel();
    }

    @FXML
    public void handleUpdateButtonFromHomeworksTab(ActionEvent actionEvent) {
        if(tableFromHomeworksTab.getSelectionModel().getSelectedItem() != null){
            try {
                this.homeworkService.update(
                        tableFromHomeworksTab.getSelectionModel().getSelectedItem().getId(),
                        descriptionTextFieldFromHomeworksTab.getText(),
                        String.valueOf(deadlineWeekChoiceBoxFromHomeworksTab.getValue())
                );
                MessageAlert.showMessage(null, Alert.AlertType.CONFIRMATION, "Homework updated!", "The homework has been updated!");
            } catch (ValidationException e) {
                MessageAlert.showErrorMessage(null, e.getMessage());
            }

        }
        else{
            MessageAlert.showErrorMessage(null, "You didn't select any homework!\n Select a homework you want to update\n and try again!");
        }
        //updateHomeworksModel();
    }


    @FXML
    public void clearFilterFieldsFromTeachersTab(){
        idFilterTextFieldFromTeachersTab.setText("");
        nameFilterTextFieldFromTeachersTab.setText("");
        firstNameFilterTextFieldFromTeachersTab.setText("");
        emailFilterTextFieldFromTeachersTab.setText("");
        this.updateTeachersModel();
    }

    @FXML
    public void clearFilterFieldsFromStudentsTab(){
        idFilterTextFieldFromStudentsTab.setText("");
        nameFilterTextFieldFromStudentsTab.setText("");
        firstNameFilterTextFieldFromStudentsTab.setText("");
        groupFilterTextFieldFromStudentsTab.setText("");
        emailFilterTextFieldFromStudentsTab.setText("");
        teacherIdTextFieldFromStudentsTab.setText("");
        this.updateStudentsModel();
    }

    @FXML
    public void clearFilterFieldsFromHomeworksTab(){
        idFilterTextFieldFromHomeworksTab.setText("");
        descriptionFilterTextFieldFromHomeworksTab.setText("");
        startWeekFilterTextFieldFromHomeworksTab.setText("");
        deadlineWeekFilterTextFieldFromHomeworksTab.setText("");
        this.updateHomeworksModel();
    }

    @FXML
    public void clearFilterFieldsFromGradesTab(){
        studentIdFilterTextFieldFromGradesTab.setText("");
        homeworkIdFilterTextFieldFromGradesTab.setText("");
        valueFilterTextFieldFromGradesTab.setText("");
        teacherIdFilterTextFieldFromGradesTab.setText("");
        this.updateGradesModel();
    }

    @FXML
    public void clearFieldsFromTeachersTab(){
        nameTextFieldFromTeachersTab.setText("");
        firstNameTextFieldFromTeachersTab.setText("");
        emailTextFieldFromTeachersTab.setText("");
    }

    @FXML
    public void clearFieldsFromStudentsTab(){
        nameTextFieldFromStudentsTab.setText("");
        firstNameTextFieldFromStudentsTab.setText("");
        emailTextFieldFromStudentsTab.setText("");
        groupChoiceBoxFromStudentsTab.getSelectionModel().clearSelection();
        teacherIdTextFieldFromStudentsTab.setText("");
    }

    @FXML
    public void clearFieldsFromHomeworksTab(){
        descriptionTextFieldFromHomeworksTab.setText("");
        deadlineWeekChoiceBoxFromHomeworksTab.getSelectionModel().clearSelection();
        deadlineWeekChoiceBoxFromHomeworksTab.setValue(this.univYearStructure.getCurrentWeek());
        startWeekChoiceBoxFromHomeworksTab.getSelectionModel().clearSelection();
        startWeekChoiceBoxFromHomeworksTab.setValue(this.univYearStructure.getCurrentWeek());
    }

    @FXML
    public void clearFieldsFromGradesTab(){
        int size = studentIdComboBoxModelFromGradesTab.size();
        if (size == 0)
            size--;
        studentIdComboBoxModelFromGradesTab.remove(0,size);
        studentIdComboBoxFromGradesTab.setValue("");
        initHomeworkIdComboBoxFromGradesTab();
        motivationCheckBoxFromGradesTab.setSelected(false);
        latePublicationOfGradesCheckBoxFromGradesTab.setSelected(false);
        teacherIdTextFieldFromGradesTab.setText("");
        valueTextFieldFromGradesTab.setText("");
        feedbackTextAreaFromGradesTab.setText("");
    }



    @FXML
    public void updateStudentsModel() {
        List<Student> studentsList = new ArrayList<>();
        studentService.findAll().forEach(studentsList::add);
        studentsModel.setAll(studentsList);
        tableFromStudentsTab.setItems(studentsModel);
    }


    @FXML
    public void updateTeachersModel() {
        List<Teacher> teachersList = new ArrayList<>();
        for(Teacher teacher:
                teacherService.findAll()){
            teachersList.add(teacher);
        }

        teachersModel.setAll(teachersList);
        //tableFromTeachersTab.setItems(teachersModel);
    }

    @FXML
    public void updateGradesModel() {
        List<Grade> gradeList = new ArrayList<>();
        gradeService.findAll().forEach(gradeList::add);
        gradesModel.setAll(gradeList);
        tableFromGradesTab.setItems(gradesModel);
    }

    @FXML
    public void updateHomeworksModel() {
        List<Homework> homeworksList = new ArrayList<>();
        homeworkService.findAll().forEach(homeworksList::add);
        homeworksModel.setAll(homeworksList);
        tableFromHomeworksTab.setItems(homeworksModel);
    }

    @FXML
    void initialize() {
//        this.studentService.addObserver(this); --not here, the services didn't set yet


        tableFromTeachersTabColumnId.setCellValueFactory(new PropertyValueFactory<Teacher, String>("id"));
        tableFromTeachersTabColumnName.setCellValueFactory(new PropertyValueFactory<Teacher, String>("name"));
        tableFromTeachersTabColumnFirstName.setCellValueFactory(new PropertyValueFactory<Teacher, String>("firstName"));
        tableFromTeachersTabColumnEmail.setCellValueFactory(new PropertyValueFactory<Teacher, String>("email"));

        tableFromStudentsTabColumnId.setCellValueFactory(new PropertyValueFactory<Student, String>("id"));
        tableFromStudentsTabColumnName.setCellValueFactory(new PropertyValueFactory<Student, String>("name"));
        tableFromStudentsTabColumnFirstName.setCellValueFactory(new PropertyValueFactory<Student, String>("firstName"));
        tableFromStudentsTabColumnGroup.setCellValueFactory(new PropertyValueFactory<Student, String>("group"));
        tableFromStudentsTabColumnEmail.setCellValueFactory(new PropertyValueFactory<Student, String>("email"));
        tableFromStudentsTabColumnTeacherTrainingLab.setCellValueFactory(new PropertyValueFactory<Student, String>("teacherTrainingLab"));

        tableFromHomeworksTabColumnId.setCellValueFactory(new PropertyValueFactory<Homework, String>("id"));
        tableFromHomeworksTabColumnStartWeek.setCellValueFactory(new PropertyValueFactory<Homework, String>("startWeek"));
        tableFromHomeworksTabColumnDeadlineWeek.setCellValueFactory(new PropertyValueFactory<Homework, String>("deadlineWeek"));
        tableFromHomeworksTabColumnDescription.setCellValueFactory(new PropertyValueFactory<Homework, String>("description"));

        tableFromGradesTabColumnHomeworkId.setCellValueFactory(new PropertyValueFactory<Grade, String>("homeworkDescription"));
        tableFromGradesTabColumnStudentName.setCellValueFactory(new PropertyValueFactory<Grade, String>("studentName"));
        tableFromGradesTabColumnTeacherId.setCellValueFactory(new PropertyValueFactory<Grade, String>("teacherName"));
        tableFromGradesTabColumnValue.setCellValueFactory(new PropertyValueFactory<Grade, String>("value"));

        tableFromTeachersTab.setItems(this.teachersModel);
        tableFromStudentsTab.setItems(this.studentsModel);
        tableFromGradesTab.setItems(this.gradesModel);
        tableFromHomeworksTab.setItems(this.homeworksModel);


        /*
         *       the services didn't set yet so it will crash if I update models here
         *
         * */
        //updateModels();


        groupChoiceBoxModelFromStudentsTab.addAll("213", "223", "233", "214", "224", "234", "215", "225", "235");
        groupChoiceBoxFromStudentsTab.setItems(groupChoiceBoxModelFromStudentsTab);


//        valueChoiceBoxModelFromGradesTab.addAll(1,2,3,4,5,6,7,8,9,10);
//        valueChoiceBoxFromGradesTab.setItems(valueChoiceBoxModelFromGradesTab);

        latePublicationOfGradesComboBoxModelFromGradesTab.addAll("1","2","3","4","5","6","7","8","9","10","11","12","13","14");
        latePublicationOfGradesComboBoxFromGradesTab.setItems(latePublicationOfGradesComboBoxModelFromGradesTab);

        latePublicationOfGradesComboBoxFromGradesTab.setDisable(true);
        latePublicationOfGradesLabelFromGradesTab.setDisable(true);

        //to set the value 10 in the text field for value
        //        valueChoiceBoxFromGradesTab.setValue(10);


        startWeekChoiceBoxModelFromHomeworksTab.addAll(1,2,3,4,5,6,7,8,9,10,11,12,13,14);
        startWeekChoiceBoxFromHomeworksTab.setItems(startWeekChoiceBoxModelFromHomeworksTab);
        deadlineWeekChoiceBoxModelFromHomeworksTab.addAll(1,2,3,4,5,6,7,8,9,10,11,12,13,14);
        deadlineWeekChoiceBoxFromHomeworksTab.setItems(deadlineWeekChoiceBoxModelFromHomeworksTab);

        reportsChoiceBoxModelFromGradesTab.addAll(
                "1.Grade laboratory for each student",
                "2.Hardest homework",
                "3.Students who can join the exam",
                "4.The students who have show the homework in time"
        );

        reportChoiceBoxFromGradesTab.setItems(reportsChoiceBoxModelFromGradesTab);

//        assignmentComboBox.setItems(modelAssignments);
//        assignmentComboBox.valueProperty().addListener(new ChangeListener<Assignment>() {
//            @Override
//            public void changed(ObservableValue<? extends Assignment> observable, Assignment oldValue, Assignment newValue) {
//                handleChangeAssignment();
//            }
//        });

        studentIdComboBoxFromGradesTab.setEditable(true);
    }

    public void updateModels(){
        updateGradesModel();
        updateHomeworksModel();
        updateStudentsModel();
        updateTeachersModel();
    }


    public void setServices(StudentService studentService, TeacherService teacherService ,
                            HomeworkService homeworkService, GradeService gradeService){
        this.studentService = studentService;
        this.teacherService = teacherService;
        this.gradeService = gradeService;
        this.homeworkService = homeworkService;
        this.gradeService.addObserver(gradeChangeEventObserver());
        this.studentService.addObserver(studentChangeEventObserver());
        this.homeworkService.addObserver(homeworkChangeEventObserver());
        this.teacherService.addObserver(teacherChangeEventObserver());
        initHomeworkIdComboBoxFromGradesTab();
    }

    private void initHomeworkIdComboBoxFromGradesTab() {
        String homeworkDescription = "";
        String descriptionForCurrentHomework = "";
        for (Homework homework :
                this.homeworkService.findAll()) {
            homeworkDescription += homework.getDescription() + ",";
            if (homework.getDeadlineWeek() == this.univYearStructure.getCurrentWeek())
                descriptionForCurrentHomework = homework.getDescription();
        }
        homeworkIdComboBoxModelFromGradesTab.remove(0,homeworkIdComboBoxModelFromGradesTab.size());
        homeworkIdComboBoxModelFromGradesTab.addAll(homeworkDescription.split(","));
        homeworkIdComboBoxFromGradesTab.setItems(homeworkIdComboBoxModelFromGradesTab);
        homeworkIdComboBoxFromGradesTab.setValue(descriptionForCurrentHomework);
        valueTextFieldFromGradesTab.setText("10");

    }

    @FXML
    public void handleTableViewSelectionFromTeachersTab(MouseEvent mouseEvent) {
        mouseEvent.consume();
        Teacher teacher = tableFromTeachersTab.getSelectionModel().getSelectedItem();
        if (teacher != null){
            clearFieldsFromTeachersTab();
            nameTextFieldFromTeachersTab.setText(teacher.getName());
            firstNameTextFieldFromTeachersTab.setText(teacher.getFirstName());
            emailTextFieldFromTeachersTab.setText(teacher.getEmail());
        }

    }

    @FXML
    public void handleTableViewSelectionFromStudentsTab(MouseEvent mouseEvent) {
        mouseEvent.consume();
        Student student = tableFromStudentsTab.getSelectionModel().getSelectedItem();
        if ( student != null){
            clearFieldsFromStudentsTab();
            nameTextFieldFromStudentsTab.setText(student.getName());
            firstNameTextFieldFromStudentsTab.setText(student.getFirstName());
            emailTextFieldFromStudentsTab.setText(student.getEmail());
            teacherIdTextFieldFromStudentsTab.setText(student.getTeacherTrainingLab());
            groupChoiceBoxFromStudentsTab.setValue(student.getGroup());
        }

    }

    @FXML
    public void handleTableViewSelectionFromGradesTab(MouseEvent mouseEvent) {
        mouseEvent.consume();
        Grade grade = tableFromGradesTab.getSelectionModel().getSelectedItem();
        if ( grade != null){
            clearFieldsFromGradesTab();
            Student student = this.studentService.findOne(grade.getStudentId());

//            studentIdComboBoxFromGradesTab.getEditor().textProperty().addListener((obs, oldText, newText) -> {
//                studentIdComboBoxFromGradesTab.setValue(newText);
//            });

            //studentIdComboBoxFromGradesTab.setEditable(false);
            int size = studentIdComboBoxModelFromGradesTab.size();
            if (size >0 )
                size--;
            studentIdComboBoxModelFromGradesTab.remove(0, size);
            studentIdComboBoxFromGradesTab.getEditor().setText((student.getName() + " " + student.getFirstName()));
            studentIdComboBoxFromGradesTab.hide();
            //studentIdComboBoxFromGradesTab.setEditable(true);
            Homework homework = this.homeworkService.findOne(grade.getHomeworkId());
            homeworkIdComboBoxFromGradesTab.setValue(homework.getDescription());
            Teacher teacher = this.teacherService.findOne(grade.getTeacherId());
            teacherIdTextFieldFromGradesTab.setText(teacher.getName() + " " + teacher.getFirstName() );
            feedbackTextAreaFromGradesTab.setText(grade.getFeedback());
        }
    }

    @FXML
    public void handleTableViewSelectionFromHomeworksTab(MouseEvent mouseEvent) {
        mouseEvent.consume();
        Homework homework = this.tableFromHomeworksTab.getSelectionModel().getSelectedItem();
        if ( homework != null){
            clearFieldsFromHomeworksTab();
            descriptionTextFieldFromHomeworksTab.setText(homework.getDescription());
            startWeekChoiceBoxFromHomeworksTab.setValue(homework.getStartWeek());
            deadlineWeekChoiceBoxFromHomeworksTab.setValue(homework.getDeadlineWeek());
        }
    }

    @FXML
    public void handleLatePublicationOfGradesCheckBoxFromGradesTab(ActionEvent actionEvent) {
        actionEvent.consume();
        if(!latePublicationOfGradesCheckBoxFromGradesTab.isSelected()){
            latePublicationOfGradesComboBoxFromGradesTab.setDisable(true);
            latePublicationOfGradesLabelFromGradesTab.setDisable(true);


        }
        else {
            latePublicationOfGradesComboBoxFromGradesTab.setDisable(false);
            latePublicationOfGradesLabelFromGradesTab.setDisable(false);

        }

    }

    public void setUnivYear(UnivYearStructure univYearStructure) {
        this.univYearStructure = univYearStructure;
        startWeekChoiceBoxFromHomeworksTab.setValue(this.univYearStructure.getCurrentWeek());
        deadlineWeekChoiceBoxFromHomeworksTab.setValue(this.univYearStructure.getCurrentWeek());
    }

    @FXML
    void handleAboutMenuItem(ActionEvent event) {

    }
    @FXML
    void handleCloseMenuItem(ActionEvent event) {

    }

    @FXML
    void handleTheme2(ActionEvent event) {

    }

    @FXML
    public void handleViewButtonFromTeachersTab(ActionEvent actionEvent) {
    }

    @FXML
    public void handleSaveButtonFromTeachersTab(ActionEvent actionEvent) {

    }

    @FXML
    public void handleViewButtonFromHomeworksTab(ActionEvent actionEvent) {
    }

    @FXML
    public void handleSaveButtonFromHomeworksTab(ActionEvent actionEvent) {
    }

    @FXML
    public void handleViewButtonFromStudentsTab(ActionEvent actionEvent) {
    }

    @FXML
    public void handleSaveButtonFromStudentsTab(ActionEvent actionEvent) {
    }

    @FXML
    public void handleSaveButtonFromGradesTab(ActionEvent actionEvent) {

    }

    private void handleFourthReport() {
        //"4.The students who have show the homework in time"
        List<Grade> gradesList = new ArrayList<>();
        List<Homework> homeworksList = new ArrayList<>();
        Map<Student, Integer> noOfHomeworksShowed = new HashMap<>();

        this.gradeService.findAll().forEach(gradesList::add);
        this.homeworkService.findAll().forEach(homeworksList::add);
        this.studentService.findAll().forEach(x->noOfHomeworksShowed.put(x,0));

        for (Grade grade :
                gradesList) {
            if (
                    this.homeworkService.findOne(grade.getHomeworkId()).getDeadlineWeek()
                            >=
                            this.univYearStructure.getCurrentWeekFromLocalDateTime(grade.getLocalDateTime())
            )
                noOfHomeworksShowed.replace(
                        this.studentService.findOne(grade.getStudentId()),
                        noOfHomeworksShowed.get(this.studentService.findOne(grade.getStudentId())) + 1
                );
        }
        String information = "";
        for (Student student :
                this.studentService.findAll()) {
            if (noOfHomeworksShowed.get(student) == homeworksList.size()) {
                information += student.getName().concat("" + student.getFirstName()) + " showed all the homeworks in time\n";
            }
        }

        if(information.length() == 0)
            information += "There is no student with all the homeworks showed.\n";
        showMReportsDialog(information);

    }

    private void handleThirdReport() {
        //"3.Students who can join the exam", has the average grade > 5

        List<Grade> gradesList = new ArrayList<>();
        Map<Student, Double> averageGrades = new HashMap<>();

        this.gradeService.findAll().forEach(gradesList::add);
        this.studentService.findAll().forEach(x->averageGrades.put(x,(double)0));

        gradesList.stream().forEach(x->{
            Student student = this.studentService.findOne(x.getStudentId());
            Homework homework = this.homeworkService.findOne(x.getHomeworkId());
            averageGrades.replace(student, averageGrades.get(student) + (double)(x.getValue()*(homework.getDeadlineWeek()-homework.getStartWeek())));
        });

        double weight = 0;

        for (Homework homework :
                this.homeworkService.findAll()) {
            weight += homework.getDeadlineWeek() - homework.getStartWeek();
        }

        String information = "";

        for (Student student :
                this.studentService.findAll()){
            if(averageGrades.get(student)/weight >= 5)
                information += student.getName().concat( " " + student.getFirstName()) + " can join the exam\n";
            else
                information += student.getName().concat( " " + student.getFirstName()) + " can't join the exam\n";
        }

        showMReportsDialog(information);



    }


    private void handleSecondReport() {
        //"2.Hardest homework, has the average grade the lowest",
        List<Grade> gradesList = new ArrayList<>();
        Map<Homework, Double> averageHomeworkGrades = new HashMap<>();

        this.gradeService.findAll().forEach(gradesList::add);
        this.homeworkService.findAll().forEach(homework -> {
            averageHomeworkGrades.put(homework, (double)0);
        });

        List<Student> studentList = new ArrayList<>();
        this.studentService.findAll().forEach(studentList::add);

        gradesList.stream().forEach(x->{
            Homework homework = this.homeworkService.findOne(x.getHomeworkId());
            averageHomeworkGrades.replace(homework, averageHomeworkGrades.get(homework) + (double)(x.getValue()));

        });

        Homework homeworkWithLowestGrade  = null;
        double averageGrade = 10;

        for (Homework homework:
                this.homeworkService.findAll()) {
            averageHomeworkGrades.put(homework,averageHomeworkGrades.get(homework) / studentList.size());

            if(averageHomeworkGrades.get(homework) /  studentList.size() < averageGrade) {
                averageGrade = averageHomeworkGrades.get(homework) /  studentList.size();
                homeworkWithLowestGrade = homework;
            }
        }

        showMReportsDialog("The homework \"" + homeworkWithLowestGrade.getDescription() + "\" has the lowest grade - > " + String.valueOf(averageGrade));





    }

    private void handleFirstReport() {
        //"1.Grade laboratory for each student",
        List<Grade> gradesList = new ArrayList<>();
        Map<Student, Double> averageGrades = new HashMap<>();

        this.gradeService.findAll().forEach(gradesList::add);
        this.studentService.findAll().forEach(x->averageGrades.put(x,(double)0));

        gradesList.stream().forEach(x->{
            Student student = this.studentService.findOne(x.getStudentId());
            Homework homework = this.homeworkService.findOne(x.getHomeworkId());
            averageGrades.replace(student, averageGrades.get(student) + (double)(x.getValue()*(homework.getDeadlineWeek()-homework.getStartWeek())));
        });

        double weight = 0;

        for (Homework homework :
                this.homeworkService.findAll()) {
            weight += homework.getDeadlineWeek() - homework.getStartWeek();
        }

        String information = "";

        for (Student student :
                this.studentService.findAll()){
            information += student.getName().concat( " " + student.getFirstName()) + " has the average grade laboratory "
                    +
                    String.valueOf(averageGrades.get(student)/weight)
                    + "\n";
        }

        showMReportsDialog(information);

    }

    @FXML
    public void handleViewButtonFromGradesTab(ActionEvent actionEvent) {
        String report = reportChoiceBoxFromGradesTab.getValue();
        if(report.contains("1"))
            handleFirstReport();
        if(report.contains("2"))
            handleSecondReport();
        if (report.contains("3"))
            handleThirdReport();
        if(report.contains("4"))
            handleFourthReport();
    }

//    @FXML
//    public void handleHomeworkIdTextFieldFromHomeworksTab(KeyEvent keyEvent) {
//        Homework homework = this.homeworkService.findOne(homeworkIdTextFieldFromGradesTab.getText());
//        if (homework != null){
//
//            valueChoiceBoxFromGradesTab.setValue(
//                    this.homeworkService.maxGrade(homework.getId(),
//                            this.univYearStructure.getCurrentWeekFromLocalDateTime(LocalDateTime.now()))
//            );
//        }
//    }


    @FXML
    public void handleHomeworkIdComboBoxFromGradesTab(ActionEvent actionEvent) {
        String homeworkDescription = (String) homeworkIdComboBoxFromGradesTab.getValue();
        Homework homework = this.homeworkService.findOneByDescription(homeworkDescription);
        if (homework != null)
            valueTextFieldFromGradesTab.setText(
                    String.valueOf(
                            this.homeworkService.maxGrade(homework.getId(),
                                    this.univYearStructure.getCurrentWeek())
                    )
            );




    }

    @FXML
    public void handleStudentIdComboBoxFromGradesTab(ActionEvent keyEvent) {
        int size = studentIdComboBoxModelFromGradesTab.size();
        if (size == 0)
            size--;
        studentIdComboBoxModelFromGradesTab.remove(0,size);
        String studentName = (String) studentIdComboBoxFromGradesTab.getValue();
        String studentsName = "";
        for (Student student :
                this.studentService.findAll()) {

            if (student.getName().concat(" " + student.getFirstName()).contains(studentName))
                if (studentsName.isEmpty())
                    studentsName += student.getName() + " " + student.getFirstName();
                else
                    studentsName += ","  + student.getName() + " " + student.getFirstName() ;
        }
        studentIdComboBoxModelFromGradesTab.addAll(studentsName.split(","));
        studentIdComboBoxFromGradesTab.setItems(studentIdComboBoxModelFromGradesTab);
        studentIdComboBoxFromGradesTab.show();
    }
    @FXML
    public void handleLatePublicationOfGradesComboBoxFromGradesTab(ActionEvent actionEvent) {
        String homeworkDescription = (String) homeworkIdComboBoxFromGradesTab.getValue();
        Homework homework = this.homeworkService.findOneByDescription(homeworkDescription);
        if (homework != null)
            valueTextFieldFromGradesTab.setText(
                    String.valueOf(
                            this.homeworkService.maxGrade(homework.getId(),
                                    Integer.parseInt((String) this.latePublicationOfGradesComboBoxFromGradesTab.getValue())
                            )
                    )
            );
    }

    public void showMEditGradeDialog(Grade grade) {
        try {
            // create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/editGrade.fxml"));

            AnchorPane root = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Preview Grade");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            //dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);

            EditGradeController editGradeController = loader.getController();
            editGradeController.setService(this.gradeService, dialogStage, grade, this);

            dialogStage.show();

        } catch (Exception e) {
            MessageAlert.showErrorMessage(null, e.getMessage());
        }
    }

    public void showMReportsDialog(String information) {
        try {
            // create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/reportsGui.fxml"));

            AnchorPane root = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Report");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            //dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);

            ReportController reportController = loader.getController();
            reportController.setInformation(information);

            dialogStage.show();

        } catch (Exception e) {
            MessageAlert.showErrorMessage(null, e.getMessage());
        }
    }

//
//    @Override
//    public void update(GradeChangeEvent gradeChangeEvent){
//        initGradeModel();
//    }



    private void initGradeModel() {
        List<Grade> gradeList = new ArrayList<>();
        this.gradeService.findAll().forEach(gradeList::add);
        gradesModel.setAll(gradeList);
    }

//    @Override
//    public void update(TeacherChangeEvent teacherChangeEvent){
//        initTeacherModel();
//    }
//


    private void initTeacherModel() {
        List<Teacher> teacherList= new ArrayList<>();
        this.teacherService.findAll().forEach(teacherList::add);
        teachersModel.setAll(teacherList);
    }

//    @Override
//    public void update(StudentChangeEvent studentChangeEvent){
//        initStudentModel();
//    }
//


    private void initStudentModel() {
        List<Student> studentList = new ArrayList<>();
        this.studentService.findAll().forEach(studentList::add);
        studentsModel.setAll(studentList);
    }
//
//    @Override
//    public void update(HomeworkChangeEvent homeworkChangeEvent){
//        initHomeworkModel();
//    }



    private void initHomeworkModel() {
        List<Homework> homeworkList = new ArrayList<>();
        this.homeworkService.findAll().forEach(homeworkList::add);
        homeworksModel.setAll(homeworkList);
    }




}


//homeworkIdTextFieldFromGradesTab in homeworkIdComboBoxFromGradesTab   -->all modified

//valueChoiceBoxFromGradesTab in valueTextFieldFromGradesTab -->all modified

//studentIdTextFieldFromGradesTab in studentIdComboBoxFromGradesTab(editable)   -->all modified

//latePublicationOfGradesChoiceBoxFromGradesTab in latePublicationOfGradesComboBoxFromGradesTab    -->all modified







