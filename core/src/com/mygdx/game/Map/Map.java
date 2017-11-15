package com.mygdx.game.Map;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Gameplay.Car;
import com.mygdx.game.Gameplay.CarInputProcessorHelper;

public class Map extends ApplicationAdapter{
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
    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer tiledMapRenderer;
    private MapBodyBuilder mapBodyBuilder;

    private TiledMapTileLayer collisionLayer;
    private TiledMapTileLayer finishLayer;

    private boolean collisionX;
    private boolean collisionY;
    private Vector2 oldPos;

    private World world;
    private OrthographicCamera camera;
    CarInputProcessorHelper input;

    private Car car;

    public Map(Car car, OrthographicCamera camera, World world){
        this.camera = camera;
        this.car = car;
        this.input = car.getInput();
        this.world = world;
        this.mapBodyBuilder = new MapBodyBuilder();
        create();
    }

    @Override
    public void create() {
        tiledMap = new TmxMapLoader().load("core\\assets\\Map\\SkrrrtMap.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

        collisionLayer = (TiledMapTileLayer) tiledMap.getLayers().get("CollisionLayer");
        finishLayer = (TiledMapTileLayer) tiledMap.getLayers().get("FinishLayer");
        isOnFinnishLine();

        mapBodyBuilder.buildShapes(tiledMap, world);
    }

    @Override
    public void render() {
        tiledMapRenderer.render();
        tiledMapRenderer.setView(camera);
        isOnFinnishLine();
        //mapBodyBuilder.buildShapes(tiledMap,64, world);

    }

    @Override
    public void dispose() {
    }

//    public void CheckCollision(){
//        /**
//         * Checks if the car is hitting the collisionLayer of the map.
//         * @param position holds the cars position to be checked during the method
//         */
//        Vector2 position = car.getKartBody().getTransform().getPosition();
//
//        if(car.getKartBody().getLinearVelocity().x != 0) {
//            //Left
//
//            //Top Left
//            collisionX = isCellOnMap(position.x, position.y + 32, collisionLayer);
//            //Middle left
//            if (!collisionX)
//                collisionX = isCellOnMap(position.x, position.y + 16, collisionLayer);
//            //Bottom left
//            if (!collisionX)
//                collisionX = isCellOnMap(position.x, position.y, collisionLayer);
//
//            //Right
//
//            //Top right
//            if(!collisionX)
//                collisionX = isCellOnMap(position.x + 32, position.y + 32, collisionLayer);
//
//            //Middle right
//            if(!collisionX)
//                collisionX = isCellOnMap(position.x + 32, position.y + 16, collisionLayer);
//
//            //Bottom right
//            if(!collisionX)
//                collisionX = isCellOnMap(position.x + 32, position.y, collisionLayer);
//
//            if(collisionX){
//                input.cancelUpTimer();
//                input.cancelDownTimer();
//                car.setSpeed(0f);
//                car.keepVelocity();
//                car.getKartBody().setTransform(oldPos, car.getKartBody().getAngle());
//
//            }
//        }
//
//        if(car.getKartBody().getLinearVelocity().y != 0){
//            //Down
//
//            //Bottom left
//            collisionY = isCellOnMap(position.x, position.y, collisionLayer);
//
//            //Bottom middle
//            if(!collisionY)
//                collisionY = isCellOnMap(position.x + 16, position.y, collisionLayer);
//
//            //Bottom right
//            if(!collisionY)
//                collisionY = isCellOnMap(position.x + 32, position.y, collisionLayer);
//
//            //Up
//
//            //Top left
//            if(!collisionY)
//                collisionY = isCellOnMap(position.x, position.y + 32, collisionLayer);
//
//            //Top middle
//            if(!collisionY)
//                collisionY = isCellOnMap(position.x + 16, position.y + 32, collisionLayer);
//
//            //Top right
//            if(!collisionY)
//                collisionY = isCellOnMap(position.x + 32, position.y + 32, collisionLayer);
//
//            if(collisionY){
//                input.cancelUpTimer();
//                input.cancelDownTimer();
//
//                car.setSpeed(0f);
//                car.keepVelocity();
//                car.getKartBody().setTransform(oldPos, car.getKartBody().getAngle());
//            }
//        }
//        oldPos = new Vector2(position);
//    }

    private boolean isCellOnMap(float x, float y, TiledMapTileLayer layer){
        /**
         * Checks if a cell is existing on the map
         */
        if(layer != null) {
            TiledMapTileLayer.Cell cell = layer.getCell((int) (x / collisionLayer.getTileWidth()), (int) (y / collisionLayer.getTileHeight()));
            return cell != null;
        }
        return false;
    }

    public void isOnFinnishLine(){
        /**
         * Checks if a car is on the finish line.
         */
        Vector2 position = car.getKartBody().getTransform().getPosition();
         if(isCellOnMap(position.x + 32, position.y + 32, finishLayer)){
             car.setOnFinishLine(true);
        }
    }
}
