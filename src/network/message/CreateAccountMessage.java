package network.message;

public class CreateAccountMessage extends Message {
    private String username;
    private String password;

    public CreateAccountMessage(String username, String password) {
        super.msgType = MessageType.CREATE_ACCOUNT;
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
