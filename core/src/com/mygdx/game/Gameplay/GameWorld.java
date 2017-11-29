package com.mygdx.game.Gameplay;

import MenuScreen.*;
import Menu.StatisticsHandler;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.esotericsoftware.kryonet.Client;
import com.mygdx.game.Map.Map;
import com.mygdx.game.Networking.Client.GameClient;
import com.mygdx.game.RaceGame;


import javax.security.auth.login.LoginContext;
import java.io.IOException;
import java.util.ArrayList;

public class GameWorld implements ApplicationListener {

    /**
     * This class gathers all game elements and applies the physics simulations to them.
     * It instanciates a list of Car, a Map, and a Camera. It also starts the game music (Soon to be changed to an audio manager)
     */
    private Box2DDebugRenderer box2DDebugRenderer;


    private RaceGame game;
    private Sound sound;
    private StatisticsHandler stats;
    private World world;
    private OrthographicCamera camera;
    private Map map;
    private Car car;
    private ArrayList<ICar> carList;
    private GameClient client;

    private SpriteBatch batch;
    private float deltaTime;

    Texture cd1;
    Texture cd2;
    Texture cd3;
    Texture cd4;
    Texture cd5;
    Texture cdReady;


    public GameWorld(RaceGame game) throws IOException {
        this.game = game;
        // Create physics world
        world = new World(new Vector2(0, 0), true);

        // Set camera and viewport
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 600, 400);

        // Create cars and assign them to list
        carList = new ArrayList<ICar>();
        car = new Car(camera, world);
        carList.add(car);

        // Set up map
        map = new Map(car, camera, world);

        // Set up statistics handler
        stats = new StatisticsHandler(carList);

        // Start background music!!
        LogInScreen.menuSound.stop();
        sound = Gdx.audio.newSound(Gdx.files.internal("core/assets/dejavu.ogg"));
        sound.play();

        //Client
        client = new GameClient();

        // Create batch
        batch = new SpriteBatch();
        deltaTime = 0;

        // Countdown textures
        cd1 = new Texture(Gdx.files.internal("core/assets/Countdown/1.png"));
        cd2 = new Texture(Gdx.files.internal("core/assets/Countdown/2.png"));
        cd3 = new Texture(Gdx.files.internal("core/assets/Countdown/3.png"));
        cd4 = new Texture(Gdx.files.internal("core/assets/Countdown/4.png"));
        cd5 = new Texture(Gdx.files.internal("core/assets/Countdown/5.png"));
        cdReady = new Texture(Gdx.files.internal("core/assets/Countdown/engine.jpg"));

        this.box2DDebugRenderer = new Box2DDebugRenderer(true, true, true, true, true, true);
    }


    @Override
    public void create() {
        client.sendGameStartRequest(car.getName(), null,0f);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render() {
        //Physics world iteration at 60hz
        world.step(Gdx.graphics.getRawDeltaTime(), 10, 2);


        //Map collision check
        //map.CheckCollision();

        map.render();
        car.render();
        stats.render();
        box2DDebugRenderer.render(world, camera.combined);

        // Draw the Countdown timer.
        drawCountdown();

        //Check if a car reached the finish line
        if (car.getIsOnFinishLine()) {
            game.setScreen(new FinishScreen(game, stats.getFinishLogList()));
        }
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        car.dispose();
        map.dispose();
        sound.stop();
    }

    private void drawCountdown() {
        // Draws the Countdown timer at the start of the game
        deltaTime += Gdx.graphics.getDeltaTime();
        batch.begin();
        if (deltaTime <5){
            batch.draw(cdReady,400,300);
        }
        else if(deltaTime < 6){
            batch.draw(cd5,500,400);
        }
        else if(deltaTime < 7){
            batch.draw(cd4,500,400);
        }
        else if(deltaTime < 8){
            batch.draw(cd3,500,400);
        }
        else if(deltaTime < 9){
            batch.draw(cd2,500,400);
        }
        else if(deltaTime < 10){
            batch.draw(cd1,500,400);
        }
        else{
            car.getInput().setUnlocked(true);
        }
        batch.end();
    }
}
