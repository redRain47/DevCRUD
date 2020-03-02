package ua.redrain47.devcrud.exceptions;

public class DeletingReferencedRecordException extends RuntimeException {
    public DeletingReferencedRecordException(Throwable cause) {
        super(cause);
    }

    @Override
    public String getMessage() {
        return "Deleting record with foreign key field is forbidden!";
    }
}
