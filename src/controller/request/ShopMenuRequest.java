package controller.request;

public class ShopMenuRequest extends Request {
    public boolean isValid() {
        RequestType type = getType();
        if (type == null)
            return false;
        switch (type) {
            case EXIT:
                break;
            case SHOP_MENU:
                break;
            case SHOW_COLLECTION:
                break;
            case SEARCH:
                break;
            case SEARCH_COLECTION:
                break;
            case BUY:
                break;
            case SELL:
                break;
            case SHOW:
                break;
            case HELP:
                break;
        }
        return true;
    }
}
