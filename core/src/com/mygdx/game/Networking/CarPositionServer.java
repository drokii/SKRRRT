package com.mygdx.game.Networking;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;

public class CarPositionServer {

    private static Server server;
    private static Kryo kryo;

    public static void main(String[] args) throws IOException {
        server = new Server();
        server.start();
        server.bind(54555, 54777);

        kryo = server.getKryo();
        kryo.register(SampleRequest.class);
        kryo.register(SampleResponse.class);
        addListenersToServer(server);


    }

    private static void addListenersToServer(Server server){
        server.addListener(new Listener() {
            public void received (Connection connection, Object object) {
                if (object instanceof SampleRequest) {
                    SampleRequest request = (SampleRequest)object;
                    System.out.println(request.text);

                    SampleResponse response = new SampleResponse();
                    System.out.println(response.text);
                    connection.sendTCP(response);
                }
            }
        });
    }



}
