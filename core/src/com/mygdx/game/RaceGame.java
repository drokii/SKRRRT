package com.mygdx.game;

import Menu.LogInScreen;
import com.badlogic.gdx.Game;

public class  RaceGame extends Game{
    @Override
    public void create() {
        this.setScreen(new LogInScreen(this));
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
