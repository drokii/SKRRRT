package com.mygdx.game.Networking.Client;

import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.mygdx.game.Networking.Network;

import java.io.IOException;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class GameClient {
    Client client;
    Map<String, Vector2> spawnLocations;

    public GameClient() throws IOException {
        client = new Client();
        client.start();
        client.connect(5000, "127.0.0.1", 54555, 54777);

        addListeners(client);
    }

    public static void main(String[] args) throws IOException {
        new GameClient();

    }

    public static void addListeners(Client client) {
        client.addListener(new Listener()  {
            public void received(Connection connection, Object object) {
                if (object instanceof Network.GameStartResponse) {
                    //begin countdown
                }
            }
        });
    }

    public Map<String, Vector2> getGameStartResponse(){
        try {
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

