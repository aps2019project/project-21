package controller.request;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BattleMenuRequest extends Request {
    @Override
    public void extractType() {
        type = RequestType.INVALID;
        if (commandLine.equalsIgnoreCase("game info"))
            gameInfoCheck();
        else if (commandLine.equalsIgnoreCase("show my minions"))
            showMyMinionsCheck();
        else if (commandLine.equalsIgnoreCase("show opponents minions"))
            showOppMinionsCheck();
        else if (commandLine.startsWith("show card info "))
            showCardInfoCheck();
        else if (commandLine.startsWith("select"))
            selectCheck();
        else if (commandLine.startsWith("move to"))
            moveToCheck();
        else if (commandLine.startsWith("attack "))
            attackCheck();
        else if (commandLine.startsWith("attack combo "))
            attackComboCheck();
        else if (commandLine.startsWith("use special power "))
            useSpecialPowerCheck();
        else if (commandLine.equalsIgnoreCase("show hand"))
            showHandCheck();
        else if (commandLine.startsWith("insert ") && commandLine.contains("in"))
            insertCheck();
        else if (commandLine.equalsIgnoreCase("end turn"))
            endTurnCheck();
        else if (commandLine.equalsIgnoreCase("show collectables"))
            showCollectablesCheck();
        else if (commandLine.equalsIgnoreCase("show info"))
            showInfoCheck();
        else if (commandLine.startsWith("use"))
            useCheck();
        else if (commandLine.equalsIgnoreCase("show next card"))
            showNextCardCheck();
        else if (commandLine.equalsIgnoreCase("enter graveyard"))
            enterGraveyardCheck();
        else if (commandLine.equalsIgnoreCase("end Game"))
            endGameCheck();
        else if (commandLine.equalsIgnoreCase("help"))
            helpCheck();
        else if (commandLine.equalsIgnoreCase("exit"))
            exitCheck();
        else if (commandLine.equalsIgnoreCase("show menu"))
            showMenuCheck();
        else if(commandLine.equals("back"))
            backCheck();
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
        String regex = "show card info (\\d+)";
        Matcher matcher = Pattern.compile(regex).matcher(commandLine);
        if (matcher.matches()) {
            commandArguments.add(matcher.group(1));
            type = RequestType.SHOW_CARD_INFO;
        }
    }

    private void selectCheck() {
        String regex = "select (\\d+)";
        Matcher matcher = Pattern.compile(regex).matcher(commandLine);
        if (matcher.matches()) {
            commandArguments.add(matcher.group(1));
            type = RequestType.SELECT;
        }
    }

    private void moveToCheck() {
        String regex = "move to [(](\\d+),(\\d+)[)]";
        Matcher matcher = Pattern.compile(regex).matcher(commandLine);
        if (matcher.matches()) {
            commandArguments.add(matcher.group(1));
            commandArguments.add(matcher.group(2));
            type = RequestType.MOVE_TO;
        }
    }

    private void attackCheck() {
        String regex = "attack (\\d+)";
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
        String regex = "use special power [(](\\d+),(\\d+)[)]";
        Matcher matcher = Pattern.compile(regex).matcher(commandLine);
        if (matcher.matches()) {
            commandArguments.add(matcher.group(1));
            commandArguments.add(matcher.group(2));
            type = RequestType.USE_SPECIAL_POWER;
        }
    }

    private void showHandCheck() {
        type = RequestType.SHOW_HAND;
    }

    private void insertCheck() {
        String regex = "insert (\\w+) in [(](\\d+)(\\d+)[)]";
        Matcher matcher = Pattern.compile(regex).matcher(commandLine);
        if (matcher.matches()) {
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

    private void showInfoCheck() {
        type = RequestType.SHOW_INFO;
    }

    private void useCheck() {
        String regex = "use location [(](\\d+),(\\d+)[)]";
        Matcher matcher = Pattern.compile(regex).matcher(commandLine);
        if (matcher.matches()) {
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
}
