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
    INVALID_MOVE_TARGET("move target not valid."),
    CAN_NOT_ATTACK(""),
    UNAVAILABLE_OPPONENT(""),
    INVALID_CARD_ID("No card with such an id."),
    HAVE_NOT_MANA("no enough mana for this."),
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
    YOU_MUST_LOGOUT("you must logout."),
    MAIN_DECK_IS_INVALID("your main deck is not valid."),
    AIPLAYER_IS_NULL("aiPlayer is null"),
    HIS_MAIN_DECK_INVALID("his main deck is not valid. (not her, I'm sexist bro.)."),
    UNAVAILABLE_FOR_ATTACK("opponent minion is unavailable for attack."),
    CARD_SELECTED("card successfully selected."),
    ATTACKER_CANT_MOVE("attacker cannot move."),
    INVALID_CELL("cell is invalid."),
    SELECTED_CARD_NOT_MINION("selected card must be minion"),
    NOT_COMBO("selected minion is not combo."),
    NO_ATTACKER_SELECTED("you must select an attacker."),
    HASNT_SPECIAL("this card has no special power"),
    CARD_OR_COLLECTABLE_ID_INVALID("card or collectable or spell id not valid."),
    NO_COLLECTABLE_SELECTED("no collectable is selected."),
    COOLDOWN("hero is in cooldown."),
    NO_SPELL_SELECTED("you must choose a spell."),
    STUNNED("attacker is stunned."),
    DISARMED("attacker is disarmed."),
    INVALID_COMMAND("invalid command");



    private String message;

    public String getMessage() {
        return message;
    }

    ErrorMode(String message) {
        this.message = message;
    }
}
