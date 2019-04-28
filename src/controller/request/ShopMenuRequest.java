package controller.request;

public class ShopMenuRequest extends Request {
    @Override
    public void extractType() {
        // set field type in parent class
        String command = this.getCommandLine();
        if (command.equals("exit"))
            this.setType(RequestType.EXIT);
        else if (command.equals("show collection"))
            this.setType(RequestType.SHOW_COLLECTION);
        else if (command.startsWith("search collection"))
            this.setType(RequestType.SEARCH_COLLECTION);
        else if (command.startsWith("search"))
            this.setType(RequestType.SEARCH);
        else if (command.equals("buy"))
            this.setType(RequestType.BUY);
        else if (command.equals("sell"))
            this.setType(RequestType.SELL);
        else if (command.equals("show"))
            this.setType(RequestType.SHOW);
        else if (command.equals("help"))
            this.setType(RequestType.HELP);
    }

    private boolean showCollectionCheck() {
        return true;
    }

    private boolean searchCheck() {
        return true;
    }

    private boolean searchCollectionCheck() {
        return true;
    }

    private boolean buyCheck() {
        return true;
    }

    private boolean sellCheck() {
        return true;
    }

    private boolean showCheck() {
        return true;
    }

    protected void backCheck() {

    }
}
