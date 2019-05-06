package controller.request;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CollectionMenuRequest extends Request {
    @Override
    public void extractType() {
        type = RequestType.INVALID;
        if (commandLine.equalsIgnoreCase("exit"))
            exitCheck();
        else if (commandLine.equalsIgnoreCase("help"))
            helpCheck();
        else if (commandLine.equalsIgnoreCase("show"))
            showCollectionCheck();
        else if (commandLine.contains("search"))
            searchCheck();
        else if (commandLine.equalsIgnoreCase("save"))
            saveCheck();
        else if (commandLine.startsWith("create deck"))
            createDeckCheck();
        else if (commandLine.startsWith("delete deck"))
            deleteDeckCheck();
        else if (commandLine.startsWith("add") && commandLine.contains(" to deck "))
            addToDeckCheck();
        else if (commandLine.startsWith("remove") && commandLine.contains(" from deck "))
            removeFromCheck();
        else if (commandLine.startsWith("validate deck "))
            validateDeckCheck();
        else if (commandLine.startsWith("select deck "))
            selectDeckCheck();
        else if (commandLine.equalsIgnoreCase("show all decks"))
            showAllDecksCheck();
        else if (commandLine.startsWith("show deck"))
            showDeckCheck();
        else if (commandLine.equalsIgnoreCase("back"))
            backCheck();
        else if(commandLine.equalsIgnoreCase("show menu"))
            showMenuCheck();
    }

    private void showCollectionCheck() {
        type = RequestType.SHOW_COLLECTION;
    }

    private void searchCheck() {
        String Regex = "search (\\w+)";
        Matcher matcher = Pattern.compile(Regex).matcher(commandLine);
        if (matcher.matches()) {
            commandArguments.add(matcher.group(1));
            type = RequestType.SEARCH_COLLECTION;
        } else
            invalidCommand();
    }

    private void saveCheck() {
        type = RequestType.SAVE;
    }

    private void createDeckCheck() {
        String Regex = "create deck (\\w+)";
        Matcher matcher = Pattern.compile(Regex).matcher(commandLine);
        if (matcher.matches()) {
            commandArguments.add(matcher.group(1));
            type = RequestType.CREATE_DECK;
        } else
            invalidCommand();
    }

    private void deleteDeckCheck() {
        String Regex = "delete deck (\\w+)";
        Matcher matcher = Pattern.compile(Regex).matcher(commandLine);
        if (matcher.matches()) {
            commandArguments.add(matcher.group(1));
            type = RequestType.DELETE_DECK;
        } else
            invalidCommand();
    }

    private void addToDeckCheck() {
        String Regex = "add (\\d+) to deck (\\w+)";
        Matcher matcher = Pattern.compile(Regex).matcher(commandLine);
        if (matcher.matches()) {
            commandArguments.add(matcher.group(1));
            commandArguments.add(matcher.group(2));
            type = RequestType.ADD_TO_DECK;
        } else
            invalidCommand();
    }

    private void removeFromCheck() {
        String Regex = "remove (\\d+) from deck (\\w+)";
        Matcher matcher = Pattern.compile(Regex).matcher(commandLine);
        if (matcher.matches()) {
            commandArguments.add(matcher.group(1));
            commandArguments.add(matcher.group(2));
            type = RequestType.REMOVE_FROM_DECK;
        }
    }

    private void validateDeckCheck() {
        String Regex = "validate deck (\\w+)";
        Matcher matcher = Pattern.compile(Regex).matcher(commandLine);
        if (matcher.matches()) {
            commandArguments.add(matcher.group(1));
            type = RequestType.VALIDATE_DECK;
        } else
            invalidCommand();
    }

    private void selectDeckCheck() {
        String Regex = "select deck (\\w+)";
        Matcher matcher = Pattern.compile(Regex).matcher(commandLine);
        if (matcher.matches()) {
            commandArguments.add(matcher.group(1));
            type = RequestType.SELECT_DECK;
        } else
            invalidCommand();
    }

    private void showAllDecksCheck() {
        type = RequestType.SHOW_ALL_DECKS;
    }

    private void showDeckCheck() {
        String Regex = "show deck (\\w+)";
        Matcher matcher = Pattern.compile(Regex).matcher(commandLine);
        if (matcher.matches()) {
            commandArguments.add(matcher.group(1));
            type = RequestType.SHOW_DECK;
        } else invalidCommand();
    }
}
