package controller.request;

public class GraveyardRequest extends Request {
    @Override
    public void checkSyntax() {
        // set field type in parent class
    }

    private boolean showInfoCheck() {
        return true;
    }

    private boolean showCardsCheck() {
        return true;
    }
}
