package controller.menus;

import controller.request.Request;
import models.Player;
import view.View;

abstract class Menu {
    protected View view = View.getInstance();
    protected Request request;
    protected Player player;

    abstract void main();

    abstract protected void showMenu();

    protected void exit(){
        MenuManager.getInstance().changeMenu(MenuType.EXIT);
    }
}
