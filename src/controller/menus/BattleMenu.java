package controller.menus;

import controller.request.BattleMenuRequest;
import controller.request.Request;
import view.View;

public class BattleMenu extends Menu {
    private static BattleMenu instance = new BattleMenu();

    static Menu getInstance() {
        return instance;
    }

    private BattleMenu() {

    }

    public void main() {
        showMenu();

        request = new BattleMenuRequest();

        request.getNewCommand();

        request.extractType();

        switch (request.getType()) {
            //  add cases
            case HELP:
                break;
            case SHOW_MENU:
                break;
            case EXIT:
                break;
        }
    }

    private boolean isDeckValid() {
        return true;
    }

    private void showGameInfo() {

    }

    private void showMyMinions() {

    }

    private void showOpponentMinions() {

    }

    private void showCardInfo() {

    }

    private void select() {

    }

    private void moveTo() {

    }

    private void attack() {

    }

    private void attackCombo() {

    }

    private void useSpecialPower() {

    }

    private void showHand() {

    }

    private void insertCardIn() {

    }

    private void endturn() {

    }

    private void showCollectables() {

    }

    private void showInfo() {

    }

    private void use() {

    }

    private void showNextCard() {

    }

    private void enterGraveyard() {

    }

    private void help() {

    }

    private void endGame() {

    }

    protected void showMenu() {

    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }
}

