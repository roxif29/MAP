package Validator;

public interface Validator<E> {
    public void validate (E element) throws ValidationException;
}