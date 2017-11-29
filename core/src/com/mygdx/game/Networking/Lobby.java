package com.mygdx.game.Networking;

import Menu.Player;

import java.util.List;

public class Lobby {
    private List<Player> playerList;
    private String name;

    public Lobby(String name)
    {
        this.name = name;
    }

    public void joinLobby(Player player)
    {
        playerList.add(player);
    }

    public void leaveLobby(Player player)
    {
        playerList.remove(player);
    }


}
