package com.mygdx.game.Networking;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import java.io.IOException;

class CarPositionClient {
    public static void main(String[] args) throws IOException {
        Client client = new Client();
        client.start();
        client.connect(5000, "127.0.0.1", 54555, 54777);

        Kryo kryoClient = client.getKryo();
        kryoClient.register(SampleRequest.class);
        kryoClient.register(SampleResponse.class);

        SampleRequest request = new SampleRequest();
        client.sendTCP(request);
        addListeners(client);

    }

    public static void addListeners(Client client) {
        client.addListener(new Listener() {
            public void received(Connection connection, Object object) {
                if (object instanceof SampleResponse) {
                    SampleResponse response = (SampleResponse) object;
                    System.out.println(response.text);
                }
            }
        });
    }
}

