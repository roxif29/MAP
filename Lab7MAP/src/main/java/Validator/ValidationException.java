package Validator;

public class ValidationException extends Exception {
    public ValidationException (String string){
        super(string);
    }

    public ValidationException(){}
}
