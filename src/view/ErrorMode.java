package view;

public enum ErrorMode {
    INVALID_DECK("selected deck is invalid");

    private String message;

    public String getMessage() {
        return message;
    }

    ErrorMode(String message) {
        this.message = message;
    }
}
