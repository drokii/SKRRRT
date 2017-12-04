package com.mygdx.game.Networking;

import Menu.Player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Lobby implements Serializable{
    private int id;
    private String name;
    private String map;
    private List<Player> players;
    private Player mainPlayer;

    public Lobby(String name){
        this.name = name;
        this.players = new ArrayList<Player>();
    }

    public Lobby()
    {
    }

    public void setId(int id){
        this.id = id;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Player getMainPlayer(){ return mainPlayer;}

    public void setMainPlayer(Player mainPlayer){this.mainPlayer = mainPlayer;}

    public void joinLobby(Player player) {
        players.add(player);
    }

    public void leaveLobby(Player player)
    {
        players.remove(player);
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}

