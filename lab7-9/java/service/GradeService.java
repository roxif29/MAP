package service;

import config.ApplicationContext;
import domain.Grade;
import domain.Homework;
import domain.UnivYearStructure;
import events.ChangeEventType;
import events.GradeChangeEvent;
import observer.Observable;
import observer.Observer;
import repository.Repository;
import validator.GradeValidator;
import validator.ValidationException;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class GradeService implements Observable<GradeChangeEvent> {
    private Repository<String, Grade> repository;
    private GradeValidator validator;
    private UnivYearStructure univYearStructure;
    private StudentService studentService;
    private HomeworkService homeworkService;
    private TeacherService teacherService;


    public GradeService(Repository<String, Grade> repository, GradeValidator validator, UnivYearStructure univYearStructure,
                        StudentService studentService, HomeworkService homeworkService, TeacherService teacherService) {
        this.repository = repository;
        this.validator = validator;
        this.univYearStructure = univYearStructure;
        this.studentService = studentService;
        this.homeworkService = homeworkService;
        this.teacherService = teacherService;
    }

    public boolean isTeacherLate(Homework homework){
        if (this.univYearStructure.getCurrentWeek() > homework.getDeadlineWeek() ){
            return true;
        }

        return false;
    }

    public List<Grade> allTheStudentsWithGradeAtAHomework(String idHomework) {
        List<Grade> gradeList = new ArrayList<Grade>();
        for (Grade grade:
                this.repository.findAll()) {
            gradeList.add(grade);
        }
        return gradeList
                .stream()
                .filter(x->x.getHomeworkId().equals(idHomework))
                .collect(Collectors.toList());
    }

    public List<Grade> allTheStudentsWithAGradeAtAHomeworkAtATeacher(String idHomework, String teacher){
        List<Grade> gradeList = new ArrayList<Grade>();
        for (Grade grade:
                this.repository.findAll()) {
            gradeList.add(grade);
        }

        return gradeList
                .stream()
                .filter(x->x.getHomeworkId().equals(idHomework) && x.getTeacherId().equals(teacher))
                .collect(Collectors.toList());
    }

    public List<Grade> allTheGradesAtAHomeworkFromAGivenWeek(String idHomework, String week){
        List<Grade> gradeList =(List<Grade>) this.repository.findAll();
        Predicate<Grade> gradePredicate = new Predicate<Grade>() {
            @Override
            public boolean test(Grade grade) {
                if (grade.getHomeworkId().equals(idHomework) && (String.valueOf(univYearStructure.getCurrentWeekFromLocalDateTime(grade.getLocalDateTime())).equals(week)))
                    return true;
                return false;
            }
        };
        return gradeList
                .stream()
                .filter(x-> gradePredicate.test(x))
                .collect(Collectors.toList());
    }

    public void save(String studentId, String homeworkId,String value, String teacherId) throws ValidationException {
        Grade grade = new Grade (studentId,homeworkId, Integer.parseInt(value), this.univYearStructure.getCurrentDateTime(), teacherId);

        this.validator.validate(grade);
        this.repository.save(grade);

    }

    public void update(String studentId, String homeworkId,String value, String teacherId, String feedback){
        Grade oldGrade = this.repository.findOne(studentId + " " + homeworkId);
        if (oldGrade != null){
            Grade newGrade = oldGrade;
            newGrade.setValue(Integer.parseInt(value));
            newGrade.setTeacherId(teacherId);
            newGrade.setLocalDateTime(this.univYearStructure.getCurrentDateTime());
            newGrade.setFeedback(feedback);
            this.repository.update(oldGrade, newGrade);
            notifyObservers(new GradeChangeEvent(ChangeEventType.UPDATE, newGrade));
        }

    }

    public void deleteAllFromOneId(String studentId){
        for (Grade grade :
                this.findAll()) {
            Grade gradeDeleted = null;
            if (grade.getStudentId().equals(studentId)) {
                gradeDeleted = this.repository.delete(grade.getStudentId() + " " + grade.getHomeworkId());
                notifyObservers(new GradeChangeEvent(ChangeEventType.DELETE, gradeDeleted));
            }
        }
    }



    public Grade delete (String id){
        Grade grade = this.repository.delete(id);
        notifyObservers(new GradeChangeEvent(ChangeEventType.DELETE, grade));
        return grade;
    }

    public Grade findOne(String id){//id = studentId + " " + homeworkId
        return this.repository.findOne(id);
    }

    public List<Grade> findALlByStudentId(String studentId, List<Grade> gradeList){
        return gradeList.stream()
                .filter(x->x.getStudentId().equals(studentId))
                .collect(Collectors.toList());
    }

    public List<Grade> findAllByHomeworkId(String homeworkId, List<Grade> gradeList){
        return gradeList.stream()
                .filter(x->x.getHomeworkId().contains(homeworkId))
                .collect(Collectors.toList());
    }

    public List<Grade> findAllByTeacherId(String teacherId, List<Grade> gradeList){
        return gradeList.stream()
                .filter(x->x.getTeacherId().contains(teacherId))
                .collect(Collectors.toList());
    }

    public List<Grade> findAllByValue(int value, List<Grade> gradeList){
        return gradeList.stream()
                .filter(x->x.getValue() == value)
                .collect(Collectors.toList());
    }

    public Iterable<Grade> findAll(){
        return this.repository.findAll();
    }

    public void saveToTxtFile(String studentName, String idHomework, String value, int theWeeKWhenTheStudentShowTheAssignment, int deadlineWeek, String feedback) {
        try(PrintWriter printWriter = new PrintWriter(new FileWriter(
                ApplicationContext.getPROPERTIES().getProperty("database.catalog.gradesTXT") + studentName + ".txt"
        ))){
            String line = "";
            line = "Homework: " + idHomework +
                    "Grade: " + value +
                    "The week when the student show the homework: " + theWeeKWhenTheStudentShowTheAssignment +
                    "Deadline:" + deadlineWeek +
                    "Feedback: " + feedback;
            printWriter.println(line);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void save(Grade grade){
        this.repository.save(grade);
        this.notifyObservers(new GradeChangeEvent(ChangeEventType.ADD, grade));
    }

    private List<Observer<GradeChangeEvent>> observers=new ArrayList<>();
    @Override
    public void addObserver(Observer<GradeChangeEvent> e) {
        observers.add(e);
    }

    @Override
    public void removeObserver(Observer<GradeChangeEvent> e) {
        observers.remove(e);
    }

    @Override
    public void notifyObservers(GradeChangeEvent t) {
        observers.stream().forEach(x->x.update(t));
    }
}