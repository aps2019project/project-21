package controller.menu;

import controller.request.BattleMenuRequest;
import controller.request.Request;
import models.Item.Collectable;
import view.View;

public class BattleMenu {
    private View view = new View();
    private Request request;

    public void main() {
        outerLoop:
        while (true) {
            showMenu();

            request = new BattleMenuRequest();

            request.getNewCommand();

            request.checkSyntax();

            switch (request.getType()) {
                //  add cases
                case HELP:
                    break;
                case SHOW_MENU:
                    continue outerLoop;
                case EXIT:
                    break outerLoop;
            }
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

    private void exit() {

    }

    private void showMenu() {

    }
}

