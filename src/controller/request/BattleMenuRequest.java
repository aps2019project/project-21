package controller.request;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BattleMenuRequest extends Request {
    @Override
    public void extractType() {
        // set field type in parent class
        type = RequestType.INVALID;
        if (commandLine.equals("Game info"))
            gameInfoCheck();
        else if (commandLine.equals("Show my minions"))
            showMyMinionsCheck();
        else if (commandLine.equals("Show opponents minions"))
            showOppMinionsCheck();
        else if (commandLine.startsWith("Show card info "))
            showCardInfoCheck();
        else if (commandLine.startsWith("Select"))
            selectCheck();
        else if (commandLine.startsWith("Move to"))
            moveToCheck();
        else if (commandLine.startsWith("Attack "))
            attackCheck();
        else if (commandLine.startsWith("Attack combo "))
            attackComboCheck();
        else if (commandLine.startsWith("Use special power "))
            useSpecialPowerCheck();
        else if (commandLine.equals("Show hand"))
            showHandCheck();
        else if (commandLine.startsWith("Insert ") && commandLine.contains("in"))
            insertCheck();
        else if (commandLine.equals("End Turn"))
            endTurnCheck();
        else if (commandLine.equals("Show collectables"))
            showCollectablesCheck();
        else if (commandLine.equals("Show info"))
            showInfoCheck();
        else if (commandLine.startsWith("Use"))
            useCheck();
        else if (commandLine.equals("Show Next Card"))
            showNextCardCheck();
        else if (commandLine.equals("Enter graveyard"))
            enterGraveyardCheck();
        else if (commandLine.equals("End Game"))
            endGameCheck();
    }

    private void gameInfoCheck() {
        type = RequestType.GAME_INFO;
    }

    private void showMyMinionsCheck() {
        type = RequestType.SHOW_MY_MINIONS;
    }

    private void showOppMinionsCheck() {
        type = RequestType.SHOW_OPPONENT_MINIONS;
    }

    private void showCardInfoCheck() {
        String regex = "Show card info (\\d+)";
        Matcher matcher = Pattern.compile(regex).matcher(commandLine);
        if (matcher.matches()) {
            commandArguments.add(matcher.group(1));
            type = RequestType.SHOW_CARD_INFO;
        }
    }

    private void selectCheck() {
        String regex = "Select (\\d+)";
        Matcher matcher = Pattern.compile(regex).matcher(commandLine);
        if (matcher.matches()) {
            commandArguments.add(matcher.group(1));
            type = RequestType.SELECT;
        }
    }

    private void moveToCheck() {
        String regex = "Move to [(](\\d+),(\\d+)[)]";
        Matcher matcher = Pattern.compile(regex).matcher(commandLine);
        if (matcher.matches()) {
            commandArguments.add(matcher.group(1));
            commandArguments.add(matcher.group(2));
            type = RequestType.MOVE_TO;
        }
    }

    private void attackCheck() {
        String regex = "Attack (\\d+)";
        Matcher matcher = Pattern.compile(regex).matcher(commandLine);
        if (matcher.matches()) {
            commandArguments.add(matcher.group(1));
            type = RequestType.ATTACK;
        }
    }

    private void attackComboCheck() {
        String regex = "\\d+";
        String[] split = commandLine.split(" ");
        int i = 2;
        for (; i < split.length; i++) {
            Matcher matcher = Pattern.compile(regex).matcher(split[i]);
            if (!matcher.matches())
                break;
        }
        if (i == split.length) {
            for (i = 2; i < split.length; i++) {
                commandArguments.add(split[i]);
            }
        }
        type = RequestType.ATTACK_COMBO;
    }

    private void useSpecialPowerCheck() {
        String regex = "Use special power [(](\\d+),(\\d+)[)]";
        Matcher matcher = Pattern.compile(regex).matcher(commandLine);
        if (matcher.matches()){
            commandArguments.add(matcher.group(1));
            commandArguments.add(matcher.group(2));
            type = RequestType.USE_SPECIAL_POWER;
        }
    }

    private void showHandCheck() {
        type = RequestType.SHOW_HAND;
    }

    private void insertCheck() {
        String regex = "Insert (\\w+) in [(](\\d+)(\\d+)[)]";
        Matcher matcher = Pattern.compile(regex).matcher(commandLine);
        if (matcher.matches()){
            commandArguments.add(matcher.group(1));
            commandArguments.add(matcher.group(2));
            commandArguments.add(matcher.group(3));
            type = RequestType.INSERT_IN;
        }
    }

    private void endTurnCheck() {
        type = RequestType.END_TURN;
    }

    private void showCollectablesCheck() {
        type = RequestType.SHOW_COLLECTABLES;
    }

    private void showInfoCheck(){
        type = RequestType.SHOW_INFO;
    }

    private void useCheck() {
        String regex = "Use location [(](\\d+),(\\d+)[)]";
        Matcher matcher = Pattern.compile(regex).matcher(commandLine);
        if (matcher.matches()){
            commandArguments.add(matcher.group(1));
            commandArguments.add(matcher.group(2));
            type = RequestType.USE;
        }
    }

    private void showNextCardCheck() {
        type = RequestType.SHOW_NEXT_CARD;
    }

    private void enterGraveyardCheck() {
        type = RequestType.ENTER_GRAVEYARD;
    }

    private void endGameCheck() {
        type = RequestType.END_GAME;
    }

    private void showCardsCheck() {
        type = RequestType.SHOW_CARDS;
    }

    protected void backCheck() {

    }
}
