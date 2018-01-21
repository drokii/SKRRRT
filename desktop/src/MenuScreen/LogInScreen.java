package MenuScreen;

import Menu.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Networking.Client.LoginClient;
import com.mygdx.game.RaceGame;
import de.tomgrill.gdxdialogs.core.GDXDialogs;
import de.tomgrill.gdxdialogs.core.GDXDialogsSystem;
import de.tomgrill.gdxdialogs.core.dialogs.GDXButtonDialog;

public class LogInScreen implements Screen{
    private GDXDialogs dialogs;

    private final int TEXTFIELD_LOGINBUTTON_X = (Gdx.graphics.getWidth()/2) - (322/2);
    private final int TEXTFIELD_USERNAME_Y = 500;
    private final int TEXTFIELD_PASSWORD_Y = 400;
    private final int LOGIN_BUTTON_Y = 300;

    private static Sound menuSound;

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
        this.dialogs = GDXDialogsSystem.install();

        loadImages();
        textFields();
        invisibleButtons();
    }

    public static Sound getMenuSound() {
        return menuSound;
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
        FreeTypeFontGenerator ftfg = new FreeTypeFontGenerator(Gdx.files.internal("core\\assets\\Menu\\BerlinSansFBRegular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter ftfp = new FreeTypeFontGenerator.FreeTypeFontParameter();
        ftfp.size = 55;
        this.bitmapFont = ftfg.generateFont(ftfp);
        ftfg.dispose();
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
        this.password.setPasswordCharacter('*');
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

        loginButtonInvisible.addListener(new ClickListener(Input.Buttons.LEFT) {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                new LoginClient(username.getText(), password.getText(), LogInScreen.this);
            }
        });
    }

    @Override
    public void show() {
        // never used part 2
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        logInScreen();

        // draw stage
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        // never used part3
    }

    @Override
    public void pause() {
        // nooit gebruikt deel 23
    }

    @Override
    public void resume() {
            // noooooooit!!!
    }

    @Override
    public void hide() {
        /// GAST
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

    public void loginPassed(Player player)
    {
        menuSound = Gdx.audio.newSound(Gdx.files.internal("core/assets/gas.ogg"));
        menuSound.play();
        game.setScreen(new MenuScreen(game, player));
        dispose();
    }

    public void loginFailed()
    {
        GDXButtonDialog bDialog = dialogs.newDialog(GDXButtonDialog.class);
        bDialog.setTitle("Oops");
        bDialog.setMessage("Login failed. Please try again");
        bDialog.addButton("Ok");
        bDialog.build().show();
    }
}
