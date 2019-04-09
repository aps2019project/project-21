package models;

import models.Match.Match;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private static List<Player> players = new ArrayList<>();
    private String username;
    private String password;
    private Collection collection;
    private int drake;
    private List<Match> matchHistory = new ArrayList<>();

    public Player(String username) {
        this.drake = 15000;
        this.username = username;
    }

    void showMatchHistory() {

    }

    public static void createAccount(String username){

    }

    public static void login(String username, String password) {

    }

    public void logout(){

    }

    public static void showLeaderBoard(){

    }

    public static void help(){}
}

