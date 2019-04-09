package view;

public enum ErrorMode {
    INVALID_DECK("selected deck is invalid"),
    USERNAME_IS_TAKEN(""),
    WRONG_PASSWORD(""),
    SYNTAX(""),
    DECKNAME_IS_TAKEN(""),
    CARD_IS_NOT_IN_COLLECTION(""),
    CARD_IS_ALREADY_IN_DECK(""),
    DECK_IS_FULL(""),
    DECK_HAS_HERO(""),
    CARD_IS_NOT_IN_DECK(""),
    HERO_IS_NOT_IN_DECK(""),
    DECK_IS_NOT_VALID(""),
    INVALID_TARGET(""),
    CAN_NOT_ATTACK(""),
    UNAVAILABLE_OPPONENT(""),
    INVALID_CARD_ID(""),
    HAVE_NOT_MANA(""),
    INVALID_CARD_NAME(""),
    CUSTOM_ERROR("");

    private String message;

    public String getMessage() {
        return message;
    }

    ErrorMode(String message) {
        this.message = message;
    }
}
