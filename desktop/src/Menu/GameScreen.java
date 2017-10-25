package Menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Gameplay.Car;
import com.mygdx.game.Map.Map;
import com.mygdx.game.RaceGame;

public class GameScreen implements Screen {

    RaceGame game;

    private Car car;
    private Map map;
    private OrthographicCamera camera;
    private World world;

    public GameScreen(RaceGame game){
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 600,600);
        world = new World(new Vector2(0, 0), true);
        car = new Car(camera, world);
        map = new Map(car, camera);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        world.step(1f/60f, 6, 2);
        map.render();
        car.render();
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
}
