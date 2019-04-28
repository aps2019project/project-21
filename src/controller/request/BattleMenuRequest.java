package controller.request;

public class BattleMenuRequest extends Request {
    @Override
    public void extractType() {
        // set field type in parent class
    }

    private boolean gameInfoCheck() {
        return true;
    }

    private boolean showMyMinionsCheck() {
        return true;
    }

    private boolean showOppMinionsCheck() {
        return true;
    }

    private boolean showCardInfoCheck() {
        return true;
    }

    private boolean selectCheck() {
        return true;
    }

    private boolean moveToCheck() {
        return true;
    }

    private boolean attacjCheck() {
        return true;
    }

    private boolean attackComboCheck() {
        return true;
    }

    private boolean useSpecialPowerCheck() {
        return true;
    }

    private boolean showHandCheck() {
        return true;
    }

    private boolean insertCheck() {
        return true;
    }

    private boolean endTurnCheck() {
        return true;
    }

    private boolean showCollectablesCheck() {
        return true;
    }

    private boolean useCheck() {
        return true;
    }

    private boolean showNextCardCheck() {
        return true;
    }

    private boolean enterGraveyardCheck() {
        return true;
    }

    private boolean endGameCheck() {
        return true;
    }

    private boolean showCardsCheck() {
        return true;
    }
    protected void backCheck() {

    }
}
