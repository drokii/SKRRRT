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
    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer tiledMapRenderer;

    private TiledMapTileLayer collisionLayer;
    private TiledMapTileLayer finishLayer;

    private boolean collisionX;
    private boolean collisionY;
    private Vector2 oldPos;

    private OrthographicCamera camera;
    CarInputProcessorHelper input;
    //Testing purpose
//    SpriteBatch batch;
//    Body body;
//    Sprite sprite;
//    Texture img;
//    World world;
    private Car car;

    public Map(Car car, OrthographicCamera camera){

        this.camera = camera;
        this.car = car;
        this.input = car.getInput();
        create();
    }

    @Override
    public void create() {
        tiledMap = new TmxMapLoader().load("core\\assets\\SmallTestMap.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

        collisionLayer = (TiledMapTileLayer) tiledMap.getLayers().get("CollisionLayer");
        finishLayer = (TiledMapTileLayer) tiledMap.getLayers().get("FinishLayer");
        isOnFinnishLine();
    }

    @Override
    public void render() {
        tiledMapRenderer.render();
        tiledMapRenderer.setView(camera);
        isOnFinnishLine();
    }

    @Override
    public void dispose() {
    }

    public void CheckCollision(){
        Vector2 position = car.getKartBody().getTransform().getPosition();

        if(car.getKartBody().getLinearVelocity().x != 0) {
            //Left

            //Top Left
            collisionX = isCellOnMap(position.x, position.y + 32, collisionLayer);
            //Middle left
            if (!collisionX)
                collisionX = isCellOnMap(position.x, position.y + 16, collisionLayer);
            //Bottom left
            if (!collisionX)
                collisionX = isCellOnMap(position.x, position.y, collisionLayer);

            //Right

            //Top right
            if(!collisionX)
                collisionX = isCellOnMap(position.x + 32, position.y + 32, collisionLayer);

            //Middle right
            if(!collisionX)
                collisionX = isCellOnMap(position.x + 32, position.y + 16, collisionLayer);

            //Bottom right
            if(!collisionX)
                collisionX = isCellOnMap(position.x + 32, position.y, collisionLayer);

            if(collisionX){
                input.cancelUpTimer();
                input.cancelDownTimer();
                car.setSpeed(0f);
                car.keepVelocity();
                car.getKartBody().setTransform(oldPos, car.getKartBody().getAngle());

            }
        }

        if(car.getKartBody().getLinearVelocity().y != 0){
            //Down

            //Bottom left
            collisionY = isCellOnMap(position.x, position.y, collisionLayer);

            //Bottom middle
            if(!collisionY)
                collisionY = isCellOnMap(position.x + 16, position.y, collisionLayer);

            //Bottom right
            if(!collisionY)
                collisionY = isCellOnMap(position.x + 32, position.y, collisionLayer);

            //Up

            //Top left
            if(!collisionY)
                collisionY = isCellOnMap(position.x, position.y + 32, collisionLayer);

            //Top middle
            if(!collisionY)
                collisionY = isCellOnMap(position.x + 16, position.y + 32, collisionLayer);

            //Top right
            if(!collisionY)
                collisionY = isCellOnMap(position.x + 32, position.y + 32, collisionLayer);

            if(collisionY){
                input.cancelUpTimer();
                input.cancelDownTimer();

                car.setSpeed(0f);
                car.keepVelocity();
                car.getKartBody().setTransform(oldPos, car.getKartBody().getAngle());
            }
        }
        oldPos = new Vector2(position);
    }

    private boolean isCellOnMap(float x, float y, TiledMapTileLayer layer){
        TiledMapTileLayer.Cell cell = layer.getCell((int)(x / collisionLayer.getTileWidth()), (int)(y / collisionLayer.getTileHeight()));
        return cell != null;
    }

    public void isOnFinnishLine(){
        Vector2 position = car.getKartBody().getTransform().getPosition();
         if(isCellOnMap(position.x + 32, position.y + 32, finishLayer)){
             car.setOnFinishLine(true);
        }
    }
}
