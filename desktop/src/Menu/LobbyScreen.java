package Menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.RaceGame;

public class LobbyScreen implements Screen {

    private RaceGame game;

    private SpriteBatch batch;
    private Texture title;
    private Texture yellowCar;

    public LobbyScreen(RaceGame game){
        this.game = game;
        this.batch = new SpriteBatch();
        this.title = new Texture("core\\assets\\Skrrrt.png");
        this.yellowCar = new Texture("core\\assets\\YellowCarBrakes.png");
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        lobbyScreen();
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

    private void lobbyScreen(){
        batch.begin();
        batch.draw(title, 0, 670);

        batch.draw(yellowCar, 0, 0);
        batch.end();
    }
}
