package com.mygdx.game.Networking.Server;

import MenuScreen.LogInScreen;
import com.mygdx.game.GameTest;
import com.mygdx.game.Networking.Client.LoginClient;
import com.mygdx.game.Networking.Enums.ELogin;
import com.mygdx.game.RaceGame;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.Test;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class LoginServerTest extends GameTest{
    @Test
    public void mainTest(){
        String[] args = null;
        try {
            LoginServer.main(args);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String username = "meme";
        String password = "meme";
        LoginClient lc = new LoginClient(username, password);

        while (lc.getELogin() == ELogin.CONNECTING){
            System.out.println(lc.getELogin());}
        if(lc.getELogin() != ELogin.CONNECTING){
            assertEquals(ELogin.LOGINFAILED, lc.getELogin());
        }

        username = "frank";
        password = "frank";
        lc = new LoginClient(username, password);

        while (lc.getELogin() == ELogin.CONNECTING){System.out.println(lc.getELogin());}
        if(lc.getELogin() != ELogin.CONNECTING){
            assertEquals(ELogin.LOGINSUCCES, lc.getELogin());
        }
    }
}
