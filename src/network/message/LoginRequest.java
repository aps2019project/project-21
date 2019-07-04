package network.message;

public class LoginRequest extends Request {
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
