package Menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.RaceGame;

public class MenuScreen implements Screen {
    private final int PLAY_SETTINGS_EXIT_BUTTONS_X = (Gdx.graphics.getWidth()/2) - (322/2);
    private final int PLAY_BUTTON_Y = 500;
    private final int SETTINGS_BUTTON_Y = 400;
    private final int EXIT_BUTTON_Y = 300;

    private RaceGame game;

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
        title = new Texture("core\\assets\\Skrrrt.png");
        playButton = new Texture("core\\assets\\PlayButton.png");
        playButtonActive = new Texture("core\\assets\\PlayButtonActive.png");
        settingsButton = new Texture("core\\assets\\SettingsButton.png");
        settingsButtonActive = new Texture("core\\assets\\SettingsButtonActive.png");
        exitButton = new Texture("core\\assets\\ExitButton.png");
        exitButtonActive = new Texture("core\\assets\\ExitButtonActive.png");
        yellowCar = new Texture("core\\assets\\YellowCarBrakes.png");
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        MainMenuScreen();
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

    }

    @Override
    public void dispose() {

    }

    private void MainMenuScreen(){
        game.batch.begin();
        game.batch.draw(title, (1440/2) - (665/2), 670);

        // play button
        if(Gdx.input.getX() > PLAY_SETTINGS_EXIT_BUTTONS_X && Gdx.input.getX() < (PLAY_SETTINGS_EXIT_BUTTONS_X + playButton.getWidth())
            && (Gdx.graphics.getHeight() - Gdx.input.getY()) > PLAY_BUTTON_Y && (Gdx.graphics.getHeight() - Gdx.input.getY()) < (PLAY_BUTTON_Y + playButton.getHeight())) {
            game.batch.draw(playButtonActive, PLAY_SETTINGS_EXIT_BUTTONS_X, PLAY_BUTTON_Y);
            // go to lobby screen
            if(Gdx.input.isTouched()){
                this.dispose();
                //game.setScreen(new LobbyScreen(game));
            }
        } else {
            game.batch.draw(playButton, PLAY_SETTINGS_EXIT_BUTTONS_X, PLAY_BUTTON_Y);
        }

        // settings button
        if(Gdx.input.getX() > PLAY_SETTINGS_EXIT_BUTTONS_X && Gdx.input.getX() < (PLAY_SETTINGS_EXIT_BUTTONS_X + playButton.getWidth())
                && (Gdx.graphics.getHeight() - Gdx.input.getY()) > SETTINGS_BUTTON_Y && (Gdx.graphics.getHeight() - Gdx.input.getY()) < (SETTINGS_BUTTON_Y + playButton.getHeight())) {
            game.batch.draw(settingsButtonActive, PLAY_SETTINGS_EXIT_BUTTONS_X, SETTINGS_BUTTON_Y);
        } else {
            game.batch.draw(settingsButton, PLAY_SETTINGS_EXIT_BUTTONS_X, SETTINGS_BUTTON_Y);
        }

        // exit button
        if(Gdx.input.getX() > PLAY_SETTINGS_EXIT_BUTTONS_X && Gdx.input.getX() < (PLAY_SETTINGS_EXIT_BUTTONS_X + playButton.getWidth())
                && (Gdx.graphics.getHeight() - Gdx.input.getY()) > EXIT_BUTTON_Y && (Gdx.graphics.getHeight() - Gdx.input.getY()) < (EXIT_BUTTON_Y + playButton.getHeight())) {
            game.batch.draw(exitButtonActive, PLAY_SETTINGS_EXIT_BUTTONS_X, EXIT_BUTTON_Y);
            // exit game
            if(Gdx.input.isTouched()){ Gdx.app.exit(); }
        } else {
            game.batch.draw(exitButton, PLAY_SETTINGS_EXIT_BUTTONS_X, EXIT_BUTTON_Y);
        }

        game.batch.draw(yellowCar, (1440 - 398) - 100, 0);
        game.batch.end();
    }
}
