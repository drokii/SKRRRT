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
        kryo.register(CreateLobbyRequest.class);
        kryo.register(CreateLobbyResponse.class);
    }

    static public class LoginRequest{

    }
    static public class LoginResponse{

    }
    static public class RetrieveLobbiesRequest{

    }
    static public class RetrieveLobbiesResponse{

    }
    static public class JoinLobbyRequest{

    }
    static public class JoinLobbyResponse{

    }

    static public class CreateLobbyRequest{

    }

    static public class CreateLobbyResponse{

    }

    static public class GameStartRequest{

        private Vector2 speed;
        private float angularSpeed;
        private String nickname;

        public GameStartRequest(Vector2 speed, float angularSpeed, String nickname) {
            this.speed = speed;
            this.angularSpeed = angularSpeed;
            this.nickname = nickname;
        }

        public Vector2 getSpeed() {
            return speed;
        }

        public float getAngularSpeed() {
            return angularSpeed;
        }

        public String getNickname() {
            return nickname;
        }


    }
    static public class GameStartResponse{

    }
    static public class GameUpdateRequest{
        private String nickname;

        public String getNickname() {
            return nickname;
        }

        public GameUpdateRequest(String nickname) {
            this.nickname = nickname;
        }
    }

}