package Menu;

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

public class LobbyScreen implements Screen {
    private final int COLUMNS_X = 30;
    private final int FIRST_COLUMN_LIGHT_Y = 650;
    private final int MIDDLE_COLUMN_DARK_Y = 532;
    private final int MIDDLE_COLUMN_LIGHT_Y = 409;
    private final int LAST_COLUMN_DARK_Y = 43;

    private final int BUTTONS_X = 1223;
    private final int CREATE_BUTTON_Y = 731;
    private final int JOIN_BUTTON_Y = 671;
    private final int BACK_BUTTON_Y = 43;

    private int count;

    private RaceGame game;
    private Stage stage;

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
    private Texture backButton;
    private Texture backButtonActive;

    private TextButton.TextButtonStyle textButtonStyle;
    private TextButton createButtonInvisible;
    private TextButton joinButtonInvisible;
    private TextButton backButtonInvisible;

    public LobbyScreen(RaceGame game){
        this.game = game;
        setUp();
    }

    private void setUp(){
        this.stage = new Stage();
        Gdx.input.setInputProcessor(stage);

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
        this.backButton = new Texture("core\\assets\\Menu\\BackButton.png");
        this.backButtonActive = new Texture("core\\assets\\Menu\\BackButtonActive.png");

        // textbuttons invisible
        this.textButtonStyle = new TextButton.TextButtonStyle();
        this.textButtonStyle.font = new BitmapFont();

        // create button
        this.createButtonInvisible = new TextButton("", textButtonStyle);
        this.createButtonInvisible.setPosition(BUTTONS_X, CREATE_BUTTON_Y);
        this.createButtonInvisible.setWidth(createButton.getWidth());
        this.createButtonInvisible.setHeight(createButton.getHeight());

        // join button
        this.joinButtonInvisible = new TextButton("", textButtonStyle);
        this.joinButtonInvisible.setPosition(BUTTONS_X, JOIN_BUTTON_Y);
        this.joinButtonInvisible.setWidth(joinButton.getWidth());
        this.joinButtonInvisible.setHeight(joinButton.getHeight());

        // back button
        this.backButtonInvisible = new TextButton("", textButtonStyle);
        this.backButtonInvisible.setPosition(BUTTONS_X, BACK_BUTTON_Y);
        this.backButtonInvisible.setWidth(backButton.getWidth());
        this.backButtonInvisible.setHeight(backButton.getHeight());

        stage.addActor(createButtonInvisible);
        stage.addActor(joinButtonInvisible);
        stage.addActor(backButtonInvisible);
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

    private void lobbyScreen(){
        batch.begin();

        // draw title
        batch.draw(title, 30, 880 - 78);

        // draw columns
        int tempDarkY = MIDDLE_COLUMN_DARK_Y;
        int tempLightY = MIDDLE_COLUMN_LIGHT_Y;

        // draw first column (light)
        if(Gdx.input.getX() > COLUMNS_X && Gdx.input.getX() < (COLUMNS_X + firstColumnLight.getWidth())
                && (Gdx.graphics.getHeight() - Gdx.input.getY()) > FIRST_COLUMN_LIGHT_Y && (Gdx.graphics.getHeight() - Gdx.input.getY()) < (FIRST_COLUMN_LIGHT_Y + firstColumnLight.getHeight())) {
            batch.draw(firstColumnLightActive, COLUMNS_X, FIRST_COLUMN_LIGHT_Y);
        } else {
            batch.draw(firstColumnLight, COLUMNS_X, FIRST_COLUMN_LIGHT_Y);
        }

        // draw middle columns (light & dark)
        for(int i = 0; i < 2; i++) {
            // draw middle column (dark)
            if(Gdx.input.getX() > COLUMNS_X && Gdx.input.getX() < (COLUMNS_X + middleColumnDark.getWidth())
                    && (Gdx.graphics.getHeight() - Gdx.input.getY()) > tempDarkY && (Gdx.graphics.getHeight() - Gdx.input.getY()) < (tempDarkY + middleColumnDark.getHeight())) {
                batch.draw(middleColumnDarkActive, COLUMNS_X, tempDarkY);
            } else {
                batch.draw(middleColumnDark, COLUMNS_X, tempDarkY);
            }

            // draw middle column (light)
            if(Gdx.input.getX() > COLUMNS_X && Gdx.input.getX() < (COLUMNS_X + middleColumnLight.getWidth())
                    && (Gdx.graphics.getHeight() - Gdx.input.getY()) > tempLightY && (Gdx.graphics.getHeight() - Gdx.input.getY()) < (tempLightY + middleColumnLight.getHeight())) {
                batch.draw(middleColumnLightActive, COLUMNS_X, tempLightY);
            } else {
                batch.draw(middleColumnLight, COLUMNS_X, tempLightY);
            }

            tempDarkY -= 241; tempLightY -= 241;
        }

        // draw last column (dark)
        if(Gdx.input.getX() > COLUMNS_X && Gdx.input.getX() < (COLUMNS_X + lastColumnDark.getWidth())
                && (Gdx.graphics.getHeight() - Gdx.input.getY()) > LAST_COLUMN_DARK_Y && (Gdx.graphics.getHeight() - Gdx.input.getY()) < (LAST_COLUMN_DARK_Y + lastColumnDark.getHeight())) {
            batch.draw(lastColumnDarkActive, COLUMNS_X, LAST_COLUMN_DARK_Y);
        } else {
            batch.draw(lastColumnDark, COLUMNS_X, LAST_COLUMN_DARK_Y);
        }

        // draw create button
        if(Gdx.input.getX() > BUTTONS_X && Gdx.input.getX() < (BUTTONS_X + createButton.getWidth())
                && (Gdx.graphics.getHeight() - Gdx.input.getY()) > CREATE_BUTTON_Y && (Gdx.graphics.getHeight() - Gdx.input.getY()) < (CREATE_BUTTON_Y + createButton.getHeight())) {
            batch.draw(createButtonActive, BUTTONS_X, CREATE_BUTTON_Y);
        } else {
            batch.draw(createButton, BUTTONS_X, CREATE_BUTTON_Y);
        }

        // draw join button
        if(Gdx.input.getX() > BUTTONS_X && Gdx.input.getX() < (BUTTONS_X + joinButton.getWidth())
                && (Gdx.graphics.getHeight() - Gdx.input.getY()) > JOIN_BUTTON_Y && (Gdx.graphics.getHeight() - Gdx.input.getY()) < (JOIN_BUTTON_Y + joinButton.getHeight())) {
            batch.draw(joinButtonActive, BUTTONS_X, JOIN_BUTTON_Y);
        } else {
            batch.draw(joinButton, BUTTONS_X, JOIN_BUTTON_Y);
        }

        // draw back button
        if(Gdx.input.getX() > BUTTONS_X && Gdx.input.getX() < (BUTTONS_X + backButton.getWidth())
                && (Gdx.graphics.getHeight() - Gdx.input.getY()) > BACK_BUTTON_Y && (Gdx.graphics.getHeight() - Gdx.input.getY()) < (BACK_BUTTON_Y + backButton.getHeight())) {
            batch.draw(backButtonActive, BUTTONS_X, BACK_BUTTON_Y);
        } else {
            batch.draw(backButton, BUTTONS_X, BACK_BUTTON_Y);
        }

        batch.end();
    }

    private void isClicked(){
        int tempDarkY = MIDDLE_COLUMN_DARK_Y;
        int tempLightY = MIDDLE_COLUMN_LIGHT_Y;
        // first column (light)
        if(Gdx.input.getX() > COLUMNS_X && Gdx.input.getX() < (COLUMNS_X + firstColumnLight.getWidth())
                && (Gdx.graphics.getHeight() - Gdx.input.getY()) > FIRST_COLUMN_LIGHT_Y && (Gdx.graphics.getHeight() - Gdx.input.getY()) < (FIRST_COLUMN_LIGHT_Y + firstColumnLight.getHeight())) {
            if(Gdx.input.isTouched()){
                //
            }
        }

        // middle columns (light & dark)
        for(int i = 0; i < 2; i++) {
            // middle column (dark)
            if(Gdx.input.getX() > COLUMNS_X && Gdx.input.getX() < (COLUMNS_X + middleColumnDark.getWidth())
                    && (Gdx.graphics.getHeight() - Gdx.input.getY()) > tempDarkY && (Gdx.graphics.getHeight() - Gdx.input.getY()) < (tempDarkY + middleColumnDark.getHeight())) {
                if(Gdx.input.isTouched()){
                    //
                }
            }

            // middle column (light)
            if(Gdx.input.getX() > COLUMNS_X && Gdx.input.getX() < (COLUMNS_X + middleColumnLight.getWidth())
                    && (Gdx.graphics.getHeight() - Gdx.input.getY()) > tempLightY && (Gdx.graphics.getHeight() - Gdx.input.getY()) < (tempLightY + middleColumnLight.getHeight())) {
                if(Gdx.input.isTouched()){
                    //
                }
            }

            tempDarkY -= 241; tempLightY -= 241;
        }

        // last column (dark)
        if(Gdx.input.getX() > COLUMNS_X && Gdx.input.getX() < (COLUMNS_X + lastColumnDark.getWidth())
                && (Gdx.graphics.getHeight() - Gdx.input.getY()) > LAST_COLUMN_DARK_Y && (Gdx.graphics.getHeight() - Gdx.input.getY()) < (LAST_COLUMN_DARK_Y + lastColumnDark.getHeight())) {
            if(Gdx.input.isTouched()){
                //
            }
        }

        createButtonInvisible.addListener(new ClickListener(Input.Buttons.LEFT){
            @Override
            public void clicked(InputEvent event, float x, float y) {
//                count++;
//                if(count == 1)
            }
        });

        joinButtonInvisible.addListener(new ClickListener(Input.Buttons.LEFT){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                count++;
                if(count == 1)
                game.setScreen(new MatchScreen(game));
            }
        });

        backButtonInvisible.addListener(new ClickListener(Input.Buttons.LEFT){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                count++;
                if(count == 1)
                game.setScreen(new MenuScreen(game));
            }
        });


    }
}
