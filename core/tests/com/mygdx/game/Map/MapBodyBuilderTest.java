package com.mygdx.game.Map;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.GameTest;
import org.junit.Test;
import static junit.framework.TestCase.*;

public class MapBodyBuilderTest extends GameTest {

    @Test
    public void mapBodyBuilderTest(){
        TiledMap tiledMap = new TmxMapLoader().load("assets\\Map\\UnitTestMap.tmx");
        MapBodyBuilder mapBodyBuilder = new MapBodyBuilder();
        Array<Body> objects = mapBodyBuilder.buildShapes(tiledMap, new World(new Vector2(0,0), false));

        assertEquals(3, objects.size);
    }
}
