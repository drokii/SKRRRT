package com.mygdx.game.Networking;

public class LoginRequest {

    private String username;
    private String password;

    public LoginRequest(String user, String pass)
    {
        username = user;
        password = pass;
    }

    public LoginRequest()
    {
            // nog een keer leeg
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
