package controller.request;

public class CollectionMenuRequest extends Request {
    @Override
    public void checkSyntax() {
        // set field type in parent class
    }

    private boolean searchCheck(){
        return true;
    }

    private boolean saveCheck(){
        return true;
    }

    private boolean createDeckCheck(){
        return true;
    }

    private boolean addToDeckCheck(){
        return true;
    }

    private boolean removeFromCheck(){
        return true;
    }

    private boolean validateDeckCheck(){
        return true;
    }

    private boolean selectDeckCheck(){
        return true;
    }

    private boolean showAllDecksCheck(){
        return true;
    }

    private boolean showDeckCheck(){
        return true;
    }


}
