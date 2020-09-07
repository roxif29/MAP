package Validator;

import Domain.Student;

public class ValidatorStudent implements Validator<Student> {
    @Override
    public void validate(Student element) throws ValidationException {
        String exceptie = "";

        if (element.getID() .equals( ""))
            exceptie += "Invalid ID\n";

        if (element.getNume() .equals( ""))
            exceptie += "Invalid nume\n";

        if (element.getPrenume() .equals( ""))
            exceptie += "Invalid prenume\n";

        if (!element.getGrupa() .matches("[1-9][0-9][0-9]"))
            exceptie += "Invalid grupa\n";

        if (element.getEmail() .equals( ""))
            exceptie += "Invalid email\n";

        if (element.getCadruDidacticIndrumatorLab() .equals( ""))
            exceptie += "Invalid cadruDidacticIndrumatorLab\n";

        if (exceptie.length() > 0)
            throw new ValidationException(exceptie);
    }
}
