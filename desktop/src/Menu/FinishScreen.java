package Menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.mygdx.game.RaceGame;

import java.util.ArrayList;

public class FinishScreen implements Screen {
    private final int BACK_BUTTON_X = (Gdx.graphics.getWidth()/2) - (195/2);
    private final int BACK_BUTTON_Y = 200;

    private RaceGame game;

    private SpriteBatch batch;
    private Texture title;
    private Texture backButton;
    private Texture backButtonActive;
    private Texture yellowCar;
    private BitmapFont font;
    private ArrayList<String> finishTimes;

    public FinishScreen(RaceGame game, ArrayList<String> results){
        this.game = game;
        this.batch = new SpriteBatch();
        this.backButton =  new Texture("core\\assets\\Menu\\BackButton.png");
        this.backButtonActive = new Texture("core\\assets\\Menu\\BackButtonActive.png");
        this.title = new Texture("core\\assets\\Menu\\Skrrrt.png");
        this.yellowCar = new Texture("core\\assets\\Menu\\YellowCarBrakes.png");
        this.font = new BitmapFont();
        finishTimes = results;


        // getting the font
        FreeTypeFontGenerator FTFG = new FreeTypeFontGenerator(Gdx.files.internal("core\\assets\\Menu\\BerlinSansFBRegular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter FTFP = new FreeTypeFontGenerator.FreeTypeFontParameter();
        FTFP.size = 55;
        this.font = FTFG.generateFont(FTFP);
        FTFG.dispose();
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        finishScreen();
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

    private void finishScreen(){
        batch.begin();

        // draw title
        batch.draw(title, (1440/2) - (665/2), 670);

        // draw results
        int y = 500;

        for (String time:  finishTimes) {
            GlyphLayout layout = new GlyphLayout(font, time);
            float x = (1440-layout.width) / 2;

            font.draw(batch,time, x, y);
            y -= 50;
        }

        // draw backButton
        if(Gdx.input.getX() > BACK_BUTTON_X && Gdx.input.getX() < (BACK_BUTTON_X + backButton.getWidth())
                && (Gdx.graphics.getHeight() - Gdx.input.getY()) > BACK_BUTTON_Y && (Gdx.graphics.getHeight() - Gdx.input.getY()) < (BACK_BUTTON_Y + backButton.getHeight())) {
            batch.draw(backButtonActive, BACK_BUTTON_X, BACK_BUTTON_Y);
        } else {
            batch.draw(backButton, BACK_BUTTON_X, BACK_BUTTON_Y);
        }

        // draw yellow car
        batch.draw(yellowCar, (1440 - 398) - 100, 0);

        batch.end();
    }

    private void isTouched(){
        // Back button
        if(Gdx.input.getX() > BACK_BUTTON_X && Gdx.input.getX() < (BACK_BUTTON_X + backButton.getWidth())
                && (Gdx.graphics.getHeight() - Gdx.input.getY()) > BACK_BUTTON_Y && (Gdx.graphics.getHeight() - Gdx.input.getY()) < (BACK_BUTTON_Y + backButton.getHeight())) {
            // exit game
            if(Gdx.input.isTouched()){
                game.setScreen(new MenuScreen(game));
            }
        }
    }
}
