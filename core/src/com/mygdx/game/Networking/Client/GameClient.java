package com.mygdx.game.Networking.Client;

import Menu.Player;
import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.mygdx.game.Gameplay.Car;
import com.mygdx.game.Networking.Network;
import com.mygdx.game.Networking.Server.Velocities;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameClient {
    private static final Logger LOGGER = Logger.getLogger(GameClient.class.getName());

    private Car car;
    private Player player;
    private Client client;
    private Map<String, Vector2> spawnLocations;

    public Map<String, Velocities> getVelocitiesMap() {
        return velocitiesMap;
    }

    private Map<String, Velocities> velocitiesMap;

    public GameClient(String ip, Player player) throws IOException {
        this.player = player;
        client = new Client(100000, 100000);
        client.start();
        //GET GAMEserver host ip through constructor
        client.connect(5000, ip, 54376, 56432);
        Network.register(client);

        addListeners(client);
    }

    public void setCar(Car car) {
        this.car = car;
        sendUpdate();
    }

    public void addListeners(Client client) {
        client.addListener(new Listener() {
            public void received(Connection connection, Object object) {
                if (object instanceof Network.GameStartResponse) {
                    spawnLocations = ((Network.GameStartResponse) object).getStartPositions();

                }
                if (object instanceof Network.GameUpdateResponse) {

                    velocitiesMap = ((Network.GameUpdateResponse) object).getMovementVectors();
                    if (car != null) {
                        sendUpdate();
                    }
                }
            }

        });
    }

    private void sendUpdate() {
        Network.GameUpdateRequest gameUpdateRequest = new Network.GameUpdateRequest();
        gameUpdateRequest.setNickname(player.getName());
        gameUpdateRequest.setAngularVelocity(car.getKartBody().getAngularVelocity());
        gameUpdateRequest.setVelocity(car.getKartBody().getLinearVelocity());
        client.sendTCP(gameUpdateRequest);
    }

    /**
     * Sends a game start request implying that the Client is ready to run the game, and then waits for
     * a response from the Game Server. This happens in another thread.
     *
     * @return
     */
    public Map<String, Vector2> getGameStartResponse() {
        try {

            Network.GameStartRequest gsr = new Network.GameStartRequest(player.getName());
            client.sendTCP(gsr);

            Future<Map<String, Vector2>> waitForResponse = waitForResponse();
            return Map<String, Vector2> spawnpositions = waitForResponse.get();

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            LOGGER.log( Level.SEVERE, e.toString(), e );
            return null;
        } catch (ExecutionException e) {
            LOGGER.log( Level.SEVERE, e.toString(), e );
            return null;
        }
    }

    public Future<Map<String, Vector2>> waitForResponse() {
        CompletableFuture<Map<String, Vector2>> completableFuture
                = new CompletableFuture<>();

        Executors.newCachedThreadPool().submit(() -> {

            while (!completableFuture.isDone()) {

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

