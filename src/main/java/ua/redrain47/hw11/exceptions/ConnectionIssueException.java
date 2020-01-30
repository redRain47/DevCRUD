package ua.redrain47.hw11.exceptions;

public class ConnectionIssueException extends Exception {
    public ConnectionIssueException(Throwable cause) {
        super(cause);
    }

    @Override
    public String getMessage() {
        return "Operation have not been done. There is some connection issue!";
    }
}
