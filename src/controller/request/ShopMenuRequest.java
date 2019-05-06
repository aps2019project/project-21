package controller.request;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShopMenuRequest extends Request {
    @Override
    public void extractType() {
        type = RequestType.INVALID;
        String command = this.getCommandLine();
        if (command.equals("exit"))
            exitCheck();
        else if (command.equalsIgnoreCase("show collection"))
            showCollectionCheck();
        else if (command.startsWith("search collection"))
            searchCollectionCheck();
        else if (command.startsWith("search"))
            searchCheck();
        else if (command.startsWith("buy"))
            buyCheck();
        else if (command.startsWith("sell"))
            sellCheck();
        else if (command.equalsIgnoreCase("show"))
            showCheck();
        else if (command.equalsIgnoreCase("help"))
            helpCheck();
        else if (command.equalsIgnoreCase("back"))
            backCheck();
        else if (command.equalsIgnoreCase("show menu"))
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
            type = RequestType.SEARCH;
        } else
            invalidCommand();
    }

    private void searchCollectionCheck() {
        String Regex = "search collection (\\w+)";
        Matcher matcher = Pattern.compile(Regex).matcher(commandLine);
        if (matcher.matches()) {
            commandArguments.add(matcher.group(1));
            type = RequestType.SEARCH_COLLECTION;
        } else
            invalidCommand();
    }

    private void buyCheck() {
        String regex = "buy (\\w+)";
        Matcher matcher = Pattern.compile(regex).matcher(commandLine);
        if (matcher.matches()) {
            commandArguments.add(matcher.group(1));
            type = RequestType.BUY;
        } else
            invalidCommand();
    }

    private void sellCheck() {
        String regex = "sell (\\w+)";
        Matcher matcher = Pattern.compile(regex).matcher(commandLine);
        if (matcher.matches()) {
            commandArguments.add(matcher.group(1));
            type = RequestType.SELL;
        } else
            invalidCommand();
    }

    private void showCheck() {
        type = RequestType.SHOW;
    }
}
