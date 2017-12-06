package com.mygdx.game.Networking.Client;

import Menu.Menu;
import Menu.Player;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.mygdx.game.Networking.Lobby;
import com.mygdx.game.Networking.Network;
import com.mygdx.game.RaceGame;

import java.io.IOException;

public class LobbyClient {
    private Client client;
    private Menu menu;

    public LobbyClient(Menu menu) throws IOException {
        this.menu = menu;
        client = new Client();
        client.start();
        client.connect(5000, "127.0.0.1", 62452, 62452);
        //client.connect(5000, "145.93.169.164", 62452, 62452);
        Network.register(client);

        addListeners(client);
    }

    public void CreateLobby(String name)
    {
        Network.CreateLobbyRequest lobby = new Network.CreateLobbyRequest(name);
        client.sendTCP(lobby);
    }

    public void joinLobby(int index, Player player)
    {
        Network.JoinLobbyRequest joinRequest = new Network.JoinLobbyRequest(index, player);
        client.sendTCP(joinRequest);
    }

    public void getLobbyRequest()
    {
        Network.getLobbyRequest lobbyRequest = new Network.getLobbyRequest();
        client.sendTCP(lobbyRequest);
    }


    public void addListeners(Client client) {
        client.addListener(new Listener()  {
            public void received(Connection connection, Object object) {
                if (object instanceof Network.CreateLobbyResponse)
                {
                    menu.setLobbies(((Network.CreateLobbyResponse) object).getLobby());
                    menu.refreshLobbies();
                }
                if(object instanceof Network.JoinLobbyResponse)
                {
                    Lobby lobby = ((Network.JoinLobbyResponse) object).getLobby();
                    menu.setLobbyPlayers(lobby);
                }
            }
        });
    }
}
