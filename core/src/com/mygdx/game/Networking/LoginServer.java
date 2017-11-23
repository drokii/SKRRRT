package com.mygdx.game.Networking;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import javafx.application.Application;
import javafx.stage.Stage;
import sun.rmi.server.Activation$ActivationSystemImpl_Stub;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginServer extends Application {

    private static Server loginServer;
    private static Kryo kryo;

    private static java.sql.Connection conn;
    private static Statement stmnt;
    private static ResultSet resultSet;

    public static void main(String[] args) throws IOException, SQLException {

        loginServer = new Server();
        loginServer.start();
        // port nummers veranderen? @pedro
        loginServer.bind(54555, 54777);

        kryo = loginServer.getKryo();
        kryo.register(LoginRequest.class);
        kryo.register(LoginResponse.class);
        addListenersToServer(loginServer);
    }

    private static void addListenersToServer(Server server){
        server.addListener(new Listener() {
            public void received (Connection connection, Object object) {
                if (object instanceof LoginRequest) {
                    LoginRequest request = (LoginRequest)object;

                    try
                    {
                        Class.forName("com.mysql.jdbc.Driver");
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
                        System.out.println(e);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } finally{
                    }
                }
            }
        });
    }

    @Override
    public void start(Stage primaryStage) {}
}
