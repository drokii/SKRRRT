package com.mygdx.game.Gameplay;

import MenuScreen.*;
import MenuScreen.LogInScreen;
import Menu.StatisticsHandler;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Map.Map;
import com.mygdx.game.RaceGame;


import javax.security.auth.login.LoginContext;
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
    private ArrayList<Car> carList;

    public GameWorld(RaceGame game) {
        this.game = game;
        // Create physics world
        world = new World(new Vector2(0, 0), true);

        // Set camera and viewport
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 600, 400);

        // Create cars and assign them to list
        carList = new ArrayList<Car>();
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

        this.box2DDebugRenderer = new Box2DDebugRenderer(true, true, true, true, true, true);
    }


    @Override
    public void create() {

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
}
