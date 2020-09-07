package events;


import Domain.Student;


public class StudentStatusEvent implements Event {
    private StudentExecutionStatusEventType type;
    private Student student;
    public StudentStatusEvent(StudentExecutionStatusEventType type, Student student) {
        this.student=student;
        this.type=type;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public StudentExecutionStatusEventType getType() {
        return type;
    }

    public void setType(StudentExecutionStatusEventType type) {
        this.type = type;
    }
}
