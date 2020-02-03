package ua.redrain47.hw11.exceptions;

public class DbConnectionIssueException extends Exception {
    public DbConnectionIssueException(Throwable cause) {
        super(cause);
    }

    @Override
    public String getMessage() {
        return "Operation failed! There is some database connection issue!";
    }
}
