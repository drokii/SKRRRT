package com.mygdx.game.Networking.Client;

import Menu.Player;
import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.mygdx.game.Gameplay.Car;
import com.mygdx.game.Networking.Network;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class GameClient {
    //private final Car car;
    //private final Player player;
    private Client client;
    private Map<String, Vector2> spawnLocations;

    public GameClient(/*Car car, Player player, */String ip) throws IOException {
        //this.car = car;
        //this.player = player;
        client = new Client();
        client.start();
        //GET GAMEserver host ip through constructor
        client.connect(5000, ip, 54376, 56432);
        System.out.println("gameclient connected");

        addListeners(client);
    }

    public void addListeners(Client client) {
        client.addListener(new Listener()  {
            public void received(Connection connection, Object object) {
                if (object instanceof Network.GameStartResponse) {
                    //spawnLocations = ((Network.GameStartResponse) object).startPositions;

                }
                if (object instanceof Network.GameUpdateResponse) {

                    /*Network.GameUpdateRequest gameUpdateRequest = new Network.GameUpdateRequest();
                    gameUpdateRequest.nickname = player.getName();
                    gameUpdateRequest.angularVelocity = car.getKartBody().getAngularVelocity();
                    gameUpdateRequest.velocity = car.getKartBody().getLinearVelocity();
                    client.sendTCP(gameUpdateRequest);*/
                }
            }

        });
    }


    /**
     * Sends a game start request implying that the client is ready to run the game, and then waits for
     * a response from the Game Server. This happens in another thread.
     * @return 
     */
    public Map<String, Vector2> getGameStartResponse(){
        try {

            Network.GameStartRequest gsr = new Network.GameStartRequest();
            //gsr.nickname = player.getName();
            client.sendTCP(gsr);

            Future<Map<String, Vector2>> waitForResponse = waitForResponse();
            Map<String, Vector2> spawnpositions = waitForResponse.get();
            return spawnpositions;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        } catch (ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Future<Map<String, Vector2>> waitForResponse() throws InterruptedException {
        CompletableFuture<Map<String, Vector2>> completableFuture
                = new CompletableFuture<>();

        Executors.newCachedThreadPool().submit(() -> {

            while (!completableFuture.isDone()) {
                System.out.println("Calculating...");

                if (spawnLocations != null) {
                    completableFuture.complete(spawnLocations);
                } else {
                    Thread.sleep(300);
                }
            }
            return null;
        });

        return completableFuture;
    }
}

