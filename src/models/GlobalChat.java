package models;

import javafx.util.Pair;
import json.CardMaker;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GlobalChat implements Serializable {
    private static final long serialVersionUID = 6529685098267757047L;

    private static GlobalChat instance = new GlobalChat();

    public static GlobalChat getInstance() {
        return instance;
    }

    public static void init(GlobalChat globalChat) {
        instance = globalChat;
    }

    public static void save() {
        CardMaker.saveToFile(instance);
    }

    private List<Pair<String, String>> messages = new ArrayList<>();

    public void addMessage(String username, String message) {
        addMessage(new Pair<>(username, message));
    }

    public void addMessage(Pair<String, String> msgPair) {
        messages.add(msgPair);
    }

    public List<Pair<String, String>> getMessages() {
        return messages;
    }

    public Pair<String, String> getLastMessage() {
        if (messages.isEmpty())
            return null;
        return messages.get(messages.size() - 1);
    }
}
