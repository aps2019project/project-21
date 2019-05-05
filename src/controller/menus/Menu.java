package controller.menus;

import controller.request.Request;
import models.Player;
import view.ErrorMode;
import view.View;

abstract class Menu {
    protected View view = View.getInstance();
    protected Request request;
    protected boolean showMenu = true;

    abstract void main();

    abstract protected void showMenu();

    protected void exit(){
        MenuManager.getInstance().exit();
    }

    protected void invalidCommand(){
        view.printError(ErrorMode.INVALID_COMMAND);
    }
}
