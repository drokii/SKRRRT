package com.mygdx.game.Networking;

import Menu.Player;
import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.EndPoint;
import com.esotericsoftware.kryonet.Server;
import com.mygdx.game.Gameplay.Enums.ESkin;
import com.mygdx.game.Networking.Server.PlayerInstance;
import com.mygdx.game.Networking.Server.Velocities;
import javafx.application.Platform;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Network {
    static public final int port = 54555;

    // This registers objects that are going to be sent over the network.
    static public void register(EndPoint endPoint) {
        Kryo kryo = endPoint.getKryo();
        kryo.register(PlayerInstance.class);
        kryo.register(GameUpdateRequest.class);
        kryo.register(GameUpdateResponse.class);
        kryo.register(GameStartRequest.class);
        kryo.register(GameStartResponse.class);
        kryo.register(CreateLobbyRequest.class);
        kryo.register(CreateLobbyResponse.class);
        kryo.register(JoinLobbyRequest.class);
        kryo.register(JoinLobbyResponse.class);
        kryo.register(getLobbyRequest.class);
        kryo.register(playerReadyRequest.class);
        kryo.register(playerReadyResponse.class);
        kryo.register(AllReadyResponse.class);
        kryo.register(GetReadyRequest.class);
        kryo.register(CreatedGameServer.class);
        kryo.register(Map.class);
        kryo.register(Player.class);
        kryo.register(ESkin.class);
        kryo.register(ArrayList.class);
        kryo.register(Lobby.class);
        kryo.register(String.class);
        kryo.register(Vector2.class);
        kryo.register(HashMap.class);
        kryo.register(int[].class);
        kryo.register(float.class);
        kryo.register(Velocities.class);
    }

    static public class AllReadyResponse{
        public AllReadyResponse()
        {

        }
    }

    public static class CreatedGameServer
    {
        private String ip;
        private Lobby lobby;

        public CreatedGameServer()
        {}

        public CreatedGameServer(String ip, Lobby lobby)
        {
            this.ip = ip;
            this.lobby = lobby;
        }

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public Lobby getLobby() {
            return lobby;
        }

        public void setLobby(Lobby lobby) {
            this.lobby = lobby;
        }
    }

    public static class JoinLobbyRequest{
        private int index;
        private Player player;

        public JoinLobbyRequest(int index, Player player)
        {
            this.index = index;
            this.player = player;
        }

        public JoinLobbyRequest()
        {}

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public Player getPlayer() {
            return player;
        }

        public void setPlayer(Player player) {
            this.player = player;
        }


    }

    public static class getLobbyRequest{

        public getLobbyRequest()
        {}
    }

    public static class JoinLobbyResponse{
        private Lobby lobby;

        public JoinLobbyResponse(Lobby lobby)
        {
            this.lobby = lobby;
        }

        public JoinLobbyResponse()
        {}

        public Lobby getLobby() {
            return lobby;
        }

        public void setLobby(Lobby lobby) {
            this.lobby = lobby;
        }
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

    public static class playerReadyRequest{
        private Lobby lobby;
        private Player player;

        public playerReadyRequest()
        {}

        public playerReadyRequest(Lobby lobby, Player player)
        {
            this.lobby = lobby;
            this.player = player;
        }

        public Lobby getLobby() {
            return lobby;
        }

        public void setLobby(Lobby lobby) {
            this.lobby = lobby;
        }

        public Player getPlayer() {
            return player;
        }

        public void setPlayer(Player player) {
            this.player = player;
        }
    }

    public static class playerReadyResponse{
        private List<Player> readyPlayers;

        public playerReadyResponse()
        {}

        public playerReadyResponse(List<Player> playerList)
        {
            readyPlayers = playerList;
        }

        public List<Player> getReadyPlayers() {
            return readyPlayers;
        }

        public void setReadyPlayers(List<Player> readyPlayers) {
            this.readyPlayers = readyPlayers;
        }
    }

    public static class GetReadyRequest{
        private Lobby lobby;

        public GetReadyRequest(Lobby lobby)
        {
            this.lobby = lobby;
        }

        public GetReadyRequest()
        {}

        public Lobby getLobby() {
            return lobby;
        }

        public void setLobby(Lobby lobby) {
            this.lobby = lobby;
        }
    }

    public static class GameStartRequest {
        private String nickname;

        public GameStartRequest()
        {

        }

        public GameStartRequest(String nickname)
        {
            this.nickname = nickname;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }
    }

    public static class GameStartResponse{
        private Map<String,Vector2> startPositions;

        public GameStartResponse()
        {}

        public GameStartResponse(Map<String, Vector2> map)
        {
            this.startPositions = map;
        }

        public Map<String, Vector2> getStartPositions() {
            return startPositions;
        }

        public void setStartPositions(Map<String, Vector2> startPositions) {
            this.startPositions = startPositions;
        }
    }

    public static class GameUpdateRequest{
        private String nickname;
        private Vector2 velocity;
        private float angularVelocity;

        public GameUpdateRequest()
        {}

        public GameUpdateRequest(String nickname, Vector2 velocity, float angularVelocity)
        {
            this.nickname = nickname;
            this.velocity = velocity;
            this.angularVelocity = angularVelocity;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public Vector2 getVelocity() {
            return velocity;
        }

        public void setVelocity(Vector2 velocity) {
            this.velocity = velocity;
        }

        public float getAngularVelocity() {
            return angularVelocity;
        }

        public void setAngularVelocity(float angularVelocity) {
            this.angularVelocity = angularVelocity;
        }
    }

    public static class GameUpdateResponse{
        private Map<String, Velocities> movementVectors;

        public GameUpdateResponse()
        {}

        public GameUpdateResponse(Map<String, Velocities> movementVectors)
        {
            this.movementVectors = movementVectors;
        }

        public Map<String, Velocities> getMovementVectors() {
            return movementVectors;
        }

        public void setMovementVectors(Map<String, Velocities> movementVectors) {
            this.movementVectors = movementVectors;
        }
    }

}