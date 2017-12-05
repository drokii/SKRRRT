package com.mygdx.game.Networking.Server;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.mygdx.game.Networking.Lobby;
import com.mygdx.game.Networking.LoginResponse;
import com.mygdx.game.Networking.Network;
import javafx.application.Application;
import javafx.stage.Stage;
import sun.nio.ch.Net;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LobbyServer extends Application {

    private static Server lobbyServer;
    private static Kryo kryo;
    private static List<Lobby> lobbyList;

    public static void main(String[] args) throws IOException, SQLException {
        lobbyList = new ArrayList<Lobby>();
        lobbyServer = new Server();
        lobbyServer.start();
        lobbyServer.bind(62452, 62452);

        Network.register(lobbyServer);
        addListenersToServer(lobbyServer);
    }

    private static void addListenersToServer(Server server) {
        server.addListener(new Listener() {
            public void received(Connection connection, Object object) {
                if (object instanceof Network.CreateLobbyRequest) {
                    Lobby lobby = new Lobby(((Network.CreateLobbyRequest) object).getLobbyName());
                    lobbyList.add(lobby);
                    connection.sendTCP(new Network.CreateLobbyResponse(lobbyList));
                }
                if(object instanceof Network.JoinLobbyRequest)
                {
                    Lobby lobby = new Lobby(((Network.JoinLobbyRequest) object).getLobby());
                    int index = lobbyList.indexOf(lobby) + 1;
                    lobbyList.get(index).joinLobby(((Network.JoinLobbyRequest) object).getPlayer());
                    connection.sendTCP(new Network.JoinLobbyResponse(lobbyList.get(index)));
                }
                if(object instanceof Network.getLobbyRequest)
                {
                    connection.sendTCP(new Network.CreateLobbyResponse(lobbyList));
                }
            }
        });
    }

    @Override
    public void start(Stage primaryStage) {

    }
}
