package com.mygdx.game;

import Menu.GameScreen;
import Menu.LogInScreen;
import com.badlogic.gdx.Game;


public class  RaceGame extends Game{
    @Override
    public void create() {
        this.setScreen(new GameScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
