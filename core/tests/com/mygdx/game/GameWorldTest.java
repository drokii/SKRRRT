package com.mygdx.game;

import Menu.GameScreen;
import com.mygdx.game.Gameplay.GameWorld;
import org.junit.Test;
import static junit.framework.TestCase.*;

public class GameWorldTest extends GameTest {

    @Test
    public void GameWorldTest(){
        RaceGame game = new RaceGame();
        //game.setScreen(new GameScreen(game));
        GameWorld gameWorld = new GameWorld(game);

        assertNotNull(gameWorld);


    }
}
