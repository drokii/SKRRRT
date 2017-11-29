package MenuScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.mygdx.game.Networking.LoginRequest;
import com.mygdx.game.Networking.LoginResponse;
import com.mygdx.game.Networking.SampleRequest;
import com.mygdx.game.Networking.SampleResponse;
import com.mygdx.game.RaceGame;
import javafx.application.Platform;
import org.lwjgl.Sys;

import java.io.IOException;

public class LogInScreen implements Screen{
    private final int TEXTFIELD_LOGINBUTTON_X = (Gdx.graphics.getWidth()/2) - (322/2);
    private final int TEXTFIELD_USERNAME_Y = 500;
    private final int TEXTFIELD_PASSWORD_Y = 400;
    private final int LOGIN_BUTTON_Y = 300;

    private int count;

    public static Sound menuSound;

    private RaceGame game;
    private Stage stage;

    private SpriteBatch batch;
    private Texture title;
    private BitmapFont bitmapFont;
    private TextField.TextFieldStyle textFieldStyle;
    private Texture backgroundTextField;
    private TextField username;
    private TextField password;
    private Texture loginButton;
    private Texture loginButtonActive;
    private Texture yellowCar;

    private TextButton.TextButtonStyle textButtonStyle;
    private TextButton loginButtonInvisible;

    public LogInScreen(RaceGame game){
        // set up
        this.game = game;
        this.stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        loadImages();
        textFields();
        invisibleButtons();
    }

    // load some images
    private void loadImages(){
        this.batch = new SpriteBatch();
        this.title = new Texture("core\\assets\\Menu\\Skrrrt.png");
        this.backgroundTextField = new Texture("core\\assets\\Menu\\TextFieldBackground.png");
        this.loginButton = new Texture("core\\assets\\Menu\\LoginButton.png");
        this.loginButtonActive = new Texture("core\\assets\\Menu\\LoginButtonActive.png");
        this.yellowCar = new Texture("core\\assets\\Menu\\YellowCarBrakes.png");
    }

    // draw username & password textfield
    private void textFields(){
        // text field style
        this.textFieldStyle = new TextField.TextFieldStyle();
        // load font
        FreeTypeFontGenerator FTFG = new FreeTypeFontGenerator(Gdx.files.internal("core\\assets\\Menu\\BerlinSansFBRegular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter FTFP = new FreeTypeFontGenerator.FreeTypeFontParameter();
        FTFP.size = 55;
        this.bitmapFont = FTFG.generateFont(FTFP);
        FTFG.dispose();
        // set font / settings
        this.textFieldStyle.font = bitmapFont;
        this.textFieldStyle.fontColor = Color.valueOf("a3a3a3");

        // settings textfield username & password
        this.username = new TextField("",textFieldStyle);
        this.username.setMessageText("Username");
        this.username.setPosition(TEXTFIELD_LOGINBUTTON_X, TEXTFIELD_USERNAME_Y);
        this.username.setWidth(322);
        this.username.setHeight(75);
        this.username.setAlignment(3);
        this.password = new TextField("",textFieldStyle);
        this.password.setMessageText("Password");
        this.password.setPasswordMode(true);
        this.password.setPosition(TEXTFIELD_LOGINBUTTON_X, TEXTFIELD_PASSWORD_Y);
        this.password.setWidth(322);
        this.password.setHeight(75);
        this.password.setAlignment(3);
    }

    // draw some invisible buttons
    private void invisibleButtons(){
        // text button style
        this.textButtonStyle = new TextButton.TextButtonStyle();
        this.textButtonStyle.font = bitmapFont;

        // draw invisible login button
        this.loginButtonInvisible = new TextButton("", textButtonStyle);
        this.loginButtonInvisible.setPosition(TEXTFIELD_LOGINBUTTON_X, LOGIN_BUTTON_Y);
        this.loginButtonInvisible.setWidth(loginButton.getWidth());
        this.loginButtonInvisible.setHeight(loginButton.getHeight());

        // add actors to stage
        stage.addActor(username);
        stage.addActor(password);
        stage.addActor(loginButtonInvisible);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        logInScreen();
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
        bitmapFont.dispose();
        backgroundTextField.dispose();
        loginButton.dispose();
        loginButtonActive.dispose();
        yellowCar.dispose();
        stage.dispose();
        batch.dispose();
    }

    // draw login screen
    private void logInScreen(){
        batch.begin();

        // draw title
        batch.draw(title, (1440/2) - (665/2), 670);

        // draw background text field
        batch.draw(backgroundTextField, TEXTFIELD_LOGINBUTTON_X, TEXTFIELD_USERNAME_Y);
        batch.draw(backgroundTextField, TEXTFIELD_LOGINBUTTON_X, TEXTFIELD_PASSWORD_Y);

        // draw log in button
        if(Gdx.input.getX() > TEXTFIELD_LOGINBUTTON_X && Gdx.input.getX() < (TEXTFIELD_LOGINBUTTON_X + loginButton.getWidth())
                && (Gdx.graphics.getHeight() - Gdx.input.getY()) > LOGIN_BUTTON_Y && (Gdx.graphics.getHeight() - Gdx.input.getY()) < (LOGIN_BUTTON_Y + loginButton.getHeight())) {
            batch.draw(loginButtonActive, TEXTFIELD_LOGINBUTTON_X, LOGIN_BUTTON_Y);
        } else {
            batch.draw(loginButton, TEXTFIELD_LOGINBUTTON_X, LOGIN_BUTTON_Y);
        }

        // draw yellow car
        batch.draw(yellowCar, (1440 - 398) - 100, 0);
        batch.end();
    }

    // check if some button is clicked
    private void isClicked(){
        // login button
        loginButtonInvisible.addListener(new ClickListener(Input.Buttons.LEFT) {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                count++;
                if(count == 1) {

                    Client client = new Client();
                    client.start();
                    try
                    {
                        client.connect(5000, "127.0.0.1", 54555, 54777);
                    }
                    catch(IOException e)
                    {
                        e.printStackTrace();
                    }

                    Kryo kryoClient = client.getKryo();
                    kryoClient.register(LoginRequest.class);
                    kryoClient.register(LoginResponse.class);

                    LoginRequest request = new LoginRequest(username.getText(), password.getText());
                    client.sendTCP(request);
                    addListeners(client);
                }
            }
        });
    }

    //check for server login response
    public void addListeners(final Client client) {
        client.addListener(new Listener() {
            public void received(final Connection connection, Object object) {
                if (object instanceof LoginResponse) {
                    LoginResponse response = (LoginResponse) object;
                    if(response.getLoginPassed())
                    {
                        System.out.println("logged in");
                        Gdx.app.postRunnable(new Runnable() {
                            @Override
                            public void run() {
                                menuSound = Gdx.audio.newSound(Gdx.files.internal("core/assets/gas.ogg"));
                                menuSound.play();
                                game.setScreen(new MenuScreen(game));
                                connection.close();
                                client.close();
                            }
                        });
                    }
                    else
                    {
                        System.out.println("login failed");
                        connection.close();
                        client.close();
                        count = 0;
                    }
                }
            }
        });
    }
}
