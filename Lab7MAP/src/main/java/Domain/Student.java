package Domain;
import Domain.Entity;

public class Student extends Entity<String> {
    private String nume;
    private String prenume;
    private String grupa;
    private String email;
    private String cadruDidacticIndrumatorLab;

    public Student (String id, String nume, String prenume, String grupa, String email,
                    String cadruDidacticIndrumatorLab){
        super(id);
        this.nume = nume;
        this.prenume = prenume;
        this.grupa = grupa;
        this.email = email;
        this.cadruDidacticIndrumatorLab = cadruDidacticIndrumatorLab;
    }

    public String getID() {
        return super.getId();
    }

    public void setID(String ID) {
        super.setId(ID);
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public String getGrupa() {
        return grupa;
    }

    public void setGrupa(String grupa) {
        this.grupa = grupa;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCadruDidacticIndrumatorLab() {
        return cadruDidacticIndrumatorLab;
    }

    public void setCadruDidacticIndrumatorLab(String cadruDidacticIndrumatorLab) {
        this.cadruDidacticIndrumatorLab = cadruDidacticIndrumatorLab;
    }

    @Override
    public String toString() {
        return "Student{" +
                "ID='" + super.getId() + '\'' +
                ", nume='" + nume + '\'' +
                ", prenume='" + prenume + '\'' +
                ", grupa='" + grupa + '\'' +
                ", email='" + email + '\'' +
                ", cadruDidacticIndrumatorLab='" + cadruDidacticIndrumatorLab + '\'' +
                '}';
    }
}