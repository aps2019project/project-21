package view;

import models.Match.Match;
import models.Player;

import javax.swing.text.PlainDocument;

public class View {
    private Player currentPlayer;
    private Match currentMatch;

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public View (){

    }

    public void printError(ErrorMode error) {
        if(error == null)
            return;
        System.out.println(error.getMessage());
    }

    public void scoreBoard(){

    }

    public void help(String menuName){

    }

    public void printCard(String name){

    }

    public void printItem(String name){

    }

    public void printMinion(String name){

    }

    public void printSpell(String name){

    }

    public void showMenu(String menuName){

    }

    public void showDeck(String name){

    }

    public void showCollection(){

    }

    public void showSearchInShop(String msg){

    }

    public void showCollectionSearchInShop(String msg){

    }

    public void showGameInfo(){}

    public void showMyMinions(){}

    public void showOpponentMinions(){}

    public void showCardInfo(String  cardID){}

    public void showHand(){}

    public void showCollectables(){}

    public void showNextCard(){}

    public void showGeneralThings(String thingName){}
}
