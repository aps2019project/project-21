package network;

import javafx.application.Platform;
import javafx.util.Pair;
import models.BattleAction;
import models.Player;
import models.match.Match;
import models.match.MatchRequest;
import network.message.Request;
import view.Scoreboard;

import java.util.concurrent.ArrayBlockingQueue;

public class MatchHandler extends Thread {
    private ClientHandler first;
    private ClientHandler second;
    private Player player1;
    private Player player2;
    private MatchRequest matchRequest;
    private ArrayBlockingQueue<Pair<ClientHandler, Request>> requests = new ArrayBlockingQueue<>(1000);
    private int readyCount = 0;
    private Match match;
    private boolean isEnded;

    MatchHandler(ClientHandler first, ClientHandler second, MatchRequest matchRequest) {
        this.first = first;
        this.second = second;
        this.matchRequest = matchRequest;
        this.player1 = first.getPlayer();
        this.player2 = second.getPlayer();
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
    }

    private void handleAction(BattleAction battleAction) {
        first.write(Request.makeBattleActionRequest(battleAction));
        second.write(Request.makeBattleActionRequest(battleAction));
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

    void addRequest(ClientHandler cl, Request request) {
        requests.add(new Pair<>(cl, request));
    }
}
