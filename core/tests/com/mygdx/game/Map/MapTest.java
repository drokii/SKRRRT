package com.mygdx.game.Map;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.GameTest;
import org.junit.Test;
import static junit.framework.TestCase.*;


public class MapTest extends GameTest {

    @Test
    public void MapTest(){
        World world = new World(new Vector2(0,0), false);
        assertNotNull(world);
    }

}