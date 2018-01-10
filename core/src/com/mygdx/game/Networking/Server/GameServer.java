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

public class GameServer {

    private static Server gameServer;
    private List<String> players;
    private Map<String, Velocities> velocityMap;



    public GameServer() throws IOException {

        players = new ArrayList<String>();
        gameServer = new Server();
        gameServer.start();
        gameServer.bind(54376, 56432);

        Network.register(gameServer);
        addListenersToServer(gameServer);

    }

    public static void main(String[] args) throws IOException {
        new GameServer();
    }

    private void addListenersToServer(final Server server) {
        server.addListener(new Listener() {
            public void received(Connection connection, Object object) {

                /*
                If a gamestart request is recieved, the sending player is added to the playerlist
                If the playerlist contains 2 players, the gamestart response is sent with
                a map containing players and their spawns.
                */

                if (object instanceof Network.GameStartRequest) {

                    players.add(((Network.GameStartRequest) object).nickname);
                    if(players.size() <= 2){
                        server.sendToAllTCP(generateGameStartResponse(players));
                        velocityMap = new HashMap<>();

                        for (String player :
                                players) {
                            velocityMap.put(player, new Velocities(new Vector2(0,0),0f));
                        }
                    }

                }
                /*
                If a gameUpdateRequest is recieved, the server updates the local velocity map and sends the
                sending player the updated map with the speeds of the other players.
                */
                if (object instanceof Network.GameUpdateRequest) {

                    String nickname = ((Network.GameUpdateRequest) object).nickname;
                    Vector2 linearSpeed = ((Network.GameUpdateRequest) object).velocity;
                    float angularSpeed = ((Network.GameUpdateRequest) object).angularVelocity;
                    velocityMap.put(nickname, new Velocities(linearSpeed, angularSpeed));

                    Network.GameUpdateResponse gameUpdateResponse = new Network.GameUpdateResponse();
                    gameUpdateResponse.movementVectors = velocityMap;

                    connection.sendTCP(gameUpdateResponse);

                }
            }
        });
    }

    private Network.GameStartResponse generateGameStartResponse(List<String> names){

        Network.GameStartResponse nsr = new Network.GameStartResponse();
        Vector2 v1 = new Vector2(1700, 600);
        Vector2 v2 = new Vector2(1700, 630);
        Vector2 v3 = new Vector2(1700, 660);
        Vector2 v4 = new Vector2(1700, 690);

        List<Vector2> vector2List = new ArrayList<Vector2>();
        vector2List.add(v1);
        vector2List.add(v2);
        vector2List.add(v3);
        vector2List.add(v4);

        Map<String, Vector2> startPositions = new HashMap<String, Vector2> ();
        int i = 0;
        for (String p :
                players) {
            startPositions.put(p, vector2List.get(i));
            i++;
        }
        nsr.startPositions = startPositions ;
        return nsr;
    }
}



