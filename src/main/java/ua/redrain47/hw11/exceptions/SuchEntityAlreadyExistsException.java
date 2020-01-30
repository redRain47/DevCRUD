package ua.redrain47.hw11.exceptions;

public class SuchEntityAlreadyExistsException extends Exception {
    public SuchEntityAlreadyExistsException(Throwable cause) {
        super(cause);
    }

    @Override
    public String getMessage() {
        return "Such entity already exists!";
    }
}
