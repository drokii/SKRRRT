package com.mygdx.game.Networking.Client;

import Menu.Menu;
import Menu.Player;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.mygdx.game.Networking.Lobby;
import com.mygdx.game.Networking.Network;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LobbyClient {
    private static final Logger LOGGER = Logger.getLogger(LobbyClient.class.getName());
    private Client client;
    private Menu menu;
    private String ip = null;

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

    public void CreatedServer(String ip, Lobby lobby)
    {
        Network.CreatedGameServer gameServer = new Network.CreatedGameServer(ip, lobby);
        client.sendTCP(gameServer);
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

    public void playerReady(Lobby lobby, Player player)
    {
        Network.playerReadyRequest readyRequest = new Network.playerReadyRequest(lobby, player);
        client.sendTCP(readyRequest);
    }

    public void getReadyPlayers(Lobby lobby)
    {
        Network.GetReadyRequest readyRequest = new Network.GetReadyRequest(lobby);
        client.sendTCP(readyRequest);
    }

    public void addListeners(Client client) {
        client.addListener(new Listener()  {
            public void received(Connection connection, Object object) {
                if (object instanceof Network.CreateLobbyResponse)
                {
                    menu.setLobbies(((Network.CreateLobbyResponse) object).getLobby());
                }
                if(object instanceof Network.JoinLobbyResponse)
                {
                    Lobby lobby = ((Network.JoinLobbyResponse) object).getLobby();
                    menu.setLobbyPlayers(lobby);
                }
                if(object instanceof Network.playerReadyResponse)
                {
                    menu.setReadyPlayers(((Network.playerReadyResponse) object).getReadyPlayers());
                }
                if(object instanceof Network.AllReadyResponse)
                {
                    menu.gameStart();
                }
                if(object instanceof Network.CreatedGameServer)
                {
                    ip = (((Network.CreatedGameServer) object).getIp());
                    try {
                        menu.CreateGameClient(ip);
                    } catch (IOException e) {
                        LOGGER.log( Level.SEVERE, e.toString(), e );
                    }

                }
            }
        });
    }
}
