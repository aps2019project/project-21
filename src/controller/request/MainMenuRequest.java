package controller.request;

public class MainMenuRequest extends Request {
    @Override
    public void extractType() {
        type = RequestType.INVALID;
        if (commandLine.contains("collection"))
            collectionCheck();
        else if (commandLine.contains("shop"))
            shopCheck();
        else if (commandLine.contains("battle"))
            battleCheck();
        else if (commandLine.contains("back"))
            backCheck();
        else if (commandLine.contains("exit"))
            exitCheck();
        else if (commandLine.contains("help"))
            helpCheck();
        else if (commandLine.equalsIgnoreCase("show menu"))
            helpCheck();
    }

    private void shopCheck() {
        type = RequestType.SHOP;
    }

    private void battleCheck() {
        type = RequestType.BATTLE;
    }

    private void collectionCheck() {
        type = RequestType.COLLECTION;
    }
}
