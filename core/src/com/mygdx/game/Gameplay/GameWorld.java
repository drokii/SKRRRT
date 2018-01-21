package com.mygdx.game.Gameplay;

import Menu.Player;
import Menu.StatisticsHandler;
import MenuScreen.FinishScreen;
import MenuScreen.LogInScreen;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Map.Map;
import com.mygdx.game.Networking.Client.GameClient;
import com.mygdx.game.Networking.Server.Velocities;
import com.mygdx.game.RaceGame;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class GameWorld implements ApplicationListener {

    /**
     * This class gathers all game elements and applies the physics simulations to them.
     * It instanciates a list of Car, a Map, and a Camera. It also starts the game music (Soon to be changed to an audio manager)
     */
    private RaceGame game;
    private Player currentPlayer;
    private Sound sound;
    private StatisticsHandler stats;
    private GameClient gameClient;
    private World world;
    private OrthographicCamera camera;
    private Map map;
    private Car car;
    private ArrayList<RemoteCar> carList;

    private SpriteBatch batch;
    private float deltaTime;

    Texture cd1;
    Texture cd2;
    Texture cd3;
    Texture cd4;
    Texture cd5;
    Texture cdReady;


    public GameWorld(RaceGame game, Player player, String ip) throws IOException, InterruptedException, ExecutionException {
        this.game = game;
        this.currentPlayer = player;
        // Create physics world
        world = new World(new Vector2(0, 0), true);

        // Set camera and viewport
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 600, 400);

        // Connect to gameserver
        gameClient = new GameClient(ip, currentPlayer);

        // Set up Map
        map = new Map(camera, world);

        // Create cars and assign them to list
        carList = new ArrayList<>();
        instantiateCars();

        // Start background music!!
        LogInScreen.getMenuSound().stop();
        sound = Gdx.audio.newSound(Gdx.files.internal("core/assets/dejavu.ogg"));
        sound.play();

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


    }


    @Override
    public void create() {
        //creates the world
    }

    @Override
    public void resize(int width, int height) {
        //resizes the view
    }



    @Override
    public void render() {
        //Physics world iteration at 60hz
        world.step(Gdx.graphics.getRawDeltaTime(), 10, 2);

        map.render();

        if (car != null) {
            gameClient.setCar(car);
            for (RemoteCar currentCar : carList) {
                if (gameClient.getVelocitiesMap() != null) {

                    Velocities v = gameClient.getVelocitiesMap().get(currentCar.getName());
                    currentCar.setLinearVelocity(v.getLinear());
                    currentCar.setAngularVelocity(v.getAngular());
                    currentCar.render();
                }

            }

            car.render();
            if (stats != null){
                stats.render();
            }

            // Draw the Countdown timer.
            drawCountdown();

            //Check if a car reached the finish line
            if (car.getIsOnFinishLine()) {
                game.setScreen(new FinishScreen(game, currentPlayer, stats.getFinishLogList()));
            }
        }
    }

    @Override
    public void pause() {
        // never used
    }

    @Override
    public void resume() {
        // never used man leeeeeeeeg
    }

    @Override
    public void dispose() {
        if(car != null) {
            car.dispose();
        }
        sound.stop();
    }

    private void drawCountdown() {
        // Draws the Countdown timer at the start of the game
        deltaTime += Gdx.graphics.getDeltaTime();
        batch.begin();
        if (deltaTime < 5) {
            batch.draw(cdReady, 500, 400);
        } else if (deltaTime < 6) {
            batch.draw(cd5, 500, 400);
        } else if (deltaTime < 7) {
            batch.draw(cd4, 500, 400);
        } else if (deltaTime < 8) {
            batch.draw(cd3, 500, 400);
        } else if (deltaTime < 9) {
            batch.draw(cd2, 500, 400);
        } else if (deltaTime < 10) {
            batch.draw(cd1, 500, 400);
        } else {
            car.getInput().setUnlocked(true);
        }
        batch.end();
    }

    public void instantiateCars() {
        //Client is hier nog null...
        java.util.Map<String, Vector2> spawnLocations = gameClient.getGameStartResponse();

        for (java.util.Map.Entry<String, Vector2> player : spawnLocations.entrySet()) {
            if (!player.getKey().equals(currentPlayer.getName())) {
                RemoteCar currentCar = new RemoteCar(camera, world, player.getKey(), player.getValue());
                carList.add(currentCar);
            } else {
                car = new Car(camera, world, map, player.getValue());
                gameClient.setCar(car);
            }
        }
    }
}
