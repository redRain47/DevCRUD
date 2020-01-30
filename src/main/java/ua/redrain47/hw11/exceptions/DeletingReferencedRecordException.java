package ua.redrain47.hw11.exceptions;

public class DeletingReferencedRecordException extends Exception {
    public DeletingReferencedRecordException(Throwable cause) {
        super(cause);
    }

    @Override
    public String getMessage() {
        return "Deleting record with foreign key field is forbidden!";
    }
}
