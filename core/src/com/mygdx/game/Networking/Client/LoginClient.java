package com.mygdx.game.Networking.Client;

import Menu.Player;
import MenuScreen.LogInScreen;
import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.mygdx.game.Networking.Enums.ELogin;
import com.mygdx.game.Networking.LoginRequest;
import com.mygdx.game.Networking.LoginResponse;

import java.io.IOException;

public class LoginClient {
    LogInScreen screen;
    Player player;
    String username;
    private ELogin eLogin;
    public ELogin getELogin(){return eLogin;}

    public LoginClient(String username, String password, LogInScreen screen)
    {
        this.screen = screen;
        this.username = username;
        Client client = new Client();
        client.start();
        try
        {
            client.connect(5000, "127.0.0.1", 54555, 54777);
            //client.connect(5000, "145.93.169.164", 54555, 54777);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        Kryo kryoClient = client.getKryo();
        kryoClient.register(LoginRequest.class);
        kryoClient.register(LoginResponse.class);

        LoginRequest request = new LoginRequest(username, password);
        client.sendTCP(request);
        addListeners(client);
    }

    public LoginClient(String username, String password)
    {
        this.eLogin = ELogin.CONNECTING;
        this.screen = screen;
        this.username = username;
        Client client = new Client();
        client.start();
        try
        {
            client.connect(5000, "127.0.0.1", 54555, 54777);
            //client.connect(5000, "145.93.168.125", 54555, 54777);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        Kryo kryoClient = client.getKryo();
        kryoClient.register(LoginRequest.class);
        kryoClient.register(LoginResponse.class);

        LoginRequest request = new LoginRequest(username, password);
        client.sendTCP(request);
        addListenersTest(client);
    }

    //check for server login response
    public void addListeners(final Client client) {
        client.addListener(new Listener() {
            public void received(final Connection connection, Object object) {
                if (object instanceof LoginResponse) {
                    LoginResponse response = (LoginResponse) object;
                    if(response.getLoginPassed())
                    {
                        System.out.println("logged in");
                        Gdx.app.postRunnable(new Runnable() {
                            @Override
                            public void run() {
                                player = new Player(username);
                                screen.loginPassed(player);
                                connection.close();
                                client.close();
                            }
                        });
                    }
                    else
                    {
                        System.out.println("login failed");
                        connection.close();
                        client.close();
                        screen.loginFailed();
                    }
                }
            }
        });
    }

    public void addListenersTest(final Client client) {
        client.addListener(new Listener() {
            public void received(final Connection connection, Object object) {
                if (object instanceof LoginResponse) {
                    LoginResponse response = (LoginResponse) object;
                    if(response.getLoginPassed())
                    {

                        System.out.println("logged in");
                        Gdx.app.postRunnable(new Runnable() {
                            @Override
                            public void run() {
                                eLogin = ELogin.LOGINSUCCES;
                                player = new Player(username);
                                connection.close();
                                client.close();
                            }
                        });
                    }
                    else
                    {
                        eLogin = ELogin.LOGINFAILED;
                        System.out.println("login failed");
                        connection.close();
                        client.close();
                    }
                }
            }
        });
    }
}
