package com.mygdx.game.Networking;

import Menu.Player;

import java.util.ArrayList;
import java.util.List;

public class Lobby {
    private int id;
    private String name;
    private String map;
    private List<Player> players;

    public Lobby(String name){
        this.name = name;
        this.players = new ArrayList<Player>();
    }

    public void setId(int id){
        this.id = id;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public boolean joinLobby(Player player) {
        return this.players.add(player);
    }

    public boolean leaveLobby(Player player)
    {
        return this.players.remove(player);
    }

    @Override
    public String toString() {
        return name;
    }
}

