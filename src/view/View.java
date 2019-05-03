package view;

import controller.menus.ShopMenu;
import models.Collection;
import models.Deck;
import models.Item.Collectable;
import models.Item.Item;
import models.Player;
import models.card.*;
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

    public void showUser(Player player) {
        System.out.println("Username:"+player.getUsername()+"-Wins:"+player.getWins());
    }

    public void scoreBoard() {
        int numOfPlayers = Player.getPlayers().size();
        Player[] players = new Player[numOfPlayers];
        for (int i=0; i < numOfPlayers; i++){
            players[i] = Player.getPlayers().get(i);
        }
        //insertion sort of users
        for (int i = numOfPlayers-2;i>-1;i--){
            for (int j=0;j<i;j++){
                if (players[j].compareTwoPlayer(players[j+1])){
                    Player p = players[j];
                    players[j] = players[j+1];
                    players[j+1] = p;
                }
            }
        }
        //print users
        for (int i=0;i<numOfPlayers;i++){
            System.out.print(i+1);
            showUser(players[i]);
        }
    }

    public void printCardGraveyard(Card card) {

    }

    public void printItem(Item item) {
        System.out.println("Name :" + item.getName()+
                " - Desc : " +
                item.getDesc() +
                " - Sell cost : " +
                item.getPrice());
    }

    public void printMinion(Minion minion) {
        System.out.println("Type : Minion - " +
                "Name : "+
                minion.getName()+
                "Class : " +
                minion.getAttackMode()+
                "- AP : "+
                minion.getAP()+
                "- HP : "+
                minion.getHP()+
                "- MP : "+
                minion.getManaCost()+
                "- Special power : "+
                minion.getSpecialPower().getTargetType().targetString()+
                "- Sell cost : "+
                minion.getPrice());
    }

    public void printSpell(Spell spell) {
        System.out.println("Type: Spell -" +
                "Name" +
                spell.getName()+
                "- MP" +
                spell.getManaCost()+
                "- Description:"+
                spell.getTargetType().targetString()+
                "- Sell cost:"+
                spell.getManaCost());
    }

    public void printHero(Hero hero){
        System.out.println("Name:"+hero.getName() +
                "-AP:"+
                hero.getAP()+
                "-HP:"+
                hero.getHP()+
                "-Class:"+
                hero.getAttackMode() +
                "-Special power:"+
                hero.getSpecialPower().getName()+
                "-Sell cost:"+
                hero.getPrice());
    }

    public void showMenu(String menuName) {

    }

    public void showDeck(Deck deck) {

    }

    public void showCollection(Collection collection) {
        System.out.println("Heroes:\n");
        for (int i=0;i<collection.getHero().size();i++){
            System.out.print("\t\t"+(i+1)+" - ");
            printHero(collection.getHero().get(i));
        }
        System.out.println("Items:\n");
        for (int i=0;i<collection.getItems().size();i++){
            System.out.print("\t\t"+(i+1)+" - ");
            printItem(collection.getItems().get(i));
        }
        System.out.println("Cards :\n");
        int i=0;
        for (;i<collection.getSpells().size();i++){
            System.out.print("\t\t"+(i+1)+" - ");
            printSpell(collection.getSpells().get(i));
        }
        for (;i<collection.getMinions().size()+collection.getSpells().size();i++){
            System.out.print("\t\t"+(i+1)+" - ");
            printMinion(collection.getMinions().get(i));
        }
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
