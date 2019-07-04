package network.message;

import models.GlobalChat;
import models.Player;

import java.io.Serializable;

public class Message implements Serializable {
    protected String authToken;
    protected MessageType msgType;
    protected Object obj;

    public Message() {
    }

    public static Message makeAccount(Player player) {
        Message message = new Message();
        message.msgType = MessageType.PLAYER;
        message.obj = player;
        return message;
    }

    public static Message makeLogout() {
        Message message = new Message();
        message.msgType = MessageType.LOGOUT;
        return message;
    }

    public static Message makeGlobalChatMessage(GlobalChat globalChat) {
        Message message = new Message();
        message.msgType = MessageType.GLOBAL_CHAT_MESSAGE;
        message.obj = globalChat.getLastMessage();
        return message;
    }

    public static Message makeGlobalChat(GlobalChat globalChat) {
        Message message = new Message();
        message.msgType = MessageType.GLOBAL_CHAT;
        message.obj = globalChat;
        return message;
    }

    public static Message makeMessage(String msg) {
        Message message = new Message();
        message.msgType = MessageType.MESSAGE;
        message.obj = msg;
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

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}
