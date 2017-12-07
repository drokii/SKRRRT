package com.mygdx.game.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.GameTest;
import com.mygdx.game.Gameplay.Car;
import org.junit.Before;
import org.junit.Test;
import static junit.framework.TestCase.*;


public class MapTest extends GameTest {

    private Map map;

    @Before
    public void mapTest(){
        World world = new World(new Vector2(0,0), true);
        map = new Map(world);
        assertNotNull(map);
    }

    @Test
    public void isCellOnMapTest(){
        TiledMap tiledMap = new TmxMapLoader().load("core\\assets\\Map\\UnitTestMap.tmx");
        TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get("UnitTestLayer");

        //Test the middle of the cell
        assertTrue(map.isCellOnMap(32,32, layer));

        //Test the edges of the cell
        assertTrue(map.isCellOnMap(63,63, layer));
        assertTrue(map.isCellOnMap(0,63, layer));
        assertTrue(map.isCellOnMap(63,0, layer));

        //Test the outer edges of the cell
        assertFalse(map.isCellOnMap(64,64, layer));
        assertFalse(map.isCellOnMap(-1,64, layer));
        assertFalse(map.isCellOnMap(64,-1, layer));

        //check if isCellOnMap return false if layer is null
        layer = null;
        assertFalse(map.isCellOnMap(32, 32, layer));
    }

    @Test
    public void isOnFinishLineTest(){
        TiledMap tiledMap = new TmxMapLoader().load("core\\assets\\Map\\UnitTestMap.tmx");
        TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get("FinishLayer");
        Car car = new Car(new World(new Vector2(0,0), false));

        car.getKartBody().setTransform(new Vector2(32,32), 0);
        map.isOnFinnishLine(car, layer);
        assertFalse(car.getIsOnFinishLine());

        car.getKartBody().setTransform(new Vector2(32,96), 0);
        map.isOnFinnishLine(car, layer);
        assertTrue(car.getIsOnFinishLine());
    }
}