package core.tests.com.mygdx.game.Networking.Server;

import com.mygdx.game.GameTest;
import com.mygdx.game.Networking.Client.LoginClient;
import com.mygdx.game.Networking.Enums.ELogin;
import com.mygdx.game.Networking.Server.LoginServer;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.Assert.*;

public class LoginServerTest extends GameTest{
    private static final Logger LOGGER = Logger.getLogger(LoginServerTest.class.getName());

    @Test
    public void mainTest(){
        String[] args = null;
        try {
            LoginServer.main(args);
        } catch (IOException e) {
            LOGGER.log( Level.SEVERE, e.toString(), e );
        } catch (SQLException e) {
            LOGGER.log( Level.SEVERE, e.toString(), e );
        }

        String username = "meme";
        String ww = "meme";
        LoginClient lc = new LoginClient(username, ww);

        while (lc.getELogin() == ELogin.CONNECTING){
            //leeg
        }
        if(lc.getELogin() != ELogin.CONNECTING){
            assertEquals(ELogin.LOGINFAILED, lc.getELogin());
        }

        username = "frank";
        ww = "frank";
        lc = new LoginClient(username, ww);

        while (lc.getELogin() == ELogin.CONNECTING){//leeg
             }
        if(lc.getELogin() != ELogin.CONNECTING){
            assertEquals(ELogin.LOGINSUCCES, lc.getELogin());
        }
    }
}
