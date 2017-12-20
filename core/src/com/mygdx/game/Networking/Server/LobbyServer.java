package com.mygdx.game.Networking.Server;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.mygdx.game.Networking.Lobby;
import com.mygdx.game.Networking.LoginResponse;
import com.mygdx.game.Networking.Network;
import com.sun.org.apache.bcel.internal.generic.NEW;
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
    private static int[] totalCon;

    public static void main(String[] args) throws IOException, SQLException {
        lobbyList = new ArrayList<Lobby>();
        totalCon = new int[50];
        lobbyServer = new Server();
        lobbyServer.start();
        lobbyServer.bind(62452, 62452);

        Network.register(lobbyServer);
        addListenersToServer(lobbyServer);
    }

    private static void addListenersToServer(final Server server) {
        server.addListener(new Listener() {
            public void received(Connection connection, Object object) {
                if (object instanceof Network.CreateLobbyRequest) {
                    Lobby lobby = new Lobby(((Network.CreateLobbyRequest) object).getLobbyName());
                    lobbyList.add(lobby);
                    for (int id: totalCon) {
                        if(id != 0)
                        server.sendToTCP(id, new Network.CreateLobbyResponse(lobbyList));
                    }
                    //server.sendToAllTCP(new Network.CreateLobbyResponse(lobbyList));
                }
                if(object instanceof Network.JoinLobbyRequest)
                {
                    int index = ((Network.JoinLobbyRequest) object).getIndex();
                    lobbyList.get(index).joinLobby(((Network.JoinLobbyRequest) object).getPlayer());
                    for(int i = 0; i< lobbyList.get(index).getIds().length; i++)
                    {
                        if(lobbyList.get(index).getIds()[i] == 0)
                        {
                            lobbyList.get(index).getIds()[i] = (connection.getID());
                            break;
                        }
                    }
                    for (Integer id: lobbyList.get(index).getIds()) {
                        if(id != 0)
                        {
                            server.sendToTCP(id, new Network.JoinLobbyResponse(lobbyList.get(index)));
                        }
                    }
                }
                if(object instanceof Network.getLobbyRequest)
                {
                    connection.sendTCP(new Network.CreateLobbyResponse(lobbyList));
                    for (int i : totalCon) {
                        if (totalCon[i] == 0)
                        {
                            totalCon[i] = connection.getID();
                            break;
                        }
                    }
                }
                if(object instanceof Network.playerReadyRequest)
                {
                    //Lobby lobby = ;
                    //lobbyList.get(index).getReadyPlayers().add(((Network.playerReadyRequest) object).getPlayer());
                    //if(lobbyList.get(index).getReadyPlayers().size() == lobbyList.get(index).getPlayers().size())
                    //{
                        //TODO: allreadyresponse
                    //}
                    //else
                    //{
                    //for (Integer id: lobbyList.get(index).getIds()) {
                    //        if(id != 0)
                    //        {
                    //            server.sendToTCP(id, new Network.playerReadyResponse(lobbyList.get(index).getReadyPlayers()));
                    //        }
                    //}
                    //}
                }
            }
        });
    }

    @Override
    public void start(Stage primaryStage) {

    }
}
