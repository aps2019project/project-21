package controller.menu;

import controller.request.MainMenuRequest;
import controller.request.Request;
import controller.request.RequestType;
import models.Item.Collectable;
import view.View;

public class BattleMenu {
    private View view = new View();

    public void main() {

        outerLoop:
        while (true) {
            showMenu();

            Request request = new MainMenuRequest();
            request.getNewCommand();

            if (request.getType() == RequestType.EXIT)
                break;
            if (!request.isValid()) {
                view.printError(request.getError());
                continue;
            }
            switch (request.getType()) {

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

    private void showCardInfo(int cardID) {

    }

    private void select(int cardID) {

    }

    private void moveTo(int x, int y) {

    }

    private void attack(int oppCardID) {

    }

    private void attackCombo(int oppCardID, int... myCardID) {

    }

    private void useSpecialPower(int x, int y) {

    }

    private void showHand() {

    }

    private void insertCardIn(int x, int y) {

    }

    private void endturn() {

    }

    private void showCollectables() {

    }

    private void select(Collectable collectable) {

    }

    private void showInfo() {

    }

    private void use(int x, int y) {

    }

    private void showNextCard() {

    }

    private void enterGraveyard() {

    }

    private void showInfo(int cardID) {

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

