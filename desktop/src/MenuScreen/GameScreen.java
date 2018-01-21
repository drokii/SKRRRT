package MenuScreen;

import Menu.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.mygdx.game.Gameplay.GameWorld;
import com.mygdx.game.RaceGame;

import java.io.IOException;

public class  GameScreen implements Screen{
    RaceGame game;
    Player currentPlayer;
    GameWorld gameWorld;

    public GameScreen(RaceGame game, Player player, GameWorld gameWorld) throws IOException {
        this.game = game;
        this.currentPlayer = player;
        this.gameWorld = gameWorld;
    }

    @Override
    public void show() {
        // G niet leuk dit
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gameWorld.render();
    }

    @Override
    public void resize(int width, int height) {
        // kanker saai
    }

    @Override
    public void pause() {
        // never usssssed
    }

    @Override
    public void resume() {
            /// nooit gebruikkktt
    }

    @Override
    public void hide() {
        /// gewoon niet
    }

    @Override
    public void dispose() {
        gameWorld.dispose();
    }
}
