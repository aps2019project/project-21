package controller.request;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GraveyardRequest extends Request {
    @Override
    public void extractType() {
        type = RequestType.INVALID;
        if (commandLine.equalsIgnoreCase("show cards"))
            showCardsCheck();
        else if (commandLine.startsWith("show info"))
            showInfoCheck();
        else if (commandLine.equalsIgnoreCase("back"))
            backCheck();
        else if (commandLine.equalsIgnoreCase("help"))
            helpCheck();
        else if (commandLine.equalsIgnoreCase("show menu"))
            helpCheck();
        else if (commandLine.equalsIgnoreCase("exit"))
            exitCheck();
    }

    private void showInfoCheck() {
        String regex = "Show info (\\w+)";
        Matcher matcher = Pattern.compile(regex).matcher(commandLine);
        if (matcher.matches()) {
            commandArguments.add(matcher.group(1));
            type = RequestType.SHOW_INFO;
        }
    }

    private void showCardsCheck() {
        type = RequestType.SHOW_CARDS;
    }
}
