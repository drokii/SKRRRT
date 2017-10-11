package com.mygdx.game;

import Menu.MenuScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class RaceGame extends Game {

    public SpriteBatch batch;

    @Override
    public void create() {
        batch = new SpriteBatch();
        this.setScreen(new MenuScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }
}
