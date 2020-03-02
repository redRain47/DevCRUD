package ua.redrain47.devcrud.exceptions;

public class DbStorageException extends RuntimeException {
    public DbStorageException(Throwable cause) {
        super(cause);
    }

    @Override
    public String getMessage() {
        return "Operation failed! There is some storage issue!";
    }
}
