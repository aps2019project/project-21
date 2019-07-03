package network.message;

public class LoginMessage extends Message {
    private String username;
    private String password;

    public LoginMessage(String username, String password) {
        super.msgType = MessageType.LOGIN;
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
