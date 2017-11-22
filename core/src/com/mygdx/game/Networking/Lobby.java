package com.mygdx.game.Networking;

import Menu.Player;

import java.util.List;

public class Lobby {
    private List<Player> playerList;
    private int id;

    public Lobby(int id)
    {
        this.id = id;
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
