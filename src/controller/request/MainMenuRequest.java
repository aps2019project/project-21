package controller.request;

public class MainMenuRequest extends Request{
    @Override
    public void checkSyntax() {
        // set field type in parent class
    }

    private boolean shopCheck(){
        return true;
    }

    private boolean battlecheck(){
        return true;
    }

    private boolean collectionCheck(){
        return true;
    }
}
