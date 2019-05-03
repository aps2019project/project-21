package view;

import controller.menus.ShopMenu;
import models.Collection;
import models.Deck;
import models.Item.Collectable;
import models.Player;
import models.card.Attacker;
import models.card.Card;
import models.match.Match;

public class View {
    private static View view = new View();
    private Player currentPlayer;
    private Match currentMatch;


    public static View getInstance() {
        return view;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    private View() {

    }

    public void printError(ErrorMode error) {
        if (error == null)
            return;
        System.out.println(error.getMessage());
    }

    public void scoreBoard() {

    }

    public void help(String menuName) {

    }

    public void printCardGraveyard(Card card) {

    }

    public void printItem(String name) {

    }

    public void printMinion(String name) {

    }

    public void printSpell(String name) {

    }

    public void showMenu(String menuName) {

    }

    public void showDeck(Deck deck) {

    }

    public void showCollection(Collection collection) {

    }

    public void showSearchInShop(String msg) {

    }

    public void showCollectionSearchInShop(String msg) {

    }

    public void showGameInfo() {
    }

    public void showMyMinions(Attacker attacker) {
    }

    public void showOpponentMinions() {
    }

    public void showCardInfo(Card card) {
    }

    public void showHand() {
    }

    public void showCollectables(Collectable collectable) {
    }

    public void showNextCard() {
    }

    public void showGeneralThings(String thingName) {
    }

    public void showShop(ShopMenu shopMenu) {
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Match getCurrentMatch() {
        return currentMatch;
    }

    public void setCurrentMatch(Match currentMatch) {
        this.currentMatch = currentMatch;
    }

    public void showItemInfo() {

    }
}
