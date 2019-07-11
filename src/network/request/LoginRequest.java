package network.request;

public class LoginRequest extends Request {
    private static final long serialVersionUID = 6529685098267757051L;

    private String username;
    private String password;

    public LoginRequest(String username, String password) {
        super.reqType = RequestType.LOGIN;
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
