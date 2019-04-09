package controller.request;

import controller.InputScanner;
import view.ErrorMode;

public abstract class Request {
    private RequestType type = null;
    private ErrorMode error = null;
    private String commandLine;

    public void getNewCommand() {
        commandLine = InputScanner.nextLine();
    }

    public RequestType getType() {
        return type;
    }

    public ErrorMode getError() {
        return error;
    }

    public abstract void checkSyntax();

    protected boolean helpCheck() {
        return true;
    }

    protected boolean exitCheck() {
        return true;
    }

    protected boolean showMenuCheck() {
        return true;
    }

    public void setType(RequestType type) {
        this.type = type;
    }

    public void setError(ErrorMode error) {
        this.error = error;
    }

    public String getCommandLine() {
        return commandLine;
    }

    public void setCommandLine(String commandLine) {
        this.commandLine = commandLine;
    }
}

