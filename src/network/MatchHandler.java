package network;

import javafx.util.Pair;
import models.BattleAction;
import models.Player;
import models.match.Match;
import models.match.MatchRequest;
import network.message.Request;
import view.View;

import java.util.concurrent.ArrayBlockingQueue;

public class MatchHandler extends Thread {
    private ClientHandler first;
    private ClientHandler second;
    private Player player1;
    private Player player2;
    private MatchRequest matchRequest;
    private ArrayBlockingQueue<Pair<ClientHandler, Request>> requests = new ArrayBlockingQueue<>(1000);
    private int readyCount;
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
            System.out.println(4444);
            while (requests.isEmpty()) ;
            System.out.println(3333);
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
                System.out.println("2222");
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
        System.out.println("3333");
        match = new Match(player1, player2, matchRequest);
        Match.setCurrentMatch(match);
        first.write(Request.makeStartMatchRequestFirst(player2));
        second.write(Request.makeStartMatchRequestSecond(player1));
        try {
            Thread.sleep(250);
        } catch (Exception e) {
            View.printThrowable(e);
        }
        first.write(Request.makeMatchInfoRequest(match.getPlayersMatchInfo()));
        second.write(Request.makeMatchInfoRequest(match.getPlayersMatchInfo()));
//        BattleView battleView = new BattleView();
//        match.setBattleView(battleView);
//        battleView.run();
    }

    void addRequest(ClientHandler cl, Request request) {
        System.out.println(5555);
        requests.add(new Pair<>(cl, request));
    }
}
