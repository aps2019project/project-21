package controller.request;

import controller.InputScanner;
import view.ErrorMode;

public abstract class Request {
    private RequestType type = RequestType.MAIN_MENU;
    private ErrorMode error = null;
    private String commandLine;

    public void getNewCommand() {
        commandLine = InputScanner.nextLine();
    }

    public RequestType getType() {  //  abstract maybe?
        //
        //
        //
        return type;
    }

    public ErrorMode getError() {
        return error;
    }

    public abstract boolean isValid();
}

