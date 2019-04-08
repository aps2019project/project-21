package View;

public enum ErrorMode {
    INVALID("invalid command");

    private String message;

    public String getMessage() {
        return message;
    }

    ErrorMode(String message) {
        this.message = message;
    }
}
