package network.request;

public class CreateAccountRequest extends Request {
    private static final long serialVersionUID = 6529685098267757050L;

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
