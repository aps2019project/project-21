package controller.request;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShopMenuRequest extends Request {
    @Override
    public void extractType() {
        // set field type in parent class
        String command = this.getCommandLine();
        if (command.equals("exit"))
            exitCheck();
        else if (command.equals("show collection"))
            showCollectionCheck();
        else if (command.startsWith("search collection"))
            searchCollectionCheck();
        else if (command.startsWith("search"))
            searchCheck();
        else if (command.equals("buy"))
            buyCheck();
        else if (command.equals("sell"))
            sellCheck();
        else if (command.equals("show"))
            showCheck();
        else if (command.equals("help"))
            helpCheck();
    }

    private void showCollectionCheck() {
        type = RequestType.SHOW_COLLECTION;
    }

    private void searchCheck() {
        String Regex = "search (\\w+)";
        Matcher matcher = Pattern.compile(Regex).matcher(commandLine);
        if (matcher.matches()){
            commandArguments.add(matcher.group(1));
            type = RequestType.SEARCH;
        }
    }

    private void searchCollectionCheck() {
        String Regex = "search collection (\\w+)";
        Matcher matcher = Pattern.compile(Regex).matcher(commandLine);
        if (matcher.matches()){
            commandArguments.add(matcher.group(1));
            type = RequestType.SEARCH_COLLECTION;
        }
    }

    private void buyCheck() {
        type = RequestType.BUY;
    }

    private void sellCheck() {
        type = RequestType.SELL;
    }

    private void showCheck() {
        type = RequestType.SHOW;
    }

    protected void backCheck() {

    }
}
