package network;

import javafx.util.Pair;
import models.Player;
import models.match.Match;
import models.match.MatchRequest;
import network.message.Request;
import view.BattleView;

import java.util.ArrayDeque;
import java.util.Queue;

public class MatchHandler extends Thread {
    private ClientHandler first;
    private ClientHandler second;
    private MatchRequest matchRequest;
    private Queue<Pair<ClientHandler, Request>> requests = new ArrayDeque<>();
    private int readyCount;
    private Match match;

    public MatchHandler(ClientHandler first, ClientHandler second, MatchRequest matchRequest) {
        this.first = first;
        this.second = second;
        this.matchRequest = matchRequest;
    }

    @Override
    public void run() {
        while (true) {
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
        }
    }

    private void startMatch() {
//        match = new Match(Player.getCurrentPlayer(), null, matchRequest);
//        Match.setCurrentMatch(match);
//        BattleView battleView = new BattleView();
//        match.setBattleView(battleView);
//        battleView.run();
    }

    public void addRequest(ClientHandler cl, Request request) {
        requests.add(new Pair<>(cl, request));
    }
}
