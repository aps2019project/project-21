package Controller;

import View.ErrorMode;

import java.util.Scanner;

public class Request {
    private Scanner scanner = new Scanner(System.in);
    private RequestType type = RequestType.MAIN_MENU;
    private ErrorMode error = null;

    public void getNewCommand() {

    }

    public RequestType getType() {
        //
        //
        //
        return type;
    }

    public boolean isValid() {
        RequestType type = getType();
        if (type == null)
            return false;
        switch (type) {
            case MAIN_MENU:
                break;
            case COLLECTION:
                break;
            case BATTLE:
                break;
            case SHOP:
                break;
            case HELP:
                break;
            case EXIT:
                break;
        }
        return true;
    }

    public ErrorMode getError() {
        return error;
    }
}
