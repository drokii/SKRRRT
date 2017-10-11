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

public class Map extends ApplicationAdapter implements InputProcessor{
    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer tiledMapRenderer;

    private TiledMapTileLayer collisionLayer;
    private TiledMapTileLayer finishLayer;

    private boolean collisionX;
    private boolean collisionY;
    private Vector2 oldPos;

    private OrthographicCamera camera;

    //Testing purpose
    SpriteBatch batch;
    Body body;
    Sprite sprite;
    Texture img;
    World world;

    public Map(){
        create();
    }

    @Override
    public void create() {
        tiledMap = new TmxMapLoader().load("assets/testmap2.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

        collisionLayer = (TiledMapTileLayer) tiledMap.getLayers().get("CollisionLayer");
        finishLayer = (TiledMapTileLayer) tiledMap.getLayers().get("FinishLayer");

        //region Testing purpose
        batch = new SpriteBatch();
        img = new Texture("assets/Player.png");
        sprite = new Sprite(img);
        sprite.setPosition(Gdx.graphics.getWidth() / 2 - sprite.getWidth() / 2,
                Gdx.graphics.getHeight() / 2);
        world = new World(new Vector2(0, 0), true);
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(sprite.getX(), sprite.getY());
        body = world.createBody(bodyDef);
        body.setTransform(new Vector2(320, 1152), 0);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(sprite.getWidth()/2, sprite.getHeight()/2);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        Fixture fixture = body.createFixture(fixtureDef);
        shape.dispose();
        //body.setLinearVelocity(new Vector2(-200f, 150f));
        //endregion

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 600,600);
        camera.update();

        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render() {
        tiledMapRenderer.render();
        tiledMapRenderer.setView(camera);

        //region Testing purpose
        world.step(Gdx.graphics.getDeltaTime(), 2, 2);
        sprite.setPosition(body.getPosition().x, body.getPosition().y);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(sprite, sprite.getX(), sprite.getY());
        batch.end();
        camera.position.set(sprite.getX(), sprite.getY(),0);
        //endregion

        camera.update();

        CheckCollision();
        System.out.println(isOnFinnishLine());
    }

    @Override
    public void dispose() {
    }

    public void CheckCollision(){
        Vector2 position = body.getTransform().getPosition();

        if(body.getLinearVelocity().x != 0) {
            //Left

            //Top Left
            collisionX = isCellOnMap(position.x, position.y + 63, collisionLayer);
            //Middle left
            if (!collisionX)
                collisionX = isCellOnMap(position.x, position.y + 32, collisionLayer);
            //Bottom left
            if (!collisionX)
                collisionX = isCellOnMap(position.x, position.y + 1, collisionLayer);

            //Right

            //Top right
            if(!collisionX)
                collisionX = isCellOnMap(position.x + 64, position.y + 63, collisionLayer);

            //Middle right
            if(!collisionX)
                collisionX = isCellOnMap(position.x + 64, position.y + 32, collisionLayer);

            //Bottom right
            if(!collisionX)
                collisionX = isCellOnMap(position.x + 64, position.y + 1, collisionLayer);

            if(collisionX){
                body.setLinearVelocity(0, body.getLinearVelocity().y);
                body.setTransform(oldPos, oldPos.angle());
            }
        }

        if(body.getLinearVelocity().y != 0){
            //Down

            //Bottom left
            collisionY = isCellOnMap(position.x + 1, position.y, collisionLayer);

            //Bottom middle
            if(!collisionY)
                collisionY = isCellOnMap(position.x + 32, position.y, collisionLayer);

            //Bottom right
            if(!collisionY)
                collisionY = isCellOnMap(position.x + 63, position.y, collisionLayer);

            //Up

            //Top left
            if(!collisionY)
                collisionY = isCellOnMap(position.x + 1, position.y + 64, collisionLayer);

            //Top middle
            if(!collisionY)
                collisionY = isCellOnMap(position.x + 32, position.y + 64, collisionLayer);

            //Top right
            if(!collisionY)
                collisionY = isCellOnMap(position.x + 63, position.y + 64, collisionLayer);

            if(collisionY){
                body.setLinearVelocity(body.getLinearVelocity().x, 0);
                body.setTransform(oldPos, oldPos.angle());
            }
        }
        oldPos = new Vector2(position);
    }

    private boolean isCellOnMap(float x, float y, TiledMapTileLayer layer){
        //TiledMapTileLayer.Cell cell = collisionLayer.getCell((int)(x / collisionLayer.getTileWidth()), (int)(y / collisionLayer.getTileHeight()));
        //return cell != null && cell.getTile() != null && cell.getTile().getProperties().containsKey("Collision");
        TiledMapTileLayer.Cell cell = layer.getCell((int)(x / collisionLayer.getTileWidth()), (int)(y / collisionLayer.getTileHeight()));
        return cell != null;
    }

    public boolean isOnFinnishLine(){
        Vector2 position = body.getTransform().getPosition();
        return isCellOnMap(position.x + 32, position.y + 32, finishLayer);
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if(keycode == Input.Keys.RIGHT)
            body.setLinearVelocity(300f, body.getLinearVelocity().y);
        if(keycode == Input.Keys.LEFT)
            body.setLinearVelocity(-300f,body.getLinearVelocity().y);
        if(keycode == Input.Keys.DOWN)
            body.setLinearVelocity(body.getLinearVelocity().x, 100f);
        if(keycode == Input.Keys.UP)
            body.setLinearVelocity(body.getLinearVelocity().x, -100f);
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
