package MenuScreen;

import Menu.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.RaceGame;
import org.lwjgl.opengl.GLContext;

public class MenuScreen implements Screen {
    private final int PLAY_SETTINGS_EXIT_BUTTONS_X = (Gdx.graphics.getWidth()/2) - (322/2);
    private final int PLAY_BUTTON_Y = 500;
    private final int SETTINGS_BUTTON_Y = 400;
    private final int EXIT_BUTTON_Y = 300;

    private int count;


    private RaceGame game;
    private Stage stage;
    private Player currentPlayer;

    private SpriteBatch batch;
    private Texture title;
    private Texture playButton;
    private Texture playButtonActive;
    private Texture settingsButton;
    private Texture settingsButtonActive;
    private Texture exitButton;
    private Texture exitButtonActive;
    private Texture yellowCar;

    private TextButton.TextButtonStyle textButtonStyle;
    private TextButton playButtonInvisible;
    private TextButton settingsButtonInvisible;
    private TextButton exitButtonInvisible;

    public MenuScreen(RaceGame game, Player player){
        // set up
        currentPlayer = player;
        this.game = game;
        this.stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        loadImages();
        invisibleButtons();
    }

    // load some images
    private void loadImages(){
        this.batch = new SpriteBatch();
        this.title = new Texture("core\\assets\\Menu\\Skrrrt.png");
        this.playButton = new Texture("core\\assets\\Menu\\PlayButton.png");
        this.playButtonActive = new Texture("core\\assets\\Menu\\PlayButtonActive.png");
        this.settingsButton = new Texture("core\\assets\\Menu\\SettingsButton.png");
        this.settingsButtonActive = new Texture("core\\assets\\Menu\\SettingsButtonActive.png");
        this.exitButton = new Texture("core\\assets\\Menu\\ExitButton.png");
        this.exitButtonActive = new Texture("core\\assets\\Menu\\ExitButtonActive.png");
        this.yellowCar = new Texture("core\\assets\\Menu\\YellowCarBrakes.png");
    }

    // draw some invisible buttons
    private void invisibleButtons(){
        // text button style
        this.textButtonStyle = new TextButton.TextButtonStyle();
        this.textButtonStyle.font = new BitmapFont();

        // draw invisible play button
        this.playButtonInvisible = new TextButton("", textButtonStyle);
        this.playButtonInvisible.setPosition(PLAY_SETTINGS_EXIT_BUTTONS_X, PLAY_BUTTON_Y);
        this.playButtonInvisible.setWidth(playButton.getWidth());
        this.playButtonInvisible.setHeight(playButton.getHeight());

        // draw invisible settings button
        this.settingsButtonInvisible = new TextButton("", textButtonStyle);
        this.settingsButtonInvisible.setPosition(PLAY_SETTINGS_EXIT_BUTTONS_X, SETTINGS_BUTTON_Y);
        this.settingsButtonInvisible.setWidth(settingsButton.getWidth());
        this.settingsButtonInvisible.setHeight(settingsButton.getHeight());

        // draw invisible exit button
        this.exitButtonInvisible = new TextButton("", textButtonStyle);
        this.exitButtonInvisible.setPosition(PLAY_SETTINGS_EXIT_BUTTONS_X, EXIT_BUTTON_Y);
        this.exitButtonInvisible.setWidth(exitButton.getWidth());
        this.exitButtonInvisible.setHeight(exitButton.getHeight());

        // add actors to stage
        stage.addActor(playButtonInvisible);
        stage.addActor(settingsButtonInvisible);
        stage.addActor(exitButtonInvisible);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        menuScreen();
        isClicked();

        // draw stage
        stage.act(delta);
        stage.draw();
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
        title.dispose();
        playButton.dispose();
        playButtonActive.dispose();
        settingsButton.dispose();
        settingsButtonActive.dispose();
        exitButton.dispose();
        exitButtonActive.dispose();
        yellowCar.dispose();
        stage.dispose();
        batch.dispose();
    }

    // draw menu screen
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

    // check if some button is clicked
    private void isClicked(){
        // playbutton
        playButtonInvisible.addListener(new ClickListener(Input.Buttons.LEFT) {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                count++;
                if(count == 1)
                    game.setScreen(new LobbyScreen(game));
            }
        });

        // settings button
        settingsButtonInvisible.addListener(new ClickListener(Input.Buttons.LEFT) {
            @Override
            public void clicked(InputEvent event, float x, float y) {
//                count++;
//                if(count == 1)
            }
        });

        // exit button
        exitButtonInvisible.addListener(new ClickListener(Input.Buttons.LEFT) {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                count++;
                if(count == 1)
                    Gdx.app.exit();
            }
        });
    }
}
