package com.mygdx.game.Networking;

public class LoginResponse {

    private Boolean loginPassed;

    public LoginResponse(Boolean response)
    {
        loginPassed = response;
    }

    public LoginResponse()
    {
        // niet gebruikt
    }

    public Boolean getLoginPassed() {
        return loginPassed;
    }

    public void setLoginPassed(Boolean loginPassed) {
        this.loginPassed = loginPassed;
    }
}
