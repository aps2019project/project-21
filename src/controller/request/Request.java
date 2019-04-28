package controller.request;

import controller.InputScanner;
import view.ErrorMode;

import java.util.ArrayList;
import java.util.List;

public abstract class Request {
    protected RequestType type = null;
    protected ErrorMode error = null;
    protected String commandLine;
    protected List<String> commandArguments = new ArrayList<>();

    public void getNewCommand() {
        commandLine = InputScanner.nextLine();
    }

    public RequestType getType() {
        return type;
    }

    public abstract void extractType();

    protected void helpCheck() {
        type = RequestType.HELP;
    }

    protected void exitCheck() {
        type = RequestType.EXIT;
    }

    protected void showMenuCheck() {
        type = RequestType.SHOW_MENU;
    }

    abstract protected void backCheck();

    public void setType(RequestType type) {
        this.type = type;
    }

    public String getCommandLine() {
        return commandLine;
    }

    public List<String> getCommandArguments() {
        return commandArguments;
    }
}

