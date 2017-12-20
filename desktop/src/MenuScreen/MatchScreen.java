package MenuScreen;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.mygdx.game.Networking.Lobby;
import Menu.*;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MatchScreen implements Screen {
    private final int COLUMNS_X = 30;
    private final int FIRST_COLUMN_LIGHT_Y = 650;
    private final int MIDDLE_COLUMN_DARK_Y = 532;
    private final int MIDDLE_COLUMN_LIGHT_Y = 409;
    private final int LAST_COLUMN_DARK_Y = 43;

    private int count = 0;

    private final int BUTTONS_X = 1223;
    private final int START_BUTTON_Y = 731;
    private final int KICK_BUTTON_Y = 671;
    private final int SETTINGS_BUTTON_Y = 611;
    private final int LEAVE_BUTTON_Y = 43;

    private RaceGame game;
    private Stage stage;
    private Player currentPlayer;
    private Lobby lobby;
    private Menu menu;

    private SpriteBatch batch;
    private Texture title;
    private Texture firstColumnLight;
    private Texture firstColumnLightActive;
    private Texture middleColumnDark;
    private Texture middleColumnDarkActive;
    private Texture middleColumnLight;
    private Texture middleColumnLightActive;
    private Texture lastColumnDark;
    private Texture lastColumnDarkActive;
    private Texture readyButton;
    private Texture readyButtonActive;
    private Texture kickButton;
    private Texture kickButtonActive;
    private Texture settingsButton;
    private Texture settingsButtonActive;
    private Texture leaveButton;
    private Texture leaveButtonActive;
    private Texture playerReady;

    private TextButton.TextButtonStyle textButtonStyle;
    private TextButton readyButtonInvisible;
    private TextButton kickButtonInvisible;
    private TextButton settingsButtonInvisible;
    private TextButton leaveButtonInvisible;

    private List<Label> playerList;
    private Label.LabelStyle labelStyle;

    public MatchScreen(RaceGame game, Player player, Lobby lobby, Menu menu) {
        // set up
        this.game = game;
        this.stage = new Stage();
        this.currentPlayer = player;
        this.lobby = lobby;
        this.menu = menu;
        Gdx.input.setInputProcessor(stage);

        loadImages();
        invisibleButtons();
    }

    // load some images
    private void loadImages() {
        this.batch = new SpriteBatch();
        this.title = new Texture("core\\assets\\Menu\\SkrrrtSmall.png");
        this.firstColumnLight = new Texture("core\\assets\\Menu\\FirstColumnLight.png");
        this.firstColumnLightActive = new Texture("core\\assets\\Menu\\FirstColumnLightActive.png");
        this.middleColumnDark = new Texture("core\\assets\\Menu\\MiddleColumnDark.png");
        this.middleColumnDarkActive = new Texture("core\\assets\\Menu\\MiddleColumnDarkActive.png");
        this.middleColumnLight = new Texture("core\\assets\\Menu\\MiddleColumnLight.png");
        this.middleColumnLightActive = new Texture("core\\assets\\Menu\\MiddleColumnLightActive.png");
        this.lastColumnDark = new Texture("core\\assets\\Menu\\LastColumnDark.png");
        this.lastColumnDarkActive = new Texture("core\\assets\\Menu\\LastColumnDarkActive.png");
        this.readyButton = new Texture("core\\assets\\Menu\\ReadyButton.png");
        this.readyButtonActive = new Texture("core\\assets\\Menu\\ReadyButtonActive.png");
        this.kickButton = new Texture("core\\assets\\Menu\\KickButton.png");
        this.kickButtonActive = new Texture("core\\assets\\Menu\\KickButtonActive.png");
        this.settingsButton = new Texture("core\\assets\\Menu\\SettingsButtonSmall.png");
        this.settingsButtonActive = new Texture("core\\assets\\Menu\\SettingsButtonSmallActive.png");
        this.leaveButton = new Texture("core\\assets\\Menu\\LeaveButton.png");
        this.leaveButtonActive = new Texture("core\\assets\\Menu\\LeaveButtonActive.png");
        this.playerReady = new Texture("core\\assets\\Menu\\PlayerReady.png");
    }

    // draw some invisibleButtons
    private void invisibleButtons() {
        // text button style
        this.textButtonStyle = new TextButton.TextButtonStyle();
        this.textButtonStyle.font = new BitmapFont();

        // draw invisible start button
        this.readyButtonInvisible = new TextButton("", textButtonStyle);
        this.readyButtonInvisible.setPosition(BUTTONS_X, START_BUTTON_Y);
        this.readyButtonInvisible.setWidth(readyButton.getWidth());
        this.readyButtonInvisible.setHeight(readyButton.getHeight());

        // draw invisible kick button
        this.kickButtonInvisible = new TextButton("", textButtonStyle);
        this.kickButtonInvisible.setPosition(BUTTONS_X, KICK_BUTTON_Y);
        this.kickButtonInvisible.setWidth(kickButton.getWidth());
        this.kickButtonInvisible.setHeight(kickButton.getHeight());

        // draw invisible settings button
        this.settingsButtonInvisible = new TextButton("", textButtonStyle);
        this.settingsButtonInvisible.setPosition(BUTTONS_X, SETTINGS_BUTTON_Y);
        this.settingsButtonInvisible.setWidth(settingsButton.getWidth());
        this.settingsButtonInvisible.setHeight(settingsButton.getHeight());

        // draw invisible leave button
        this.leaveButtonInvisible = new TextButton("", textButtonStyle);
        this.leaveButtonInvisible.setPosition(BUTTONS_X, LEAVE_BUTTON_Y);
        this.leaveButtonInvisible.setWidth(leaveButton.getWidth());
        this.leaveButtonInvisible.setHeight(leaveButton.getHeight());

        // load font
        FreeTypeFontGenerator FTFG = new FreeTypeFontGenerator(Gdx.files.internal("core\\assets\\Menu\\BerlinSansFBRegular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter FTFP = new FreeTypeFontGenerator.FreeTypeFontParameter();
        FTFP.size = 55;
        BitmapFont bitmapFont = FTFG.generateFont(FTFP);
        FTFG.dispose();

        // draw and add empty lobbies
        this.playerList = new ArrayList<Label>();
        this.labelStyle = new Label.LabelStyle();
        this.labelStyle.font = bitmapFont;
        this.labelStyle.fontColor = Color.valueOf("ffffff");
        List<Player> players = lobby.getPlayers();
        for (int i = 0; i < players.size(); i++) {
            playerList.add(new Label(players.get(i).toString(), labelStyle));
            stage.addActor(playerList.get(i));
        }
        for (int i = players.size(); i < 4; i++) {
            playerList.add(new Label("Empty", labelStyle));
            stage.addActor(playerList.get(i));
        }

        // add actors to stage
        stage.addActor(readyButtonInvisible);
        stage.addActor(kickButtonInvisible);
        stage.addActor(settingsButtonInvisible);
        stage.addActor(leaveButtonInvisible);

        // start button
        readyButtonInvisible.addListener(new ClickListener(Input.Buttons.LEFT) {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                menu.playerReady(lobby);
                batch.draw(playerReady, COLUMNS_X + firstColumnLight.getWidth() - 100, getCurrentLabelPlayer().getY());
            }
        });

        // kick button
        kickButtonInvisible.addListener(new ClickListener(Input.Buttons.LEFT) {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            }
        });


        // settings button
        settingsButtonInvisible.addListener(new ClickListener(Input.Buttons.LEFT) {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            }
        });

        // leave button
        leaveButtonInvisible.addListener(new ClickListener(Input.Buttons.LEFT) {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                count++;
                if (count == 1) {
                    if (currentPlayer == lobby.getHost()) {
                        menu.removeLobby(lobby);
                    }
                    lobby.leaveLobby(currentPlayer);
                    game.setScreen(new LobbyScreen(game, currentPlayer, menu));
                }
            }
        });
    }

    public Label getCurrentLabelPlayer(){
        for(Label playerLabel : playerList){
            if(currentPlayer.toString() == playerLabel.getText().toString()){
                return playerLabel;
            }
        }
        return null;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        matchScreen();
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

    }

    @Override
    public void dispose() {
        title.dispose();
        firstColumnLight.dispose();
        firstColumnLightActive.dispose();
        middleColumnDark.dispose();
        middleColumnDarkActive.dispose();
        middleColumnLight.dispose();
        middleColumnLightActive.dispose();
        lastColumnDark.dispose();
        lastColumnDarkActive.dispose();
        readyButton.dispose();
        readyButtonActive.dispose();
        kickButton.dispose();
        kickButtonActive.dispose();
        settingsButton.dispose();
        settingsButtonActive.dispose();
        leaveButton.dispose();
        leaveButtonActive.dispose();
        stage.dispose();
        batch.dispose();
    }

    // draw match screen
    private void matchScreen() {
        batch.begin();

        // draw title
        batch.draw(title, 30, 880 - 78);

        // draw columns
        int tempDarkY = MIDDLE_COLUMN_DARK_Y;
        int tempLightY = MIDDLE_COLUMN_LIGHT_Y;

        // draw first column (light)
        playerList.get(0).setPosition(COLUMNS_X + 20, FIRST_COLUMN_LIGHT_Y + (firstColumnLight.getHeight() / 3) - 10);
        if (Gdx.input.getX() > COLUMNS_X && Gdx.input.getX() < (COLUMNS_X + firstColumnLight.getWidth())
                && (Gdx.graphics.getHeight() - Gdx.input.getY()) > FIRST_COLUMN_LIGHT_Y && (Gdx.graphics.getHeight() - Gdx.input.getY()) < (FIRST_COLUMN_LIGHT_Y + firstColumnLight.getHeight())) {
            batch.draw(firstColumnLightActive, COLUMNS_X, FIRST_COLUMN_LIGHT_Y);
        } else {
            batch.draw(firstColumnLight, COLUMNS_X, FIRST_COLUMN_LIGHT_Y);
        }

        // draw middle columns (light & dark)
        for (int i = 0; i < 2; i++) {
            // draw middle column (dark)
            int tempLabelY = FIRST_COLUMN_LIGHT_Y - firstColumnLight.getHeight();
            for (int j = 1; j < 4; j++) {
                playerList.get(j).setPosition(COLUMNS_X + 20, tempLabelY + (firstColumnLight.getHeight() / 3));
                tempLabelY -= 120;
            }

            if (Gdx.input.getX() > COLUMNS_X && Gdx.input.getX() < (COLUMNS_X + middleColumnDark.getWidth())
                    && (Gdx.graphics.getHeight() - Gdx.input.getY()) > tempDarkY && (Gdx.graphics.getHeight() - Gdx.input.getY()) < (tempDarkY + middleColumnDark.getHeight())) {
                batch.draw(middleColumnDarkActive, COLUMNS_X, tempDarkY);
            } else {
                batch.draw(middleColumnDark, COLUMNS_X, tempDarkY);
            }

            // draw middle column (light)
            if (Gdx.input.getX() > COLUMNS_X && Gdx.input.getX() < (COLUMNS_X + middleColumnLight.getWidth())
                    && (Gdx.graphics.getHeight() - Gdx.input.getY()) > tempLightY && (Gdx.graphics.getHeight() - Gdx.input.getY()) < (tempLightY + middleColumnLight.getHeight())) {
                batch.draw(middleColumnLightActive, COLUMNS_X, tempLightY);
            } else {
                batch.draw(middleColumnLight, COLUMNS_X, tempLightY);
            }

            tempDarkY -= 241;
            tempLightY -= 241;
        }

        // draw last column (dark)
        if (Gdx.input.getX() > COLUMNS_X && Gdx.input.getX() < (COLUMNS_X + lastColumnDark.getWidth())
                && (Gdx.graphics.getHeight() - Gdx.input.getY()) > LAST_COLUMN_DARK_Y && (Gdx.graphics.getHeight() - Gdx.input.getY()) < (LAST_COLUMN_DARK_Y + lastColumnDark.getHeight())) {
            batch.draw(lastColumnDarkActive, COLUMNS_X, LAST_COLUMN_DARK_Y);
        } else {
            batch.draw(lastColumnDark, COLUMNS_X, LAST_COLUMN_DARK_Y);
        }

        // draw start button
        if (Gdx.input.getX() > BUTTONS_X && Gdx.input.getX() < (BUTTONS_X + readyButton.getWidth())
                && (Gdx.graphics.getHeight() - Gdx.input.getY()) > START_BUTTON_Y && (Gdx.graphics.getHeight() - Gdx.input.getY()) < (START_BUTTON_Y + readyButton.getHeight())) {
            batch.draw(readyButtonActive, BUTTONS_X, START_BUTTON_Y);
        } else {
            batch.draw(readyButton, BUTTONS_X, START_BUTTON_Y);
        }

        // draw kick button
        if (Gdx.input.getX() > BUTTONS_X && Gdx.input.getX() < (BUTTONS_X + kickButton.getWidth())
                && (Gdx.graphics.getHeight() - Gdx.input.getY()) > KICK_BUTTON_Y && (Gdx.graphics.getHeight() - Gdx.input.getY()) < (KICK_BUTTON_Y + kickButton.getHeight())) {
            batch.draw(kickButtonActive, BUTTONS_X, KICK_BUTTON_Y);
        } else {
            batch.draw(kickButton, BUTTONS_X, KICK_BUTTON_Y);
        }

        // draw settings button
        if (Gdx.input.getX() > BUTTONS_X && Gdx.input.getX() < (BUTTONS_X + settingsButton.getWidth())
                && (Gdx.graphics.getHeight() - Gdx.input.getY()) > SETTINGS_BUTTON_Y && (Gdx.graphics.getHeight() - Gdx.input.getY()) < (SETTINGS_BUTTON_Y + settingsButton.getHeight())) {
            batch.draw(settingsButtonActive, BUTTONS_X, SETTINGS_BUTTON_Y);
        } else {
            batch.draw(settingsButton, BUTTONS_X, SETTINGS_BUTTON_Y);
        }

        // draw leave button
        if (Gdx.input.getX() > BUTTONS_X && Gdx.input.getX() < (BUTTONS_X + leaveButton.getWidth())
                && (Gdx.graphics.getHeight() - Gdx.input.getY()) > LEAVE_BUTTON_Y && (Gdx.graphics.getHeight() - Gdx.input.getY()) < (LEAVE_BUTTON_Y + leaveButton.getHeight())) {
            batch.draw(leaveButtonActive, BUTTONS_X, LEAVE_BUTTON_Y);
        } else {
            batch.draw(leaveButton, BUTTONS_X, LEAVE_BUTTON_Y);
        }

        batch.end();
    }

    // check if some button is clicked
    private void isClicked() {
        int tempDarkY = MIDDLE_COLUMN_DARK_Y;
        int tempLightY = MIDDLE_COLUMN_LIGHT_Y;
        // first column (light)
        if (Gdx.input.getX() > COLUMNS_X && Gdx.input.getX() < (COLUMNS_X + firstColumnLight.getWidth())
                && (Gdx.graphics.getHeight() - Gdx.input.getY()) > FIRST_COLUMN_LIGHT_Y && (Gdx.graphics.getHeight() - Gdx.input.getY()) < (FIRST_COLUMN_LIGHT_Y + firstColumnLight.getHeight())) {
            if (Gdx.input.isTouched()) {
                //
            }
        }

        // middle columns (light & dark)
        for (int i = 0; i < 2; i++) {
            // middle column (dark)
            if (Gdx.input.getX() > COLUMNS_X && Gdx.input.getX() < (COLUMNS_X + middleColumnDark.getWidth())
                    && (Gdx.graphics.getHeight() - Gdx.input.getY()) > tempDarkY && (Gdx.graphics.getHeight() - Gdx.input.getY()) < (tempDarkY + middleColumnDark.getHeight())) {
                if (Gdx.input.isTouched()) {
                    //
                }
            }

            // middle column (light)
            if (Gdx.input.getX() > COLUMNS_X && Gdx.input.getX() < (COLUMNS_X + middleColumnLight.getWidth())
                    && (Gdx.graphics.getHeight() - Gdx.input.getY()) > tempLightY && (Gdx.graphics.getHeight() - Gdx.input.getY()) < (tempLightY + middleColumnLight.getHeight())) {
                if (Gdx.input.isTouched()) {
                    //
                }
            }

            tempDarkY -= 241;
            tempLightY -= 241;
        }

        // last column (dark)
        if (Gdx.input.getX() > COLUMNS_X && Gdx.input.getX() < (COLUMNS_X + lastColumnDark.getWidth())
                && (Gdx.graphics.getHeight() - Gdx.input.getY()) > LAST_COLUMN_DARK_Y && (Gdx.graphics.getHeight() - Gdx.input.getY()) < (LAST_COLUMN_DARK_Y + lastColumnDark.getHeight())) {
            if (Gdx.input.isTouched()) {
                //
            }
        }
    }
}
