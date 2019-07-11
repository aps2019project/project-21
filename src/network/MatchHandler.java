package network;

import javafx.application.Platform;
import javafx.util.Pair;
import models.BattleAction;
import models.Player;
import models.match.Match;
import models.match.MatchRequest;
import network.request.Request;
import view.Scoreboard;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

public class MatchHandler extends Thread {
    private static List<MatchHandler> matchHandlers = new ArrayList<>();
    private ClientHandler first;
    private ClientHandler second;
    private Player player1;
    private Player player2;
    private MatchRequest matchRequest;
    private ArrayBlockingQueue<Pair<ClientHandler, Request>> requests = new ArrayBlockingQueue<>(1000);
    private int readyCount = 0;
    private Match match;
    private boolean isEnded;
    private List<BattleAction> battleActions = new ArrayList<>();
    private List<ClientHandler> watchingClients = new ArrayList<>();

    MatchHandler(ClientHandler first, ClientHandler second, MatchRequest matchRequest) {
        this.first = first;
        this.second = second;
        this.matchRequest = matchRequest;
        this.player1 = first.getPlayer();
        this.player2 = second.getPlayer();
        matchHandlers.add(this);
        first.sendOnlineMatchesToAll();
    }

    @Override
    public void run() {
        while (!isEnded) {
            while (requests.isEmpty()) ;
            Pair<ClientHandler, Request> requestPair = requests.poll();
            ClientHandler cl = requestPair.getKey();
            Request request = requestPair.getValue();
            handleRequest(cl, request);
        }
    }

    private void handleRequest(ClientHandler cl, Request request) {
        if (cl == null || request == null)
            return;
        switch (request.getReqType()) {
            case READY:
                readyCount++;
                if (readyCount >= 2)
                    startMatch();
                break;
            case WITHDRAW:
                isEnded = true;
                getOther(cl).write(Request.makeWithdrawRequest());
                finish();
                break;
            case BATTLE_ACTION:
                handleAction((BattleAction) request.getObj());
                break;
            case PLAYER:
                Player.saveOldPlayer((Player) request.getObj());
                readyCount--;
                if (readyCount <= 0) {
                    finish();
                    first.sendScoreboardToAll();
                }
                Platform.runLater(Scoreboard::drawScoreboardForHost);
                break;
        }
    }

    void finish() {
        first.setMatchNull();
        second.setMatchNull();
        matchHandlers.remove(this);
        first.sendOnlineMatchesToAll();
    }

    private void handleAction(BattleAction battleAction) {
        battleActions.add(battleAction);
        Request request = Request.makeBattleActionRequest(battleAction);
        first.write(request);
        second.write(request);
        for (ClientHandler clientHandler : watchingClients)
            clientHandler.write(request);
    }

    private ClientHandler getOther(ClientHandler cl) {
        if (first.getPlayer() == cl.getPlayer())
            return second;
        return first;
    }

    private void startMatch() {
        match = new Match(player1, player2, matchRequest);
        Match.setCurrentMatch(match);
        first.write(Request.makeStartMatchRequestFirst(player2));
        second.write(Request.makeStartMatchRequestSecond(player1));
        first.write(Request.makeMatchRequest(match));
        second.write(Request.makeMatchRequest(match));
    }

    static List<MatchHandler> getMatchHandlers() {
        return matchHandlers;
    }

    Player getPlayer1() {
        return player1;
    }

    Player getPlayer2() {
        return player2;
    }

    void addRequest(ClientHandler cl, Request request) {
        requests.add(new Pair<>(cl, request));
    }

    void addWatchingClient(ClientHandler cl) {
        watchingClients.add(cl);
        cl.write(Request.makeWatchMatchRequest(match));
        for (BattleAction b : battleActions)
            cl.write(Request.makeBattleActionRequest(b));
    }

    static MatchHandler getMatchHandlerByNames(Pair<String, String> pair) {
        for (MatchHandler m : matchHandlers)
            if (m.player1.getUsername().equals(pair.getKey()) && m.player2.getUsername().equals(pair.getValue()))
                return m;
        return null;
    }
}
