package controller.menus;

import controller.request.Request;
import view.View;

abstract class Menu {
    protected View view = View.getInstance();
    protected Request request;

    abstract void main();
}
