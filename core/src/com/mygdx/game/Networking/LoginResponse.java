package com.mygdx.game.Networking;

public class LoginResponse {
    private Boolean loginPassed;

    public LoginResponse(Boolean response)
    {
        loginPassed = response;
    }

    public LoginResponse()
    {

    }
}
