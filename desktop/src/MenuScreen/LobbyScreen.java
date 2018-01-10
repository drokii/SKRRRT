package MenuScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Networking.Lobby;
import com.mygdx.game.RaceGame;
import Menu.Menu;
import de.tomgrill.gdxdialogs.core.GDXDialogs;
import de.tomgrill.gdxdialogs.core.GDXDialogsSystem;
import de.tomgrill.gdxdialogs.core.dialogs.GDXTextPrompt;
import de.tomgrill.gdxdialogs.core.listener.TextPromptListener;
import Menu.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LobbyScreen implements Screen {
    private Menu menu;
    private GDXDialogs dialogs;
    private int columnClicked = 0;

    private final int COLUMNS_X = 30;
    private final int FIRST_COLUMN_LIGHT_Y = 650;
    private final int MIDDLE_COLUMN_DARK_Y = 532;
    private final int MIDDLE_COLUMN_LIGHT_Y = 409;
    private final int LAST_COLUMN_DARK_Y = 43;

    private final int BUTTONS_X = 1223;
    private final int CREATE_BUTTON_Y = 731;
    private final int JOIN_BUTTON_Y = 671;
    private final int REFRESH_BUTTON_Y = 611;
    private final int BACK_BUTTON_Y = 43;

    private int joinedLobby;

    private RaceGame game;
    private Stage stage;
    private Player currentPlayer;

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
    private Texture createButton;
    private Texture createButtonActive;
    private Texture joinButton;
    private Texture joinButtonActive;
    private Texture refreshButton;
    private Texture refreshButtonActive;
    private Texture backButton;
    private Texture backButtonActive;

    private TextButton.TextButtonStyle textButtonStyle;
    private TextButton createButtonInvisible;
    private TextButton joinButtonInvisible;
    private TextButton refreshButtonInvisible;
    private TextButton backButtonInvisible;

    private List<Label> labelList;
    private Label.LabelStyle labelStyle;

    public LobbyScreen(RaceGame game, Player player) throws IOException {
        this.game = game;
        this.stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        this.menu = new Menu(game, this);
        this.dialogs = GDXDialogsSystem.install();
        this.currentPlayer = player;

        loadImages();
        invisibleButtons();
        refreshLobbies();
    }

    public LobbyScreen(RaceGame game, Player player, Menu menu) {
        this.game = game;
        this.stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        this.menu = menu;
        this.dialogs = GDXDialogsSystem.install();
        this.currentPlayer = player;

        loadImages();
        invisibleButtons();
        refreshLobbies();
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
        this.createButton = new Texture("core\\assets\\Menu\\CreateButton.png");
        this.createButtonActive = new Texture("core\\assets\\Menu\\CreateButtonActive.png");
        this.joinButton = new Texture("core\\assets\\Menu\\JoinButton.png");
        this.joinButtonActive = new Texture("core\\assets\\Menu\\JoinButtonActive.png");
        this.refreshButton = new Texture("core\\assets\\Menu\\RefreshButton.png");
        this.refreshButtonActive = new Texture("core\\assets\\Menu\\RefreshButtonActive.png");
        this.backButton = new Texture("core\\assets\\Menu\\BackButton.png");
        this.backButtonActive = new Texture("core\\assets\\Menu\\BackButtonActive.png");
    }

    // draw some invisible buttons
    private void invisibleButtons() {
        // text button style
        this.textButtonStyle = new TextButton.TextButtonStyle();
        this.textButtonStyle.font = new BitmapFont();

        // draw invisible create button
        this.createButtonInvisible = new TextButton("", textButtonStyle);
        this.createButtonInvisible.setPosition(BUTTONS_X, CREATE_BUTTON_Y);
        this.createButtonInvisible.setWidth(createButton.getWidth());
        this.createButtonInvisible.setHeight(createButton.getHeight());

        // draw invisible join button
        this.joinButtonInvisible = new TextButton("", textButtonStyle);
        this.joinButtonInvisible.setPosition(BUTTONS_X, JOIN_BUTTON_Y);
        this.joinButtonInvisible.setWidth(joinButton.getWidth());
        this.joinButtonInvisible.setHeight(joinButton.getHeight());

        // draw invisible refresh button
        this.refreshButtonInvisible = new TextButton("", textButtonStyle);
        this.refreshButtonInvisible.setPosition(BUTTONS_X, REFRESH_BUTTON_Y);
        this.refreshButtonInvisible.setWidth(refreshButton.getWidth());
        this.refreshButtonInvisible.setHeight(refreshButton.getHeight());

        // draw invisible back button
        this.backButtonInvisible = new TextButton("", textButtonStyle);
        this.backButtonInvisible.setPosition(BUTTONS_X, BACK_BUTTON_Y);
        this.backButtonInvisible.setWidth(backButton.getWidth());
        this.backButtonInvisible.setHeight(backButton.getHeight());

        // load font
        FreeTypeFontGenerator FTFG = new FreeTypeFontGenerator(Gdx.files.internal("core\\assets\\Menu\\BerlinSansFBRegular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter FTFP = new FreeTypeFontGenerator.FreeTypeFontParameter();
        FTFP.size = 55;
        BitmapFont bitmapFont = FTFG.generateFont(FTFP);
        FTFG.dispose();

        // draw and add empty lobbies
        this.labelList = new ArrayList<Label>();
        this.labelStyle = new Label.LabelStyle();
        this.labelStyle.font = bitmapFont;
        this.labelStyle.fontColor = Color.valueOf("ffffff");
        List<Lobby> lobbies = menu.getLobbies();
        for (int i = 0; i < lobbies.size(); i++) {
            labelList.add(new Label(lobbies.get(i).toString(), labelStyle));
            stage.addActor(labelList.get(i));
        }
        for (int i = lobbies.size(); i < 6; i++) {
            labelList.add(new Label("Empty", labelStyle));
            stage.addActor(labelList.get(i));
        }

        // add actors to stage
        stage.addActor(createButtonInvisible);
        stage.addActor(joinButtonInvisible);
        stage.addActor(backButtonInvisible);
        stage.addActor(refreshButtonInvisible);

        createButtonInvisible.addListener(new ClickListener(Input.Buttons.LEFT) {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GDXTextPrompt textPrompt = dialogs.newDialog(GDXTextPrompt.class);
                textPrompt.setTitle("Name");
                textPrompt.setMessage("Please fill in your lobby name");
                textPrompt.setCancelButtonLabel("Cancel");
                textPrompt.setConfirmButtonLabel("Create");

                textPrompt.setTextPromptListener(new TextPromptListener() {
                    @Override
                    public void cancel() {
                    }

                    @Override
                    public void confirm(final String text) {
                        menu.createLobby(text);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Gdx.app.postRunnable(new Runnable() {
                                    @Override
                                    public void run() {
                                        menu.refreshLobbies();
                                        new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Gdx.app.postRunnable(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        menu.joinLobby((menu.getLobbies().size() - 1), currentPlayer);
                                                        //menu.getLobbies().get(menu.getLobbies().size() -1 ).setHost(currentPlayer);
                                                    }
                                                });
                                            }
                                        }).start();
                                    }
                                });
                            }
                        }).start();
                    }
                });
                textPrompt.build().show();
            }
        });

        // join button
        joinButtonInvisible.addListener(new ClickListener(Input.Buttons.LEFT) {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (columnClicked != 0) {
                    joinedLobby = (columnClicked - 1);
                    menu.joinLobby(joinedLobby, currentPlayer);
                }
            }
        });

        // refresh button
        refreshButtonInvisible.addListener(new ClickListener(Input.Buttons.LEFT) {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                refreshLobbies();
            }
        });

        // back button
        backButtonInvisible.addListener(new ClickListener(Input.Buttons.LEFT) {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MenuScreen(game, currentPlayer));
            }
        });
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        lobbyScreen();
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
        System.out.println("aa");
        title.dispose();
        firstColumnLight.dispose();
        firstColumnLightActive.dispose();
        middleColumnDark.dispose();
        middleColumnDarkActive.dispose();
        middleColumnLight.dispose();
        middleColumnLightActive.dispose();
        lastColumnDark.dispose();
        lastColumnDarkActive.dispose();
        createButton.dispose();
        createButtonActive.dispose();
        joinButton.dispose();
        joinButtonActive.dispose();
        backButton.dispose();
        backButtonActive.dispose();
        stage.dispose();
        batch.dispose();
    }

    // draw lobby screen
    private void lobbyScreen() {
        batch.begin();

        // draw title
        batch.draw(title, 30, 880 - 78);

        // draw columns
        int tempDarkY = MIDDLE_COLUMN_DARK_Y;
        int tempLightY = MIDDLE_COLUMN_LIGHT_Y;

        // draw first column (light)
        labelList.get(0).setPosition(COLUMNS_X + 20, FIRST_COLUMN_LIGHT_Y + (firstColumnLight.getHeight() / 3) - 10);
        if (Gdx.input.getX() > COLUMNS_X && Gdx.input.getX() < (COLUMNS_X + firstColumnLight.getWidth())
                && (Gdx.graphics.getHeight() - Gdx.input.getY()) > FIRST_COLUMN_LIGHT_Y && (Gdx.graphics.getHeight() - Gdx.input.getY()) < (FIRST_COLUMN_LIGHT_Y + firstColumnLight.getHeight())
                || columnClicked == 1) {
            batch.draw(firstColumnLightActive, COLUMNS_X, FIRST_COLUMN_LIGHT_Y);
        } else {
            batch.draw(firstColumnLight, COLUMNS_X, FIRST_COLUMN_LIGHT_Y);
        }

        // draw middle columns (light & dark)
        for (int i = 0; i < 2; i++) {
            // draw middle column (dark)
            int tempLabelY = FIRST_COLUMN_LIGHT_Y - firstColumnLight.getHeight();
            for (int j = 1; j < 5; j++) {
                labelList.get(j).setPosition(COLUMNS_X + 20, tempLabelY + (firstColumnLight.getHeight() / 3));
                tempLabelY -= 120;
            }

            if (Gdx.input.getX() > COLUMNS_X && Gdx.input.getX() < (COLUMNS_X + middleColumnDark.getWidth())
                    && (Gdx.graphics.getHeight() - Gdx.input.getY()) > tempDarkY && (Gdx.graphics.getHeight() - Gdx.input.getY()) < (tempDarkY + middleColumnDark.getHeight())
                    || (i == 0 && columnClicked == 2) || (i == 1 && columnClicked == 4)) {
                batch.draw(middleColumnDarkActive, COLUMNS_X, tempDarkY);
            } else {
                batch.draw(middleColumnDark, COLUMNS_X, tempDarkY);
            }

            // draw middle column (light)
            if (Gdx.input.getX() > COLUMNS_X && Gdx.input.getX() < (COLUMNS_X + middleColumnLight.getWidth())
                    && (Gdx.graphics.getHeight() - Gdx.input.getY()) > tempLightY && (Gdx.graphics.getHeight() - Gdx.input.getY()) < (tempLightY + middleColumnLight.getHeight())
                    || (i == 0 && columnClicked == 3) || (i == 1 && columnClicked == 5)) {
                batch.draw(middleColumnLightActive, COLUMNS_X, tempLightY);
            } else {
                batch.draw(middleColumnLight, COLUMNS_X, tempLightY);
            }

            tempDarkY -= 241;
            tempLightY -= 241;
        }

        // draw last column (dark)
        labelList.get(5).setPosition(COLUMNS_X + 20, LAST_COLUMN_DARK_Y + (lastColumnDark.getHeight() / 3) - 10);
        if (Gdx.input.getX() > COLUMNS_X && Gdx.input.getX() < (COLUMNS_X + lastColumnDark.getWidth())
                && (Gdx.graphics.getHeight() - Gdx.input.getY()) > LAST_COLUMN_DARK_Y && (Gdx.graphics.getHeight() - Gdx.input.getY()) < (LAST_COLUMN_DARK_Y + lastColumnDark.getHeight())
                || columnClicked == 6) {
            batch.draw(lastColumnDarkActive, COLUMNS_X, LAST_COLUMN_DARK_Y);
        } else {
            batch.draw(lastColumnDark, COLUMNS_X, LAST_COLUMN_DARK_Y);
        }

        // draw create button
        if (Gdx.input.getX() > BUTTONS_X && Gdx.input.getX() < (BUTTONS_X + createButton.getWidth())
                && (Gdx.graphics.getHeight() - Gdx.input.getY()) > CREATE_BUTTON_Y && (Gdx.graphics.getHeight() - Gdx.input.getY()) < (CREATE_BUTTON_Y + createButton.getHeight())) {
            batch.draw(createButtonActive, BUTTONS_X, CREATE_BUTTON_Y);
        } else {
            batch.draw(createButton, BUTTONS_X, CREATE_BUTTON_Y);
        }

        // draw join button
        if (Gdx.input.getX() > BUTTONS_X && Gdx.input.getX() < (BUTTONS_X + joinButton.getWidth())
                && (Gdx.graphics.getHeight() - Gdx.input.getY()) > JOIN_BUTTON_Y && (Gdx.graphics.getHeight() - Gdx.input.getY()) < (JOIN_BUTTON_Y + joinButton.getHeight())) {
            batch.draw(joinButtonActive, BUTTONS_X, JOIN_BUTTON_Y);
        } else {
            batch.draw(joinButton, BUTTONS_X, JOIN_BUTTON_Y);
        }

        // draw refresh button
        if (Gdx.input.getX() > BUTTONS_X && Gdx.input.getX() < (BUTTONS_X + refreshButton.getWidth())
                && (Gdx.graphics.getHeight() - Gdx.input.getY()) > REFRESH_BUTTON_Y && (Gdx.graphics.getHeight() - Gdx.input.getY()) < (REFRESH_BUTTON_Y + refreshButton.getHeight())) {
            batch.draw(refreshButtonActive, BUTTONS_X, REFRESH_BUTTON_Y);
        } else {
            batch.draw(refreshButton, BUTTONS_X, REFRESH_BUTTON_Y);
        }

        // draw back button
        if (Gdx.input.getX() > BUTTONS_X && Gdx.input.getX() < (BUTTONS_X + backButton.getWidth())
                && (Gdx.graphics.getHeight() - Gdx.input.getY()) > BACK_BUTTON_Y && (Gdx.graphics.getHeight() - Gdx.input.getY()) < (BACK_BUTTON_Y + backButton.getHeight())) {
            batch.draw(backButtonActive, BUTTONS_X, BACK_BUTTON_Y);
        } else {
            batch.draw(backButton, BUTTONS_X, BACK_BUTTON_Y);
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
                columnClicked = 1;
            }
        }

        // middle columns (light & dark)
        for (int i = 0; i < 2; i++) {
            // middle column (dark)
            if (Gdx.input.getX() > COLUMNS_X && Gdx.input.getX() < (COLUMNS_X + middleColumnDark.getWidth())
                    && (Gdx.graphics.getHeight() - Gdx.input.getY()) > tempDarkY && (Gdx.graphics.getHeight() - Gdx.input.getY()) < (tempDarkY + middleColumnDark.getHeight())) {
                if (Gdx.input.isTouched()) {
                    if (i == 0) {
                        columnClicked = 2;
                    }
                    if (i == 1) {
                        columnClicked = 4;
                    }
                }
            }

            // middle column (light)
            if (Gdx.input.getX() > COLUMNS_X && Gdx.input.getX() < (COLUMNS_X + middleColumnLight.getWidth())
                    && (Gdx.graphics.getHeight() - Gdx.input.getY()) > tempLightY && (Gdx.graphics.getHeight() - Gdx.input.getY()) < (tempLightY + middleColumnLight.getHeight())) {
                if (Gdx.input.isTouched()) {
                    if (i == 0) {
                        columnClicked = 3;
                    }
                    if (i == 1) {
                        columnClicked = 5;
                    }
                }
            }

            tempDarkY -= 241;
            tempLightY -= 241;
        }


        // last column (dark)
        if (Gdx.input.getX() > COLUMNS_X && Gdx.input.getX() < (COLUMNS_X + lastColumnDark.getWidth())
                && (Gdx.graphics.getHeight() - Gdx.input.getY()) > LAST_COLUMN_DARK_Y && (Gdx.graphics.getHeight() - Gdx.input.getY()) < (LAST_COLUMN_DARK_Y + lastColumnDark.getHeight())) {
            if (Gdx.input.isTouched()) {
                columnClicked = 6;
            }
        }
    }


    public void refreshLobbies() {

        menu.getLobbiesRequest();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        if (menu.getLobbies().size() == 0) {

                        } else {
                            for (int i = 0; i < menu.getLobbies().size(); i++) {
                                labelList.get(i).setText(menu.getLobbies().get(i).getName());
                            }
                        }
                    }
                });
            }
        }).start();
    }
}

