package com.mygdx.game.Map;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.Gameplay.Car;

public class Map implements ApplicationListener{
    /**
     * The Map class is about the map. So where is the finish line, where are the collisionborders, where is the car located on the map.
     * @param tiledMap the entire map
     * @param tiledMapRenderer the map renederer
     * @param collisionLayer the layer that provides the collision tiles
     * @param finishLayer the layer that provides as finish tiles
     * @param collisionX boolean that holds a value if a certain x value is on the map
     * @param collisionY boolean that holds a value if a certain y value is on the map
     * @param oldPos a Vector2 variable that holds the position of the car gets updated when no collision occured.
     * @param camera a camera that follows the car
     */
    private OrthogonalTiledMapRenderer tiledMapRenderer;
    private MapBodyBuilder mapBodyBuilder;

    private TiledMapTileLayer collisionLayer;
    private TiledMapTileLayer finishLayer;

    private World world;
    private OrthographicCamera camera;

    public Map(OrthographicCamera camera, World world){
        this.camera = camera;
        this.world = world;
        this.mapBodyBuilder = new MapBodyBuilder();
        create();
    }

    public Map(World world){
        this.world = world;
        this.mapBodyBuilder = new MapBodyBuilder();
        createForTesting();
    }

    @Override
    public void create() {
        TiledMap tiledMap;
        tiledMap = new TmxMapLoader().load("core\\assets\\Map\\BigSkrrrt.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

        collisionLayer = (TiledMapTileLayer) tiledMap.getLayers().get("CollisionLayer");
        finishLayer = (TiledMapTileLayer) tiledMap.getLayers().get("FinishLayer");

        mapBodyBuilder.buildShapes(tiledMap, world);
    }

    public void createForTesting(){
        TiledMap tiledMap;
        tiledMap = new TmxMapLoader().load("core\\assets\\Map\\SkrrrtMap.tmx");

        collisionLayer = (TiledMapTileLayer) tiledMap.getLayers().get("CollisionLayer");
        finishLayer = (TiledMapTileLayer) tiledMap.getLayers().get("FinishLayer");

        mapBodyBuilder.buildShapes(tiledMap, world);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render() {
        tiledMapRenderer.render();
        tiledMapRenderer.setView(camera);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        //used to dispose unused objects
    }

    public boolean isCellOnMap(float x, float y, TiledMapTileLayer layer){
        /**
         * Checks if a cell is existing on the map
         */
        if(layer != null) {
            TiledMapTileLayer.Cell cell = layer.getCell((int) (x / layer.getTileWidth()), (int) (y / layer.getTileHeight()));
            return cell != null;
        }
        return false;
    }

    public void isOnFinnishLine(Car car){
        /**
         * Checks if a car is on the finish line.
         */
        Vector2 position = car.getKartBody().getTransform().getPosition();
         if(isCellOnMap(position.x, position.y, finishLayer)){
             car.setOnFinishLine(true);
        }
    }

    public void isOnFinnishLine(Car car, TiledMapTileLayer layer){
        /**
         * Checks if a car is on the finish line.
         */
        Vector2 position = car.getKartBody().getTransform().getPosition();
        if(isCellOnMap(position.x, position.y, layer)){
            car.setOnFinishLine(true);
        }
    }
}
