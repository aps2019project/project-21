package controller.request;

public class AccountMenuRequest extends Request {
    @Override
    public void checkSyntax() {
        // set field type in parent class
    }

    private boolean createAccountCheck(){
        return true;
    }

    private boolean loginCheck(){
        return true;
    }

    private boolean showLeaderBoardCheck(){
        return true;
    }

    private boolean saveCheck(){
        return true;
    }

    private boolean logoutCheck(){
        return true;
    }
}
