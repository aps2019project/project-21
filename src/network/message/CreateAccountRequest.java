package network.message;

public class CreateAccountRequest extends Request {
    private String username;
    private String password;

    public CreateAccountRequest(String username, String password) {
        super.reqType = RequestType.CREATE_ACCOUNT;
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
