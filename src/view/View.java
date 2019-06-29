package view;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.Collection;
import models.Deck;
import models.Item.Collectable;
import models.Item.Item;
import models.Item.Usable;
import models.Player;
import models.card.*;
import models.match.GameMode;
import models.match.GoalMode;
import models.match.Match;
import models.match.PlayerMatchInfo;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Stack;

public class View {
    private static View view = new View();

    private Stack<Scene> scenes = new Stack<>();

    private Stage primaryStage;

    public static View getInstance() {
        return view;
    }

    private View() {
    }

    public void run() {
        primaryStage.setTitle("Duelyst");
        primaryStage.setMaximized(true);
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            View.printThrowable(e);
        }
        Player.login("a", "a");
        Match.setCurrentMatch(new Match(Player.getCurrentPlayer(), Player.getPlayerByUsername("b"), GameMode.MULTI_PLAYER,
                null, GoalMode.KILL_HERO, 0));
        BattleView battleView = new BattleView();
        Match.getCurrentMatch().setBattleView(battleView);
        battleView.run();
//        MainMenuView.getInstance().run();
    }


    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    void setScene(Scene scene) {
        this.primaryStage.setScene(scene);
        this.scenes.push(scene);
    }

    void back() {
        scenes.pop();
        primaryStage.setScene(scenes.peek());
    }

    void exit() {
        primaryStage.close();
    }

    void removeNodeFromPane(Node node, Pane pane) {
        pane.getChildren().remove(node);
    }

    void addNodeToPane(Node node, Pane pane) {
        if (!pane.getChildren().contains(node))
            pane.getChildren().add(node);
    }

    public void popup(String message) {
        final Stage popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.initOwner(primaryStage);
        popup.setResizable(false);
        Label label = new Label(message);
        Button ok = new Button("OK");
        label.relocate(100, 50);
        ok.relocate(100, 100);
        Group group = new Group();
        group.getChildren().addAll(label, ok);
        Scene dialogScene = new Scene(group, 300, 150);
        ok.setOnAction(event -> popup.close());
        dialogScene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER)
                popup.close();
        });
        popup.setScene(dialogScene);
        VoicePlay.notification();
        popup.show();
    }

    public void printMessage(Message msg) {
        if (msg == null)
            return;
        popup(msg.getMessage());
    }

    private void showUser(Player player) {
        if (player == null)
            return;
        System.out.println("Username: " + player.getUsername() + " - Wins: " + player.getWins() +
                " drake : " + player.getDrake());
    }

    public void showLeaderBoard() {
        Player[] players = new Player[Player.getPlayers().size()];
        Player.getPlayers().toArray(players);
        Arrays.sort(players, Comparator.comparingInt(Player::getWins).reversed());

        for (int i = 0; i < players.length; i++) {
            System.out.print((i + 1) + " - ");
            showUser(players[i]);
        }
    }

    public void printGraveyardCard(Card card) {
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
        }
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
                    "7. show match history\n" +
                    "8. help\n" +
                    "9. exit\n");
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
                    "1. game info\n" +
                    "2. show my minions\n" +
                    "3. show opponent minions\n" +
                    "4. show card info [card id]\n" +
                    "5. select [card id]\n" +
                    "6. move to(x,y)\n" +
                    "7. attack [opponent card id]\n" +
                    "8. attack combo [opponent card id][my card id][...]\n" +
                    "9. use special power (x,y)\n" +
                    "10. show hand\n" +
                    "11. insert [card name] in (x,y)\n" +
                    "12. end turn\n" +
                    "13. show collectables\n" +
                    "14. select [collectable id]\n" +
                    "15. show info\n" +
                    "16. use (x,y)\n" +
                    "17. show Next Card\n" +
                    "18. enter graveyard\n" +
                    "19. help\n" +
                    "20. end Game\n" +
                    "21. withdraw\n" +
                    "22. use spell (x,y)\n" +
                    "23. back\n" +
                    "24. exit\n");
        } else if (menuName.equals("Graveyard")) {
            System.out.print("------ Graveyard ------\n"
                    + "1 - show info [card id]\n"
                    + "2 - show cards\n");
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

    public void showSearchInShop(Card card) {
        if (card == null) {
            System.out.println("Card with this name doesn't exist");
        } else {
            System.out.println("Name : " +
                    card.getName() +
                    " - ID : " +
                    card.getId());
        }
    }

    public void showCollectionSearchInShop(Card card) {
        if (card == null) {
            System.out.println("Card not found");
        } else {
            System.out.println("Name" +
                    card.getName() +
                    "ID" +
                    card.getId());
        }
    }

    public void showGameInfo() {
        Match match = Match.getCurrentMatch();
        if (match == null)
            return;
        String message = "";
        if (match.getGoalMode() == GoalMode.KILL_HERO) {
            for (PlayerMatchInfo p : match.getPlayersMatchInfo())
                message += p.getHero().getCardIDInGame() + "'s hp: " + p.getHero().getHP();
        } else if (match.getGoalMode() == GoalMode.HOLD_FLAG) {
            message += match.whoHasFlag().getName();
            message += "flags position: (" + match.getFlags().get(0).getCurrentCell().getX() + ", "
                    + match.getFlags().get(0).getCurrentCell().getY() + ")";
        } else if (match.getGoalMode() == GoalMode.GATHER_FLAG) {
            message += "These guys have the flags: ";
            for (Attacker attacker : match.whoHasFlags())
                message += attacker.getCardIDInGame() + " in team " + (match.getCardsTeam(attacker) + 1);
        }
        view.popup(message);
    }

    public void showAttacker(Attacker attacker) {
        if (attacker == null)
            return;
        System.out.println(attacker.getCardIDInGame() +
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
            System.out.print("Hero : " +
                    " Name : " +
                    card.getName() +
                    " Price : " +
                    card.getPrice() +
                    " Desc : " +
                    card.getDesc());
        } else if (card instanceof Spell) {
            System.out.print("Spell : " +
                    " Name : " +
                    card.getName() +
                    " MP : " +
                    card.getManaCost() +
                    " Price : " +
                    card.getPrice()
                    + " Desc : " +
                    card.getDesc());
        } else if (card instanceof Minion) {
            System.out.print("Minion : " +
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
                    " Price : " +
                    card.getPrice() +
                    " Desc : " +
                    card.getDesc());
        } else
            System.out.println("not a card");
        if (card.hasCardInGameID())
            System.out.println(" cardIDInGame : " + card.getCardIDInGame());
        else System.out.println();
    }

    public void showCollectable(Collectable collectable) {
        if (collectable == null)
            return;
        System.out.println("achieved collectable : name: " + collectable.getName() +
                " desc: " + collectable.getDesc() + " idInGame: " + collectable.getCardIDInGame());
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

    public void showItemInfo(Item item) {
        System.out.println("Name : " +
                item.getName() +
                "- Desc : " +
                item.getDesc() +
                "- SellCost : " +
                item.getPrice());
    }

    public static void printThrowable(Throwable throwable) {
        System.out.println(throwable.getMessage());
        throwable.printStackTrace();
    }

    public void showMatchResults(Match match) {
        System.out.println("Player " + match.getWinner().getUsername() + " won " + match.getLoser().getUsername() + ".");
        System.out.println("enter end game to go back to main menu.");
    }

    public void showMatchHistory(Player player) {
        if (player == null)
            return;
        for (Match match : player.getMatchHistory())
            showMatchHistory(match, player);
    }

    private void showMatchHistory(Match match, Player player) {
        if (match == null || player == null)
            return;
        Player opp = match.getOpp(player);
        System.out.println("Opponent: " + opp.getUsername() + " win\\loss: "
                + match.getWinner().getUsername().equals(player.getUsername())
                + " time: " + match.getGameTime());
    }

}
