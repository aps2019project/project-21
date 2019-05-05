package controller.request;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GraveyardRequest extends Request {
    @Override
    public void extractType() {
        // set field type in parent class
        type = RequestType.INVALID;
        if (commandLine.equals("Show cards"))
            showCardsCheck();
        else if (commandLine.startsWith("Show info"))
            showInfoCheck();
    }

    private void showInfoCheck() {
        String regex = "Show info (\\d+)";
        Matcher matcher = Pattern.compile(regex).matcher(commandLine);
        if (matcher.matches()){
            commandArguments.add(matcher.group(1));
            type = RequestType.SHOW_INFO;
        }
    }

    private void showCardsCheck() {
        type = RequestType.SHOW_CARDS;
    }
}
