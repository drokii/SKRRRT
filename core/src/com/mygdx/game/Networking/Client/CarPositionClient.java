package com.mygdx.game.Networking.Client;

import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.mygdx.game.Networking.Network;

import java.io.IOException;

public class CarPositionClient {

    public CarPositionClient() throws IOException {
        Client client = new Client();
        client.start();
        client.connect(5000, "127.0.0.1", 54555, 54777);

        Kryo kryoClient = client.getKryo();

        addListeners(client);
    }

    public static void main(String[] args) throws IOException {
        new CarPositionClient();

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
    public Network.GameStartRequest sendGameStartRequest(String name, Vector2 velocity, float angularVelocity){
        return new Network.GameStartRequest(velocity,angularVelocity,name);
    }
}

