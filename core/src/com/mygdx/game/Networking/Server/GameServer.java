package com.mygdx.game.Networking.Server;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.mygdx.game.Networking.Network;

import java.io.IOException;
import java.util.List;

public class GameServer {

    private static Server server;
    private static Kryo kryo;
    private List<PlayerInstance> players;
    private int lobbySize = 2; //to be substituted by lobby


    public GameServer() throws IOException {

        server = new Server();
        server.start();
        server.bind(54555, 54777);

        Network.register(server);
        addListenersToServer(server);

    }

    public static void main(String[] args) throws IOException {
        new GameServer();
    }

    private void addListenersToServer(final Server server) {
        server.addListener(new Listener() {
            public void received(Connection connection, Object object) {
                if (object instanceof Network.GameStartRequest) {

                    Network.GameStartRequest player = (Network.GameStartRequest) object;
                    players.add(new PlayerInstance(player.nickname, player.speed, player.angularSpeed));

                    if (players.size() >= lobbySize) {
                        for (Connection c : server.getConnections()
                                ) {

                            c.sendTCP(Network.GameStartResponse.class);

                        }
                    }

                }
                if (object instanceof Network.GameUpdateRequest) {

                    connection.sendTCP(players);

                }
            }
        });
    }
}



