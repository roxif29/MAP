package services;

import Domain.Student;
import Observer.Observable;
import Observer.Observer;
import Repository.CrudRepository;
import Validator.ValidationException;
import Validator.ValidatorStudent;
import events.StudentChangeEvent;

import java.util.ArrayList;
import java.util.List;

public class ServiceStudent implements Observable<StudentChangeEvent> {
    private CrudRepository<String, Student> repository;
    private ValidatorStudent validator;

    public ServiceStudent(CrudRepository<String , Student> repository, ValidatorStudent validator) {
        this.repository = repository;
        this.validator = validator;
    }




    public Student save(Student s) {
        Student student = new Student(s.getID(),s.getNume(),s.getPrenume(),s.getGrupa(),s.getEmail(),s.getCadruDidacticIndrumatorLab());
        try {
            this.validator.validate(student);
            this.repository.save(student);
        }
        catch (ValidationException e) {
            System.out.println(e);
        }
        return student;
    }

    public Student findOne(String id) {
        return this.repository.findOne(id);
    }

    public Student delete(String id) {
        return this.repository.delete(id);
    }

    public Iterable<Student> findAll() {
        return this.repository.findAll();
    }

    public Student update(Student s) {


        String id=s.getID();
        String nume=s.getNume();
        String prenume=s.getPrenume();
        String grupa=s.getGrupa();
        String email=s.getEmail();
        String cadruDidacticIndrumatorLab=s.getCadruDidacticIndrumatorLab();
        Student oldStudent = this.repository.findOne(id);
        if(oldStudent==null){
            System.out.println("Can't find the student");
            return new Student("","","","","","");
        }
        Student newStudent = oldStudent;
        newStudent.setNume(nume);
        newStudent.setPrenume(prenume);
        newStudent.setGrupa(grupa);
        newStudent.setEmail(email);
        newStudent.setCadruDidacticIndrumatorLab(cadruDidacticIndrumatorLab);
        try {
            this.validator.validate(newStudent);
            this.repository.update(newStudent);
        }
        catch (ValidationException e) {
            System.out.println(e);
        }
        return newStudent;
    }

    private List<Observer<StudentChangeEvent>> observers=new ArrayList<>();

    @Override
    public void addObserver(Observer<StudentChangeEvent> e) {
        observers.add(e);

    }

    @Override
    public void removeObserver(Observer<StudentChangeEvent> e) {
        //observers.remove(e);
    }

    @Override
    public void notifyObservers(StudentChangeEvent t) {
        observers.stream().forEach(x->x.update(t));
    }
}