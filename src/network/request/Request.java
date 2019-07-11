package network.request;

import javafx.util.Pair;
import models.BattleAction;
import models.GlobalChat;
import models.Player;
import models.card.Card;
import models.match.Match;
import models.match.MatchRequest;

import java.io.Serializable;
import java.util.List;

public class Request implements Serializable {
    private static final long serialVersionUID = 6529685098267757052L;

    private String authToken;
    RequestType reqType;
    private Object obj;

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

    public static Request makeMatchRequest(Match match) {
        Request request = new Request();
        request.reqType = RequestType.MATCH;
        request.obj = match;
        return request;
    }

    public static Request makeWatchMatchRequest(Match match) {
        Request request = new Request();
        request.reqType = RequestType.WATCH_MATCH;
        request.obj = match;
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

    public static Request makeHesoyamRequest() {
        Request request = new Request();
        request.reqType = RequestType.HESOYAM;
        return request;
    }

    public static Request makeScoreboardRequest() {
        Request request = new Request();
        request.reqType = RequestType.SCOREBOARD;
        request.obj = Player.getPlayersSortedForScoreboard();
        return request;
    }

    public static Request makeOnlineMatchesRequest(List<Pair<String, String>> matches) {
        Request request = new Request();
        request.reqType = RequestType.ONLINE_MATCHES;
        request.obj = matches;
        return request;
    }

    public static Request makeWatchOnlineRequest(Pair<String, String> names) {
        Request request = new Request();
        request.reqType = RequestType.WATCH_ONLINE;
        request.obj = names;
        return request;
    }

    public static Request makeCardRequest(Card card) {
        Request request = new Request();
        request.reqType = RequestType.CARD;
        request.obj = card;
        return request;
    }

    public static Request makeTimeLimitRequest(String limit) {
        Request request = new Request();
        request.reqType = RequestType.TIME_LIMIT;
        request.obj = limit;
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
