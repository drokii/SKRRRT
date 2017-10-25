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
import com.mygdx.game.Map.Map;
import com.mygdx.game.RaceGame;

import java.util.ArrayList;

public class GameScreen implements Screen{

    RaceGame game;
    private ArrayList<Car> carList;
    private Car car;
    private Map map;
    private OrthographicCamera camera;
    private World world;
    private Sound sound;
    private Sound skrrrt;
    private StatisticsHandler stats;

    public GameScreen(RaceGame game){
        this.game = game;
        carList = new ArrayList<Car>();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800,800);
        world = new World(new Vector2(0, 0), true);
        car = new Car(camera, world); // SinglePlayer Only
        carList.add(car);
        map = new Map(car, camera);
        sound = Gdx.audio.newSound(Gdx.files.internal("core/assets/dejavu.ogg"));
        sound.play();
        stats = new StatisticsHandler(carList);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        world.step( 1f / 60f, 6, 2);
        map.render();
        car.render();
        stats.render();
        if (car.getIsOnFinishLine()){
            game.setScreen(new FinishScreen(game, stats.getFinishLogList()));
        }
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
        car.dispose();
        map.dispose();
        sound.stop();
    }
}
