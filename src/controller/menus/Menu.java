package controller.menus;

import controller.request.Request;
import view.Message;
import view.View;

abstract class Menu {
    protected View view = View.getInstance();
    protected Request request;
    protected boolean showMenu = true;

    protected void exit(){
        MenuManager.getInstance().exit();
    }

    protected void invalidCommand(){
        view.printError(Message.INVALID_COMMAND);
    }
}
