package controller.request;

public class ShopMenuRequest extends Request {
    @Override
    public void extractType() {
        // set field type in parent class
    }

    private boolean showCollectionCheck(){
        return true;
    }

    private boolean searchCheck(){
        return true;
    }

    private boolean searchCollectionCheck(){
        return true;
    }

    private boolean buyCheck(){
        return true;
    }

    private boolean sellCheck(){
        return true;
    }

    private boolean showCheck(){
        return true;
    }
}
