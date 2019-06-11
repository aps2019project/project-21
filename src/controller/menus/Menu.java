package controller.menus;

import controller.request.Request;
import view.Message;
import view.View;

abstract class Menu {
    protected View view = View.getInstance();
    protected Request request;
    boolean showMenu = true;

    protected void exit(){
        MenuManager.getInstance().exit();
    }

    void invalidCommand(){
        view.printMessage(Message.INVALID_COMMAND);
    }
}
