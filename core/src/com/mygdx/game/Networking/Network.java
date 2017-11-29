package com.mygdx.game.Networking;

import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;
import com.mygdx.game.Networking.Server.PlayerInstance;

public class Network {
    static public final int port = 54555;

    // This registers objects that are going to be sent over the network.
    static public void register(EndPoint endPoint) {
        Kryo kryo = endPoint.getKryo();
        kryo.register(GameStartRequest.class);
        kryo.register(GameStartResponse.class);
        kryo.register(PlayerInstance.class);
        kryo.register(GameUpdateRequest.class);
        kryo.register(String.class);
        kryo.register(Vector2.class);
        kryo.register(float.class);

    }

    static public class LoginRequest{

    }
    static public class LoginResponse{

    }
    static public class RetrieveLobbiesRequest{

    }
    public class RetrieveLobbiesResponse{

    }
    public class JoinLobbyRequest{

    }
    public class JoinLobbyResponse{

    }
    public static class GameStartRequest {

        public Vector2 speed;
        public float angularSpeed;
        public String nickname;

    }
    static public class GameStartResponse{

    }
    static public class GameUpdateRequest{
        public String nickname;
    }

}