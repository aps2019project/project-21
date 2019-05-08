package view;

import models.Collection;
import models.Deck;
import models.Item.Collectable;
import models.Item.Item;
import models.Item.Usable;
import models.Player;
import models.card.*;
import models.match.GoalMode;
import models.match.Match;
import models.match.PlayerMatchInfo;
import models.match.GoalMode;
import models.match.Match;

import java.util.Arrays;
import java.util.Comparator;

public class View {
    private static View view = new View();

    public static View getInstance() {
        return view;
    }

    private View() {

    }

    public void printError(ErrorMode error) {
        if (error == null)
            return;
        System.out.println(error.getMessage());
    }

    public void showUser(Player player) {
        if (player == null)
            return;
        System.out.println("Username: " + player.getUsername() + " -Wins: " + player.getWins());
    }

    public void scoreBoard() {
        Player[] players = new Player[Player.getPlayers().size()];
        Player.getPlayers().toArray(players);
        Arrays.sort(players, Comparator.comparingInt(Player::getWins));

        for (int i = 0; i < players.length; i++) {
            System.out.print((i + 1) + "- ");
            showUser(players[i]);
        }
    }

    public void printCardGraveyard(Card card) {

    }

    private void printItem(Item item, String sellOrBuy, boolean showCollectionID) {
        if (showCollectionID)
            System.out.print(" collectionID : " + item.getCollectionID());
        System.out.print(" - Name : " + item.getName() +
                " - Desc : " +
                item.getDesc());
        if (sellOrBuy != null)
            System.out.println(" - " + sellOrBuy + " cost : " +
                    item.getPrice());
        else
            System.out.println();
    }

    private void printMinion(Minion minion, String sellOrBuy, boolean showCollectionID) {
        if (showCollectionID)
            System.out.print(" collectionID : " + minion.getCollectionID());
        System.out.print(" - Type : Minion" +
                " - Name : " +
                minion.getName() +
                " - Class : " +
                minion.getAttackMode() +
                " - AP : " +
                minion.getAP() +
                " - HP : " +
                minion.getHP() +
                " - MP : " +
                minion.getManaCost() +
                " - Special power : ");
        if (minion.hasSpecialPower())
            System.out.print(minion.getSpecialPower().getDesc());
        if (sellOrBuy != null)
            System.out.println(" - " + sellOrBuy + " cost : " +
                    minion.getPrice());
        else
            System.out.println();
    }

    private void printSpell(Spell spell, String sellOrBuy, boolean showCollectionID) {
        if (showCollectionID)
            System.out.print(" collectionID : " + spell.getCollectionID());
        System.out.print(" - Type: Spell" +
                " - Name: " +
                spell.getName() +
                " - MP: " +
                spell.getManaCost() +
                " - Description: " +
                spell.getDesc());
        if (sellOrBuy != null)
            System.out.println(" - " + sellOrBuy + "  cost: " +
                    spell.getPrice());
        else
            System.out.println();
    }

    private void printHero(Hero hero, String sellOrBuy, boolean showCollectionID) {
        if (showCollectionID)
            System.out.print(" collectionID : " + hero.getCollectionID());
        System.out.print(" - Name: " + hero.getName() +
                " - AP: " +
                hero.getAP() +
                " - HP: " +
                hero.getHP() +
                " - Class: ");
        System.out.print(hero.getAttackMode());
        System.out.print(" - Special power: ");
        if (hero.hasSpecialPower())
            System.out.print(hero.getSpecialPower().getName());
        if (sellOrBuy != null)
            System.out.println(" - " + sellOrBuy + " cost : " +
                    hero.getPrice());
        else
            System.out.println();
    }

    public void showMenu(String menuName) {
        if (Player.getCurrentPlayer() != null)
            System.out.println("logged in as " + Player.getCurrentPlayer().getUsername());
        if (menuName.equals("Account")) {
            System.out.print("------ AccountMenu ------\n" +
                    "1. create account [user name]\n" +
                    "2. login [user name]\n" +
                    "3. show leaderboard\n" +
                    "4. save\n" +
                    "5. logout\n" +
                    "6. main menu\n" +
                    "7. help\n" +
                    "8. exit\n");
        } else if (menuName.equals("MainMenu")) {
            System.out.print("------ MainMenu ------\n" +
                    "1. collection\n" +
                    "2. shop\n" +
                    "3. battle\n" +
                    "4. show menu\n" +
                    "5. help\n" +
                    "6. back\n" +
                    "7. exit\n");
        } else if (menuName.equals("Collection")) {
            System.out.print("------ Collection ------\n" +
                    "1. show\n" +
                    "2. search [card name|item name]\n" +
                    "3. save\n" +
                    "4. create deck [deck name]\n" +
                    "5. delete deck [deck name]\n" +
                    "6. add [card id|card id|hero id] to deck [deck name]\n" +
                    "7. remove [card id|card id|hero id] from deck [deck name]\n" +
                    "8. validate deck [deck name]\n" +
                    "9. select deck [deck name]\n" +
                    "10. show all decks\n" +
                    "11. show deck [deck name]\n" +
                    "12. help\n" +
                    "13. back\n" +
                    "14. exit\n");
        } else if (menuName.equals("Shop")) {
            System.out.print("------ Shop ------\n" +
                    "1. show collection\n" +
                    "2. search [item name|card name]\n" +
                    "3. search collection [item name|card name]\n" +
                    "4. buy [card name|item name]\n" +
                    "5. sell [card id|card id]\n" +
                    "6. show\n" +
                    "7. help\n" +
                    "8. back\n" +
                    "9. exit\n");
        } else if (menuName.equals("Battle")) {
            System.out.print("------ Battle ------\n" +
                    "1. Game info\n" +
                    "2. Show my minions\n" +
                    "3. Show opponent minions\n" +
                    "4. Show card info [card id]\n" +
                    "5. Select [card id]\n" +
                    "6. Move to(x,y)\n" +
                    "7. Attack [opponent card id]\n" +
                    "8. Attack combo [opponent card id][my card id][...]\n" +
                    "9. Use special power (x,y)\n" +
                    "10. Show hand\n" +
                    "11. Insert [card name] in (x,y)\n" +
                    "12. End turn\n" +
                    "13. Show collectables\n" +
                    "14. Select [collectable id]\n" +
                    "15. Show info\n" +
                    "16. Use (x,y)\n" +
                    "17. Show Next Card\n" +
                    "18. Enter graveyard\n" +
                    "19. Help\n" +
                    "20. End Game\n" +
                    "21. Exit\n" +
                    "22. exit\n");
        } else if (menuName.equals("Graveyard")) {
            System.out.print("Graveyard :\n" +
                    "1 - Show info [card id]\n" +
                    "2 - Show cards\n");
        }
    }

    public void showDeck(Deck deck) {
        if (deck == null)
            return;
        System.out.print("\tHeroes:\n");
        if (deck.hasHero()) {
            System.out.print("\t\t1 : ");
            printHero(deck.getHero(), null, true);
        }
        System.out.print("\tItems:\n");
        if (deck.hasUsable()) {
            System.out.print("\t\t1 : ");
            printItem(deck.getUsable(), null, true);
        }
        System.out.print("\tCards:\n");
        for (int i = 0; i < deck.getCards().size(); i++) {
            Card card = deck.getCards().get(i);
            System.out.print("\t\t" + (i + 1) + " : ");
            if (card.getClass().equals(Minion.class))
                printMinion((Minion) card, null, true);
            else if (card.getClass().equals(Spell.class))
                printSpell((Spell) card, null, true);
        }
    }

    public void showDecks(Collection collection) {
        if (collection == null)
            return;
        int counter = 1;
        if (collection.hasMainDeck()) {
            System.out.println(counter + " : "
                    + collection.getMainDeck().getName() + " :");
            showDeck(collection.getMainDeck());
            counter++;
        }
        for (Deck deck : collection.getDecks())
            if (!collection.hasMainDeck()
                    || !deck.getName().equals(collection.getMainDeck().getName())) {
                System.out.println(counter + " : " + deck.getName() + " :");
                showDeck(deck);
            }
    }

    public void showCollection(Collection collection) {
        System.out.println("Heroes:");
        for (int i = 0; i < collection.getHeroes().size(); i++) {
            System.out.print("\t\t" + (i + 1) + " - ");
            printHero(collection.getHeroes().get(i), "sell", true);
        }
        System.out.println("Items:");
        for (int i = 0; i < collection.getUsables().size(); i++) {
            System.out.print("\t\t" + (i + 1) + " - ");
            printItem(collection.getUsables().get(i), "sell", true);
        }
        System.out.println("Cards :");
        int counter = 1;
        for (int i = 0; i < collection.getSpells().size(); i++) {
            System.out.print("\t\t" + counter + " - ");
            printSpell(collection.getSpells().get(i), "sell", true);
            counter++;
        }
        for (int i = 0; i < collection.getMinions().size(); i++) {
            System.out.print("\t\t" + counter + " - ");
            printMinion(collection.getMinions().get(i), "sell", true);
            counter++;
        }
    }

    public void showSearchInShop(String msg) {

    }

    public void showCollectionSearchInShop(String msg) {

    }

    public void showGameInfo() {
        Match match = Match.getCurrentMatch();
        if (match == null)
            return;
        if (match.getGoalMode() == GoalMode.KILL_HERO) {
            for (PlayerMatchInfo p : match.getPlayersMatchInfo())
                System.out.println(p.getHero().getCardIDInGame() + "'s hp: " + p.getHero().getHP());
        } else if (match.getGoalMode() == GoalMode.HOLD_FLAG) {
            System.out.println(match.whoHasFlag());
            System.out.println("flags position: (" + match.getFlag().getX() + ", "
                    + match.getFlag().getY() + ")");
        } else if (match.getGoalMode() == GoalMode.GATHER_FLAG) {
            for (String name : match.whoHasFlags())
                System.out.println(name);
        }
    }

    public void showMinions(Attacker attacker) {
        if (attacker == null)
            return;
        System.out.print(attacker.getId() +
                " : " +
                attacker.getName() +
                " , health : " +
                attacker.getHP() +
                " , location : (" +
                attacker.getCurrentCell().getX() +
                "," +
                attacker.getCurrentCell().getY() +
                ") , power : " +
                attacker.getAP());
    }

    public void showCardInfo(Card card) {
        if (card == null)
            return;
        if (card instanceof Hero) {
            System.out.println("Hero : " +
                    " Name : " +
                    card.getName() +
                    " Cost : " +
                    card.getPrice() +
                    " Desc : " +
                    card.getDesc());
        } else if (card instanceof Spell) {
            System.out.println("Spell : " +
                    " Name : " +
                    card.getName() +
                    " MP : " +
                    card.getManaCost() +
                    " Cost : " +
                    card.getPrice()
                    + " Desc : " +
                    card.getDesc());
        } else if (card instanceof Minion) {
            System.out.println("Minion : " +
                    " Name : " +
                    card.getName() +
                    " HP : " +
                    ((Minion) card).getHP() +
                    " AP : " +
                    ((Minion) card).getAP() +
                    " MP : " +
                    card.getManaCost() +
                    " Range : " +
                    ((Minion) card).getAttackRange() +
                    " Combo-ability : " +
                    ((Minion) card).isCombo() +
                    " Cost : " +
                    card.getPrice() +
                    " Desc : " +
                    card.getDesc());
        } else
            System.out.println("not a card");
    }

    public void showCollectables(Collectable collectable) {
        showCardInfo(collectable);
    }

    public void showGeneralThings(String thingName) {

    }

    public void showShop() {
        System.out.println("Heroes :");
        for (int i = 0; i < Hero.getHeroes().size(); i++) {
            System.out.print("\t\t" + (i + 1) + " : ");
            printHero(Hero.getHeroes().get(i), "buy", false);
        }
        System.out.println("Items :");
        for (int i = 0; i < Usable.getUsables().size(); i++) {
            System.out.print("\t\t" + (i + 1) + " : ");
            printItem(Usable.getUsables().get(i), "buy", false);
        }
        System.out.println("Cards :");
        for (int i = 0; i < Spell.getSpells().size(); i++) {
            System.out.print("\t\t" + (i + 1) + " : ");
            printSpell(Spell.getSpells().get(i), "buy", false);
        }
        for (int i = 0; i < Minion.getMinions().size(); i++) {
            System.out.print("\t\t" + (i + 1 + Spell.getSpells().size()) + " : ");
            printMinion(Minion.getMinions().get(i), "buy", false);
        }
    }

    public void showItemInfo() {

    }

    public static void printException(Exception e) {
        System.out.println(e.getMessage());
        for (StackTraceElement s : e.getStackTrace())
            System.out.println(s);
    }

    public void showNextCard(Card card) {
        System.out.println("this is the next card in your hand:");
        showCardInfo(card);
    }


}
