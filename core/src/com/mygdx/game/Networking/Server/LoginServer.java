package com.mygdx.game.Networking.Server;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.mygdx.game.Networking.LoginRequest;
import com.mygdx.game.Networking.LoginResponse;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginServer{

    private static Server server;
    private static Kryo kryo;

    private static java.sql.Connection conn;
    private static Statement stmnt;
    private static ResultSet resultSet;

    public LoginServer() throws IOException {
        server = new Server();
        server.start();
        // port nummers veranderen? @pedro
        server.bind(54555, 54777);

        kryo = server.getKryo();
        kryo.register(LoginRequest.class);
        kryo.register(LoginResponse.class);
        addListenersToServer(server);
    }

    public static void main(String[] args) throws IOException, SQLException {
        new LoginServer();
    }

    private static void addListenersToServer(Server server){
        server.addListener(new Listener() {
            public void received (Connection connection, Object object) {
                if (object instanceof LoginRequest) {
                    LoginRequest request = (LoginRequest)object;

                    try
                    {
                        conn = DriverManager.getConnection("jdbc:mysql://studmysql01.fhict.local:3306/dbi360089","dbi360089","PTS3");
                        stmnt = conn.createStatement();
                        resultSet = stmnt.executeQuery("SELECT DisplayName FROM player WHERE DisplayName = '" + request.getUsername() + "'" + "&& Password = '" + request.getPassword() + "'");
                        if(resultSet.next())
                        {
                            LoginResponse response = new LoginResponse(true);
                            connection.sendTCP(response);
                        }
                        else
                        {
                            LoginResponse response = new LoginResponse(false);
                            connection.sendTCP(response);
                        }
                        conn.close();
                    }
                    catch(SQLException e) {
                        // leeg
                    }
                }
            }
        });
    }
}
