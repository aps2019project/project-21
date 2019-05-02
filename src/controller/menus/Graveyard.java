package controller.menus;

import controller.request.MainMenuRequest;
import controller.request.Request;
import models.card.Card;
import models.match.Match;
import models.match.PlayerMatchInfo;
import view.View;

public class Graveyard extends Menu {
    private static Graveyard instance = new Graveyard();

    static Menu getInstance() {
        return instance;
    }

    private Graveyard() {

    }

    public void main() {
        showMenu();

        request = new MainMenuRequest();

        request.getNewCommand();

        request.extractType();

        switch (request.getType()) {
            //  add cases
            case SHOW_INFO:
                showInfo();
                break;
            case SHOW_CARDS:
                showCards();
                break;
            case EXIT:
                exit();
                break;
        }
    }

    protected void showMenu() {
        System.out.println("------graveyard--------");
        System.out.println("options:\n1 - Show cards\n2 - Show info [card id]");
    }

    private void showInfo() {
        Card card = Card.getCardByID(request.getCommandArguments().get(0));
        view.printCardGraveyard(card);
    }

    private void showCards() {
        PlayerMatchInfo player = Match.getCurrentMatch().getPlayersMatchInfo()[0];
        for (Card card : player.getGraveyard()){
            view.printCardGraveyard(card);
        }
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }
}
