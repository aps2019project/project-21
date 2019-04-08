package controller.request;

public class MainMenuRequest extends Request{

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
}
