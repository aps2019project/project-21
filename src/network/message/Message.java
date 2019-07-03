package network.message;

import models.Player;

import java.io.Serializable;

public class Message implements Serializable {
    protected String authToken;
    protected MessageType msgType;
    protected String message;
    protected Object obj;

    public Message() {
    }

    public Message(String message) {
        this.message = message;
    }

    public static Message makeAccount(Player player) {
        Message message = new Message();
        message.msgType = MessageType.PLAYER;
        message.obj = player;
        return message;
    }

    public static Message makeLogout(){
        Message message = new Message();
        message.msgType = MessageType.LOGOUT;
        return message;
    }

    public Object getObj() {
        return obj;
    }

    public String getAuthToken() {
        return authToken;
    }

    public MessageType getMsgType() {
        return msgType;
    }

    public String getMessage() {
        return message;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}
