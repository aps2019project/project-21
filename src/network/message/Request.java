package network.message;

import models.GlobalChat;
import models.Player;
import models.match.MatchRequest;

import java.io.Serializable;

public class Request implements Serializable {
    protected String authToken;
    protected RequestType reqType;
    protected Object obj;

    public Request() {
    }

    public static Request makeAccount(Player player) {
        Request request = new Request();
        request.reqType = RequestType.PLAYER;
        request.obj = player;
        return request;
    }

    public static Request makeLogout() {
        Request request = new Request();
        request.reqType = RequestType.LOGOUT;
        return request;
    }

    public static Request makeGlobalChatMessage(GlobalChat globalChat) {
        Request request = new Request();
        request.reqType = RequestType.GLOBAL_CHAT_MESSAGE;
        request.obj = globalChat.getLastMessage();
        return request;
    }

    public static Request makeGlobalChat(GlobalChat globalChat) {
        Request request = new Request();
        request.reqType = RequestType.GLOBAL_CHAT;
        request.obj = globalChat;
        return request;
    }

    public static Request makeMessage(String msg) {
        Request request = new Request();
        request.reqType = RequestType.MESSAGE;
        request.obj = msg;
        return request;
    }

    public static Request makeMatchRequest(MatchRequest matchRequest) {
        Request request = new Request();
        request.reqType = RequestType.MATCH_REQUEST;
        request.obj = matchRequest;
        return request;
    }

    public static Request makeIntroduceOppRequest(String oppName) {
        Request request = new Request();
        request.reqType = RequestType.INTRODUCE_OPP;
        request.obj = oppName;
        return request;
    }

    public static Request makeReadyRequest() {
        Request request = new Request();
        request.reqType = RequestType.READY;
        return request;
    }

    public Object getObj() {
        return obj;
    }

    public String getAuthToken() {
        return authToken;
    }

    public RequestType getReqType() {
        return reqType;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}
