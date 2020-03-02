package ua.redrain47.devcrud.exceptions;

public class SuchEntityAlreadyExistsException extends RuntimeException {
    public SuchEntityAlreadyExistsException(Throwable cause) {
        super(cause);
    }

    @Override
    public String getMessage() {
        return "Such entity already exists!";
    }
}
