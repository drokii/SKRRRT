package com.mygdx.game.Networking.Server;

import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.mygdx.game.Networking.Network;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.mygdx.game.Networking.Network.*;

public class GameServer {

    private static Server server;
    private List<String> players;
    private Map<String, Velocities> velocityMap;
    private Map<String, Vector2> positionMap;


    public GameServer() throws IOException {

        players = new ArrayList<String>();
        server = new Server(100000, 100000);
        server.start();
        server.bind(54376, 56432);

        register(server);
        addListenersToServer(server);
        positionMap = new HashMap<>();

    }

    private void addListenersToServer(final Server server) {
        server.addListener(new Listener() {
            public void received(Connection connection, Object object) {

                /*
                If a gamestart request is recieved, the sending player is added to the playerlist
                If the playerlist contains 2 players, the gamestart response is sent with
                a Map containing players and their spawns.
                */

                if (object instanceof GameStartRequest) {
                    players.add(((GameStartRequest) object).getNickname());
                    if (players.size() >= 2) {
                        server.sendToAllTCP(generateGameStartResponse());
                        velocityMap = new HashMap<>();

                        for (String player :
                                players) {
                            velocityMap.put(player, new Velocities(new Vector2(0, 0), 0f));
                        }
                    }

                }
                /*
                If a gameUpdateRequest is recieved, the Server updates the local velocity Map and sends the
                sending player the updated Map with the speeds of the other players.
                */
                if (object instanceof GameUpdateRequest) {
                    String nickname = ((GameUpdateRequest) object).getNickname();
                    Vector2 linearSpeed = ((GameUpdateRequest) object).getVelocity();
                    float angularSpeed = ((GameUpdateRequest) object).getAngularVelocity();
                    velocityMap.remove(nickname);
                    velocityMap.put(nickname, new Velocities(linearSpeed, angularSpeed));

                    GameUpdateResponse gameUpdateResponse = new GameUpdateResponse(velocityMap);
                    connection.sendTCP(gameUpdateResponse);
                }
                if (object instanceof GameUpdatePositionRequest) {
                    String nickname = ((GameUpdatePositionRequest) object).name;
                    Vector2 position = ((GameUpdatePositionRequest) object).position;
                    positionMap.remove(nickname);
                    positionMap.put(nickname, position);

                    GameUpdatePositionResponse gameUpdateResponse = new GameUpdatePositionResponse();
                    gameUpdateResponse.positions = positionMap;
                    connection.sendTCP(gameUpdateResponse);
                }

            }
    });
}

    private GameStartResponse generateGameStartResponse() {

        GameStartResponse nsr = new GameStartResponse();
        Vector2 v1 = new Vector2(1700, 600);
        Vector2 v2 = new Vector2(1700, 700);
        Vector2 v3 = new Vector2(1700, 760);
        Vector2 v4 = new Vector2(1700, 900);

        List<Vector2> vector2List = new ArrayList<Vector2>();
        vector2List.add(v1);
        vector2List.add(v2);
        vector2List.add(v3);
        vector2List.add(v4);

        Map<String, Vector2> startPositions = new HashMap<>();
        int i = 0;
        for (String p : players) {
            startPositions.put(p, vector2List.get(i));
            i++;
        }
        nsr.setStartPositions(startPositions);
        return nsr;
    }
}



