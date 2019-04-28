package view;

public enum ErrorMode {
    INVALID_DECK("selected deck is invalid"),
    CARD_ID_INVALID("card id is invalid."),
    NO_CARD_IS_SELECTED("No card is selected."),
    USERNAME_IS_TAKEN(""),
    WRONG_PASSWORD(""),
    SYNTAX(""),
    DECKNAME_IS_TAKEN(""),
    CARD_IS_NOT_IN_COLLECTION("there is no item or card with this name in collection"),
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
    NOT_IN_SHOP("there is no item or card with this name in shop"),
    NOT_ENOUGH_MONEY("not enough money"),
    HAVE_3_ITEMS("you have 3 items, you can't buy more item"),
    CUSTOM_ERROR(""),
    YOU_MUST_LOG_IN("You must log in to enter the main menu"),
    LOGIN_FAILED("log in failed"),
    INVALID_USERNAME("invalid username");

    private String message;

    public String getMessage() {
        return message;
    }

    ErrorMode(String message) {
        this.message = message;
    }
}
