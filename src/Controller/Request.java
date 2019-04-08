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
        return type;
    }

    public boolean isValid() {
        return true;
    }

    public ErrorMode getError(){
        return error;
    }
}
