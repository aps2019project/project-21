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
    CARD_IS_NOT_IN_DECK(""),
    HERO_IS_NOT_IN_DECK(""),
    DECK_IS_NOT_VALID("deck is not valid."),
    INVALID_TARGET(""),
    CAN_NOT_ATTACK(""),
    UNAVAILABLE_OPPONENT(""),
    INVALID_CARD_ID("No card with such an id."),
    HAVE_NOT_MANA(""),
    INVALID_CARD_NAME(""),
    NOT_IN_SHOP("there is no item or card with this name in shop"),
    NOT_ENOUGH_MONEY("not enough money"),
    HAVE_3_ITEMS("you have 3 items, you can't buy more item"),
    CUSTOM_ERROR(""),
    YOU_MUST_LOG_IN("You must login"),
    LOGIN_FAILED("log in failed"),
    INVALID_USERNAME("invalid username"),
    NO_CARD_WITH_THIS_NAME("no card with this name."),
    HAS_THREE_ITEMS("player's collection has already 3 items.\nno more items can be added."),
    BUY_SUCCESSFUL("successfully bought card | item."),
    YOU_DONT_HAVE_THIS_CARD("you don't have this card or item. sorry!"),
    SELL_SUCCESSFUL("sold the card or item successfully."),
    DECK_ALREADY_EXISTS("a deck with h this name already exits"),
    NO_SUCH_DECK("there is no deck with this name"),
    ALREADY_IN_DECK("card is already in deck."),
    DECK_IS_FULL("deck is full. you cannot add more cards."),
    DECK_HAS_HERO("deck already has a hero. you cannot add more heroes to it."),
    DECK_HAS_ITEM("deck already has a item. you cannot add more items to it."),
    DECK_IS_TOTALLY_VALID("deck is valid. good for you!"),
    DECK_CANNOT_BE_MAIN("deck is not complete. and cannot be chosen as main deck."),
    LOGIN_SUCCESSFUL("login successful."),
    ADDED_SUCCESSFULLY("added successfully"),
    HERO_IS_USED_IN_A_DECK("this hero is already in a deck"),
    DELETED_SUCCESSFULLY("deleted successfully."),
    INVALID_COMMAND("invalid command");


    private String message;

    public String getMessage() {
        return message;
    }

    ErrorMode(String message) {
        this.message = message;
    }
}
