package Menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.RaceGame;

import java.awt.*;

public class MenuScreen implements Screen {
    private final int PLAY_SETTINGS_EXIT_BUTTONS_X = (Gdx.graphics.getWidth()/2) - (322/2);
    private final int PLAY_BUTTON_Y = 500;
    private final int SETTINGS_BUTTON_Y = 400;
    private final int EXIT_BUTTON_Y = 300;

    private RaceGame game;

    private SpriteBatch batch;
    private Texture title;
    private Texture playButton;
    private Texture playButtonActive;
    private Texture settingsButton;
    private Texture settingsButtonActive;
    private Texture exitButton;
    private Texture exitButtonActive;
    private Texture yellowCar;

    public MenuScreen(RaceGame game){
        this.game = game;
        this.batch = new SpriteBatch();
        this.title = new Texture("core\\assets\\Skrrrt.png");
        this.playButton = new Texture("core\\assets\\PlayButton.png");
        this.playButtonActive = new Texture("core\\assets\\PlayButtonActive.png");
        this.settingsButton = new Texture("core\\assets\\SettingsButton.png");
        this.settingsButtonActive = new Texture("core\\assets\\SettingsButtonActive.png");
        this.exitButton = new Texture("core\\assets\\ExitButton.png");
        this.exitButtonActive = new Texture("core\\assets\\ExitButtonActive.png");
        this.yellowCar = new Texture("core\\assets\\YellowCarBrakes.png");
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        menuScreen();
        isTouched();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        this.dispose();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    private void menuScreen(){
        batch.begin();

        // draw title
        batch.draw(title, (1440/2) - (665/2), 670);

        // draw play button
        if(Gdx.input.getX() > PLAY_SETTINGS_EXIT_BUTTONS_X && Gdx.input.getX() < (PLAY_SETTINGS_EXIT_BUTTONS_X + playButton.getWidth())
            && (Gdx.graphics.getHeight() - Gdx.input.getY()) > PLAY_BUTTON_Y && (Gdx.graphics.getHeight() - Gdx.input.getY()) < (PLAY_BUTTON_Y + playButton.getHeight())) {
            batch.draw(playButtonActive, PLAY_SETTINGS_EXIT_BUTTONS_X, PLAY_BUTTON_Y);
        } else {
            batch.draw(playButton, PLAY_SETTINGS_EXIT_BUTTONS_X, PLAY_BUTTON_Y);
        }

        // draw settings button
        if(Gdx.input.getX() > PLAY_SETTINGS_EXIT_BUTTONS_X && Gdx.input.getX() < (PLAY_SETTINGS_EXIT_BUTTONS_X + playButton.getWidth())
                && (Gdx.graphics.getHeight() - Gdx.input.getY()) > SETTINGS_BUTTON_Y && (Gdx.graphics.getHeight() - Gdx.input.getY()) < (SETTINGS_BUTTON_Y + playButton.getHeight())) {
            batch.draw(settingsButtonActive, PLAY_SETTINGS_EXIT_BUTTONS_X, SETTINGS_BUTTON_Y);
        } else {
            batch.draw(settingsButton, PLAY_SETTINGS_EXIT_BUTTONS_X, SETTINGS_BUTTON_Y);
        }

        // draw exit button
        if(Gdx.input.getX() > PLAY_SETTINGS_EXIT_BUTTONS_X && Gdx.input.getX() < (PLAY_SETTINGS_EXIT_BUTTONS_X + playButton.getWidth())
                && (Gdx.graphics.getHeight() - Gdx.input.getY()) > EXIT_BUTTON_Y && (Gdx.graphics.getHeight() - Gdx.input.getY()) < (EXIT_BUTTON_Y + playButton.getHeight())) {
            batch.draw(exitButtonActive, PLAY_SETTINGS_EXIT_BUTTONS_X, EXIT_BUTTON_Y);
        } else {
            batch.draw(exitButton, PLAY_SETTINGS_EXIT_BUTTONS_X, EXIT_BUTTON_Y);
        }

        // draw yellow car
        batch.draw(yellowCar, (1440 - 398) - 100, 0);
        batch.end();
    }

    private void isTouched(){
        // play button
        if(Gdx.input.getX() > PLAY_SETTINGS_EXIT_BUTTONS_X && Gdx.input.getX() < (PLAY_SETTINGS_EXIT_BUTTONS_X + playButton.getWidth())
                && (Gdx.graphics.getHeight() - Gdx.input.getY()) > PLAY_BUTTON_Y && (Gdx.graphics.getHeight() - Gdx.input.getY()) < (PLAY_BUTTON_Y + playButton.getHeight())) {
            // go to lobby screen
            if (Gdx.input.isTouched()) {
                //game.setScreen(new LobbyScreen(game));
                game.setScreen(new GameScreen(game));
            }
        }

        // settings button
        if(Gdx.input.getX() > PLAY_SETTINGS_EXIT_BUTTONS_X && Gdx.input.getX() < (PLAY_SETTINGS_EXIT_BUTTONS_X + playButton.getWidth())
                && (Gdx.graphics.getHeight() - Gdx.input.getY()) > SETTINGS_BUTTON_Y && (Gdx.graphics.getHeight() - Gdx.input.getY()) < (SETTINGS_BUTTON_Y + playButton.getHeight())) {

        }

        // exit button
        if(Gdx.input.getX() > PLAY_SETTINGS_EXIT_BUTTONS_X && Gdx.input.getX() < (PLAY_SETTINGS_EXIT_BUTTONS_X + playButton.getWidth())
                && (Gdx.graphics.getHeight() - Gdx.input.getY()) > EXIT_BUTTON_Y && (Gdx.graphics.getHeight() - Gdx.input.getY()) < (EXIT_BUTTON_Y + playButton.getHeight())) {
            // exit game
            if(Gdx.input.isTouched()){
                Gdx.app.exit();
            }
        }
    }
}
