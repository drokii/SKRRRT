package com.mygdx.game.Map;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.GameTest;
import com.mygdx.game.Gameplay.Car;
import org.junit.Test;
import static junit.framework.TestCase.*;


public class MapTest extends GameTest {

    @Test
    public void MapTest(){
        Car car = new Car(new World(new Vector2(0,0), true));
        OrthographicCamera camera = new OrthographicCamera();
        World world = new World(new Vector2(0,0), true);
        Map map = new Map(car, camera, world);
        assertNotNull(map);
    }
}