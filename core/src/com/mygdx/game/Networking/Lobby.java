package com.mygdx.game.Networking;

import Menu.Player;
import com.esotericsoftware.kryonet.Connection;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Lobby implements Serializable{
    private int id;
    private String name;
    private String map;
    private List<Player> players;
    private List<Player> readyPlayers;
    private int[] ids;

    private Player host;

    public Lobby(String name){
        this.name = name;
        this.players = new ArrayList<Player>();
        this.readyPlayers = new ArrayList<Player>();
        ids = new int[4];
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

    public Player getHost(){ return host;}

    public void setHost(Player host){this.host = host;}

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

    public int[] getIds() {
        return ids;
    }

    public void setIds(int[] ids) {
        this.ids = ids;
    }

    public List<Player> getReadyPlayers() {
        return readyPlayers;
    }

    public void setReadyPlayers(List<Player> readyPlayers) {
        this.readyPlayers = readyPlayers;
    }

    @Override
    public String toString() {
        return name;
    }
}

