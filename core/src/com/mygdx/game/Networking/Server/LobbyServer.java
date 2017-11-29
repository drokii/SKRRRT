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

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LobbyServer extends Application {

    private static Server loginServer;
    private static Kryo kryo;
    private static List<Lobby> lobbyList;

    public static void main(String[] args) throws IOException, SQLException {
        lobbyList = new ArrayList<Lobby>();
        loginServer = new Server();
        loginServer.start();
        loginServer.bind(62452, 34142);

        kryo = loginServer.getKryo();
        kryo.register(Network.CreateLobbyRequest.class);
        kryo.register(Network.CreateLobbyResponse.class);
        kryo.register(ArrayList.class);
        addListenersToServer(loginServer);
    }

    private static void addListenersToServer(Server server) {
        server.addListener(new Listener() {
            public void received(Connection connection, Object object) {
                if (object instanceof Network.CreateLobbyRequest) {
                    Lobby lobby = new Lobby(((Network.CreateLobbyRequest) object).getLobbyName());
                    lobbyList.add(lobby);
                    Network.CreateLobbyResponse response = new Network.CreateLobbyResponse(lobbyList);
                    connection.sendTCP(response);
                    connection.close();
                }
            }
        });
    }

    @Override
    public void start(Stage primaryStage) {

    }
}
