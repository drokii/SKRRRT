package com.mygdx.game.Networking.Client;

import Menu.Menu;
import Menu.Player;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.mygdx.game.Networking.Lobby;
import com.mygdx.game.Networking.Network;

import java.io.IOException;

public class LobbyClient {
    private Client client;
    private Menu menu;

    public LobbyClient(Menu menu) throws IOException {
        this.menu = menu;
        client = new Client();
        client.start();
        client.connect(5000, "127.0.0.1", 62452, 62452);
        Network.register(client);

        addListeners(client);
    }

    public void CreateLobby(String name)
    {
        Network.CreateLobbyRequest lobby = new Network.CreateLobbyRequest(name);
        client.sendTCP(lobby);
    }

    public void joinLobby(String lobbyName, Player player)
    {
        Network.JoinLobbyRequest joinRequest = new Network.JoinLobbyRequest(lobbyName, player);
        client.sendTCP(joinRequest);
    }


    public void addListeners(Client client) {
        client.addListener(new Listener()  {
            public void received(Connection connection, Object object) {
                if (object instanceof Network.CreateLobbyResponse) {
                    menu.setLobbies(((Network.CreateLobbyResponse) object).getLobby());
                }
            }
        });
    }
}
