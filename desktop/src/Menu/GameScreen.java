package Menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Gameplay.Car;
import com.mygdx.game.Gameplay.GameWorld;
import com.mygdx.game.Map.Map;
import com.mygdx.game.RaceGame;

import java.util.ArrayList;

public class GameScreen implements Screen{

    RaceGame game;
    GameWorld gameWorld;

    final float PIXELS_TO_METERS = 100f;

    public GameScreen(RaceGame game){
        this.game = game;
        gameWorld = new GameWorld(game);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gameWorld.render();
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
        gameWorld.dispose();
    }
}
