package com.mygdx.game.Networking;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

public class Network {
    static public final int port = 54555;

    // This registers objects that are going to be sent over the network.
    static public void register(EndPoint endPoint) {
        Kryo kryo = endPoint.getKryo();

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
    static public class GameStartRequest{
        //wait for all
    }
    static public class GameStartResponse{
        //do we need this??
    }
    static public class PlayerIsReadyUpdate{

    }
    static public class 

}