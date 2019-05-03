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
        System.out.println("Username:" + player.getUsername() + "-Wins:" + player.getWins());
    }

    public void scoreBoard() {
        int numOfPlayers = Player.getPlayers().size();
        Player[] players = new Player[numOfPlayers];
        for (int i = 0; i < numOfPlayers; i++) {
            players[i] = Player.getPlayers().get(i);
        }
        //insertion sort of users
        for (int i = numOfPlayers - 2; i > -1; i--) {
            for (int j = 0; j < i; j++) {
                if (players[j].compareTwoPlayer(players[j + 1])) {
                    Player p = players[j];
                    players[j] = players[j + 1];
                    players[j + 1] = p;
                }
            }
        }
        //print users
        for (int i = 0; i < numOfPlayers; i++) {
            System.out.print(i + 1);
            showUser(players[i]);
        }
    }

    public void printCardGraveyard(Card card) {

    }

    private void printItem(Item item) {
        System.out.println("Name :" + item.getName() +
                " - Desc : " +
                item.getDesc() +
                " - Sell cost : " +
                item.getPrice());
    }

    private void printMinion(Minion minion) {
        System.out.println("Type : Minion - " +
                "Name : " +
                minion.getName() +
                "Class : " +
                minion.getAttackMode() +
                "- AP : " +
                minion.getAP() +
                "- HP : " +
                minion.getHP() +
                "- MP : " +
                minion.getManaCost() +
                "- Special power : " +
                minion.getSpecialPower().getTargetType().targetString() +
                "- Sell cost : " +
                minion.getPrice());
    }

    private void printSpell(Spell spell) {
        System.out.println("Type: Spell -" +
                "Name" +
                spell.getName() +
                "- MP" +
                spell.getManaCost() +
                "- Description:" +
                spell.getTargetType().targetString() +
                "- Sell cost:" +
                spell.getManaCost());
    }

    private void printHero(Hero hero) {
        System.out.println("Name:" + hero.getName() +
                "-AP:" +
                hero.getAP() +
                "-HP:" +
                hero.getHP() +
                "-Class:" +
                hero.getAttackMode() +
                "-Special power:" +
                hero.getSpecialPower().getName() +
                "-Sell cost:" +
                hero.getPrice());
    }

    public void showMenu(String menuName) {
        if (menuName.equals("Account")) {
            System.out.print("Account:\n" +
                    "1 - create acount [user name]\n" +
                    "2 - login [user name]\n" +
                    "3 - show leaderboard\n" +
                    "4 - save\n" +
                    "5 - logout\n" +
                    "6 - help\n");
        } else if (menuName.equals("mainMenu")) {
            System.out.print("Main menu :\n" +
                    "1 - Collection\n" +
                    "2 - Shop\n" +
                    "3 - Battle\n" +
                    "4 - Exit\n" +
                    "5 - Help\n");
        } else if (menuName.equals("Collection")) {
            System.out.print("Collection :\n" +
                    "1 - exit\n" +
                    "2 - how" +
                    "3 - search [card name|item name]\n" +
                    "4 - save\n" +
                    "5 - create deck [deck name]\n" +
                    "6 - delete deck [deck name]\n" +
                    "7 - add [card id|card id|hero id] to deck [deck name]\n" +
                    "8 - remove [card id|card id|hero id] from deck [deck name]\n" +
                    "9 - validate deck [deck name]\n" +
                    "10 - show all decks\n" +
                    "11 - show deck [deck name]\n" +
                    "12 - help\n");
        } else if (menuName.equals("Shop")){
            System.out.print("Shop : \n" +
                    "1 - exit\n" +
                    "2 - show collection\n" +
                    "3 - search [item name|card name]\n" +
                    "4 - search collection [item name|card name]\n" +
                    "5 - buy [card name|item name]n" +
                    "6 - sell [card id|card id]\n" +
                    "7 - show\n" +
                    "8 - help\n");
        } else if (menuName.equals("Battle")){
            System.out.print("Battle :\n" +
                    "1 - Game info\n" +
                    "2 - Show my minions\n" +
                    "3 - Show opponent minions\n" +
                    "4 - Show card info [card id]\n" +
                    "5 - Select [card id]\n" +
                    "6 - Move to([x],[y])\n" +
                    "7 - Attack [opponent card id]\n" +
                    "8 - Attack combo [opponent card id][my card id][...]\n" +
                    "9 - Use special power ([x],[y])\n" +
                    "10 - Show hand\n" +
                    "11 - Insert [card name] in ([x],[y])\n" +
                    "12 - End turn\n" +
                    "13 - Show collectables\n" +
                    "14 - Select [collectable id]\n" +
                    "15 - Show info\n" +
                    "16 - Use ([x],[y])\n" +
                    "17 - Show Next Card\n" +
                    "18 - Enter graveyard\n" +
                    "19 - Help\n" +
                    "20 - End Game\n" +
                    "21 - Exit\n" +
                    "22 - Show menu\n");
        } else if (menuName.equals("Graveyard")){
            System.out.print("Graveyard :\n" +
                    "1 - Show info [card id]\n" +
                    "2 - Show cards\n");
        }
    }

    public void showDeck(Deck deck) {

    }

    public void showCollection(Collection collection) {
        System.out.println("Heroes:\n");
        for (int i = 0; i < collection.getHero().size(); i++) {
            System.out.print("\t\t" + (i + 1) + " - ");
            printHero(collection.getHero().get(i));
        }
        System.out.println("Items:\n");
        for (int i = 0; i < collection.getItems().size(); i++) {
            System.out.print("\t\t" + (i + 1) + " - ");
            printItem(collection.getItems().get(i));
        }
        System.out.println("Cards :\n");
        int i = 0;
        for (; i < collection.getSpells().size(); i++) {
            System.out.print("\t\t" + (i + 1) + " - ");
            printSpell(collection.getSpells().get(i));
        }
        for (; i < collection.getMinions().size() + collection.getSpells().size(); i++) {
            System.out.print("\t\t" + (i + 1) + " - ");
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
        System.out.print(attacker.getId() +
                " : "+
                attacker.getName() +
                " , health : "+
                attacker.getHP() +
                " , location : (" +
                attacker.getCurrentCell().getX() +
                ","+
                attacker.getCurrentCell().getY() +
                ") , power : " +
                attacker.getAP());
    }

    public void showCardInfo(Card card) {
        if (card.getClass().equals(Hero.class)){
            System.out.println("Hero : \n" +
                    "Name : " +
                    card.getName() +
                    "\nCost : " +
                    card.getPrice());
        } else if (card.getClass().equals(Spell.class)){
            System.out.println("Spell : \n" +
                    "Name :" +
                    card.getName() +
                    "\nMP : " +
                    card.getManaCost() +
                    "\nCost : " +
                    card.getPrice());
        } else if (card.getClass().equals(Minion.class)){
            System.out.println("Minion :\n" +
                    "Name :" +
                    card.getName() +
                    "\nHP : " +
                    ((Minion)card).getHP() +
                    " AP : " +
                    ((Minion)card).getAP() +
                    " MP : " +
                    card.getPrice()+
                    "\nRange : " +
                            ((Minion)card).getAttackRange() +
                    "\nCombo-ability :" +
                    "?????" +
                    "\nCost : "+
                    card.getPrice());
        } else if (card.getClass().equals(Item.class)){
            System.out.println("Name : " +
                    card.getName() +
                    " , Desc : " +
                        "???????");
        }
    }

    public void showCollectables(Collectable collectable) {
        showCardInfo(collectable);
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
