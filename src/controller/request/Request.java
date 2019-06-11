package controller.request;

import controller.InputScanner;
import view.Message;
import view.View;

import java.util.ArrayList;
import java.util.List;

public abstract class Request {
    protected RequestType type = null;
    String commandLine;
    List<String> commandArguments = new ArrayList<>();

    public void getNewCommand() {
        commandLine = InputScanner.nextLine();
    }

    public RequestType getType() {
        return type;
    }

    public abstract void extractType();

    void helpCheck() {
        type = RequestType.HELP;
    }

    void exitCheck() {
        type = RequestType.EXIT;
    }

    void showMenuCheck() {
        type = RequestType.SHOW_MENU;
    }

    void backCheck() {
        type = RequestType.BACK;
    }

    public void setType(RequestType type) {
        this.type = type;
    }

    String getCommandLine() {
        return commandLine;
    }

    public List<String> getCommandArguments() {
        return commandArguments;
    }

    protected void invalidCommand() {
        View.getInstance().printMessage(Message.INVALID_COMMAND);
    }
}

