package com.mygdx.game.Networking;

import Menu.Player;
import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;
import com.mygdx.game.Networking.Server.PlayerInstance;

import java.util.List;

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
    public static class CreateLobbyRequest{
        private String lobbyName;
        private List<Player> players;

        public CreateLobbyRequest(String name)
        {
            lobbyName = name;
        }
        public CreateLobbyRequest()
        {}
        public String getLobbyName() {
            return lobbyName;
        }

        public void setLobbyName(String lobbyName) {
            this.lobbyName = lobbyName;
        }

        public List<Player> getPlayers() {
            return players;
        }

        public void setPlayers(List<Player> players) {
            this.players = players;
        }

    }
    public static class CreateLobbyResponse{
        private List<Lobby> lobbyList;

        public CreateLobbyResponse(List<Lobby> lobbyList)
        {
            this.lobbyList = lobbyList;
        }
        public CreateLobbyResponse()
        {}

        public List<Lobby> getLobby() {
            return lobbyList;
        }

        public void setLobby(List<Lobby> lobbyList) {
            this.lobbyList = lobbyList;
        }
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