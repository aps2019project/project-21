package controller.request;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AccountMenuRequest extends Request {
    @Override
    public void extractType() {
        type = RequestType.INVALID;
        if (commandLine.contains("create account"))
            createAccountCheck();
        else if (commandLine.contains("login"))
            loginCheck();
        else if (commandLine.contains("show leaderboard"))
            showLeaderBoardCheck();
        else if (commandLine.contains("save"))
            saveCheck();
        else if (commandLine.contains("logout"))
            logoutCheck();
        else if (commandLine.contains("main menu"))
            mainMenuCheck();
        else if (commandLine.contains("help"))
            helpCheck();
        else if (commandLine.contains("show menu"))
            showMenuCheck();
        else if (commandLine.contains("back"))
            backCheck();
        else if (commandLine.contains("exit"))
            exitCheck();
        else if (commandLine.equals("hesoyam"))
            hesoyamCheck();
    }

    private void createAccountCheck() {
        String regex = "create account (\\w+)";
        Matcher matcher = Pattern.compile(regex).matcher(commandLine);
        if (matcher.matches()) {
            commandArguments.add(matcher.group(1));
            type = RequestType.CREATE_ACCOUNT;
        }
    }

    private void loginCheck() {
        String regex = "login (\\w+)";
        Matcher matcher = Pattern.compile(regex).matcher(commandLine);
        if (matcher.matches()) {
            commandArguments.add(matcher.group(1));
            type = RequestType.LOGIN;
        }
    }

    private void hesoyamCheck() {
        type = RequestType.HESOYAM;
    }

    private void showLeaderBoardCheck() {
        type = RequestType.SHOW_LEADERBOARD;
    }

    private void mainMenuCheck() {
        type = RequestType.MAIN_MENU;
    }

    private void saveCheck() {
        type = RequestType.SAVE;
    }

    private void logoutCheck() {
        type = RequestType.LOGOUT;
    }
}
