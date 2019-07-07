package network.message;

import models.BattleAction;
import models.GlobalChat;
import models.Player;
import models.match.MatchRequest;
import models.match.PlayerMatchInfo;

import java.io.Serializable;
import java.util.List;

public class Request implements Serializable {
    protected String authToken;
    protected RequestType reqType;
    protected Object obj;

    public Request() {
    }

    public static Request makePlayer(Player player) {
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

    public static Request makeWithdrawRequest() {
        Request request = new Request();
        request.reqType = RequestType.WITHDRAW;
        return request;
    }

    public static Request makeStartMatchRequestFirst(Player opponent) {
        Request request = new Request();
        request.reqType = RequestType.START_MATCH_FIRST;
        request.obj = opponent;
        return request;
    }

    public static Request makeStartMatchRequestSecond(Player opponent) {
        Request request = new Request();
        request.reqType = RequestType.START_MATCH_SECOND;
        request.obj = opponent;
        return request;
    }

    public static Request makeBattleActionRequest(BattleAction battleAction) {
        Request request = new Request();
        request.reqType = RequestType.BATTLE_ACTION;
        request.obj = battleAction;
        return request;
    }

    public static Request makeMatchInfoRequest(PlayerMatchInfo[] infos) {
        Request request = new Request();
        request.reqType = RequestType.MATCH_INFO;
        request.obj = infos;
        return request;
    }

    public static Request makeOnlineUsersRequest(List<String> usernames) {
        Request request = new Request();
        request.reqType = RequestType.TAKE_ONLINE_USERS;
        request.obj = usernames;
        return request;
    }

    public static Request makeBuyRequest(String cardName) {
        Request request = new Request();
        request.reqType = RequestType.BUY;
        request.obj = cardName;
        return request;
    }

    public static Request makeSellRequest(String collectionID) {
        Request request = new Request();
        request.reqType = RequestType.SELL;
        request.obj = collectionID;
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
