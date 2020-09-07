package Repository;

import Domain.Student;

import java.util.List;

public class InFileStudentRepository extends AbstractFileRepository<String, Student> {

    public InFileStudentRepository(String fileName) {
        super(fileName);
    }


    @Override
    public Student extractEntity(List<String> attr) {

        String id=attr.get(0).split("=")[1];
        String nume=attr.get(1).split("=")[1];
        String prenume=attr.get(2).split("=")[1];
        String grupa=attr.get(3).split("=")[1];
        String email=attr.get(4).split("=")[1];
        String cadrudidactic=attr.get(5).split("=")[1];
        Student t=new Student(id,nume,prenume,grupa,email,cadrudidactic);
        return t;
    }

    @Override
    public String createStringEntity(Student s) {
        String res="";
        res+="id="+s.getId()+"|description="+s.getNume()+"|message="+s.getPrenume()
                +"|from="+s.getGrupa()+"|to="+s.getEmail()
                +"|date="+s.getCadruDidacticIndrumatorLab();
        return res;
    }


}


